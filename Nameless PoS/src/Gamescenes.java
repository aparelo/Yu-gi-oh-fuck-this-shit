

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.File;

public class Gamescenes  {
    private static Scene deckMakerScene;
    private static Scene deckMakerMenuScene;
    private static Scene mainMenuScene;
    private static Scene battleScene;
    private static Scene battleMenuScene;
    public  static String labelText;

    public static String getLabelText() {
        return labelText;
    }

    public static void setLabelText(String labelText) {
        Gamescenes.labelText = labelText;
    }

    public static Scene getDeckMakerScene() {
        return deckMakerScene;
    }

    public static Scene getDeckMakerMenuScene() {
        return deckMakerMenuScene;
    }

    public static Scene getMainMenuScene() {
        return mainMenuScene;
    }

    public static Scene getBattleScene() {
        return battleScene;
    }

    public static Scene getBattleMenuScene() {
        return battleMenuScene;
    }

    public static void setDeckMakerMenuScene(int x, int y, Stage primaryStage) {
        primaryStage.setTitle("Deck Maker Menu");
        GridPane grid = new GridPane();
        Scene deckMenuScene = new Scene(grid, x*0.5, y*0.5);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button newDeck = new Button("Make a new deck");
        HBox hbNewDeck = new HBox(10);
        hbNewDeck.setAlignment(Pos.CENTER);
        newDeck.setMinWidth(150);
        newDeck.setPrefWidth(200);
        hbNewDeck.getChildren().add(newDeck);
        grid.add(hbNewDeck, 0, 0);

        Button manageDeck = new Button("Manage an existing deck");
        HBox hbManageDeck = new HBox(10);
        hbManageDeck.setAlignment(Pos.CENTER);
        manageDeck.setMinWidth(150);
        manageDeck.setPrefWidth(200);
        hbManageDeck.getChildren().add(manageDeck);
        grid.add(hbManageDeck, 0, 1);

        deckMenuScene.getStylesheets().add(Gamescenes.class.getResource("/GUI.css").toExternalForm());
        Gamescenes.deckMakerMenuScene = deckMenuScene;

    }

    public static void setBattleMenuScene(int x, int y, Stage primaryStage) throws  Exception {
        GridPane grid = new GridPane();
        Scene battleMenuScene = new Scene(grid, x, y);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label player1Label = new Label("Player 1 name: ");
        Label player2Label = new Label("Player 2 name: ");
        TextField player1Text = new TextField();
        TextField player2Text = new TextField();

        MenuButton deck1 = new MenuButton("Choose your deck");
        deck1.setPrefWidth(300);
        MenuButton deck2 = new MenuButton("Choose your deck");
        deck2.setPrefWidth(300);

        File folder = new File("Nameless Pos\\Decks\\");  // Lisatakse MenuButtoni menüüvalikusse "Decks" folderis olevad deckide nimed, menüünupu vajutamisel seatakse vastava Decki nimi Manguvaljaku isendivalja väärtuseks
        File[] listOfFiles = folder.listFiles();
        for (File fail : listOfFiles) {
            String nimi = fail.getName().replaceAll(".txt","");
            MenuItem nimi1 = new MenuItem(nimi);
            nimi1.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)   {
                  Manguvaljak.setCurrentPlayerDeck(nimi);
                    deck1.setText(nimi);
            }});
            MenuItem nimi2 = new MenuItem(nimi);
            nimi2.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)   {
                    Manguvaljak.setCurrentOpponentDeck(nimi);
                    deck2.setText(nimi);
                }});
            deck1.getItems().add(nimi1);
            deck2.getItems().add(nimi2);
        }
        grid.add(player1Label, 0, 0);
        grid.add(deck1,0,1,2,1);
        grid.add(player1Text, 1, 0);
        grid.add(player2Label,0,2);
        grid.add(player2Text,1,2);
        grid.add(deck2,0,3,2,1);

        Button start = new Button("Start the game");
        HBox hbStart = new HBox(10);
        hbStart.setAlignment(Pos.CENTER);
        start.setMinWidth(200);
        start.setPrefWidth(500);
        hbStart.getChildren().add(start);
        grid.add(hbStart, 0, 4,3,1);
        start.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)   {
                try {
                    String player1Name = player1Text.getText();
                    String player2Name = player2Text.getText();

                    if (player1Name.equals("") || player2Name.equals("")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Empty name");
                        alert.setHeaderText(null);
                        alert.setContentText("A player must have a name!");
                        alert.showAndWait();

                    } else {
                        GUI.setPlayer1Name(player1Name);
                        GUI.setPlayer2Name(player2Name);
                        Main.main();
                        setBattleScene(x, y, primaryStage);
                        primaryStage.setScene(Gamescenes.getBattleScene());
                        primaryStage.show();
                    }
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        battleMenuScene.getStylesheets().add(Gamescenes.class.getResource("/GUI.css").toExternalForm());
        Gamescenes.battleMenuScene = battleMenuScene;
    }


    public static void setBattleScene(int x, int y, Stage primary) throws Exception {
        GridPane grid = new GridPane();
        Scene battleScene = new Scene(grid, x, y);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(15, 15, 15, 15));

        VBox opponentStats = new VBox();
        opponentStats.setPadding(new Insets(10));
        opponentStats.setSpacing(8);
        Text opName = new Text("Name: " + Manguvaljak.currentOpponent.getNimi());
        opponentStats.getChildren().add(opName);
        Text opHp = new Text("Hitpoints: " + Manguvaljak.currentOpponent.getElud());
        opponentStats.getChildren().add(opHp);
        Text opMana = new Text("Manapoints: " + Manguvaljak.currentOpponent.getMana());
        opponentStats.getChildren().add(opMana);
      //  grid.add(opponentStats, 0, 0);

        VBox playerStats = new VBox();
        playerStats.setPadding(new Insets(10));
        playerStats.setSpacing(8);
        Text name = new Text("Name: " + Manguvaljak.currentPlayer.getNimi());
        playerStats.getChildren().add(name);
        Text hp = new Text("Hitpoints: " + Manguvaljak.currentPlayer.getElud());
        playerStats.getChildren().add(hp);
        Text mana = new Text("Manapoints: " + Manguvaljak.currentPlayer.getMana());
        playerStats.getChildren().add(mana);
       // grid.add(playerStats, 0, 1);

        Image cardSpot = new Image("file:\\img\\CardSpot.jpg");
        ImageView iv2 = new ImageView();
        iv2.setImage(cardSpot);
        iv2.setFitWidth(100);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);
        HBox hbCardSpot = new HBox();
        hbCardSpot.getChildren().add(iv2);
        grid.add(hbCardSpot, 0,0);


        Label info = new Label(labelText);


        battleScene.getStylesheets().add(Gamescenes.class.getResource("/GUI.css").toExternalForm());
        Gamescenes.battleScene = battleScene;



    }
    public static void main() throws Exception {
        int x = GUI.getX();
        int y = GUI.getY();
        Stage primary = GUI.getPrimary();
        setDeckMakerMenuScene(x, y, primary);
        setBattleMenuScene(x, y, primary);
    }



}
