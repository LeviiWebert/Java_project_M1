package DBTo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import service.DBconnection;

public class DBTolignecommande {

	
	public static int getMaxLigneCommande() {
		int maxID = 0;
		try (Connection connection = DBconnection.getConnection()) {
			if (connection != null) {
				System.out.println("Connexion réussie !");
				String query = "SELECT MAX(id) FROM lignecommande";
				try (PreparedStatement preparedStatement = connection.prepareStatement(query);
						ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						maxID = resultSet.getInt(1);
					}
				}
			} else {
				System.out.println("La connexion a échoué !");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxID;
	}
	
	
}
