package org.game.DiceGame.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.game.DiceGame.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AuthentificationController implements Initializable {
	private Stage stage;
	@FXML
	private ImageView image;
	@FXML
	private Button jouerButton;
	@FXML
	private TextField pseudoTextField;
	@FXML
	private Label fieldError;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		image.setImage(new Image(getClass().getResource("/images/dice-game.jpg").toString()));
		fieldError.setVisible(false);
		jouerButton.setOnAction(event -> {
			// Pas de caractère spécial pour le pseudo :
			// Lettre ou chiffre
			if (pseudoTextField.getText().trim().length() != 0 && pseudoTextField.getText().trim().length() <= 255
					&& Pattern.matches("[a-zA-Z\\d]*", pseudoTextField.getText().trim())) {
				App.nomJoueur = pseudoTextField.getText();
				App.authentification = true;
				App.showGame();
			} else {
				fieldError.setText("Le pseudo ne peut pas être vide et ne peut pas comporter de caractères spéciaux.");
				fieldError.setVisible(true);
			}
		});
	}

}
