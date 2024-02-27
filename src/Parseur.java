package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The ParseurMot class extracts French words from a specified URL.
 * It retrieves the source code from the URL, filters and processes the data
 * to provide a list of French words and sorts them based on their length.
 */
public class Parseur {

    /**
     * Default constructor for ParseurMot class.
     */
    public Parseur() {}

    /**
     * Retrieves the source code from a specified URL.
     *
     * @return The source code retrieved from the URL as a string
     */
    public static String getSource() {
        String urlStr = "https://fr.wiktionary.org/wiki/Wiktionnaire:Liste_de_1750_mots_fran%C3%A7ais_les_plus_courants#La_cuisine,_la_nourriture";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder codeSource = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    codeSource.append(line);
                }
                reader.close();
                String code = codeSource.toString();
                connection.disconnect();
                return code;
            } else {
                connection.disconnect();
                return "La requête a échoué avec le code : " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception";
        }
    }

    /**
     * Extracts a list of French words from the parsed source code.
     *
     * @return List of French words extracted from the source code
     */
    public static List<String> getListeMots() {
        String codeSource = Parseur.getSource();
        Pattern pattern = Pattern.compile("<a href=\"/wiki/([^\"']+)\" title=\"[^\"]+\">([^<]+)</a>");
        Matcher matcher = pattern.matcher(codeSource);
        List<String> listeMot = matcher.results()
                .map(result -> result.group(1).toUpperCase()) 
                .distinct()
                .collect(Collectors.toList());
        return listeMot;
    }

    /**
     * Sorts the extracted words by their length.
     *
     * @param k The length of words to filter by (between 5 and 7)
     * @return List of words with the specified length
     */
    public static List<String> getListeMotsDeLongueur(int k) {
        List<String> listeMot = Parseur.getListeMots();
        List<String> listeMotDeLongueurK = new ArrayList<>();
        for (int i = 0; i < listeMot.size(); i++) {
            if (listeMot.get(i).length() == k) {
                listeMotDeLongueurK.add(listeMot.get(i));
            }
        }
        return listeMotDeLongueurK;
    }
}