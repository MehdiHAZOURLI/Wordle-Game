package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * @author BOUROUINA Akram
 * 
 */
public class ScorePage extends GridPane {

	private Button option;
	private Label discription;
	private String text;
	private VBox head;
	String[] namePlayer = new String[10];
	int[] scorePlayer = new int[10];

	public ScorePage() {
		super();
		this.option = new Button();
		this.head = new VBox();
		option = new Button("Back");

		discription = new Label();

		createButtonsOptions();
		createFirstLabel();
		addRow(1, head);
		desactive();
	}

	public Button getOption() {
		return option;
	}

	/*
	 * reda from file best scores
	 */
	public void readFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("best_score.txt"));
			String content;
			String score;
			int e;
			for (int j = 0; (content = reader.readLine()) != null; j++) {
				namePlayer[j] = "";
				score = "";
				for (int i = 10; content.charAt(i) != ' '; i++) {
					namePlayer[j] = namePlayer[j] + content.charAt(i);
				}

				for (e = 0; e < content.length(); e++) {
					if (content.charAt(e) == '(')
						break;
				}
				for (int i = e + 1; content.charAt(i) != ')'; i++) {
					score = score + content.charAt(i);
				}
				scorePlayer[j] = Integer.parseInt(score);

			}
		} catch (Exception r) {
			
		}
	}

	void createButtonsOptions() {

		option.setMinHeight(60);
		option.setMinWidth(150);
		head.getChildren().add(option);
		head.setMargin(option, new Insets(10, 0, 0, 20));

	}
/*
 * put players in screen
 */
	void showPlayer() {
		text = "";
		for (int i = 0; i < namePlayer.length; i++) {
			if (namePlayer[i] != null)
				text += "Player Name : " + namePlayer[i] + "        Score : " + scorePlayer[i] + "\n";
		}
		if(text=="")
			discription.setText("Error There is no Score");
		discription.setText(text);

	}

	void createFirstLabel() {
		discription.setMinHeight(200);
		discription.setMinWidth(600);
		head.getChildren().addAll(discription);
		head.setMargin(discription, new Insets(40, 0, 0, 220));
	}

	public VBox getHead() {
		return head;
	}

	void interactionPageInitialPage(Stage sc, InitialPage GP, Scene scene) {

		option.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {

				playAudio("sound/sound.wav");
				scene.setRoot(GP);
				sc.setScene(scene);
				responsivityInitialPage(sc, GP);
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

	public void active() {
		option.setBackground(Background.fill(Color.TRANSPARENT));
		setBackground(Background.fill(Color.WHITE));

		option.setStyle(
				"-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
		option.textFillProperty().set(Color.BLACK);
		this.discription.textFillProperty().set(Color.BLACK);
		this.discription.setStyle("-fx-font-size:18px;");

	}

	public void desactive() {

		option.setBackground(Background.fill(Color.TRANSPARENT));

		setBackground(Background.fill(Color.BLACK));
		option.setStyle(
				"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
		option.textFillProperty().set(Color.WHITE);

		this.discription.textFillProperty().set(Color.WHITE);
		this.discription.setStyle("-fx-font-size:18px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");

	}
}
