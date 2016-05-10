import com.sun.javafx.geom.transform.Translate2D;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import static javafx.animation.Animation.Status.STOPPED;

public class Animations  {

    public static void cardToHand(String indeks, Kaart card, Mangija mangija) {
        Image cardImage = card.getPilt();
        ImageView iv = new ImageView();
        iv.setImage(cardImage);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        TranslateTransition translateCard = new TranslateTransition(Duration.millis(1000), iv);

        Node cardNode = Gamescenes.getBattleScenePane().lookup(indeks);

        Bounds cardBounds = cardNode.localToScene(cardNode.getBoundsInLocal());
        Bounds deckBounds = cardNode.localToScene(cardNode.getBoundsInLocal());

        translateCard.setFromX(300);
        translateCard.setFromY(450);
        translateDeck1.setToX();
        translateDeck1.setToY(cardBounds1.getMinY() - 24);






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
                        Gamescenes.setLabelText("Cards in deck: " + Integer.toString(Manguvaljak.currentPlayer.getMangijaDeck().size()));
                    }
                });
            }      });

        translateDeck2.setToX(cardBounds2.getMinX() - 110);
        translateDeck2.setToY(cardBounds2.getMinY() - 24);

        Gamescenes.getBattleScenePane().getChildren().addAll(iv1,iv2);

        ParallelTransition parallelDeck = new ParallelTransition(translateDeck1,translateDeck2);

        //Deck draw animation end
        //Opponent and current player hand draw animation begins


    }
}
