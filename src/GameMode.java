package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

/**
 * @author HAZOURLI Mohamed Mehdi
 * 
 * classe that connect all classes of game, so the dark/light mode work and change for everyone
 */
public class GameMode {
	InitialPage a;
	AcceuilPage b;
	MenuPage c;
	MainPage d;
	HowToPlayPage e;
	LoseOption f;
	AproposPage g;
	ScoreSave h; 
	ScorePage i;
public GameMode(InitialPage a,AcceuilPage b,MenuPage c,MainPage d,HowToPlayPage e,LoseOption f,AproposPage g,ScoreSave h,ScorePage i) {
this.a=a;
this.b=b;
this.c=c;
this.d=d;
this.e=e;
this.f=f;
this.g=g;
this.h=h;
this.i=i;
}
/*
 * light mode
 */
public void active() {
a.active();	
b.active();	
c.active();	
d.active();	
e.active();	
f.active();
g.active();
h.active();
i.active();
}
/*
 * dark mode
 */
public void desactive() {
a.desactive();
b.desactive();
c.desactive();
d.desactive();
e.desactive();
f.desactive();
g.desactive();
h.desactive();
i.active();
}
}