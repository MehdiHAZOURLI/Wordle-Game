package application;

import java.io.File;

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
 * @author BOUROUINA Akram
 * 
 */
public class ScoreSave extends GridPane {

	private Button[] option;
	private Label affichageScor;
	private TextField nomPrenom;
	private HBox options;
	private VBox head;

	public ScoreSave() {

		super();
		setMaxHeight(400);
		setMaxWidth(800);

		this.option = new Button[2];
		this.affichageScor = new Label();
		this.nomPrenom = new TextField();
		this.options = new HBox();
		this.head = new VBox();

		option[0] = new Button("SAVE");
		option[1] = new Button("EXIT");
		affichageScor = new Label("0 POINTS");
		createLevelsDifficulty();
		createTextField();
		createButtonsOptions();
		addRow(1, head);
desactive();
	}

	public Label getAffichageScor() {
		return affichageScor;
	}

	public Button[] getOption() {
		return option;
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

	public TextField getNomPrenom() {
		return nomPrenom;
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
		affichageScor.setMinHeight(80);
		affichageScor.setMinWidth(200);
		affichageScor.setBackground(Background.fill(Color.TRANSPARENT));

		this.affichageScor.setStyle("-fx-font-size:25px;");
		this.affichageScor.setAlignment(Pos.CENTER);
		head.getChildren().add(affichageScor);
		head.setMargin(affichageScor, new Insets(10, 0, 0, 0));
		affichageScor.setPadding(new Insets(0, 0, 0, 80));
	}

	public VBox getHead() {
		return head;
	}

	void interactionPageInitialPage(Stage sc, InitialPage I, Scene scene) {

		option[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {

				playAudio("sound/sound.wav");
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
				I.jeu.setBestScore(I.jeu.getActualScore(), I.jeu.tempsEcoule, nomPrenom.getText());
				scene.setRoot(I);
				sc.setScene(scene);
				responsivityInitialPage(sc, I);
				I.inverseOption();

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

	public void active() {
		setStyle(
				"-fx-border-color:BLACK;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
		setBackground(Background.fill(Color.WHITE));
		for (int i = 0; i < getOption().length; i++) {
			getOption()[i].setStyle(
					"-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;");
		}
		getNomPrenom().setBackground(Background.fill(Color.WHITE));

		getNomPrenom().setStyle(
				"-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:10px;-fx-font-size:20px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
		this.affichageScor.textFillProperty().set(Color.BLACK);

	}

	public void desactive() {
		setStyle(
				"-fx-border-color:#949494;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
		setBackground(Background.fill(Color.BLACK));
		for (int i = 0; i < getOption().length; i++) {
			getOption()[i].setStyle(
					"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;");
		}
		getNomPrenom().setBackground(Background.fill(Color.WHITE));
		getNomPrenom().setStyle(
				"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:10px;-fx-font-size:20px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
		this.affichageScor.textFillProperty().set(Color.WHITE);

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

}
