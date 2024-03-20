# Wordle

Wordle is a word guessing game that challenges players to find a secret word by making guesses and receiving feedback on the presence of correct letters. In our version, we include numerous features such as hint management and score saving. We also parse the [French wordlist from Wiktionary](https://en.wiktionary.org/wiki/Wiktionary:Frequency_lists/French_wordlist_1750) to obtain a database of our words.

## Installation

- For Windows: Download `jeu_Windows` folder and execute `play.bat` to play the game directly.
- For Linux: Download `jeu_Linux` folder then execute the command to launch the game :
-  `java --module-path ./javafx-sdk-21/lib --add-modules javafx.controls,javafx.graphics,javafx.fxml,javafx.media -jar app.jar`

## Indice Option

If you want to enable the Indice Option in the game,install and execute Python script `word2vecmodel.py` found in the `Script` folder.
## Photos
Here are some screenshots from our Wordle game:
![Start Game](https://github.com/MehdiHAZOURLI/Wordle-Game/blob/main/Ressources/photos/Wordle1.png)
![Dark Mode](https://github.com/MehdiHAZOURLI/Wordle-Game/blob/main/Ressources/photos/Wordle2.png)
![Light Mode](https://github.com/MehdiHAZOURLI/Wordle-Game/blob/main/Ressources/photos/Wordle3.png)
## Authors

- [@bourouinaakram](https://github.com/bourouinaakram)
- [geyer-rayane](https://github.com/geyer-rayane)
- [@MehdiHAZOURLI](https://github.com/MehdiHAZOURLI)
- [WajdiMejai](https://github.com/WajdiMejai)


## Project Status
Active


## File Structure

- `src`: Contains the source code java of the game.
- `Resources`: Includes photos and sounds used in the game.
- `Script`: Python code for retrieving game indices.

## License Details

This project is non-profit and non-commercial. It is developed solely for learning and improvement purposes.

