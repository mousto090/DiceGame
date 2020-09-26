package org.game.DiceGame.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.game.DiceGame.App;
import org.game.DiceGame.utils.Util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ParamController implements Initializable {

	private Stage stage;
	@FXML
	private Button validerTour;
	@FXML
	private Spinner<Integer> spinnerTour;
	@FXML
	private Label labelTour;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Input number pour choisir le nombre de tours
		SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 20);
		svf.setValue(Util.NOMBRE_MAX_TOURS);
		spinnerTour.setValueFactory(svf);

		validerTour.setOnAction(event-> {
			Util.NOMBRE_MAX_TOURS = (int) spinnerTour.getValue();

			// MAJ VUE MEILLEUR SCORE
			App.initRootLayout();
			// REDEMARRAGE DE LA PARTIE
			App.showGame();
			// On réaffiche les paramètres
			((BorderPane) stage.getScene().getRoot()).setCenter(App.paramAnchorPane);

			labelTour.setText("Le nombre de tours a bien été modifié!");
		});

	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
