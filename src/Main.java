package application;
import javafx.geometry.Insets;

import java.io.File;
import java.util.Iterator;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.css.*;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage arg0) throws Exception {
		Wordle jeu =new Wordle();
		jeu.PlayWordle();
		
		LoseOption loseOption = new LoseOption();
		MainPage mainpage=new MainPage(5,jeu,loseOption);
		MenuPage menupage=new MenuPage(jeu);
		InitialPage initialpage=new InitialPage(jeu);
		AcceuilPage acceuilpage = new AcceuilPage();
		AproposPage apropospage  = new AproposPage();
		ScorePage scorepage = new ScorePage();
		Scene sc = new Scene(acceuilpage,750,700);
		HowToPlayPage howtoplay = new HowToPlayPage();
		ScoreSave scoreSave=new ScoreSave();
		GameMode x =new GameMode(initialpage,acceuilpage,menupage,mainpage,howtoplay,loseOption,apropospage,scoreSave,scorepage);
		acceuilpage.interactionPageInitialPage(arg0, initialpage, sc);
		initialpage.interactionPageMainPage(arg0, mainpage,sc);
		initialpage.interactionPageAcceuilPage(arg0, acceuilpage, sc);
		mainpage.interactionPageMenuPage(arg0, menupage,sc);
		menupage.interactionPageHowToPlayPage(arg0, howtoplay, sc);
		
		menupage.interactionPageInitialPage(arg0,initialpage,scoreSave,sc);
		menupage.interactionGameMode(arg0,x,sc);
		menupage.interactionPageMainPage(arg0, mainpage,sc);
		howtoplay.interactionPageMenuPage(arg0, menupage, sc);
		
		initialpage.interactionPageAproposPage(arg0, apropospage, sc);
		apropospage.interactionPageInitialPage(arg0, initialpage, sc);
		loseOption.interactionPageMainPageAndInitialPage(arg0, mainpage, initialpage, sc);
		scorepage.interactionPageInitialPage(arg0, initialpage, sc);
		initialpage.interactionPageScorePage(arg0, scorepage, sc);
		responsivityAcceuilPage(arg0,acceuilpage);
		arg0.setScene(sc);
		arg0.setMinWidth(750);
		arg0.setMinHeight(650);
		arg0.setTitle("WORDLE");
		arg0.show();
		
	}
	 
	public  void responsivityAcceuilPage(Stage arg0,AcceuilPage GP) {
		
		ChangeListener<Number> eventScreen=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
				if(arg1.doubleValue() < arg2.doubleValue()) {
			GP.setMargin(GP.getBody(), new Insets(0, 0, 0,(arg2.doubleValue()-750)/2));            
			}
			if(arg1.doubleValue() > arg2.doubleValue()) {
				GP.setMargin(GP.getBody(), new Insets(0, 0,0,(arg2.doubleValue()-750-(arg1.doubleValue()- arg2.doubleValue()))/2));
			}	
			
			};
		};
		
		arg0.widthProperty().addListener(eventScreen);
	}
	
	
}
