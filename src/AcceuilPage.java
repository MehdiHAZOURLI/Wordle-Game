package application;

import java.io.File;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class AcceuilPage extends GridPane {


	private Button[]option;
	 private VBox body;
	 
	public AcceuilPage() {
		super();
		this.option =new Button[2];
		this.body = new VBox();
		
		option[0] = new Button("WORDLE");
		option[1] = new Button("EXIT");
		createButtonsOptions();
		addRow(1, body);
		desactive();
	}
	 
	public Button[] getOption() {
		return option;
	}

	
	/*
	 * Creation  of  buttons ( with characteristics)
	 */
	void createButtonsOptions() {
		int var=0;
		for (int i = 0; i <2; i++) {
		option[i].setBackground(Background.fill(Color.TRANSPARENT));
		option[i].setMinHeight(60);
		option[i].setMinWidth(150);
		body.getChildren().add(option[i]);
		if (var==0) {
			body.setMargin(option[var], new Insets(240, 0, 0, 300));
		} else {
			body.setMargin(option[var], new Insets(60, 0, 0, 300));
		}
		var++;
		}
	} 
	
	public VBox getBody() {
		return 	body;
	}
	
	void interactionPageInitialPage(Stage sc,InitialPage i,Scene scene) {
		
		option[0].setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent arg) {

			playAudio("sound/sound.wav");
			scene.setRoot(i);
			sc.setScene(scene);
			responsivityInitialPage(sc,i);
		}
		
	});
		
		option[1].setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg) {
                
				playAudio("sound/sound.wav");
				sc.close();			}		
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
	
	public  void responsivityInitialPage(Stage arg0,InitialPage GP) {
		arg0.widthProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
			if(arg1.doubleValue() < arg2.doubleValue()) {
			GP.setMargin(GP.getLevel(), new Insets(0, 0, 0, (arg2.doubleValue()-750)/2));
			GP.setMargin(GP.getBody(), new Insets(0, 0, 0,(arg2.doubleValue()-750)/2));            
			}
			if(arg1.doubleValue() > arg2.doubleValue()) {
				GP.setMargin(GP.getLevel(), new Insets(0, 0,0,(arg2.doubleValue()-750-(arg1.doubleValue()- arg2.doubleValue()))/2));
				GP.setMargin(GP.getBody(), new Insets(0, 0,0,(arg2.doubleValue()-750-(arg1.doubleValue()- arg2.doubleValue()))/2));
			}	
			
			};
		});

		 	}
	/*
	 * active and desactive for dark and light mode
	 */
	public void active() {
		setBackground(Background.fill(Color.WHITE));
		for(int i=0;i<getOption().length;i++) {
		getOption()[i].textFillProperty().set(Color.BLACK);
		getOption()[i].setStyle("-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
		}
	
	}
	public void desactive() {
		setBackground(Background.fill(Color.BLACK));
		for(int i=0;i<getOption().length;i++) {
		getOption()[i].textFillProperty().set(Color.WHITE);
		getOption()[i].setStyle("-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
		}
	
	}
}
