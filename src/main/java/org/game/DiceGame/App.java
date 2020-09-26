package org.game.DiceGame;

import java.io.IOException;

import org.game.DiceGame.controllers.AuthentificationController;
import org.game.DiceGame.controllers.GameController;
import org.game.DiceGame.controllers.MenuController;
import org.game.DiceGame.controllers.ParamController;
import org.game.DiceGame.models.Partie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	public static Partie partie;
	public static boolean authentification = false;
	public static AnchorPane authentificationAnchorPane;
	public static AnchorPane gameAnchorPane;
	public static AnchorPane paramAnchorPane;
	public static AnchorPane regleAnchorPane;

	public static String nomJoueur;

	private static Stage primaryStage;
	public static BorderPane rootLayout;
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
    	App.primaryStage = primaryStage;
		App.primaryStage.setTitle("Dice Game");
		App.primaryStage.getIcons().add(new Image(getClass().getResource("/images/dicegame-icon-large.png").toString()));
		initRootLayout();
		showRegles();
		showParam();
		showMenu();
    }
    
    /**
	 * Initializes the root layout.
	 */
	public static void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("menu.fxml"));
			rootLayout = (BorderPane) loader.load();

			MenuController controller = (MenuController) loader.getController();
			controller.setStage(primaryStage);

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void showGame() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("game.fxml"));

			gameAnchorPane = (AnchorPane) loader.load();

			GameController controller = (GameController) loader.getController();
			controller.setStage(primaryStage);
			// Set the game into the center of root layout.
			((BorderPane) primaryStage.getScene().getRoot()).setCenter(gameAnchorPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showMenu() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("authentification.fxml"));

			authentificationAnchorPane = (AnchorPane) loader.load();
			AuthentificationController controller = (AuthentificationController) loader.getController();
			controller.setStage(primaryStage);

			// Set the menu into the center of root layout.
			rootLayout.setCenter(authentificationAnchorPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showParam() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("param.fxml"));
			paramAnchorPane = (AnchorPane) loader.load();
			ParamController controller = (ParamController) loader.getController();
			controller.setStage(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showRegles() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("regles.fxml"));
			regleAnchorPane = (AnchorPane) loader.load();
			((BorderPane) primaryStage.getScene().getRoot()).setCenter(regleAnchorPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the main stage.
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    

}