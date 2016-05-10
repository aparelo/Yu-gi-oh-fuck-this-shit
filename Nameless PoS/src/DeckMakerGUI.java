/**
 * Created by aleksander on 09-May-16.
 */

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
import sun.font.TextLabel;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

public class DeckMakerGUI extends Application {
    private TableView table1 = new TableView();
    private TableView table2 = new TableView();
    private static File cardBank = new File("C:\\Users\\Siim-Sander\\Documents\\GitHub\\Yu-gi-oh-fuck-this-shit\\Nameless PoS\\Decks\\Deck.txt"); //Siia peab tegeliku Card Banki nime panema
    private static ArrayList<String> cardBankList = new ArrayList<>();
    //private static ObservableList<Kaart> oCardList;
    private static ObservableList<Kaart> oDeckList;
    private ObservableList<Kaart> oCardList = FXCollections.observableArrayList(cards);
    private static ArrayList<Kaart> DeckList = new ArrayList<>();
    private static List<Kaart> cards = new ArrayList<>();
    private Label cardsInDeck = new Label();
    private VBox hoverBox = new VBox();

    public static void setDeckList(ArrayList<Kaart> deckList) {
        DeckList = deckList;
    }

    public static void main(String[] args) throws Exception {
        makeCardBank();
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        BorderPane border = new BorderPane();
        root.getChildren().add(border);
        Scene stseen = new Scene(root);
        Group topPane = new Group();
        Group leftPane = new Group();
        Group rightPane = new Group();
        Group bottomPane = new Group();
        VBox data = new VBox();
        data.setPadding(new Insets(5));
        BorderPane cardHover = new BorderPane();
        Label name = new Label();
        Label attack = new Label();
        Label defence = new Label();
        Label subType = new Label();
        Label mana = new Label();
        Label effekt = new Label();
        Label tugevus = new Label();
        Label pikkus = new Label();
        border.setLeft(leftPane);
        border.setRight(rightPane);
        border.setTop(topPane);
        border.setBottom(bottomPane);
        Label title = new Label("Deck Maker");
        topPane.getChildren().add(title);
        TableColumn cardName = new TableColumn("Card name");
        cardName.setSortable(false);
        ObservableList<Kaart> oDeckList = FXCollections.observableArrayList(DeckList);
        cardName.setCellValueFactory(new PropertyValueFactory<Kaart, String>("nimi"));
        table1.setItems(oDeckList);
        table1.setRowFactory(tableView -> {
            final TableRow<Kaart> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {

                        final Kaart kaart = row.getItem();
                        if(!hoverBox.getChildren().contains(cardHover)) {
                            hoverBox.getChildren().add(cardHover);
                        }
                System.out.println(row.isHover());
                System.out.println(kaart);
                        if (row.isHover() && kaart != null) {
                            leftPane.getChildren().remove(hoverBox);
                            name.setText(kaart.getNimi() + " - " + kaart.getTyyp());
                            if (kaart.getTyyp().equals("Hero")) {
                                data.getChildren().removeAll(attack, defence,subType, mana, effekt, tugevus, pikkus);
                                attack.setText("Attack: " + Integer.toString(kaart.getAttack()));
                                defence.setText("Defence: " + Integer.toString(kaart.getDefence()));
                                mana.setText("Mana: " + kaart.getManaPoints());
                                data.getChildren().addAll(attack, defence, mana);
                            } else {
                                if (kaart.getAlamTyyp().equals("Buff") || kaart.getAlamTyyp().equals("Vulnerability")) {
                                    data.getChildren().removeAll(attack, defence,subType, mana, effekt, tugevus, pikkus);
                                    subType.setText("Sub type: " + kaart.getAlamTyyp());
                                    mana.setText("Mana: " + kaart.getManaPoints());
                                    effekt.setText("Effekt: " + kaart.getEffekt());
                                    tugevus.setText("Strenght: " + kaart.getTugevus());
                                    pikkus.setText("Length: " + kaart.getLength());
                                    data.getChildren().addAll(subType, mana, effekt, tugevus, pikkus);
                                } else {
                                    data.getChildren().removeAll(attack, defence,subType, mana, effekt, tugevus, pikkus);
                                    mana.setText("Mana: " + kaart.getManaPoints());
                                    data.getChildren().add(mana);

                                }
                            }
                            hoverBox.setStyle("-fx-background-color: #FFFFFF;");
                            cardHover.setTop(name);
                            cardHover.setRight(data);
                            cardHover.setLeft(new Rectangle(60,90,Color.GREEN));
                            if(!leftPane.getChildren().contains(hoverBox)) {
                                leftPane.getChildren().add(hoverBox);
                            }
                        }
                else if(kaart != null) {
                            leftPane.getChildren().remove(hoverBox);
                            hoverBox.getChildren().remove(cardHover);
                        }
                else if (row.isHover() && kaart == null) {
                            //leftPane.getChildren().remove(hoverBox);
                        }
            });
            return row;
        });
        TableColumn removeFromDeck = new TableColumn("Remove");
        removeFromDeck.setSortable(false);
        removeFromDeck.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Kaart, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Kaart, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                }
        );
        removeFromDeck.setCellFactory(
                new Callback<TableColumn<Kaart, Boolean>, TableCell<Kaart, Boolean>>() {
                    @Override
                    public TableCell<Kaart, Boolean> call(TableColumn<Kaart, Boolean> p) {
                        return new ButtonCell(table1,"Remove");
                    }
                }
        );
        table1.getColumns().addAll(removeFromDeck, cardName);
        leftPane.getChildren().add(table1);
        TableColumn cardName2 = new TableColumn("Card Name");
        cardName2.setSortable(false);
        cardName2.setCellValueFactory(new PropertyValueFactory<Kaart, String>("nimi"));
        table2.setItems(oCardList);
        table2.getColumns().add(cardName2);

        //Add a button
        TableColumn addToDeck = new TableColumn("Add to Deck");
        addToDeck.setSortable(false);
        addToDeck.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Kaart, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Kaart, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                }
        );
        addToDeck.setCellFactory(
                new Callback<TableColumn<Kaart, Boolean>, TableCell<Kaart, Boolean>>() {
                    @Override
                    public TableCell<Kaart, Boolean> call(TableColumn<Kaart, Boolean> p) {
                        return new ButtonCell(table2,"Add");
                    }
                }
        );

        table2.getColumns().addAll(addToDeck);
        rightPane.getChildren().add(table2);
        Label deckLabel = new Label("Deck Name: ");
        TextField deckName = new TextField();
        Button makeDeck = new Button("Save Deck");
        cardsInDeck.setText(Integer.toString(DeckList.size()));
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5,10,10,5));
        hbox.getChildren().addAll(deckLabel,deckName,makeDeck,cardsInDeck);
        makeDeck.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            if(deckName.getText().equals("")) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("No Deck name!");
                                alert.setHeaderText(null);
                                alert.setContentText("Deck name field can't be empty!");
                                alert.showAndWait();
                            }
                            else {
                                File fail = new File(deckName.getText() + ".csv");
                                PrintWriter pw = new PrintWriter(fail, "UTF-8");
                                if (DeckList.size() >= 10) {
                                    for (Kaart tempCard : DeckList) {
                                        pw.write(tempCard.toCSV() + "\n");
                                    }
                                    pw.close();
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Deck saved!");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Your Deck " +deckName.getText()+" is saved.");
                                    alert.showAndWait();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Not enough cards!");
                                    alert.setHeaderText(null);
                                    alert.setContentText("The deck must contain at least 10 cards.");
                                    alert.showAndWait();
                                }
                            }
                        }
                        catch(Exception x) {
                            throw new RuntimeException();
                        }
                    }
                }
        );
        bottomPane.getChildren().addAll(hbox);
        primaryStage.setScene(stseen);
        primaryStage.show();

    }

    public static void makeCardBank() throws Exception {
        Scanner sc = new Scanner(cardBank, "UTF-8");
        if (cardBank.exists()) {
            while (sc.hasNextLine()) {
                String kaart = sc.nextLine();
                cardBankList.add(kaart);
                String[] kaartSplit = kaart.split(",");
                if (kaartSplit[1].equals("Hero")) {
                    Kaart tempCard = new Hero(kaartSplit[0], Integer.parseInt(kaartSplit[2]), Integer.parseInt(kaartSplit[3]), Integer.parseInt(kaartSplit[4]), Integer.parseInt(kaartSplit[5]), kaartSplit[6], kaartSplit[7]);
                    cards.add(tempCard);
                }
                if (kaartSplit[1].equals("Spell")) {
                    if (kaartSplit[4].equals("Buff")) {
                        Kaart tempCard = new Buff(kaartSplit[0], kaartSplit[4], Integer.parseInt(kaartSplit[3]), kaartSplit[2], Integer.parseInt(kaartSplit[5]), Integer.parseInt(kaartSplit[6]));
                        cards.add(tempCard);
                    }
                    if (kaartSplit[4].equals("Vulnerability")) {
                        Kaart tempCard = new Vulnerability(kaartSplit[0], kaartSplit[4], Integer.parseInt(kaartSplit[3]), kaartSplit[2], Integer.parseInt(kaartSplit[5]), Integer.parseInt(kaartSplit[6]));
                        cards.add(tempCard);
                    }
                    if (kaartSplit[4].equals("Purge")) {
                        Kaart tempCard = new Purge(kaartSplit[0], kaartSplit[4], Integer.parseInt(kaartSplit[3]), kaartSplit[2]);
                        cards.add(tempCard);
                    }
                }
            }
        }
    }

    private class ButtonCell extends TableCell<Kaart, Boolean> {

        final Button cellButton = new Button("");

        ButtonCell(final TableView tblView,String buttonLabel) {
            cellButton.setText(buttonLabel);
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    if(cellButton.getText().equals("Add")) {
                        Kaart removedCard = cards.remove(getTableRow().getIndex());
                        oCardList = FXCollections.observableArrayList(cards);
                        DeckList.add(removedCard);
                        oDeckList = FXCollections.observableArrayList(DeckList);
                        table2.setItems(oCardList);
                        table1.setItems(oDeckList);
                        cardsInDeck.setText(Integer.toString(DeckList.size()));
                    }
                    else{
                        Kaart removedCard = DeckList.remove(getTableRow().getIndex());
                        oDeckList = FXCollections.observableArrayList(DeckList);
                        cards.add(removedCard);
                        oCardList = FXCollections.observableArrayList(cards);
                        table2.setItems(oCardList);
                        table1.setItems(oDeckList);
                        cardsInDeck.setText(Integer.toString(DeckList.size()));

                    }

                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
            else {
                setGraphic(null);
            }
        }
    }
}