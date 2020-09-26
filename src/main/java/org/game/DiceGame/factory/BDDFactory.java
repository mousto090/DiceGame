package org.game.DiceGame.factory;

import java.util.Map;

public abstract class BDDFactory {
	

	public abstract Map<String, String> charger(int tour);

	public abstract void sauvegarder(int score, String pseudo);
}
