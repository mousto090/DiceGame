package org.game.DiceGame.factory;

import java.util.HashMap;
import java.util.Map;

import org.game.DiceGame.utils.Util;

import redis.clients.jedis.Jedis;

public class Redis extends BDDFactory {

	private String nom;

	/** Constructeur privé */
	private Redis() {
		this.setNom(Util.TYPE_BDD.get(1));
	}

	/** Instance unique pré-initialisée */
	private static Redis INSTANCE = new Redis();

	/** Point d'accès pour l'instance unique du singleton */
	public static Redis getInstance() {
		return INSTANCE;
	}

	@Override
	public Map<String, String> charger(int tour) {
		System.out.println("Load from Redis");
		String cacheKey = String.valueOf(Util.NOMBRE_MAX_TOURS);
		Jedis jedis = new Jedis("localhost");
		Map<String, String> properties = jedis.hgetAll("meilleurscore:" + cacheKey);
		jedis.close();
		return properties;
	}

	@Override
	public void sauvegarder(int score, String pseudo) {
		// Connecting to Redis server on localhost
		Jedis jedis = new Jedis("localhost");
		System.out.println("Save to Redis");
		// System.out.println("Connection to server sucessfully");
		// check whether server is running or not
		// System.out.println("Server is running: " + jedis.ping());

		String cacheKey = String.valueOf(Util.NOMBRE_MAX_TOURS);

		Map<String, String> map = new HashMap<String, String>();
		map.put("score", String.valueOf(score));
		map.put("pseudo", pseudo);
		jedis.hmset("meilleurscore:" + cacheKey, map);
		jedis.close();

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
