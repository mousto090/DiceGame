package org.game.DiceGame.models;

import java.util.Map;
import java.util.Observable;

import org.game.DiceGame.App;
import org.game.DiceGame.factory.BDDFactory;
import org.game.DiceGame.utils.Util;

public class Partie extends Observable {

	private int tour;
	private int score;
	private De de1;
	private De de2;
	private BDDFactory meilleurScore;

	public Partie(BDDFactory meilleurScore) {
		this.tour = 0;
		this.de1 = new De(1);
		this.de2 = new De(2);
		this.score = 0;
		this.meilleurScore = meilleurScore;
	}

	public void lancer() {
		Integer arg = null;
		de1.roll();
		de2.roll();
		tour += 1;

		// Score incrémenté de 10 points si le total des dés fait 7
		if (de1.getEtat() + de2.getEtat() == 7)
			score += 10;

		// Si c'est la fin du jeu, on sauvegarde le score si c'est le meilleur score
		if (Util.NOMBRE_MAX_TOURS == tour) {

			Map<String, String> highscore = getMeilleurScore().charger(Util.NOMBRE_MAX_TOURS);
			System.out.println(highscore);
			// sauvegarder(score)
			if (highscore == null || highscore.isEmpty()
					|| Integer.parseInt((String) highscore.get("score")) <= score) {
				arg = score;
				getMeilleurScore().sauvegarder(score, App.nomJoueur);
			}
		}

		// On notifie les observateurs
		this.setChanged();
		this.notifyObservers(arg);
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public De getDe1() {
		return de1;
	}

	public void setDe1(De de1) {
		this.de1 = de1;
	}

	public De getDe2() {
		return de2;
	}

	public void setDe2(De de2) {
		this.de2 = de2;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public BDDFactory getMeilleurScore() {
		return meilleurScore;
	}

	public void setMeilleurScore(BDDFactory meilleurScore) {
		this.meilleurScore = meilleurScore;
	}
}