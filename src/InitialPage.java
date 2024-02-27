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
import javafx.scene.control.ProgressBar;
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
public class InitialPage extends GridPane {
 private Label[] difficulty;
 private HBox level;
String nameLevel;
private Button[] option;
 private VBox body;
 Wordle jeu;
public InitialPage(Wordle jeu) {
	super();
	this.jeu=jeu;
	this.difficulty = new Label[3];
	this.option = new Button[4];
	this.body = new VBox();
	
	this.level = new HBox();
	
	option[0] = new Button("PLAY");
	option[1] = new Button("Scores");
	option[2] = new Button("CREDITS");
	option[3] = new Button("EXIT");
	
	difficulty[0] = new Label("EASY");
	difficulty[1] = new Label("MEDUIM");
	difficulty[2] = new Label("HARD");
	
	createLevelsDifficulty() ;
	createButtonsOptions ();
	addRow(1, body);
	desactive();
}
 
public Label[] getDifficulty() {
	return difficulty;
}

public Button[] getOption() {
	return option;
}

void createButtonsOptions() {
	int var = 0;
	for (int i = 0; i <4; i++) {
	option[i].setFocusTraversable(false);
	option[i].setBackground(Background.fill(Color.TRANSPARENT));
	option[i].setMinHeight(60);
	option[i].setMinWidth(230);
	body.getChildren().add(option[i]);
	if (var==0) {
		body.setMargin(option[var], new Insets(150, 0, 0, 270));
	} else {
		body.setMargin(option[var], new Insets(60, 0, 0, 270));
	}
	var++;
	}
	
}

void createLevelsDifficulty() {
	for(int i=0;i<3;i++) {
		difficulty[i].setMinHeight(60);
		difficulty[i].setMinWidth(120);
		this.difficulty[i].setAlignment(Pos.CENTER);

	}
	level.getChildren().addAll(difficulty[0],difficulty[1],difficulty[2]);
	level.setSpacing(30);
	level.setPadding(new Insets(40, 0, 0,170));
}
void  defineDifficulty(Stage sc,MainPage x,Scene scene) {
	 int i=0;
	for(;i<3;i++) {
		final int z=i;
		difficulty[i].setOnMouseClicked(new EventHandler<MouseEvent>(){
		@Override
		public void handle(MouseEvent arg) {
			int size;
			nameLevel=difficulty[z].getText();
			if(nameLevel=="EASY") {
				 size=5;
			}else if(nameLevel=="MEDUIM"){
				 size=6;
			}else{
				 size=7;
			}
			jeu.longueurMotParDefault=size;
			jeu.resetScoreWhenLoose();
			jeu.lancerTimer();
			jeu.tempsEcoule=0;
			x.clearTimerCount();
			x.InitialtimerCount();
			x.timerCount(0);
			playAudio("sound/sound.wav");
			x.restore(size);
			if(getBackground().getFills().get(0).getFill()!=Color.BLACK)
				x.active();
			else
				x.desactive();
			scene.setRoot(x);		
			sc.setScene(scene);
			responsivityMainPage(sc,x);
		}
		
	});
	}
}



void interactionPageMainPage (Stage sc,MainPage x,Scene scene) {
	option[0].setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg) {
                
				playAudio("sound/sound.wav");
				addRow(0,level);
				defineDifficulty(sc,x,scene);
				inverseOption();
			}
			
		});
	
       }

void interactionPageAcceuilPage(Stage sc,AcceuilPage a,Scene scene) {
	
	option[3].setOnAction(new EventHandler<ActionEvent>(){
	@Override
	public void handle(ActionEvent arg) {

		playAudio("sound/sound.wav");
		scene.setRoot(a);
		sc.setScene(scene);
		responsivityAcceuilPage(sc,a);
	}
	
});
}

void interactionPageAproposPage(Stage sc,AproposPage a,Scene scene) {
	
	option[2].setOnAction(new EventHandler<ActionEvent>(){
	@Override
	public void handle(ActionEvent arg) {

		playAudio("sound/sound.wav");
		scene.setRoot(a);
		sc.setScene(scene);
		responsivityAproposPage(sc,a);
	}
	
});
}


void interactionPageScorePage(Stage sc,ScorePage s,Scene scene) {
	
	option[1].setOnAction(new EventHandler<ActionEvent>(){
	@Override
	public void handle(ActionEvent arg) {

		playAudio("sound/sound.wav");
		s.readFile();
		s.showPlayer();
		scene.setRoot(s);
		sc.setScene(scene);
		responsivityScorePage(sc,s);
	}
	
});
}

public VBox getBody() {
	return body;
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

public  void responsivityMainPage(Stage arg0,MainPage GP) {
	arg0.widthProperty().addListener(new ChangeListener<Number>(){
		@Override
		public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
		if(arg1.doubleValue() < arg2.doubleValue()) {
		GP.setPadding( new Insets(0, 0, 0, (arg2.doubleValue()-750)/2));
		}
		if(arg1.doubleValue() > arg2.doubleValue()) {
			GP.setPadding( new Insets(0, 0,0,(arg2.doubleValue()-750-(arg1.doubleValue()- arg2.doubleValue()))/2));
		}	
		
		};
	});	
}

public  void responsivityAcceuilPage(Stage arg0,AcceuilPage GP) {
	arg0.widthProperty().addListener(new ChangeListener<Number>(){
		@Override
		public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
		if(arg1.doubleValue() < arg2.doubleValue()) {
		GP.setMargin(GP.getBody(), new Insets(0, 0, 0,(arg2.doubleValue()-750)/2));            
		}
		if(arg1.doubleValue() > arg2.doubleValue()) {
			GP.setMargin(GP.getBody(), new Insets(0, 0,0,(arg2.doubleValue()-750-(arg1.doubleValue()- arg2.doubleValue()))/2));
		}	
		
		};
	});
}
   
public void responsivityAproposPage(Stage arg0, AproposPage GP) {
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


public void responsivityScorePage(Stage arg0, ScorePage GP) {
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
	
public HBox getLevel() {
	return level;
}
// sert a inverser les option d'apparataire et disparatre l'option JOUER et les difficulty
public void inverseOption() {

	if(body.getChildren().contains(option[0])) {
		body.getChildren().remove(option[0]);
	}else {
		body.getChildren().add(0, option[0]);
		getChildren().removeAll(level);
        
	}
   }
public void active() {

setBackground(Background.fill(Color.WHITE));
for(int i=0;i<getOption().length;i++) {
getOption()[i].textFillProperty().set(Color.BLACK);
getOption()[i].setStyle("-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(0,0,0), 10, 0, 0, 0);");
}
for(int i=0;i<getDifficulty().length;i++) {
	getDifficulty()[i].textFillProperty().set(Color.GREEN);
	getDifficulty()[i].setStyle("-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:18px;-fx-border-radius:7px;");
	
}
}
public void desactive() {
	setBackground(Background.fill(Color.BLACK));
	for(int i=0;i<getOption().length;i++) {
	getOption()[i].textFillProperty().set(Color.WHITE);
	getOption()[i].setStyle("-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:25px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(255,255,255), 10, 0, 0, 0);");
	}
	for(int i=0;i<getDifficulty().length;i++) {
		getDifficulty()[i].textFillProperty().set(Color.GREEN);
		getDifficulty()[i].setStyle("-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:18px;-fx-border-radius:7px;");
		
	}
}
}

