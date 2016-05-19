/**
 * Created by aleksander on 09-May-16.
 */

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class DeckMakerGUI {
    private TableView table1 = new TableView();
    private TableView table2 = new TableView();
    private Scene stseen;
    private static File cardBank = new File("Nameless Pos\\cardBank.csv");
    private static ObservableList<Kaart> oDeckList;
    private  static ObservableList<Kaart> oCardList;
    private ArrayList<Kaart> DeckList;
    private static List<Kaart> cards;
    private Label cardsInDeck = new Label();
    private VBox hoverBox = new VBox();
    private String filename;

   public DeckMakerGUI(ArrayList<Kaart> deckList, String filename) throws Exception {
        DeckList = deckList;
        this.filename = filename;
        cards = makeCardBank(cardBank);
        oCardList = FXCollections.observableArrayList(cards);
    }



    public Scene deckMakerGUIRun() throws Exception {
        table1.setColumnResizePolicy(table1.UNCONSTRAINED_RESIZE_POLICY);
        table2.setColumnResizePolicy(table2.UNCONSTRAINED_RESIZE_POLICY);
        AudioClip deckMakerMusic = new AudioClip(new File("Nameless PoS\\music\\song.mp3").toURI().toString());
        deckMakerMusic.play();
        deckMakerMusic.setCycleCount(AudioClip.INDEFINITE);
        Group root = new Group();
        root.setId("root");
        BorderPane border = new BorderPane();
        border.setId("border");
        root.getChildren().add(border);
        stseen = new Scene(root);
        stseen.getStylesheets().add(Gamescenes.class.getResource("/GUI.css").toExternalForm());
        Group topPane = new Group();
        Group leftPane = new Group();
        Group rightPane = new Group();
        Group bottomPane = new Group();
        Rectangle placeHolder = new Rectangle();
        placeHolder.minHeight(264);
        placeHolder.minWidth(200);
        placeHolder.setWidth(200);
        placeHolder.setHeight(264);
        placeHolder.setId("placeHolder");
        //placeHolder.setFill(Color.WHITE);
        BorderPane headerPane = new BorderPane();
        topPane.getChildren().add(headerPane);
        headerPane.setLeft(placeHolder);
        VBox data = new VBox();
        data.setPadding(new Insets(5));
        data.setId("data");
        data.setMaxHeight(230);
        data.setMinHeight(230);
        BorderPane cardHover = new BorderPane();
        cardHover.setId("cardHover");
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

        //Teen tabeli tulba, kus on kaartide nimed ja lisan kaardid sinna tulpa
        TableColumn cardName = new TableColumn("Card name");
        cardName.setId("cardName");
        cardName.setSortable(false);
        ObservableList<Kaart> oDeckList = FXCollections.observableArrayList(DeckList);
        cardName.setCellValueFactory(new PropertyValueFactory<Kaart, String>("nimi"));
        table1.setItems(oDeckList);

        //Lisan ridadele hoveri jaoks jalgija
        table1.setRowFactory(tableView -> {
            final TableRow<Kaart> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {

                        final Kaart kaart = row.getItem();
                        if(!hoverBox.getChildren().contains(cardHover)) {
                            hoverBox.getChildren().add(cardHover);
                        }
                //Jalgin hoverit ja vastavalt selle kuvan kaardi info uleval info boxis
                        if (row.isHover() && kaart != null) {
                            headerPane.getChildren().remove(hoverBox);
                            headerPane.setLeft(placeHolder);
                            name.setText(kaart.getNimi() + " - " + kaart.getTyyp());
                            if (kaart.getTyyp().equals("Hero")) {
                                data.getChildren().removeAll(attack, defence,subType, mana, effekt, tugevus, pikkus);
                                attack.setText("Attack: " + Integer.toString(kaart.getAttack()));
                                defence.setText("Defence: " + Integer.toString(kaart.getDefence()));
                                mana.setText("Mana: " + kaart.getManaPoints());
                                cardHover.setLeft(new ImageView(kaart.getFrontPicture()));
                                data.getChildren().addAll(attack, defence, mana);
                            } else {
                                if (kaart.getAlamTyyp().equals("Buff") || kaart.getAlamTyyp().equals("Vulnerability")) {
                                    data.getChildren().removeAll(attack, defence,subType, mana, effekt, tugevus, pikkus);
                                    subType.setText("Sub type: " + kaart.getAlamTyyp());
                                    mana.setText("Mana: " + kaart.getManaPoints());
                                    effekt.setText("Effect: " + kaart.getEffekt());
                                    tugevus.setText("Strenght: " + kaart.getTugevus());
                                    pikkus.setText("Length: " + kaart.getLength());
                                    cardHover.setLeft(new ImageView(kaart.getFrontPicture()));
                                    data.getChildren().addAll(subType, mana, effekt, tugevus, pikkus);
                                } else {
                                    data.getChildren().removeAll(attack, defence,subType, mana, effekt, tugevus, pikkus);
                                    mana.setText("Mana: " + kaart.getManaPoints());
                                    cardHover.setLeft(new ImageView(kaart.getFrontPicture()));
                                    data.getChildren().add(mana);

                                }
                            }
                            hoverBox.setStyle("-fx-background-color: #FFFFFF;");
                            cardHover.setTop(name);
                            cardHover.setRight(data);
                            cardHover.setId("cardHover");
                            if(!headerPane.getChildren().contains(hoverBox)) {
                                headerPane.setLeft(hoverBox);
                            }
                        }
                else if(kaart != null) {
                            headerPane.getChildren().remove(hoverBox);
                            headerPane.setLeft(placeHolder);
                            hoverBox.getChildren().remove(cardHover);
                        }
            });
            return row;
        });

        //Loon nupud millega saab kaarte eemaldada
        TableColumn removeFromDeck = new TableColumn();
        removeFromDeck.setId("remove");
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
                        return new ButtonCell(table1,"Remove"); //Nuppudega too kaib peamiselt ButtonCell klassis, mis on parast DeckMakerGUIStart klassi
                    }
                }
        );
        table1.getColumns().addAll(removeFromDeck, cardName);
        leftPane.getChildren().add(table1);

        //Teen samad asjad teise tabeli jaoks
        TableColumn cardName2 = new TableColumn("Card Name");
        cardName2.setId("cardName2");
        cardName2.setSortable(false);
        cardName2.setCellValueFactory(new PropertyValueFactory<Kaart, String>("nimi"));
        table2.setItems(oCardList);
        //table2.getColumns().add(cardName2);
        table2.setRowFactory(tableView -> {
            final TableRow<Kaart> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {

                final Kaart kaart = row.getItem();
                if(!hoverBox.getChildren().contains(cardHover)) {
                    hoverBox.getChildren().add(cardHover);
                }
                if (row.isHover() && kaart != null) {
                    headerPane.getChildren().remove(hoverBox);
                    headerPane.setLeft(placeHolder);
                    name.setText(kaart.getNimi() + " - " + kaart.getTyyp());
                    if (kaart.getTyyp().equals("Hero")) {
                        data.getChildren().removeAll(attack, defence,subType, mana, effekt, tugevus, pikkus);
                        attack.setText("Attack: " + Integer.toString(kaart.getAttack()));
                        defence.setText("Defence: " + Integer.toString(kaart.getDefence()));
                        mana.setText("Mana: " + kaart.getManaPoints());
                        cardHover.setLeft(new ImageView(kaart.getFrontPicture()));
                        data.getChildren().addAll(attack, defence, mana);
                    } else {
                        if (kaart.getAlamTyyp().equals("Buff") || kaart.getAlamTyyp().equals("Vulnerability")) {
                            data.getChildren().removeAll(attack, defence,subType, mana, effekt, tugevus, pikkus);
                            subType.setText("Sub type: " + kaart.getAlamTyyp());
                            mana.setText("Mana: " + kaart.getManaPoints());
                            effekt.setText("Effekt: " + kaart.getEffekt());
                            tugevus.setText("Strenght: " + kaart.getTugevus());
                            pikkus.setText("Length: " + kaart.getLength());
                            cardHover.setLeft(new ImageView(kaart.getFrontPicture()));
                            data.getChildren().addAll(subType, mana, effekt, tugevus, pikkus);
                        } else {
                            data.getChildren().removeAll(attack, defence,subType, mana, effekt, tugevus, pikkus);
                            mana.setText("Mana: " + kaart.getManaPoints());
                            cardHover.setLeft(new ImageView(kaart.getFrontPicture()));
                            data.getChildren().add(mana);

                        }
                    }
                    hoverBox.setStyle("-fx-background-color: #FFFFFF;");
                    cardHover.setTop(name);
                    cardHover.setRight(data);
                    if(!headerPane.getChildren().contains(hoverBox)) {
                        headerPane.setLeft(hoverBox);
                    }
                }
                else if(kaart != null) {
                    headerPane.getChildren().remove(hoverBox);
                    headerPane.setLeft(placeHolder);
                    hoverBox.getChildren().remove(cardHover);
                }
            });
            return row;
        });

        //Add a button
        TableColumn addToDeck = new TableColumn();
        addToDeck.setId("addToDeck");
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

        table2.getColumns().addAll(addToDeck,cardName2);
        rightPane.getChildren().add(table2);
        Label deckLabel = new Label("Deck Name: ");
        TextField deckName = new TextField(filename);
        Button makeDeck = new Button("Save Deck");
        Button toMainMenu = new Button("Return to main menu");
        cardsInDeck.setText(Integer.toString(DeckList.size()));
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5,10,10,5));
        HBox cardNr = new HBox();
        Label cardsDeckLabel = new Label("Cards in Deck: ");
        cardNr.getChildren().addAll(cardsDeckLabel, cardsInDeck);
        HBox fuckThisShit = new HBox();
        fuckThisShit.getChildren().addAll(cardNr);
        headerPane.setTop(fuckThisShit);
        cardsInDeck.setTextAlignment(TextAlignment.RIGHT);
        hbox.getChildren().addAll(deckLabel,deckName,makeDeck,toMainMenu);
        //Salvestan decki faili
        makeDeck.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try { //Kontrollin, et deckile oleksa antud nimi
                            if(deckName.getText().equals("")) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("No Deck name!");
                                alert.setHeaderText(null);
                                alert.setContentText("Deck name field can't be empty!");
                                alert.showAndWait();
                            }
                            else { //Salvestan Decki csv failina Decks kausta
                                File folder = new File("Nameless Pos\\Decks\\");
                                File[] listOfFiles = folder.listFiles();
                                ArrayList<String> filenames = new ArrayList<>();
                                for(File file : listOfFiles) {
                                    String filename = file.getName().replaceAll(".csv","");
                                    filenames.add(filename);
                                }
                                if (deckName.getText().equals(filename) || filenames.contains(deckName.getText())) {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Overwrite");
                                    alert.setContentText("Are you sure you want to overwrite your old deck?");
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        File fail = new File("Nameless Pos\\Decks\\" + deckName.getText() + ".csv");
                                        PrintWriter pw = new PrintWriter(fail, "UTF-8");
                                        if (DeckList.size() >= 10) {
                                            for (Kaart tempCard : DeckList) {
                                                pw.write(tempCard.toCSV() + "\n");
                                            }
                                            pw.close();
                                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                            alert1.setTitle("Deck saved!");
                                            alert1.setHeaderText(null);
                                            alert1.setContentText("Your Deck " + deckName.getText() + " is saved.");
                                            alert1.showAndWait();
                                        } else { // Deckis peaks olema vahemalt 10 kaarti, muidu ei saa salvestada
                                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                            alert2.setTitle("Not enough cards!");
                                            alert2.setHeaderText(null);
                                            alert2.setContentText("The deck must contain at least 10 cards.");
                                            alert2.showAndWait();
                                        }
                                    }
                                } else {
                                    File fail = new File("Nameless Pos\\Decks\\" + deckName.getText() + ".csv");
                                    PrintWriter pw = new PrintWriter(fail, "UTF-8");
                                    if (DeckList.size() >= 10) {
                                        for (Kaart tempCard : DeckList) {
                                            pw.write(tempCard.toCSV() + "\n");
                                        }
                                        pw.close();
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Deck saved!");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Your Deck " + deckName.getText() + " is saved.");
                                        alert.showAndWait();
                                    } else { // Deckis peaks olema vahemalt 10 kaarti, muidu ei saa salvestada
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Not enough cards!");
                                        alert.setHeaderText(null);
                                        alert.setContentText("The deck must contain at least 10 cards.");
                                        alert.showAndWait();
                                    }
                                }
                            }
                        }
                        catch(Exception x) {
                            throw new RuntimeException();
                        }
                    }
                }
        );
        //Return to main menu nupp, kusin kasutajalt ule kas ta on kindel, et soovib tagasi minna
        toMainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Return to main menu");
                alert.setHeaderText("Return to main menu");
                alert.setContentText("Are you sure you want to return to the main menu, unsaved changes will be lost?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    deckMakerMusic.stop();
                    stseen = Gamescenes.getMainMenuScene();
                    GUI.getPrimary().setScene(stseen);
                }

            }
        });
        bottomPane.getChildren().addAll(hbox);
        return stseen;

    }

    //Loon failist kaartide listi, mille pohjal luuakse molemad tabelid

    public static ArrayList makeCardBank(File cardBank) throws Exception {
        Scanner sc = new Scanner(cardBank, "UTF-8");
        ArrayList<Kaart> cards = new ArrayList<>();
        if (cardBank.exists()) {
            while (sc.hasNextLine()) {
                String kaart = sc.nextLine();
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
        return cards;
    }

    private class ButtonCell extends TableCell<Kaart, Boolean> {

        final Button cellButton = new Button("");

        ButtonCell(final TableView tblView,String buttonLabel) {
            cellButton.setText(buttonLabel);
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                //Tegelen nupu vajutamisel juhtuvaga
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