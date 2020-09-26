package org.game.DiceGame.controllers;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import org.game.DiceGame.App;
import org.game.DiceGame.factory.AbstractFactory;
import org.game.DiceGame.factory.BDDFactory;
import org.game.DiceGame.factory.MariaDB;
import org.game.DiceGame.factory.Redis;
import org.game.DiceGame.factory.XML;
import org.game.DiceGame.models.De;
import org.game.DiceGame.models.Partie;
import org.game.DiceGame.utils.Util;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameController implements Initializable, Observer {

	private Stage stage;

	@FXML
	private Button roll; // Value injected by FXMLLoader
	@FXML
	private Button newGame;
	@FXML
	private ImageView de1;
	@FXML
	private ImageView de2;
	@FXML
	private ImageView mariadb;
	@FXML
	private ImageView mongodb;
	@FXML
	private ImageView xml;
	@FXML
	private ImageView redis;
	@FXML
	private ImageView mariadbLOGO;
	@FXML
	private ImageView mongodbLOGO;
	@FXML
	private ImageView xmlLOGO;
	@FXML
	private ImageView redisLOGO;
	@FXML
	private ImageView resultImage;
	@FXML
	private Label score;
	@FXML
	private Label tour;
	@FXML
	private Label labelResult1;
	@FXML
	private Label labelResult2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reinitialiser();
		App.initRootLayout();
		roll.setOnAction(event -> {
			App.partie.lancer();

			System.out.println("SCORE : De 1 = " + App.partie.getDe1().getEtat() + 
					" De 2 = " + App.partie.getDe2().getEtat());
		});

		newGame.setOnAction(event -> reinitialiser());
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof De) {
			String imageName = ((De) o).getEtat() + ".png";
			if ((Integer) arg == 1) {
				setImage(imageName, de1);
			} else {
				setImage(imageName, de2);
			}
		} else if (o instanceof Partie) {
			tour.setText(((Partie) o).getTour() + "/" + Util.NOMBRE_MAX_TOURS);
			score.setText(((Partie) o).getScore() + "");

			if (((Partie) o).getTour() == Util.NOMBRE_MAX_TOURS) {

				roll.setDisable(true);
				// Si Meilleur Score, il est en argument de la méthode update
				if (arg != null) {
					majMeilleurScore();

					setImage("podium.png", resultImage);
					labelResult1.setText("Meilleur score!");
					labelResult2.setText("Félicitations!");
				} else {
					setImage("gameover.png", resultImage);
					labelResult1.setText("Dommage!");
					labelResult2.setText("Réessayez...");
				}
				resultImage.setVisible(true);
				labelResult1.setVisible(true);
				labelResult2.setVisible(true);
			}
		}

	}

	/**
	 * Set image view image, all images must be located under images folder
	 * 
	 * @param imgName   the image name with the extension
	 * @param imageView the image view
	 */
	private void setImage(String imgName, ImageView imageView) {
		imageView.setImage(new Image(getClass().getResource("/images/" + imgName).toString()));
	}

	public void reinitialiser() {

		// Random sur la liste des BDDs
		int nombreAleatoire = (int) (Math.random() * (Util.TYPE_BDD.size() + 1));
		AbstractFactory abstractFactory = new AbstractFactory();
		BDDFactory bddFactory = abstractFactory.getFactory(nombreAleatoire);

		// Nouvelle partie : on ajoute l'observateur ( cette classe )
		App.partie = new Partie(bddFactory);
		App.partie.getMeilleurScore().charger(Util.NOMBRE_MAX_TOURS);
		App.partie.addObserver(this);
		App.partie.getDe1().addObserver(this);
		App.partie.getDe2().addObserver(this);
		labelResult1.setVisible(false);
		labelResult2.setVisible(false);
		resultImage.setVisible(false);
		setImage("6.png", de1);
		setImage("6.png", de2);
		setImage("rouge.png", mariadb);
		setImage("rouge.png", xml);
		setImage("rouge.png", redis);

		// Le cercle vert correspond à la BDD utilisée
		if (App.partie.getMeilleurScore() instanceof Redis) {
			setImage("vert.png", redis);
		} else if (App.partie.getMeilleurScore() instanceof XML) {
			setImage("vert.png", xml);
		} else if (App.partie.getMeilleurScore() instanceof MariaDB) {
			setImage("vert.png", mariadb);
		}

		// Images des BDDs
		setImage("mariadb.png", mariadbLOGO);
		setImage("xml.png", xmlLOGO);
		setImage("redis.png", redisLOGO);

		roll.setDisable(false);
		tour.setText("0/" + Util.NOMBRE_MAX_TOURS);
		score.setText("0");

		// On met à jour le meilleur score correspond à la BDD utilisée
		if (stage != null) {
			majMeilleurScore();
		}
	}

	private void majMeilleurScore() {
		// Maj meilleur score
		App.initRootLayout();
		// On réaffiche la partie
		((BorderPane) stage.getScene().getRoot()).setCenter(App.gameAnchorPane);
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
