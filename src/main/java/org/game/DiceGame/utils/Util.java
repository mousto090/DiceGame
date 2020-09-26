package org.game.DiceGame.utils;

import java.util.ArrayList;
import java.util.List;

public class Util {

	public static int NOMBRE_MAX_TOURS = 10;
	@SuppressWarnings("serial")
	public static final List<String> TYPE_BDD = new ArrayList<String>() {
		{
			add("MariaDB");
			add("Redis");
			add("XML");
		}
	};
}
