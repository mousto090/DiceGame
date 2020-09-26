package org.game.DiceGame.factory;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.game.DiceGame.utils.Util;

public class XML extends BDDFactory {

	private String nom;
	private static final String FILE_NAME = "dicegame.xml";

	/** Constructeur privé */
	private XML() {
		this.setNom(Util.TYPE_BDD.get(2));
	}

	/** Instance unique pré-initialisée */
	private static XML INSTANCE = new XML();

	/** Point d'accès pour l'instance unique du singleton */
	public static XML getInstance() {
		return INSTANCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> charger(int tour) {
		System.out.println("Load XML");
		Map<String, HashMap<String, String>> map = null;
		// ouverture de decodeur
		XMLDecoder decoder;
		try {
			decoder = new XMLDecoder(new FileInputStream(FILE_NAME));
			// deserialisation de l'objet
			map = (Map<String, HashMap<String, String>>) decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fichier XML inexistant!");
			return null;
		}
		if (map != null && map.containsKey(String.valueOf(Util.NOMBRE_MAX_TOURS))) {
			return map.get(String.valueOf(Util.NOMBRE_MAX_TOURS));
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sauvegarder(int score, String pseudo) {
		System.out.println("Save XML");
		Map<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
		XMLDecoder decoder;
		try {
			decoder = new XMLDecoder(new FileInputStream(FILE_NAME));
			// deserialisation de l'objet
			map = (Map<String, HashMap<String, String>>) decoder.readObject();
			decoder.close();

			if (map == null) {
				map = new HashMap<String, HashMap<String, String>>();
			}
		} catch (FileNotFoundException e1) {

		}

		Map<String, String> valeur = new HashMap<String, String>();

		valeur.put("score", String.valueOf(score));
		valeur.put("pseudo", pseudo);
		map.put(String.valueOf(Util.NOMBRE_MAX_TOURS), (HashMap<String, String>) valeur);
		// ouverture de l'encodeur vers le fichier
		XMLEncoder encoder;

		try {
			encoder = new XMLEncoder(new FileOutputStream(FILE_NAME));
			encoder.writeObject(map);
			encoder.flush();

			// fermeture de l'encodeur
			encoder.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
