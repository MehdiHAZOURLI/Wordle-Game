package application;

import java.io.File;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author HAZOURLI Mohamed Mehdi
 * 
 */
public class LoseOption extends GridPane {

	private Button[] option;
	private Label affichageScore;
	private TextField nomPrenom;
	private HBox options;
	private VBox head;

	public LoseOption() {

		super();
		setMaxHeight(400);
		setMaxWidth(800);

		this.option = new Button[2];
		this.affichageScore = new Label();
		this.nomPrenom = new TextField();
		this.options = new HBox();
		this.head = new VBox();

		option[0] = new Button("SAVE");
		option[1] = new Button("EXIT");
		affichageScore = new Label("0 POINTS");
		createLevelsDifficulty();
		createTextField();
		createButtonsOptions();
		addRow(1, head);
		desactive();
	}

	public Label getTextScore() {
		return affichageScore;
	}

	void createButtonsOptions() {
		for (int i = 0; i < 2; i++) {
			option[i].setBackground(Background.fill(Color.TRANSPARENT));
			option[i].setMinHeight(60);
			option[i].setMinWidth(150);

		}
		option[0].textFillProperty().set(Color.GREEN);
		option[1].textFillProperty().set(Color.RED);

		options.getChildren().addAll(option[0], option[1]);
		options.setSpacing(30);
		options.setPadding(new Insets(0, 0, 20, 0));
		head.getChildren().add(options);
	}

	void createTextField() {
		nomPrenom.setMinHeight(40);
		nomPrenom.setMinWidth(250);
		nomPrenom.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (" ".equals(event.getCharacter())) {
                event.consume();  }
        });
		head.getChildren().add(nomPrenom);
		head.setMargin(nomPrenom, new Insets(30, 0, 30, 0));
	}

	void createLevelsDifficulty() {
		affichageScore.setMinHeight(80);
		affichageScore.setMinWidth(200);
		affichageScore.setBackground(Background.fill(Color.TRANSPARENT));

		this.affichageScore.setAlignment(Pos.CENTER);
		head.getChildren().add(affichageScore);
		head.setMargin(affichageScore, new Insets(10, 0, 0, 0));
		affichageScore.setPadding(new Insets(0, 0, 0, 80));
	}

	public VBox getHead() {
		return head;
	}

	void interactionPageMainPageAndInitialPage(Stage sc, MainPage E, InitialPage I, Scene scene) {

		option[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {

				playAudio("sound/sound.wav");
				E.clearTimerCount();
				scene.setRoot(I);
				sc.setScene(scene);
				responsivityInitialPage(sc, I);
				I.inverseOption();

			}

		});

		option[0].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {

				playAudio("sound/sound.wav");
				Platform.runLater(() -> {

					E.jeu.setBestScore(E.jeu.getActualScore(), E.jeu.tempsEcoule, nomPrenom.getText());
					E.restore(E.getSize());
					E.clearTimerCount();
					E.InitialtimerCount();
					E.timerCount(0);
				});
			}

		});

	}

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

	public void desactive() {
		setStyle(
				"-fx-border-color:WHITE;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
		setBackground(Background.fill(Color.BLACK));
		option[0].setStyle(
				"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;");
		option[1].setStyle(
				"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;");
		nomPrenom.setBackground(Background.fill(Color.WHITE));
		this.nomPrenom.setStyle(
				"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:10px;-fx-font-size:20px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
		this.affichageScore.textFillProperty().set(Color.WHITE);
		this.affichageScore.setStyle("-fx-font-size:25px;");

	}

	public void active() {
		setStyle(
				"-fx-border-color:BLACK;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
		setBackground(Background.fill(Color.WHITE));
		option[0].setStyle(
				"-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;");
		option[1].setStyle(
				"-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;");
		this.nomPrenom.setStyle(
				"-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:10px;-fx-font-size:20px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
		this.affichageScore.textFillProperty().set(Color.BLACK);
	}
}
