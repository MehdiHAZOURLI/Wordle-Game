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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
public class HowToPlayPage extends GridPane {

	private Button []option;
	private Label[] discription;
	private HBox how;
	private VBox head;
    
	public HowToPlayPage() {
		super();
		this.option = new Button[6];
		this.discription = new Label[2];
		this.how = new HBox();
		this.head = new VBox();
		option[0] = new Button("Back");
		discription[0] = new Label("Guess the masked word in 6 tries.\nEach guess should be a valid (5 or 6 or 7)-letter word, \n and you cannot enter random or nonsensical letters. \nPress the Enter button to submit your answer.\nAfter your submission, the tile colors will change as described below");
		discription[1] = new Label("The letter I is in the word and in the correct position.\nThe letters F And M is in the word but in the wrong position.\nThe letters O And T is not in the word at all.");

		createButtonsOptions();
		 createFirstLabel();
		 initoption();
		 createSecondLabel();
		 
		addRow(1, head);
		desactive();
	}

	public Button getOption() {
		return option[0];
	}

	void createButtonsOptions() {

		option[0].setMinHeight(60);
		option[0].setMinWidth(150);
		head.getChildren().add(option[0]);
		head.setMargin(option[0], new Insets(10, 0, 0, 20));

	}
	void createFirstLabel() {
		discription[0].setMinHeight(200);
		discription[0].setMinWidth(600);
		head.getChildren().addAll(discription[0]);
		head.setMargin(discription[0],new Insets(40, 0, 0,50));
	}
	
	void createSecondLabel() {
		discription[1].setMinHeight(200);
		discription[1].setMinWidth(500);
		head.getChildren().addAll(discription[1]);
		head.setMargin(discription[1],new Insets(40, 0, 0,50));
	}
	
	public void initoption() {
		for (int i = 1; i < 6; i++) {
			option[i] = new Button();
			option[i].setFocusTraversable(false);
			how.getChildren().addAll(option[i]);
			how.setSpacing(20);
		}
		option[1].setText("M");
		option[2].setText("O");
		option[3].setText("T");
		option[4].setText("I");
		option[5].setText("F");

		option[1].setStyle("-fx-background-color:#FF5733;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px; -fx-effect: dropshadow(gaussian, rgb(255, 87, 51), 10, 0, 0, 0);");
		option[2].setStyle("-fx-background-color:#808080;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px; -fx-effect: dropshadow(gaussian, rgb(128, 128, 128), 10, 0, 0, 0);");
		option[3].setStyle("-fx-background-color:#808080;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px; -fx-effect: dropshadow(gaussian, rgb(128, 128, 128), 10, 0, 0, 0);");
		option[4].setStyle("-fx-background-color:#00E640;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px; -fx-effect: dropshadow(gaussian, rgb(0,230,64), 10, 0, 0, 0);");
		option[5].setStyle("-fx-background-color:#FF5733;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px; -fx-effect: dropshadow(gaussian, rgb(255, 87, 51), 10, 0, 0, 0);");

		head.getChildren().addAll(how);
		head.setMargin(how,new Insets(40, 0, 0,50));
	}

	public VBox getHead() {
		return head;
	}

	void interactionPageMenuPage(Stage sc, MenuPage m, Scene scene) {

		option[0].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {

				playAudio("sound/sound.wav");
				scene.setRoot(m);
				sc.setScene(scene);
				responsivity(sc, m);
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

	public void responsivity(Stage arg0, MenuPage GP) {
		arg0.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
				if (arg1.doubleValue() < arg2.doubleValue()) {
					GP.setMargin(GP.getBody(), new Insets(0, 0, 0, (arg2.doubleValue() - 750) / 2));
				}
				if (arg1.doubleValue() > arg2.doubleValue()) {
					GP.setMargin(GP.getBody(), new Insets(0, 0, 0,
							(arg2.doubleValue() - 750 - (arg1.doubleValue() - arg2.doubleValue())) / 2));
				}

			};
		});
	}

	public void active() {
		option[0].setBackground(Background.fill(Color.TRANSPARENT));
		setBackground(Background.fill(Color.WHITE));
		for(int i=1;i<option.length;i++) {
			option[i].textFillProperty().set(Color.BLACK);
		}
		option[0].setStyle("-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
		option[0].textFillProperty().set(Color.BLACK);
		for(int i=0;i<2;i++) {
			this.discription[i].textFillProperty().set(Color.BLACK);
			this.discription[i].setStyle("-fx-font-size:18px;");

		}
	}

	public void desactive() {
		
		option[0].setBackground(Background.fill(Color.TRANSPARENT));
		
		for(int i=1;i<option.length;i++) {
			option[i].textFillProperty().set(Color.WHITE);
		}
		setBackground(Background.fill(Color.BLACK));
		option[0].setStyle("-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
		option[0].textFillProperty().set(Color.WHITE);
		for(int i=0;i<2;i++) {
			this.discription[i].textFillProperty().set(Color.WHITE);
			this.discription[i].setStyle("-fx-font-size:18px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");

		}
	}
}
