

import com.sun.org.glassfish.gmbal.Impact;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Gamescenes  {
    private static Scene deckMakerScene;
    private static Scene deckMakerMenuScene;
    private static Scene mainMenuScene;
    private static Scene battleScene;
    private static Scene battleMenuScene;
    private static GridPane battleScenePane;
    private static StringProperty labelText = new SimpleStringProperty();

    public static StringProperty getLabelText() {
        return labelText;
    }

    public static void setLabelText(String labelText) {
        Gamescenes.labelText.setValue(labelText);
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

    public static GridPane getBattleScenePane() {
        return battleScenePane;
    }


    public static void setDeckMakerMenuScene(int x, int y, Stage primaryStage) {
        primaryStage.setTitle("Deck Maker Menu");
        GridPane grid = new GridPane();
        BorderPane border = new BorderPane();
        Scene deckMenuScene = new Scene(grid, x*0.5, y*0.5);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        TextField deckLocation = new TextField();
        Button newDeck = new Button("Start");
        Label intro = new Label("Enter current deck name to edit,\n or leave blank to start a new deck.");
        intro.setAlignment(Pos.CENTER);
        border.setTop(intro);
        HBox hbNewDeck = new HBox(10);
        hbNewDeck.setAlignment(Pos.CENTER);
        newDeck.setMinWidth(150);
        newDeck.setPrefWidth(200);
        hbNewDeck.getChildren().addAll(deckLocation,newDeck);
        border.setCenter(hbNewDeck);
        grid.add(border, 0, 0);
        newDeck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if(deckLocation.getText().equals("")){
                        ArrayList<Kaart> cards = new ArrayList<>();
                        DeckMakerGUI deckMaker = new DeckMakerGUI(cards);
                        primaryStage.setScene(deckMaker.deckMakerGUIRun());
                    }
                    else {
                        try {
                            File fail = new File(deckLocation.getText());
                            ArrayList<Kaart> cards = DeckMakerGUI.makeCardBank(fail);
                            DeckMakerGUI deckMaker = new DeckMakerGUI(cards);
                            primaryStage.setScene(deckMaker.deckMakerGUIRun());
                        }
                        catch (FileNotFoundException e) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("File Not found!");
                            alert.setHeaderText(null);
                            alert.setContentText("Could not find a file with that name, please try again.");
                            alert.showAndWait();
                        }

                    }
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
                        setBattleScene(x, y, primaryStage);
                        primaryStage.setScene(Gamescenes.getBattleScene());
                        primaryStage.show();
                        Main.gameLogic();
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
        Gamescenes.battleScenePane = grid;
        Scene battleScene = new Scene(grid, x, y);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(15, 15, 15, 15));



        Image cardSpot = new Image("\\img\\CardSpot.jpg");

        int hand1 = 4;
        int spellAndHero1 = 3;
        int hand2 = 1;
        int spellAndHero2 = 2;

        for (int i = 0; i <34; i++) {

            ImageView iv = new ImageView();
            iv.setId(Integer.toString(i));
            iv.setImage(cardSpot);
            iv.setPreserveRatio(true);
            iv.setSmooth(true);
            iv.setCache(true);

            if (i < 5) {
                grid.add(iv, hand1, 6);
                hand1++;
            }
            if (i >= 5 && i < 10) {
                grid.add(iv, spellAndHero1, 5);
                spellAndHero1++;
            }
            if (i == 10) {
                spellAndHero1 = 3;
            }
            if (i >= 10 && i < 15) {
                grid.add(iv, spellAndHero1, 4);
                spellAndHero1++;
            }
            if (i >= 15 && i < 20) {
                grid.add(iv, hand2, 0);
                hand2++;
            }
            if (i >= 20 && i < 25) {
                grid.add(iv, spellAndHero2, 1);
                spellAndHero2++;
            }

            if (i == 25) {
                spellAndHero2 = 2;
            }
            if (i >= 25 && i < 30) {
                grid.add(iv, spellAndHero2, 2);
                spellAndHero2++;
            }
            if (i == 30) {
                grid.add(iv, 0, 2);
            }
            if (i == 31) {
                grid.add(iv, 9, 2);
            }
            if (i == 32) {
                grid.add(iv, 0, 4);
            }
            if (i == 33) {
                grid.add(iv, 9, 4);
            }
        }
        Gamescenes.battleScene = battleScene;
        Main.createPlayersAndDecks();

        VBox opponentStats = new VBox();
        opponentStats.setPadding(new Insets(10));
        opponentStats.setSpacing(8);
        Label opName = new Label("Name: " + Manguvaljak.currentOpponent.getNimi());
        opponentStats.getChildren().add(opName);
        Label opHp = new Label("Hitpoints: " + Manguvaljak.currentOpponent.getElud());
        opponentStats.getChildren().add(opHp);
        Label opMana = new Label("Manapoints: " + Manguvaljak.currentOpponent.getMana());
        opponentStats.getChildren().add(opMana);
        grid.add(opponentStats, 0, 6);

        VBox playerStats = new VBox();
        playerStats.setPadding(new Insets(10));
        playerStats.setSpacing(8);
        Label name = new Label("Name: " + Manguvaljak.currentPlayer.getNimi());
        playerStats.getChildren().add(name);
        Label hp = new Label("Hitpoints: " + Manguvaljak.currentPlayer.getElud());
        playerStats.getChildren().add(hp);
        Label mana = new Label("Manapoints: " + Manguvaljak.currentPlayer.getMana());
        playerStats.getChildren().add(mana);
        grid.add(playerStats, 0, 0);

        Label info = new Label("");
        setLabelText("");
        info.textProperty().bind(labelText);
        grid.add(info,1,3,9,1);


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
