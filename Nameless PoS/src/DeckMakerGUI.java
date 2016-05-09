/**
 * Created by aleksander on 09-May-16.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

public class DeckMakerGUI extends Application {
    private TableView table = new TableView();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene stseen = new Scene(root);
        Group topPane = new Group();
        Group leftPane = new Group();
        Group rightPane = new Group();
        Group bottomPane = new Group();
        bottomPane.getChildren().addAll(leftPane,rightPane);
        root.getChildren().addAll(topPane, bottomPane);
        Label title = new Label("Deck Maker");
        topPane.getChildren().add(title);
        TableColumn removeFromDeck = new TableColumn("Remove");
        TableColumn cardName = new TableColumn("Card name");
        TableColumn nrInDeck = new TableColumn("Amount");
        table.getColumns().addAll(removeFromDeck,cardName,nrInDeck);
        leftPane.getChildren().add(table);
        primaryStage.show();

    }
}
