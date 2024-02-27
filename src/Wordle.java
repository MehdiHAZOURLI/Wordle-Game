package application;

import java.util.Scanner;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.lang.StringBuffer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Timer;

/**
 * The Wordle class represents a word guessing game that extends the Jeu class.
 * It allows players to guess words within specified longeurDuMot levels and
 * provides scoring mechanisms.
 */
public class Wordle extends Jeu {
	static int score = 0;
	static int meilleurScore = 0;
	boolean indiceUtilise = false;
	boolean longeurDuMotChoisie = false;
	static int longueurMotParDefault = 5;
	private static Timer timer;
	private static long tempsDebut;
	private static long tempsTotal = 0;
	long tempsEcoule=0;
	/**
	 * final public static String cheminWord2Vec
	 */
	final public static String cheminWord2Vec = "/word2vecmodel.py";
	final public static String cheminDictionnaireLocal = "C:\\Users\\etudiant\\eclipse-workspace\\Test_jeu\\dictionnaire.txt";
	final public static String nomFichierScore = "best_score.txt";
	static StringBuffer motChoisiParLeJoueur;
	static String lecteurPropositionJoueur;
    static  String motChercher;
	/**
	 * Constructor to initialize a Wordle game.
	 */
	public Wordle() {
	}

	/**
	 * Set the timer for the game
	 */
	public  void lancerTimer() {
		Timer x=new Timer();
		tempsDebut = System.currentTimeMillis();
	}

	/**
	 * Display the time since the beginning
	 */
	public  long afficherTimer() {
		long tempsFin = System.currentTimeMillis();
		 tempsEcoule = tempsFin - tempsDebut;
		return tempsEcoule;
	}

	/**
	 * Retrieves a list of words based on the specified length of the word.
	 *
	 * @param longeurDuMot length of the word
	 * @return List of words based on the specified longeurDuMot
	 */
	public List<String> getListeDeMotDeLongueurDemande(int longeurDuMot) {
		return Parseur.getListeMotsDeLongueur(longeurDuMot);
	}

	/**
	 * Selects a random word from the word list of specified length of the word.
	 *
	 * @param longeurDuMot longeurDuMot level for word selection
	 * @return Randomly chosen word
	 */
	public String getRandomWord(int longeurDuMot) {
		Random random = new Random();
		return getListeDeMotDeLongueurDemande(longeurDuMot)
				.get(random.nextInt(getListeDeMotDeLongueurDemande(longeurDuMot).size() - 1));
	}

	/**
	 * Checks if a word exists in the provided dictionary.
	 *
	 * @param s The word to check
	 * @return True if the word exists in the dictionary, otherwise false
	 */
	public boolean wordExistsInTheLocalDictionnary(String s) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(cheminDictionnaireLocal));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals(s)) {
					br.close();
					return true;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks if a word exists in the game's word list based on the specified
	 * longeurDuMot.
	 *
	 * @param s            The word to check
	 * @param longeurDuMot The longeurDuMot level to check against
	 * @return True if the word exists in the word list, otherwise false
	 */
	public boolean wordExistsInTheParserDictionnary(String wordToSearch, int longeurDuMot) {
		if (getListeDeMotDeLongueurDemande(longeurDuMot).contains(wordToSearch)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Updates the best score and the name of the player based on the current score
	 * and stores it in a file.
	 *
	 * @param Stringbest_score Current best score as a string
	 */
	public void setBestScore(int newBestScore, long tempsTotal,String nomJoueur) {
	 	int meilleurScore = 0;
		long ancienTemps = 0;
		BufferedReader reader=null;
		String content;
		String [] namePlayer=new String [10];
		int [] scorePlayer=new int [10];
		String score="";
		String resultat=new String();
		int numberLines=0;
		BufferedWriter writer ;
		try {
			reader=new BufferedReader(new FileReader(nomFichierScore));
		} catch (FileNotFoundException e) {
		}
	try {
		if(reader==null) {

			 writer = new BufferedWriter(new FileWriter(nomFichierScore));
			Date temps = new Date(tempsTotal);
			    SimpleDateFormat formatDate = new SimpleDateFormat("mm:ss");
			            String formatTemps = formatDate.format(temps);
			            writer.write("Le Joueur "+nomJoueur);
						writer.write(" avec un Score ("+newBestScore);
						writer.write(") et un temps "+formatTemps);
						writer.newLine();
						writer.close();
		}else {
			int e=0;
		 for(int j=0;(content = reader.readLine())!=null;j++) {
			 numberLines++;
			 namePlayer[j]="";
			 score="";
			for(int i=10;content.charAt(i)!=' ';i++) {
				namePlayer[j]=namePlayer[j]+content.charAt(i);
			}
			
			for(e=0;e<content.length();e++) {
				if(content.charAt(e)=='(')
					break;
			}
			for(int i=e+1;content.charAt(i)!=')';i++) {
				score=score+content.charAt(i);
			}
			scorePlayer[j]=Integer.parseInt(score);

		 }
		 
	int i=0	 ;
for(;i<scorePlayer.length && numberLines>=10;i++) {
	if(scorePlayer[i]<newBestScore) {
		scorePlayer[i]=newBestScore;
		namePlayer[i]=nomJoueur;
		break;
	}
}
if(scorePlayer.length!=i || numberLines<10) {
	 Date temps = new Date(tempsTotal);
	    SimpleDateFormat formatDate = new SimpleDateFormat("mm:ss");
	            String formatTemps = formatDate.format(temps);
	            resultat="";
	            resultat+="Le Joueur "+nomJoueur;
	            resultat+=" avec un Score ("+newBestScore;
	            resultat+=") et un temps "+formatTemps;
reader.close();
if(numberLines<10) {
	boolean change=false;
	int t=0;
for(;t<numberLines;t++) {
	if(newBestScore > scorePlayer[t]) {
		change=true;
		break;
	}
}


if(change) {
	reader=new BufferedReader(new FileReader(nomFichierScore));

	StringBuilder a=new StringBuilder();
		for(int j=0;(content = reader.readLine())!=null;j++) {
	if(j==t) {
		
		a.append(resultat+"\n"+content+"\n");
	}else {
		a.append(content+"\n");
	}
	}
		 writer = new BufferedWriter(new FileWriter(nomFichierScore));
		writer.write(a.toString());
		writer.close();
		reader.close();		
}else {
	 writer = new BufferedWriter(new FileWriter(nomFichierScore,true));
	writer.write(resultat);
	writer.newLine();
	writer.close();
}
reader.close();
}
else if(scorePlayer.length!=i){

	reader=new BufferedReader(new FileReader(nomFichierScore));

StringBuilder a=new StringBuilder();
	for(int j=0;(content = reader.readLine())!=null;j++) {
if(j==i) {
	
	a.append(resultat+"\n");
}else {
	a.append(content+"\n");
}
}
	 writer = new BufferedWriter(new FileWriter(nomFichierScore));
	writer.write(a.toString());
	writer.close();
	reader.close();
}

}	 
reader.close();		
		}
		
}catch(Exception e) {
	e.printStackTrace();
}
	
	}
	/**
	 * Increases the player's score on winning and displays the current and best
	 * scores.
	 */
	public void add1ToScoreWhenWin() {
		score++;
	}

	/**
	 * Resets the player's score to zero on losing.
	 */
	public void resetScoreWhenLoose() {
		score = 0;
	}

	/**
	 * Retrieves the current score.
	 *
	 * @return Current score as a integer value
	 */
	public int getActualScore() {
		return score;
	}

	/**
	 * Connects to an HTTP server to get a clue using a word2vec algorithm.
	 *
	 * @param s The word for which the clue is requested
	 * @return The clue obtained from the server
	 */
	public String getIndice(String wordToGetInt) throws Exception {
		try {
			String url = "http://localhost:5000/getIndice?s=" + wordToGetInt.toLowerCase();
			URL endpoint = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
				String responseBody = response.toString();
				connection.disconnect();
				responseBody = responseBody.replaceAll("[^a-zA-Z0-9]", "");
				if (responseBody.startsWith("resultat")) {
					responseBody = responseBody.substring("resultat".length());
				}
				StringBuffer x = new StringBuffer(responseBody.substring(6));
				String indiceConverti = x.toString();
				return indiceConverti;
			} else {
				connection.disconnect();
				return "Connexion echouee";
			}
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Starts the game where the player guesses a word.
	 *
	 * @throws WrongNumberOfLetterException Throws when an invalid input for
	 *                                      longeurDuMot is provided
	 */
	public void PlayWordle() throws Exception {
		motChercher=getRandomWord(longueurMotParDefault);	
	}

	public String comparer() {
	StringBuffer motChercherBuffer=new StringBuffer(motChercher);
		
	if (wordExistsInTheLocalDictionnary(motChoisiParLeJoueur.toString())
			|| wordExistsInTheParserDictionnary(motChoisiParLeJoueur.toString(), longueurMotParDefault)) {

		if (motChercher.toString().equals(motChoisiParLeJoueur.toString())) {
			indiceUtilise = true;
			for (int i = 0; i <motChoisiParLeJoueur.length(); i++) {
				motChoisiParLeJoueur.setCharAt(i,'*');
			}
		} else {
			for (int i = 0; i < motChercher.length(); i++) {
				if (motChoisiParLeJoueur.charAt(i) == motChercher.charAt(i)) {
					motChoisiParLeJoueur.setCharAt(i, '*');
					motChercherBuffer.setCharAt(i, '/');
				}
			}
			for (int i = 0; i < motChercher.length(); i++) {
				for (int j = 0; j < motChercher.length(); j++) {
					if (motChoisiParLeJoueur.charAt(i) == motChercher.charAt(j)
							&& motChercherBuffer.charAt(j) != '/') {
						motChoisiParLeJoueur.setCharAt(i, '%');
						motChercherBuffer.setCharAt(j, '/');
					}
				}
			}
	
	}
}else {
	motChoisiParLeJoueur.delete(0, motChoisiParLeJoueur.length());
}
	return motChoisiParLeJoueur.toString();
	}}
