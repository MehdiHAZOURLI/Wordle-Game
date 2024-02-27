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
public class AproposPage  extends GridPane {

	private Button[]option;
	 private Label[] nomPrenom;
	 private Label[] gitHub;
	 private HBox[] apropos;
	 private VBox head;
	
	public AproposPage() {
		super();
		this.option =new Button[1];
		this.nomPrenom = new Label[4];
		this.gitHub = new Label[4];
		this.apropos = new HBox[4];
		this.head = new VBox();
		
		option[0] = new Button("Back");
		
		nomPrenom[0] = new Label("Hazourli Mehdi");
		nomPrenom[1] = new Label("Bourouina Akram");
		nomPrenom[2] = new Label("Mejai Wajdi");
		nomPrenom[3] = new Label("Geyer Rayane");
		
		gitHub[0] = new Label("hazourlimm2020@gmail.com");
		gitHub[1] = new Label("akrambourouina462@gmail.com");
		gitHub[2] = new Label("wajdimejai78@gmail.com");
		gitHub[3] = new Label("rayane.geyer@alumni.univ-avignon.fr");
		
		
		createButtonsOptions();
		createLabel();
		addRow(1, head);
		desactive();
	}
	 
	void createButtonsOptions() {
		
		
		option[0].setBackground(Background.fill(Color.TRANSPARENT));
		option[0].setMinHeight(60);
		option[0].setMinWidth(150);
		head.getChildren().add(option[0]);
		head.setMargin(option[0], new Insets(10, 0, 0, 20));
		
	} 
	
	void createLabel() {
		for(int i=0;i<4;i++) {
			apropos[i] = new HBox();
			nomPrenom[i].setMinHeight(70);
			nomPrenom[i].setMinWidth(200);
			this.nomPrenom[i].setAlignment(Pos.CENTER);
			
			gitHub[i].setMinHeight(70);
			gitHub[i].setMinWidth(300);
			this.gitHub[i].setAlignment(Pos.CENTER);

		}
		apropos[0].getChildren().addAll(nomPrenom[0],gitHub[0]);
		apropos[0].setSpacing(30);
		apropos[0].setPadding(new Insets(80, 0, 0,140));
		
		for(int i=1;i<4;i++) {
			apropos[i].getChildren().addAll(nomPrenom[i],gitHub[i]);
			apropos[i].setSpacing(30);
			apropos[i].setPadding(new Insets(40, 0, 0,140));	
		}
		head.getChildren().addAll(apropos[0],apropos[1],apropos[2],apropos[3]);
	}
	
	public VBox getHead() {
		return 	head;
	}
	
	void interactionPageInitialPage(Stage sc,InitialPage I,Scene scene) {
		
		option[0].setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent arg) {

			playAudio("sound/sound.wav");
			scene.setRoot(I);
			sc.setScene(scene);
			responsivityInitialPage(sc,I);
		}
		
	});
	}
	
	
	public void playAudio(final String filePath) {
		File file=new File(filePath);
		   Media media=new Media(file.toURI().toString());
		   MediaPlayer mediaplayer=new MediaPlayer(media);
		Task task = new Task<Void>() {
			    @Override public Void call() {
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
		setBackground(Background.fill(Color.BLACK));
		option[0].textFillProperty().set(Color.WHITE);
		option[0].setStyle("-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
		for(int i=0;i<4;i++) {
			this.nomPrenom[i].textFillProperty().set(Color.WHITE);
			this.nomPrenom[i].setStyle("-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:18px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255	), 10, 0, 0, 0);");
			
			this.gitHub[i].textFillProperty().set(Color.WHITE);
			this.gitHub[i].setStyle("-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:18px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255	), 10, 0, 0, 0);");

		}
	}
	public void active() {
		setBackground(Background.fill(Color.WHITE));
		option[0].setStyle("-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
		option[0].textFillProperty().set(Color.BLACK);
		for(int i=0;i<4;i++) {
			this.nomPrenom[i].textFillProperty().set(Color.BLACK);
			this.nomPrenom[i].setStyle("-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:18px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
			
			this.gitHub[i].textFillProperty().set(Color.BLACK);
			this.gitHub[i].setStyle("-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:18px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");

		}
	}
}
