package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	// Méthode pour formater une date en fonction de différents formats possibles
	public static String formatDate(String inputDate) {
		// Les formats que tu acceptes en entrée
		String[] formats = { "yyyy-MM-dd", "yyyy/MM/dd", "dd-MM-yyyy", "dd/MM/yyyy" };

		for (String format : formats) {
			try {
				// Essayer de parser la date avec le format courant
				SimpleDateFormat parser = new SimpleDateFormat(format);
				Date date = parser.parse(inputDate);

				// Si le parsing est réussi, formate la date au format standard "yyyy-MM-dd"
				SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
				return outputFormat.format(date);
			} catch (ParseException e) {
				// Ignorer l'erreur et essayer le format suivant
			}
		}

		// Retourner null si aucun format n'est valide
		return null;
	}
	
}
