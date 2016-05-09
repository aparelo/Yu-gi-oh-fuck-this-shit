package application;
	
import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Scanner;


public class GUI extends Application {
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Not Quite Yu-Gi-Oh");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Button play = new Button("Start the game");
		HBox hbPlay = new HBox(10);
		hbPlay.setAlignment(Pos.CENTER);
		hbPlay.setMinWidth(100);
		hbPlay.getChildren().add(play);
		grid.add(hbPlay, 0, 0);
		Button deckMaker = new Button("Make a deck");
		HBox hbDeckMaker = new HBox(10);
		hbDeckMaker.setAlignment(Pos.CENTER);
		hbDeckMaker.setMinWidth(100);
		hbDeckMaker.getChildren().add(deckMaker);
		grid.add(hbDeckMaker, 0, 1);
		deckMaker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				GridPane grid = new GridPane();
				Text cardsInDeckText = new Text("How many cards do you wish to add to your deck:");
				grid.add(cardsInDeckText, 0, 0);
				TextField cardsInDeck = new TextField();
				grid.add(cardsInDeck, 1, 0);
				
				Scene deckMaker = new Scene(grid, 300, 275);
				primaryStage.setScene(deckMaker);
				primaryStage.show();
			}
		});
		Button exit = new Button("Exit the game");
		HBox hbExit = new HBox(10);
		hbExit.setAlignment(Pos.CENTER);
		hbExit.setMinWidth(100);
		hbExit.getChildren().add(exit);
		grid.add(hbExit, 0, 2);
		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("/GUI.css").toExternalForm());
		primaryStage.show();
	}
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
