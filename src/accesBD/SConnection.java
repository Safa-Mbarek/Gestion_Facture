package accesBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SConnection {
	private static String url = "jdbc:mysql://localhost:3306/bdfactures?autoReconnect=true&useSSL=false";
	private static String utilisateur = "root";
	private static String motPasse = "";
	private static Connection cnx;

	private SConnection() {
	}

	public static Connection getInstance() {
		try {
			
		if(cnx==null || cnx.isClosed()) {
			cnx = DriverManager.getConnection(url, utilisateur, motPasse);
			System.out.println("Connexion à la bd bdfactures bien établie.");}
		} catch (SQLException e) {
			System.out.println("La connexion a échoué. Vérifiez que vous avez bien "
					+ "ajouté le pilote jdbc à votre build path");
			System.exit(0);
		}
		return cnx;
	}

	public static void close() {
		try {
			if (cnx != null && !cnx.isClosed())
				cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
