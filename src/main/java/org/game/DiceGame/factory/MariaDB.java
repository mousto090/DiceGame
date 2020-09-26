package org.game.DiceGame.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.game.DiceGame.utils.Util;

public class MariaDB extends BDDFactory {

	private String nom;

	private static final String DB_NAME = "dicegame";
	private static final String TABLE_NAME = "meilleurscore";
	private static final String URL_CONN = "jdbc:mariadb://localhost:3306/" + DB_NAME;
	private static final String USER = "root";
	private static final String PASSWORD = "needle-pass";

	/** Constructeur privé */
	private MariaDB() {
		this.nom = Util.TYPE_BDD.get(0);
	}

	/** Instance unique pré-initialisée */
	private static MariaDB INSTANCE = new MariaDB();

	/** Point d'accès pour l'instance unique du singleton */
	public static MariaDB getInstance() {
		return INSTANCE;
	}

	@Override
	public Map<String, String> charger(int tour) {
		System.out.println("Load MariaDB");
		Map<String, String> result = null;
		/* Chargement du driver JDBC pour MySQL */
		try {
			System.out.println("Chargement du driver...");
			Class.forName("org.mariadb.jdbc.Driver");
			System.out.println("Driver chargé !");
		} catch (ClassNotFoundException e) {
			System.out.println("Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! "
					+ e.getMessage());
		}

		Connection connexion = null;
		try {
			connexion = DriverManager.getConnection(URL_CONN, USER, PASSWORD);
			Statement s = connexion.createStatement();
			s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
			s.executeUpdate("use " + DB_NAME);

			String sql = "CREATE TABLE IF NOT EXISTS `meilleurscore` (" + "`nombre_tour` int(11) NOT NULL,"
					+ "`score` int(11) NOT NULL," + "`pseudo` varchar(255) NOT NULL," + " PRIMARY KEY (`nombre_tour`)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";

			s.executeUpdate(sql);

			String selectTableSQL = "SELECT score, pseudo from " + TABLE_NAME + " WHERE nombre_tour="
					+ Util.NOMBRE_MAX_TOURS;
			s = connexion.createStatement();
			ResultSet rs = s.executeQuery(selectTableSQL);
			while (rs.next()) {
				String score = rs.getString("score");
				String pseudo = rs.getString("pseudo");
				if (score != null && pseudo != null) {
					result = new HashMap<String, String>();
					result.put("score", String.valueOf(score));
					result.put("pseudo", pseudo);
				}
			}

		} catch (SQLException e) {
			System.out.println(e);
			/* Gérer les éventuelles erreurs ici */
			System.out.println("Exception.............. ! " + e.getMessage());
		} finally {
			if (connexion != null)
				try {
					/* Fermeture de la connexion */
					connexion.close();
				} catch (SQLException ignore) {
					/* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
				}
		}

		return result;
	}

	@Override
	public void sauvegarder(int score, String pseudo) {
		System.out.println("Save MariaDB");
		try {
			System.out.println("Chargement du driver...");
			Class.forName("org.mariadb.jdbc.Driver");
			System.out.println("Driver chargé !");
		} catch (ClassNotFoundException e) {
			System.out.println("Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>"
					+ e.getMessage());
		}

		Connection connexion = null;
		try {
			connexion = DriverManager.getConnection(URL_CONN, USER, PASSWORD);

			String insertTableSQL = "REPLACE INTO " + TABLE_NAME + "(nombre_tour, score, pseudo) VALUES" + "(?,?,?)";
			PreparedStatement preparedStatement = connexion.prepareStatement(insertTableSQL);
			preparedStatement.setInt(1, Util.NOMBRE_MAX_TOURS);
			preparedStatement.setInt(2, score);
			preparedStatement.setString(3, pseudo);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			/* Gérer les éventuelles erreurs ici */
			System.out.println("Exception.............. !" + e.getMessage());
		} finally {
			if (connexion != null)
				try {
					/* Fermeture de la connexion */
					connexion.close();
				} catch (SQLException ignore) {
					/* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
				}
		}

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
