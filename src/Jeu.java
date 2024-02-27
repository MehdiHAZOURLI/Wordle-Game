package application;

/**
 * The Jeu class represents a game.
 * It contains information about the game's name and an identifier.
 */
public class Jeu {
    public static String nom = "Wordle";
    private static int id = 0;

    /**
     * Constructor to initialize a game with a name.
     *
     * @param nom The name of the game
     */
    public Jeu() {
        id++;
    }

    /**
     * Retrieves the name of the game.
     *
     * @return The name of the game
     */
    public static String getNom() {
        return nom;
    }

    /**
     * Retrieves the identifier of the game.
     *
     * @return The identifier of the game
     */
    public static int getId() {
        return id;
    }
}