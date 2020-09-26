package org.game.DiceGame.controllers;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import org.game.DiceGame.App;
import org.game.DiceGame.factory.AbstractFactory;
import org.game.DiceGame.factory.BDDFactory;
import org.game.DiceGame.utils.Util;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	private Stage stage;
	@FXML
	private Button jeu;
	@FXML
	private Button parametres;
	@FXML
	private Button regles;
	@FXML
	private Button exporter;
	@FXML
	public Label score;
	@FXML
	public Label pseudo;

	/**
	 * This method is called by the FXMLLoader when initialization is complete
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		afficherHighScore();

		jeu.setOnAction(event -> {
			if (App.authentification) {
				showGame();
			} else {
				showAuthentification();
			}
		});

		parametres.setOnAction(event -> showParam());

		regles.setOnAction(event -> showRegles());

		exporter.setOnAction(event -> exporterHighScore());

	}

	// Le meilleur score est propagé dans toutes les BDDs
	public void exporterHighScore() {
		AbstractFactory abstractFactory = new AbstractFactory();
		int scoreInteger = 0;
		String pseudoString = "PERSONNE";
		for (int i = 0; i < Util.TYPE_BDD.size(); i++) {
			BDDFactory bddFactory = abstractFactory.getFactory(i);
			Map<String, String> map = bddFactory.charger(Util.NOMBRE_MAX_TOURS);

			if (map != null && !map.isEmpty() && Integer.parseInt((String) map.get("score")) >= scoreInteger) {
				scoreInteger = Integer.parseInt(map.get("score"));
				pseudoString = map.get("pseudo");
			}
		}
		for (int i = 0; i < Util.TYPE_BDD.size(); i++) {
			BDDFactory bddFactory = abstractFactory.getFactory(i);
			bddFactory.sauvegarder(scoreInteger, pseudoString);
		}
		if (App.authentification) {
			score.setText(String.valueOf(scoreInteger));
			pseudo.setText(pseudoString);
		}
	}

	// Affiche/Met à jour le meilleur score
	public void afficherHighScore() {
		if (App.authentification) {
			Map<String, String> map = App.partie.getMeilleurScore().charger(Util.NOMBRE_MAX_TOURS);
			if (map != null && !map.isEmpty()) {
				score.setText(map.get("score"));
				pseudo.setText(map.get("pseudo"));
			}
		}
	}

	public void showGame() {
		((BorderPane) stage.getScene().getRoot()).setCenter(App.gameAnchorPane);
	}

	public void showAuthentification() {
		((BorderPane) stage.getScene().getRoot()).setCenter(App.authentificationAnchorPane);
	}

	public void showParam() {
		((BorderPane) stage.getScene().getRoot()).setCenter(App.paramAnchorPane);
	}

	public void showRegles() {
		((BorderPane) stage.getScene().getRoot()).setCenter(App.regleAnchorPane);
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
