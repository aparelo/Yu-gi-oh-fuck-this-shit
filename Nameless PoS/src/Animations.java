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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.HashMap;

import static javafx.animation.Animation.Status.STOPPED;

public class Animations  {

    public static void cardToHand(Mangija mangija) throws InterruptedException {

        if (mangija.equals(Manguvaljak.currentPlayer)) {
            Kaart card = mangija.getMangijaDeck().get(0);
            Image cardImage = card.getFrontPicture();
            ImageView iv = new ImageView();
            iv.setImage(cardImage);
            iv.setPreserveRatio(true);
            iv.setSmooth(true);
            iv.setCache(true);
            iv.setVisible(false);


            TranslateTransition translateCard = new TranslateTransition(Duration.millis(2000), iv);

            String indeks = "";
            Kaart removableKaart = new EmptyCard();
            for (Kaart kaart : mangija.getHandMap().keySet()) {
                if (kaart.toString().equals("Empty")) {
                    indeks = mangija.getHandMap().get(kaart);
                    removableKaart = kaart;
                }
            }
            mangija.getHandMap().put(card,indeks);
            mangija.getHandMap().remove(removableKaart);

            Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);

            Bounds cardBounds = cardNode.localToScene(cardNode.getBoundsInLocal());

            Gamescenes.getBattleScenePane().lookup("#" + indeks).setId("NA" + indeks);

            translateCard.setFromX(mangija.getDeckX());
            translateCard.setFromY(mangija.getDeckY());
            translateCard.setToX(cardBounds.getMinX() - 110);
            translateCard.setToY(cardBounds.getMinY() - 24);


            iv.setId(indeks);
                iv.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e)   {
                        Gamescenes.setLabelText(card.toInfo());
                    }
                });
                iv.setOnMouseExited(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e)   {
                        Gamescenes.setLabelText("");
                    }
                });
                iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e)   {
                        Manguvaljak.kaartLauale(card, mangija);
                    }
                });




            Gamescenes.getBattleScenePane().getChildren().add(iv);

            PauseTransition pause = new PauseTransition(Duration.millis(1200));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)   {
                    iv.setVisible(true);
                }
            });
            SequentialTransition seqTransition = new SequentialTransition (pause,translateCard);
            seqTransition.play();
        }
        else {
            Kaart card = mangija.getMangijaDeck().get(0);
            Image cardImage = card.getBackPicture();
            ImageView iv = new ImageView();
            iv.setImage(cardImage);
            iv.setPreserveRatio(true);
            iv.setSmooth(true);
            iv.setCache(true);
            iv.setVisible(false);


            TranslateTransition translateCard = new TranslateTransition(Duration.millis(2000), iv);

            String indeks = "";
            Kaart removableKaart = new EmptyCard();
            for (Kaart kaart : mangija.getHandMap().keySet()) {
                if (kaart.toString().equals("Empty")) {
                    indeks = mangija.getHandMap().get(kaart);
                    removableKaart = kaart;
                }
            }
            mangija.getHandMap().put(card,indeks);
            mangija.getHandMap().remove(removableKaart);

            Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + indeks);

            Bounds cardBounds = cardNode.localToScene(cardNode.getBoundsInLocal());

            Gamescenes.getBattleScenePane().lookup("#" + indeks).setId("NA" + indeks);

            translateCard.setFromX(mangija.getDeckX());
            translateCard.setFromY(mangija.getDeckY());
            translateCard.setToX(cardBounds.getMinX() - 110);
            translateCard.setToY(cardBounds.getMinY() - 24);

            iv.setId(indeks);

            iv.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e)   {
                    Gamescenes.setLabelText(card.toInfo());
                }
            });
            iv.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e)   {
                    Gamescenes.setLabelText("");
                }
            });

            Gamescenes.getBattleScenePane().getChildren().add(iv);

            PauseTransition pause = new PauseTransition(Duration.millis(1200));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)   {
                    iv.setVisible(true);
                }
            });
            SequentialTransition seqTransition = new SequentialTransition (pause,translateCard);
            seqTransition.play();

            final String finalIndeks = indeks;

            seqTransition.setOnFinished(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)   {
                    iv.setImage(card.getFrontPicture());
                    iv.setVisible(false);
                    Node cardBackground = Gamescenes.getBattleScenePane().lookup("##" + finalIndeks);
                    System.out.println(cardBackground);
                    cardBackground.setVisible(true);
                }

        });

    }}

    public static String getHeroFieldIndex(Mangija mangija) {
        HashMap<Kaart, String> cardMap = mangija.getHeroMap();
        for (Kaart kaart : cardMap.keySet()) {
            System.out.println(cardMap.keySet());
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
        System.out.println(cardIndeks);
        String fieldIndeks;

        Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + cardIndeks);
        if (card.getTyyp().equals("Hero")) {
            fieldIndeks = getHeroFieldIndex(mangija);
            mangija.getHandMap().values().remove(cardIndeks);
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
            mangija.getSpellMap().put(card,fieldIndeks);
            cardNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e)   {
                    Manguvaljak.useSpell(card);
                }
            });
        }
        Node fieldNode = Gamescenes.getBattleScenePane().lookup("#" +fieldIndeks);



        Bounds cardBounds = cardNode.localToScene(cardNode.getBoundsInLocal());
        Bounds fieldBounds = fieldNode.localToScene(fieldNode.getBoundsInLocal());

        TranslateTransition translateCard = new TranslateTransition(Duration.millis(2000), cardNode);

        translateCard.setFromX(cardBounds.getMinX() - 110);
        translateCard.setFromY(cardBounds.getMinY() - 24);
        translateCard.setToX(fieldBounds.getMinX() - 110);
        translateCard.setToY(fieldBounds.getMinY() - 24);

        cardNode.setId(fieldIndeks);
        System.out.println("CardOnField - Card node Index:" + fieldIndeks);
        fieldNode.setId("NA" + fieldIndeks);
        System.out.println("CardOnField - Field Node: " + fieldNode);

        translateCard.play();

    }

    public static void cardToGraveyard(Kaart card, Mangija mangija) {
        String cardIndeks = getPositionIndex(card, mangija);
        System.out.println(cardIndeks);

        Node cardNode = Gamescenes.getBattleScenePane().lookup("#" + cardIndeks);
        System.out.println("Graveyard card index: " + cardIndeks);

        Node fieldNode = Gamescenes.getBattleScenePane().lookup("#NA" + cardIndeks);

        Bounds cardBounds = cardNode.localToScene(cardNode.getBoundsInLocal());

        TranslateTransition translateCard = new TranslateTransition(Duration.millis(2000), cardNode);

        translateCard.setFromX(cardBounds.getMinX() - 110);
        translateCard.setFromY(cardBounds.getMinY() - 24);
        translateCard.setToX(mangija.getGraveYardX());
        translateCard.setToY(mangija.getGraveYardY());

        cardNode.setId("Dead");
        System.out.println("Field node: " + fieldNode);
        fieldNode.setId("#" + cardIndeks);

        translateCard.play();

    }


    public static void startShuffle() {
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
                Gamescenes.getBattleScenePane().lookup("#33").setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        try {
                            Kaik.uusKaik();
                            Gamescenes.setLabelText("Cards in deck: " + Integer.toString(Manguvaljak.currentPlayer.getMangijaDeck().size()));
                        }
                        catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }      });

        translateDeck2.setToX(cardBounds2.getMinX() - 110);
        translateDeck2.setToY(cardBounds2.getMinY() - 24);

        Gamescenes.getBattleScenePane().getChildren().addAll(iv1,iv2);

        ParallelTransition parallelDeck = new ParallelTransition(translateDeck1,translateDeck2);

        parallelDeck.play();

        //Deck draw animation end

    }

    public static void flipDown(Mangija mangija) {
       // PauseTransition pause = new PauseTransition(Duration.millis(5000));

        for (String indeks : mangija.getHandMap().values()) {
            System.out.println(indeks);

            Node card = Gamescenes.getBattleScenePane().lookup("#" + indeks);

            Node cardBackGround = Gamescenes.getBattleScenePane().lookup("##" + indeks);
            System.out.println(cardBackGround);


            ScaleTransition stHideFront = new ScaleTransition(Duration.millis(1000), card);
            stHideFront.setFromX(1);
            stHideFront.setToX(0);

            ScaleTransition stShowBack = new ScaleTransition(Duration.millis(1000), cardBackGround);
            stShowBack.setFromX(0);
            stShowBack.setToX(1);

            stHideFront.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    cardBackGround.setScaleX(0);
                    cardBackGround.setVisible(true);
                    stShowBack.play();
                }
            });

            stHideFront.play();

        }
    }

    public static void flipUp(Mangija mangija) {

        for (String indeks : mangija.getHandMap().values()) {

            Node card = Gamescenes.getBattleScenePane().lookup("#" + indeks);

            Node cardBackGround = Gamescenes.getBattleScenePane().lookup("##" + indeks);

            ScaleTransition stHideBack = new ScaleTransition(Duration.millis(1000), cardBackGround);
            stHideBack.setFromX(1);
            stHideBack.setToX(0);

            ScaleTransition stShowFront = new ScaleTransition(Duration.millis(1000), card);
            stShowFront.setFromX(0);
            stShowFront.setToX(1);

            stHideBack.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    card.setScaleX(0);
                    card.setVisible(true);
                    stShowFront.play();
                }
            });
            System.out.println(Gamescenes.getBattleScenePane().getChildren());
            stHideBack.play();

    }
}}
