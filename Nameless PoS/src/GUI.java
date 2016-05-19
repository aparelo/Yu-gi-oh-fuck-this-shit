
	
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
		primaryStage.setTitle("Game of Cards");
		primaryStage.setMinWidth(1000);
		primaryStage.setMaxWidth(1500);
		primaryStage.setMinHeight(700);
		primaryStage.setMaxHeight(1200);
		primaryStage.setScene(Gamescenes.getMainMenuScene());
		primaryStage.show();
	}
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
