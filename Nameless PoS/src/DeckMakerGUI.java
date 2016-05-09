/**
 * Created by aleksander on 09-May-16.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class DeckMakerGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Group topPane = new Group();
        Group leftPane = new Group();
        Group rightPane = new Group();
        Group bottomPane = new Group();
        bottomPane.getChildren().add(leftPane);
        bottomPane.getChildren().add(rightPane);
        root.getChildren().add(topPane);
        root.getChildren().add(bottomPane);
        Label title = new Label("Deck Maker");
        topPane.getChildren().add(title);
        TableColumn removeFromDeck = new TableColumn("Remove");
        TableColumn cardName = new TableColumn("Card name");

    }
}
