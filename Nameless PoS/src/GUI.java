
	
import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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

	private static int x;
	private static int y;
	private static Stage primary;
	static String player1Name;
	static String player2Name;

	public static void setPlayer2Name(String player2Name) {
		GUI.player2Name = player2Name;
	}

	public static void setPlayer1Name(String player1Name) {
		GUI.player1Name = player1Name;
	}



	public static Stage getPrimary() {
		return primary;
	}

	public static int getX() {
		return x;
	}

	public static int getY() {
		return y;
	}

	public void start(Stage primaryStage) throws Exception {
		this.x = 1000;
		this.y = 700;
		this.primary = primaryStage;
		Gamescenes.main(); // Luuakse Gamescenes klassi jaoks kõik mänguks vajalikud stseenid
		primaryStage.setTitle("Not Quite Yu-Gi-Oh");
		GridPane grid = new GridPane();

		Scene scene = new Scene(grid, 1000, 700);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Button play = new Button("Play Yu-Gi-Oh (not quite)");
		HBox hbPlay = new HBox(10);
		hbPlay.setAlignment(Pos.CENTER);
		play.setPrefWidth(200);
		hbPlay.getChildren().add(play);
		grid.add(hbPlay, 0, 0);
		play.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				primaryStage.setScene(Gamescenes.getBattleMenuScene());
				primaryStage.show();
			}
		});

		Button deckMaker = new Button("Make a deck");
		HBox hbDeckMaker = new HBox(10);
		hbDeckMaker.setAlignment(Pos.CENTER);
		deckMaker.setPrefWidth(200);
		hbDeckMaker.getChildren().add(deckMaker);
		grid.add(hbDeckMaker, 0, 1);
		deckMaker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				primaryStage.setScene(Gamescenes.getDeckMakerMenuScene());
				primaryStage.show();
			}
		});

		Button exit = new Button("Exit the game");
		HBox hbExit = new HBox(10);
		hbExit.setAlignment(Pos.CENTER);
		exit.setPrefWidth(200);
		hbExit.getChildren().add(exit);
		grid.add(hbExit, 0, 2);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("/GUI.css").toExternalForm());
		primaryStage.show();
	}
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
