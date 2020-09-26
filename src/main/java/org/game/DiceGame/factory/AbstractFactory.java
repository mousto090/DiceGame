package org.game.DiceGame.factory;

public class AbstractFactory {

	public BDDFactory getFactory(int type) {
		if (type == 0) {
			return MariaDB.getInstance();
		} else if (type == 1) {
			return Redis.getInstance();
		} else {
			return XML.getInstance();
		}
	}
}
