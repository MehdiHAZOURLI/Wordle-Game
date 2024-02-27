package application;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

/**
 * @author BOUROUINA Akram
 * 
 */
public class MenuPage extends GridPane {

	Button[] option;
	private VBox body;
	Wordle jeu;

	public MenuPage(Wordle jeu) {
		super();
		this.jeu = jeu;
		this.option = new Button[5];
		this.body = new VBox();
		option[0] = new Button("CONTINUE");
		option[1] = new Button("RESTART");
		option[2] = new Button("LIGHT MODE");
		option[3] = new Button("HOW TO PLAY");
		option[4] = new Button("EXIT");
		option[4].textFillProperty().set(Color.RED);

		createButtonsOptions();
		add(body, 0, 0);
		desactive();
	}

	public Button[] getOption() {
		return option;
	}

	/**
	 * for creating buttons of Options like Continue, Restart
	 */
	void createButtonsOptions() {

		int var = 0;
		for (int i = 0; i < 5; i++) {
			option[i].setBackground(Background.fill(Color.TRANSPARENT));
			option[i].setMinHeight(60);
			option[i].setMinWidth(230);
			body.getChildren().add(option[i]);
			if (var == 0) {
				body.setMargin(option[var], new Insets(70, 0, 0, 270));
			} else {
				body.setMargin(option[var], new Insets(40, 0, 0, 270));
			}
			var++;
		}
	}

	/**
	 * for the interactivity it changes page(MainPage) of scene to show it in
	 * stage(screen)
	 * 
	 * @param sc
	 * @param x
	 * @param scene
	 */
	void interactionPageMainPage(Stage sc, MainPage x, Scene scene) {

		option[0].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {

				playAudio("sound/sound.wav");
				x.timerCount(0);
				scene.setRoot(x);
				sc.setScene(scene);
				responsivityMainPage(sc, x);
			}

		});

		option[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {

				playAudio("sound/sound.wav");
				jeu.tempsEcoule = 0;
				x.timerCount(0);
				scene.setRoot(x);
				sc.setScene(scene);
				jeu.resetScoreWhenLoose();
				responsivityMainPage(sc, x);
				x.restore(x.getSize());
				if(getBackground().getFills().get(0).getFill()!=Color.BLACK)
					x.active();
				else
					x.desactive();
			}

		});

	}

	/**
	 * for turning from light/dark to dark/light mode
	 * 
	 * @param sc
	 * @param x
	 * @param scene
	 */
	void interactionGameMode(Stage sc, GameMode x, Scene scene) {
		option[2].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {

				playAudio("sound/sound.wav");
				if(getBackground().getFills().get(0).getFill()==Color.BLACK) {
					option[2].setText("DARK MODE");
					x.active();}
				else {
					option[2].setText("LIGHT MODE");
					x.desactive();
				}
			
			}

		});
	}
	/**
	 * for the interactivity it changes page(InitialPage) of scene to show it in
	 * stage(screen)
	 * 
	 * @param sc
	 * @param I
	 * @param scene
	 * @param r
	 */
	void interactionPageInitialPage(Stage sc, InitialPage I,ScoreSave r , Scene scene) {
		option[4].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {

				playAudio("sound/sound.wav");
				r.interactionPageInitialPage(sc, I, scene);
				getChildren().clear();
				r.setPadding(new Insets(0, 201, 0, 201));
				setMargin(r, new Insets(0, 0, 0, 7));
				setPadding(new Insets(200, 0, 0, 0));
				r.getAffichageScor().setText(jeu.getActualScore() + " POINTS");
				add(r, 0, 0);

				
			}

		});

	}

	/**
	 * for the interactivity it changes page(HowToPlayPage) of scene to show it in
	 * stage(screen)
	 * 
	 * @param sc
	 * @param h
	 * @param scene
	 */
	void interactionPageHowToPlayPage(Stage sc, HowToPlayPage h, Scene scene) {

		option[3].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {

				playAudio("sound/sound.wav");
				scene.setRoot(h);
				sc.setScene(scene);
				responsivityHowToPlayPage(sc, h);
			}

		});
	}

	public VBox getBody() {
		return body;
	}

	/**
	 * get file path of sound and play it
	 * 
	 * @param filePath
	 */
	public void playAudio(final String filePath) {
		File file = new File(filePath);
		Media media = new Media(file.toURI().toString());
		MediaPlayer mediaplayer = new MediaPlayer(media);
		Task task = new Task<Void>() {
			@Override
			public Void call() {
				mediaplayer.play();
				return null;
			}
		};
		new Thread(task).start();
	}

	public void responsivityMainPage(Stage arg0, MainPage GP) {
		arg0.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
				if (arg1.doubleValue() < arg2.doubleValue()) {
					GP.setPadding(new Insets(0, 0, 0, (arg2.doubleValue() - 750) / 2));
				}
				if (arg1.doubleValue() > arg2.doubleValue()) {
					GP.setPadding(new Insets(0, 0, 0,
							(arg2.doubleValue() - 750 - (arg1.doubleValue() - arg2.doubleValue())) / 2));
				}

			};
		});
	}

	public void active() {

		setBackground(Background.fill(Color.WHITE));
		for (int i = 0; i < getOption().length; i++) {
			if (i != 4)
				getOption()[i].textFillProperty().set(Color.BLACK);
			getOption()[i].setStyle(
					"-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;");
		}
	}

	public void desactive() {

		setBackground(Background.fill(Color.BLACK));
		for (int i = 0; i < getOption().length; i++) {
			if (i != 4)
				getOption()[i].textFillProperty().set(Color.WHITE);
			getOption()[i].setStyle(
					"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;");
		}
	}
	
	
	public void responsivityInitialPage(Stage arg0, InitialPage GP) {
		arg0.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
				if (arg1.doubleValue() < arg2.doubleValue()) {
					GP.setMargin(GP.getLevel(), new Insets(0, 0, 0, (arg2.doubleValue() - 750) / 2));
					GP.setMargin(GP.getBody(), new Insets(0, 0, 0, (arg2.doubleValue() - 750) / 2));
				}
				if (arg1.doubleValue() > arg2.doubleValue()) {
					GP.setMargin(GP.getLevel(), new Insets(0, 0, 0,
							(arg2.doubleValue() - 750 - (arg1.doubleValue() - arg2.doubleValue())) / 2));
					GP.setMargin(GP.getBody(), new Insets(0, 0, 0,
							(arg2.doubleValue() - 750 - (arg1.doubleValue() - arg2.doubleValue())) / 2));
				}

			};
		});
	}

	public void responsivityHowToPlayPage(Stage arg0, HowToPlayPage GP) {
		arg0.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
				if (arg1.doubleValue() < arg2.doubleValue()) {
					GP.setMargin(GP.getHead(), new Insets(0, 0, 0, (arg2.doubleValue() - 750) / 2));
				}
				if (arg1.doubleValue() > arg2.doubleValue()) {
					GP.setMargin(GP.getHead(), new Insets(0, 0, 0,
							(arg2.doubleValue() - 750 - (arg1.doubleValue() - arg2.doubleValue())) / 2));
				}

			};
		});
	}
}
