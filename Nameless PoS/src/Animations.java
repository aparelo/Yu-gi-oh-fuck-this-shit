import com.sun.javafx.geom.transform.Translate2D;
import javafx.animation.*;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.HashMap;

import static javafx.animation.Animation.Status.STOPPED;

public class Animations  {

    public static void cardToHand(Mangija mangija) throws InterruptedException {

            Kaart card = mangija.getMangijaDeck().get(0);
            Image cardImage = card.getFrontPicture();
            ImageView iv = new ImageView();
        if (mangija.equals(Manguvaljak.currentPlayer)) {
            iv.setImage(cardImage);
        }
        else {
            iv.setImage(Gamescenes.cardBackgroundImage);
            iv.setMouseTransparent(true);
        }
            iv.setPreserveRatio(true);
            iv.setSmooth(true);
            iv.setCache(true);
            iv.setVisible(false);

            TranslateTransition translateCard = new TranslateTransition(Duration.millis(1400), iv);

            String indeks = ""; // If any card in a player's hand is empty, the index for it is chosen, the first card of the deck added to the hand and the EmptyCard removed.
            Kaart removableKaart = new EmptyCard();
            for (Kaart kaart : mangija.getHandMap().keySet()) {
                if (kaart.toString().equals("Empty")) {
                    indeks = mangija.getHandMap().get(kaart);
                    removableKaart = kaart;
                }
            }

            mangija.getHandMap().remove(removableKaart);
            mangija.getHandMap().put(card,indeks);


            Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);  // The original node in the grid, where the card is supposed to go

            Bounds cardBounds = cardNode.localToScene(cardNode.getBoundsInLocal()); // The position of the card on the field

            Gamescenes.getBattleScenePane().lookup("#" + indeks).setId("Back" + indeks);  // The node, which is now behind the card node, is given the index "Back" + the number corresponding to the grid system
            iv.setId(indeks); // The card now has the original index



            translateCard.setFromX(mangija.getDeckX());
            translateCard.setFromY(mangija.getDeckY());

        if (cardBounds.getWidth() == 0) {
            translateCard.setToX(cardBounds.getMinX() - 140);
        }
        else {
            translateCard.setToX(cardBounds.getMinX() - 110);
        }

            translateCard.setToY(cardBounds.getMinY() - 24);



                iv.setOnMouseEntered(new EventHandler<MouseEvent>() { // On mouse entered, the info of the card is displayed on the label in the middle of the battlefield
                    public void handle(MouseEvent e)   {
                        Gamescenes.setLabelText(card.toInfo());
                    }
                });
                iv.setOnMouseExited(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e)   {
                        Gamescenes.setLabelText("");
                    }
                });

            iv.setOnMouseClicked(new EventHandler<MouseEvent>() { // On the current player, if the mouse is clicked, a method is called for adding a card to the field
                public void handle(MouseEvent e) {
                    Manguvaljak.kaartLauale(card, mangija);
                }
            });



            Gamescenes.getBattleScenePane().getChildren().add(iv);

            final String finalIndeks = indeks;

            PauseTransition pause = new PauseTransition(Duration.millis(1200));

            pause.setOnFinished(new EventHandler<ActionEvent>() {  // The pause is needed for the original Deck shuffle to complete first
                public void handle(ActionEvent e)   {
                    iv.setVisible(true);  // After the pause, the cards become visible and first appear on the deck

                }
            });

            SequentialTransition seqTransition = new SequentialTransition (pause,translateCard);
        if (mangija.equals(Manguvaljak.currentOpponent)) {
            seqTransition.setOnFinished(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)   {
                    iv.setImage(card.getFrontPicture());
                    iv.setVisible(false);
                    iv.setMouseTransparent(false);
                    Node cardBackgroundNode = Gamescenes.getBattleScenePane().lookup("#Back" + finalIndeks);
                    cardBackgroundNode.setVisible(true);
                }

            });
        }
            seqTransition.play();

    }

    public static String getHeroFieldIndex(Mangija mangija) {
        HashMap<Kaart, String> cardMap = mangija.getHeroMap();
        for (Kaart kaart : cardMap.keySet()) {
            if (kaart.toString().equals("Empty")) {
                return cardMap.get(kaart);
            }
        }
        return null;
    }

    public static String getSpellFieldIndex(Mangija mangija) {
        HashMap<Kaart, String> cardMap = mangija.getSpellMap();
        for (Kaart kaart : cardMap.keySet()) {
            if (kaart.toString().equals("Empty")) {
                return cardMap.get(kaart);
            }
        }
        return null;
    }

    public static String getPositionIndex(Kaart card, Mangija mangija) {
        if (mangija.getHandMap().containsKey(card)) {
            return mangija.getHandMap().get(card);
        }
        else if (mangija.getHeroMap().containsKey(card)) {
            return mangija.getHeroMap().get(card);
        }
        else {
            return mangija.getSpellMap().get(card);
        }
        }

    public static void cardToField(Kaart card, Mangija mangija) {
        String cardIndeks = getPositionIndex(card, mangija);

        String fieldIndeks;

        Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + cardIndeks);

        if (card.getTyyp().equals("Hero")) {
            fieldIndeks = getHeroFieldIndex(mangija);
            mangija.getHandMap().values().remove(cardIndeks);
            mangija.getHandMap().put(new EmptyCard(),cardIndeks);
            mangija.getHeroMap().values().remove(fieldIndeks);
            mangija.getHeroMap().put(card,fieldIndeks);
            cardNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e)   {
                    Manguvaljak.attack(card);
                }
            });

        }
        else {
            fieldIndeks = getSpellFieldIndex(mangija);
            mangija.getHandMap().values().remove(cardIndeks);
            mangija.getHandMap().put(new EmptyCard(),cardIndeks);
            mangija.getSpellMap().values().remove(fieldIndeks);
            mangija.getSpellMap().put(card,fieldIndeks);
            cardNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e)   {
                    Manguvaljak.useSpell(card);
                }
            });
        }

        Node cardBackgroundNode = Gamescenes.getBattleScenePane().lookup("#Back" + cardIndeks);

        Node fieldNode = Gamescenes.getBattleScenePane().lookup("#" +fieldIndeks);
        System.out.println(cardBackgroundNode);
        System.out.println(fieldNode);

        Bounds cardBounds = cardNode.localToScene(cardNode.getBoundsInLocal());
        Bounds fieldBounds = fieldNode.localToScene(fieldNode.getBoundsInLocal());

        TranslateTransition translateCard = new TranslateTransition(Duration.millis(1400), cardNode);

        translateCard.setFromX(cardBounds.getMinX() - 110);
        translateCard.setFromY(cardBounds.getMinY() - 24);
        translateCard.setToX(fieldBounds.getMinX() - 110);
        translateCard.setToY(fieldBounds.getMinY() - 24);

        cardNode.setId(fieldIndeks);
        fieldNode.setId("Back" + fieldIndeks);
        cardBackgroundNode.setId(cardIndeks);


        translateCard.play();

    }

    public static void cardToGraveyard(Kaart card, Mangija mangija) {
        String cardIndeks = getPositionIndex(card, mangija);

        Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + cardIndeks);
        Node fieldNode = Gamescenes.getBattleScenePane().lookup("#Back" + cardIndeks);

        if (card.getTyyp().equals("Hero")) {
            mangija.getHeroMap().values().remove(cardIndeks);
            mangija.getHeroMap().put(new EmptyCard(),cardIndeks);
        }

        else {
            mangija.getSpellMap().values().remove(cardIndeks);
            mangija.getSpellMap().put(new EmptyCard(),cardIndeks);
        }

        Bounds cardBounds = cardNode.localToScene(cardNode.getBoundsInLocal());

        TranslateTransition translateCard = new TranslateTransition(Duration.millis(1400), cardNode);

        translateCard.setFromX(cardBounds.getMinX() - 110);
        translateCard.setFromY(cardBounds.getMinY() - 24);
        translateCard.setToX(mangija.getGraveYardX());
        translateCard.setToY(mangija.getGraveYardY());

        cardNode.setId("Dead");
        fieldNode.setId(cardIndeks);

        cardNode.setOnMouseClicked(null); // When a card, which is in the graveyard, is clicked, nothing will happen. Information is still visible by MouseOnEntered

        translateCard.play();

    }


    public static void startShuffle() throws InterruptedException {

        Image deckImage1 = new Image("\\img\\CardBackground.jpg");
        ImageView iv1 = new ImageView();
        iv1.setImage(deckImage1);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setCache(true);

        Image deckImage2 = new Image("\\img\\CardBackground.jpg");
        ImageView iv2 = new ImageView();
        iv2.setImage(deckImage2);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);




        TranslateTransition translateDeck1 = new TranslateTransition(Duration.millis(1000), iv1);
        TranslateTransition translateDeck2 = new TranslateTransition(Duration.millis(1000), iv2);

        Node deck1 = Gamescenes.getBattleScenePane().lookup("#33");
        Node deck2 = Gamescenes.getBattleScenePane().lookup("#30");

        Bounds cardBounds1 = deck1.localToScene(deck1.getBoundsInLocal());
        Bounds cardBounds2 = deck2.localToScene(deck2.getBoundsInLocal());

        translateDeck1.setFromX(300);
        translateDeck1.setFromY(450);
        translateDeck2.setFromX(300);
        translateDeck2.setFromY(450);
        translateDeck1.setToX(cardBounds1.getMinX() - 110);
        translateDeck1.setToY(cardBounds1.getMinY() - 24);

        translateDeck1.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)   {
                iv1.setMouseTransparent(true);
                iv2.setMouseTransparent(true);
                Gamescenes.getBattleScenePane().lookup("#33").setOnMouseEntered(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                            Gamescenes.setLabelText("Cards in deck: " + Integer.toString(Manguvaljak.currentPlayer.getMangijaDeck().size()));
                        }
                });
                Gamescenes.getBattleScenePane().lookup("#30").setOnMouseEntered(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        Gamescenes.setLabelText("Cards in deck: " + Integer.toString(Manguvaljak.currentOpponent.getMangijaDeck().size()));
                        //Gamescenes.getBattleScenePane().lookup("#30").setVisible(false);
                    }
                });
            }});

        translateDeck2.setToX(cardBounds2.getMinX() - 110);
        translateDeck2.setToY(cardBounds2.getMinY() - 24);

        Gamescenes.getBattleScenePane().getChildren().addAll(iv1,iv2);

        ParallelTransition parallelDeck = new ParallelTransition(translateDeck1,translateDeck2);

        parallelDeck.play();
        parallelDeck.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle (ActionEvent e)  {
                try {
                    Kaik.handShuffle();
                }
                catch (InterruptedException d) {
                    throw new RuntimeException(d);
                }
        }});

        //Deck draw animation end

    }

    public static void flipDown(Mangija mangija) {

        PauseTransition pause;
        if (Kaik.needCards) {
            pause = new PauseTransition(Duration.millis(3000));
        }
        else {
            pause = new PauseTransition(Duration.millis(100));
        }


        for (String indeks : mangija.getHandMap().values()) {

            Node card = Gamescenes.getBattleScenePane().lookup("#" + indeks);

            Node cardBackGround = Gamescenes.getBattleScenePane().lookup("#Back" + indeks);

            ScaleTransition stHideFront = new ScaleTransition(Duration.millis(1500), card);
            stHideFront.setFromX(1);
            stHideFront.setToX(0);

            cardBackGround.setScaleX(0);

            ScaleTransition stShowBack = new ScaleTransition(Duration.millis(1500), cardBackGround);
            stShowBack.setFromX(0);
            stShowBack.setToX(1);

            stHideFront.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    stShowBack.play();
                    cardBackGround.setVisible(true);
                }
            });


           /* RotateTransition rt = new RotateTransition(Duration.millis(1200), card);
            rt.setAxis(Rotate.Y_AXIS);
            rt.setFromAngle(0);
            rt.setToAngle(90);

            RotateTransition backgroundUp = new RotateTransition(Duration.millis(1200), cardBackGround);
            backgroundUp.setAxis(Rotate.Y_AXIS);
            backgroundUp.setFromAngle(90);
            backgroundUp.setToAngle(0);


            rt.setOnFinished((ActionEvent event) -> {
                backgroundUp.play();
                cardBackGround.setVisible(true);
            });*/


            SequentialTransition seqT = new SequentialTransition(pause,stHideFront);
            seqT.play();

        }
    }

    public static void flipUp(Mangija mangija) {
        PauseTransition pause;

        if (Kaik.needCards) {  // If cards need to be dealt to the player's hand, the pause time is increased to let the draw animation finish
            pause = new PauseTransition(Duration.millis(3000));
        } else {
            pause = new PauseTransition(Duration.millis(100));
        }

        for (String indeks : mangija.getHandMap().values()) {

            Node card = Gamescenes.getBattleScenePane().lookup("#" + indeks);

            Node cardBackGround = Gamescenes.getBattleScenePane().lookup("#Back" + indeks);

            ScaleTransition stHideBack = new ScaleTransition(Duration.millis(1500), cardBackGround);
            stHideBack.setFromX(1);
            stHideBack.setToX(0);

            cardBackGround.setScaleX(0);

            ScaleTransition stShowFront = new ScaleTransition(Duration.millis(1500), card);
            stShowFront.setFromX(0);
            stShowFront.setToX(1);

            stHideBack.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    stShowFront.play();
                    card.setVisible(true);
                }
            });

            /*RotateTransition rt = new RotateTransition(Duration.millis(1200), cardBackGround);
            rt.setAxis(Rotate.Y_AXIS);
            rt.setFromAngle(0);
            rt.setToAngle(90);
            RotateTransition cardUp = new RotateTransition(Duration.millis(1200), card);
            cardUp.setAxis(Rotate.Y_AXIS);
            cardUp.setFromAngle(90);
            cardUp.setToAngle(0);
            


            rt.setOnFinished((ActionEvent event) -> {
                cardUp.play();
                card.setVisible(true);
            });*/

            SequentialTransition seqT = new SequentialTransition(pause, stHideBack);
            seqT.play();
        }
    }



    public static void makeCardsUnclickable(Mangija mangija) {
        HashMap<Kaart, String> heroMap = mangija.getHeroMap();
        HashMap<Kaart, String> spellMap = mangija.getSpellMap();
        for (Kaart kaart : heroMap.keySet()) {
            if (!((kaart.toString()).equals("Empty"))) {
                String indeks = heroMap.get(kaart);
                Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);
                cardNode.setOnMouseClicked(null);
            }
        }
        for (Kaart kaart : spellMap.keySet()) {
            if (!((kaart.toString()).equals("Empty"))) {
                String indeks = spellMap.get(kaart);
                Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);
                cardNode.setOnMouseClicked(null);
            }
        }

    }

    public static void makeCardsClickable(Mangija mangija) {
        HashMap<Kaart, String> heroMap = mangija.getHeroMap();
        HashMap<Kaart, String> spellMap = mangija.getSpellMap();
        for (Kaart kaart : heroMap.keySet()) {
            if (!kaart.toString().equals("Empty")) {
                String indeks = heroMap.get(kaart);
                Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);
                cardNode.setOnMouseClicked(MouseEvent -> Manguvaljak.attack(kaart));
            }
        }
        for (Kaart kaart : spellMap.keySet()) {
            if (!kaart.toString().equals("Empty")) {
                String indeks = spellMap.get(kaart);
                Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);
                cardNode.setOnMouseClicked(MouseEvent -> Manguvaljak.useSpell(kaart));
            }
        }

    }

    public static void makeCardsAttackable(Mangija mangija) {
        HashMap<Kaart, String> heroMap = mangija.getHeroMap();
        for (Kaart kaart : heroMap.keySet()) {
            if (!kaart.toString().equals("Empty")) {
                String indeks = heroMap.get(kaart);
                Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);
                cardNode.setOnMouseClicked(MouseEvent -> {
                    Manguvaljak.attackedCard = cardNode;
                });

            }
        }
    }
}
