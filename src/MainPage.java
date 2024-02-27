package application;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.sound.sampled.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author HAZOURLI Mohamed Mehdi
 * 
 */
public class MainPage extends GridPane {
	char[] alphabet = { 'a', 'z', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'q', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
			'm', 'w', 'x', 'c', 'v', 'b', 'n' };
	HBox head;
	Timeline timeline;
	VBox body;
	StringBuffer word = new StringBuffer("");
	Wordle jeu;
	Label[] labelsOfHead;
	HBox[] wordTest;
	Button[] ButtonWordTest;
	HBox rowKeyBoard1;
	HBox rowKeyBoard2;
	HBox rowKeyBoard3;
	VBox keyBoard;
	Button[] keyBoardAlphabet;
	int size;
	Label notification = new Label();
	Label errorName = new Label();
	LoseOption LosePane;

	public MainPage(int size, Wordle jeu, LoseOption LosePane) {
		this.LosePane= LosePane;
		this.jeu = jeu;
		this.size = size;
		initHead(70);

	}

	/**
	 * this function take message as an argument to show him in screen as indice for
	 * now
	 * 
	 * @param message
	 * 
	 */
	public void notification(String message) {
		timerCount(1);
		notification.setMinHeight(90);
		notification.setMinWidth(340);
		notification.setText(message);
		notification.setAlignment(Pos.CENTER);
		setMargin(notification, new Insets(30, 0, 0, (getWidth() - 355) / 2));
		EventHandler<MouseEvent> mouseNotification = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				setOnMouseClicked(null);
				setOnKeyPressed(null);
				getChildren().remove(notification);
				timerCount(0);
			}

		};

		EventHandler<KeyEvent> keyboardNotification = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				setOnMouseClicked(null);
				setOnKeyPressed(null);
				getChildren().remove(notification);
				timerCount(0);
			}

		};
		setOnMouseClicked(mouseNotification);
		setOnKeyPressed(keyboardNotification);
		add(notification, 0, 1);
	}

	public Label getNotification() {
		return notification;
	}
/*
 * message of error in case of Exceptions
 */
	public void error(String message) {
		timerCount(1);
		errorName.setMinHeight(90);
		errorName.setMinWidth(340);
		errorName.setText(message);
		errorName.setAlignment(Pos.CENTER);
		setMargin(errorName, new Insets(30, 0, 0, (getWidth() - 355) / 2));
		EventHandler<MouseEvent> mouseError = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				setOnMouseClicked(null);
				setOnKeyPressed(null);
				getChildren().remove(errorName);
				timerCount(0);
			}

		};

		EventHandler<KeyEvent> keyboardError = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				setOnMouseClicked(null);
				setOnKeyPressed(null);
				getChildren().remove(errorName);
				timerCount(0);
			}

		};
		setOnMouseClicked(mouseError);
		setOnKeyPressed(keyboardError);
		add(errorName, 0, 1);
	}

	/**
	 * initialize the head of page and we take take height of head as an argument
	 * 
	 * @param height
	 */
	public void initHead(int height) {
		head = new HBox();
		head.setMinHeight(height);
		head.setPadding(new Insets(0, 0, 0, 30));
		initLabelsOfHead();
		for (int i = 0; i < 3; i++) {
			head.getChildren().add(this.labelsOfHead[i]);
			head.setMargin(this.labelsOfHead[i], new Insets(10, 0, 0, 10));
		}
		head.getChildren().addAll(this.labelsOfHead[3], this.labelsOfHead[4], this.labelsOfHead[5]);
		head.setMargin(this.labelsOfHead[5], new Insets(10, 0, 0, 20));
		head.setMargin(this.labelsOfHead[4], new Insets(10, 0, 0, 0));

	}

	public HBox getHead() {
		return head;
	}

	/**
	 * this function is used to restore or create a new page of game with same or
	 * other level of difficulty
	 * 
	 * @param size
	 */
	public void restore(int size) {
		score(0);
		this.size = size;
		jeu.longueurMotParDefault = size;
		try {
			jeu.PlayWordle();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initBody();
		keyboardScreen();
		keyboard();
		getChildren().clear();
		add(head, 0, 0);
		add(body, 0, 1);
		add(keyBoard, 0, 2);
		labelsOfHead[4].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg) {
				Platform.runLater(() -> {
					playAudio("sound/sound.wav");
					try {
						notification(jeu.getIndice(jeu.motChercher).toUpperCase());
					} catch (Exception e) {
						error("Problem in Connection");
					}
				});
			}

		});
	}

	/**
	 * this function initialize elements(Label) of Head parametre,score ...
	 */
	public void initLabelsOfHead() {
		this.labelsOfHead = new Label[6];
		for (int i = 0; i < 3; i++) {
			this.labelsOfHead[i] = new Label();
			this.labelsOfHead[i].setFocusTraversable(false);
			this.labelsOfHead[i].setText("0");
			this.labelsOfHead[i].setMinHeight(30);
			this.labelsOfHead[i].setMinWidth(30);

		}

		Image help = new Image("file:photo/help.png");
		ImageView helpImageView = new ImageView(help);
		helpImageView.setFitHeight(35);
		helpImageView.setFitWidth(35);

		Image parameter = new Image("file:photo/parameter.png");
		ImageView parameterImageView = new ImageView(parameter);
		parameterImageView.setFitHeight(30);
		parameterImageView.setFitWidth(30);

		this.labelsOfHead[3] = new Label();
		this.labelsOfHead[3].setMinWidth(440);
		this.labelsOfHead[3].setMinHeight(45);
		this.labelsOfHead[3].setFocusTraversable(false);
		this.labelsOfHead[3].setAlignment(Pos.CENTER);
		this.labelsOfHead[3].setText("00:00");

		this.labelsOfHead[4] = new Label();
		this.labelsOfHead[4].setMinHeight(45);
		this.labelsOfHead[4].setMinWidth(45);

		this.labelsOfHead[4].setGraphic(helpImageView);
		this.labelsOfHead[4].setAlignment(Pos.CENTER);
		this.labelsOfHead[4].setFocusTraversable(false);

		this.labelsOfHead[5] = new Label();
		this.labelsOfHead[5].setMinHeight(45);
		this.labelsOfHead[5].setMinWidth(45);

		this.labelsOfHead[5].setGraphic(parameterImageView);
		this.labelsOfHead[5].setAlignment(Pos.CENTER);
		this.labelsOfHead[5].setFocusTraversable(false);

		this.labelsOfHead[5].setBackground(Background.fill(Color.TRANSPARENT));
		this.labelsOfHead[4].setBackground(Background.fill(Color.TRANSPARENT));

	}

	public Label[] getLabelsOfHead() {
		return labelsOfHead;
	}

	/**
	 * this function initialize body elements and add row of word test in body
	 */
	public void initBody() {
		body = new VBox();
		initwordTest();
		body.getChildren().add(wordTest[6]);
		body.setMinHeight(300);
		body.setMinWidth(750);
		body.setSpacing(10);

		for (int i = 0; i < 6; i++) {
			body.setMargin(wordTest[i], new Insets(0, 0, 0, 230 - ((size - 5) * 25)));
		}
		wordTest[6].setSpacing(40);
		wordTest[6].setPadding(new Insets(30, 0, 0, 192 - ((size - 5) * 32)));

	}

	public void InitialtimerCount() {
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			jeu.tempsEcoule += 1000;
			Date now = new Date(jeu.tempsEcoule);

			SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
			String formattedTime = sdf.format(now);
			this.labelsOfHead[3].setText(formattedTime);

		}));

	}

	public void timerCount(int i) {
		if (i == 0) {

			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();

		} else {
			timeline.pause();
		}

	}

	public void clearTimerCount() {
		labelsOfHead[3].setText("00:00");
		jeu.score = 0;
		jeu.tempsEcoule = 0;
		timeline = null;
	}

	/**
	 * initialize all buttons in body
	 */
	public void initwordTest() {
		int var = 0;
		wordTest = new HBox[7];
		ButtonWordTest = new Button[(size * 7)];
		wordTest[0] = new HBox();
		for (int i = 0; i < size * 7; i++) {
			ButtonWordTest[i] = new Button();
			ButtonWordTest[i].setFocusTraversable(false);
			ButtonWordTest[i].setBackground(Background.fill(Color.TRANSPARENT));
			if (i % size == 0 && i != 0) {
				body.getChildren().addAll(wordTest[var]);
				var++;
				wordTest[var] = new HBox();

			}

			wordTest[var].setSpacing(20);
			wordTest[var].getChildren().addAll(ButtonWordTest[i]);
		}

	}

	public Button[] getButtonWordTest() {
		return ButtonWordTest;
	}

	/**
	 * this function create keyboard of screen in keyBoard element
	 */
	public void keyboardScreen() {
		keyBoardAlphabet = new Button[28];

		rowKeyBoard1 = new HBox();
		rowKeyBoard2 = new HBox();
		rowKeyBoard3 = new HBox();
		keyBoard = new VBox(rowKeyBoard1, rowKeyBoard2, rowKeyBoard3);
		rowKeyBoard1.setSpacing(20);
		rowKeyBoard2.setSpacing(20);
		rowKeyBoard2.setId("ligne2");
		rowKeyBoard3.setSpacing(20);
		rowKeyBoard2.setId("ligne3");
		keyBoard.setSpacing(20);

		rowKeyBoard3.setPadding(new Insets(0, 0, 0, 5));
		keyBoard.setPadding(new Insets(90, 0, 30, 90));

		int taille = 0;

		for (int i = 0; i < alphabet.length; i++) {
			keyBoardAlphabet[taille] = new Button((alphabet[i] + "").toUpperCase());
			keyBoardAlphabet[taille].setFocusTraversable(false);
			keyBoardAlphabet[taille].setBackground(Background.fill(Color.TRANSPARENT));

			if (taille < 10) {
				rowKeyBoard1.getChildren().add(keyBoardAlphabet[taille]);
			} else if (taille == 20) {
				keyBoardAlphabet[taille] = new Button("Enter");

				keyBoardAlphabet[taille].setBackground(Background.fill(Color.TRANSPARENT));
				rowKeyBoard3.getChildren().add(keyBoardAlphabet[taille]);
				i--;
			} else if (taille < 20) {
				rowKeyBoard2.getChildren().add(keyBoardAlphabet[taille]);

			} else {
				rowKeyBoard3.getChildren().add(keyBoardAlphabet[taille]);

			}
			taille++;
		}

		keyBoardAlphabet[27] = new Button("supp");
		keyBoardAlphabet[27].setId("supp");
		keyBoardAlphabet[27].setBackground(Background.fill(Color.TRANSPARENT));
		rowKeyBoard3.getChildren().add(keyBoardAlphabet[27]);

		for (int i = 0; i < 28; i++) {
			int a = i;

			if (keyBoardAlphabet[a].getText() != "Enter" && keyBoardAlphabet[a].getText() != "supp") {
				keyBoardAlphabet[a].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						for (int j = 0; j < size; j++) {
							if (ButtonWordTest[(size * 6) + j].getText() == "") {
								ButtonWordTest[(size * 6) + j].setText(keyBoardAlphabet[a].getText());
								break;
							}
						}

					}

				});
				/*
				 * event to enter button
				 */
			} else if (keyBoardAlphabet[a].getText() == "Enter") {
				keyBoardAlphabet[a].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						Timer t = new Timer();
						TimerTask tt = new TimerTask() {
							int i = isEmptyLine();
							int z = 0;
							int k = i;

							/*
							 * put the clicked letter(Alphabet) in buttons of last word test
							 */
							@Override
							public void run() {
								if (k < (size + i) && i != -1) {

									word.append(ButtonWordTest[(size * 6) + z].getText());
									z++;
									k++;
								}
								/*
								 * enter this case when we lose
								 */
								else if (i == (5 * size) && !jeu.motChercher.equalsIgnoreCase(word.toString())
										&& word.length() == jeu.motChercher.length()) {
									t.cancel();

									Platform.runLater(() -> {
										timerCount(1);
										LosePane.setPadding(new Insets(0, 201, 0, 201));
										LosePane.getTextScore().setPadding(new Insets(0, 0, 0, 30));
										LosePane.getTextScore().setText("The word was : " + jeu.motChercher
												+ "\nYour score was : " + jeu.getActualScore() + " POINTS");
										add(LosePane, 0, 1);
									});
								} else {
									// enter this case when verify if word exists in dictionary or not
									t.cancel();
									jeu.motChoisiParLeJoueur = word;
									String yy = jeu.comparer();
									word.delete(0, word.length());
									if (yy.length() != 0) {
										Timer a = new Timer();
										TimerTask aa = new TimerTask() {
											int i = isEmptyLine();
											int z = 0;
											boolean verifyWinner = true;

											int k = i;

											@Override
											public void run() {
												for (int j = 0; j < yy.length(); j++) {
													if (yy.charAt(j) != '*') {
														verifyWinner = false;
														break;
													}
												}

												if (k < (size + i) && i != -1) {
													Platform.runLater(() -> {
														ButtonWordTest[k]
																.setText(ButtonWordTest[(size * 6) + z].getText());
														ButtonWordTest[(size * 6) + z].setText("");
														if (yy.charAt(z) == '*') {
															ButtonWordTest[k].setStyle(
																	"-fx-background-color:#00E640;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px; -fx-effect: dropshadow(gaussian, rgb(0,230,64), 10, 0, 0, 0);");
														} else if (yy.charAt(z) == '%') {
															ButtonWordTest[k].setStyle(
																	"-fx-background-color:#FF5733;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px; -fx-effect: dropshadow(gaussian, rgb(255, 87, 51), 10, 0, 0, 0);");
														} else {
															for (int u = 0; u < keyBoardAlphabet.length; u++) {
																if (ButtonWordTest[k].getText()
																		.equals(keyBoardAlphabet[u].getText())) {
																	keyBoardAlphabet[u].setStyle(
																			"-fx-background-color:#808080;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px; -fx-effect: dropshadow(gaussian, rgb(128, 128, 128), 10, 0, 0, 0);");
																	break;
																}
															}
															ButtonWordTest[k].setStyle(
																	"-fx-background-color:#808080;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px; -fx-effect: dropshadow(gaussian, rgb(128, 128, 128), 10, 0, 0, 0);");
														}

														z++;
														k++;
													});
												} else {
													a.cancel();
													/*
													 * case of winning
													 */
													if (verifyWinner) {
														Platform.runLater(() -> {

															jeu.add1ToScoreWhenWin();
															restore(size);
															if (notification.getBackground().getFills().get(0)
																	.getFill() != Color.BLACK)
																active();
															else
																desactive();
															score(jeu.getActualScore());
															notification("Bravo  ");
															ScaleTransition e = new ScaleTransition();
															e.setDuration(Duration.millis(1000));
															e.setAutoReverse(true);
															e.setByX(500);
															e.setByY(500);
															e.setCycleCount(2);
															e.setNode(notification);
															e.play();
														});
													}

												}
											};
										};
										a.schedule(aa, 0, 200);
									} else {
										/*
										 * case if word does not exists in dictionary
										 */
										Platform.runLater(() -> {
											TranslateTransition e = new TranslateTransition();
											e.setDuration(Duration.millis(100));
											e.setAutoReverse(true);
											e.setByX(25);
											e.setCycleCount(2);
											e.setNode(wordTest[6]);
											e.play();

											error("ce mot n\'existe pas");

										});
									}
								}
							};
						};
						t.schedule(tt, 0, 200);

					}

				});

			} else {
				keyBoardAlphabet[a].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						for (int k = (size * 7) - 1; k >= (size * 6); k--) {
							if (ButtonWordTest[k].getText() != "") {
								ButtonWordTest[k].setText("");
								break;
							}

						}

					}
				});
			}

		}

	}

	public Button[] getKeyBoardAlphabet() {
		return keyBoardAlphabet;
	}

	/**
	 * verify all buttons in body and return first button of empty line
	 * 
	 * @return
	 */
	public int isEmptyLine() {
		int k = 0;
		int i;
		for (i = 0; i < (size * 6); i++) {
			if (k == size) {
				break;
			}
			if (ButtonWordTest[i].getText() != "") {
				i = i + (size - k);
				k = 0;
			}
			k++;
		}
		if (i > (size * 6))
			return -1;
		return i - size;
	}
/*
 * verify case when i click in buttons different than alphabet  
 */
	public boolean isAlphabetExist(KeyEvent i) {
		for (int j = 0; j < alphabet.length; j++) {
			if (i.getCode() == KeyCode.ENTER || i.getCode() == KeyCode.BACK_SPACE) {
				return true;
			}
			if (alphabet[j] == i.getCode().getChar().toLowerCase().charAt(0)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * this get event from keyboard of user
	 */

	public void keyboard() {
		EventHandler<KeyEvent> handlerkey = new EventHandler<KeyEvent>() {
			@Override

			public void handle(KeyEvent x) {
				int k;
				if (isAlphabetExist(x)) {
					for (k = (size * 6); k < (size * 7); k++) {
						if (ButtonWordTest[k].getText() == "") {
							ButtonWordTest[k].setText(x.getText().toUpperCase());
							break;
						}
					}
					if (k == (size * 7) && x.getCode() == KeyCode.ENTER) {
						int i = isEmptyLine();
						int z = 0;
						for (int j = i; j < (size + i) && i != -1; j++) {
							ButtonWordTest[j].setText(ButtonWordTest[(size * 6) + z].getText());
							ButtonWordTest[(size * 6) + z].setText("");
							ButtonWordTest[j].setStyle(
									"-fx-background-color:#00E640;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px; -fx-effect: dropshadow(gaussian, rgb(0,230,64), 10, 0, 0, 0);");

							z++;
						}

					}

					if (x.getCode() == KeyCode.BACK_SPACE) {
						for (int i = (size * 7) - 1; i >= (size * 6); i--) {
							if (ButtonWordTest[i].getText() != "") {
								ButtonWordTest[i].setText("");
								break;
							}

						}
					}

				}
			}
		};
		setOnKeyReleased(handlerkey);
	}

	/**
	 * this function get score value and separate it in 3 elements (Label) of head
	 * so we can show it in screen
	 * 
	 * @param i
	 */
	public void score(int i) {
		int j = 2;
		while (i >= 0 && j >= 0) {

			this.labelsOfHead[j].setText("" + (i % 10));
			i = i / 10;
			j--;
		}
	}

	/**
	 * for the interactivity it changes page of scene to show it in stage so screen
	 * 
	 * @param sc
	 * @param x
	 * @param scene
	 */
	void interactionPageMenuPage(Stage sc, MenuPage x, Scene scene) {
		labelsOfHead[5].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg) {
				playAudio("sound/sound.wav");
				timerCount(1);
				x.getChildren().clear();
				x.add(x.getBody(), 0, 0);
				x.setPadding(new Insets(0, 0, 0, 0));
				scene.setRoot(x);
				sc.setScene(scene);
				responsivity(sc, x);
			}

		});

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

	/**
	 * for responsivity of page which is depends on changing of width in stage
	 * 
	 * @param arg0
	 * @param GP
	 */
	public void responsivity(Stage arg0, MenuPage GP) {
		arg0.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg, Number arg1, Number arg2) {
				if (arg1.doubleValue() < arg2.doubleValue()) {
					GP.setMargin(GP.getChildren().get(0), new Insets(0, 0, 0, (arg2.doubleValue() - 750) / 2));
				}
				if (arg1.doubleValue() > arg2.doubleValue()) {
					GP.setMargin(GP.getChildren().get(0), new Insets(0, 0, 0,
							(arg2.doubleValue() - 750 - (arg1.doubleValue() - arg2.doubleValue())) / 2));
				}

			};
		});

	}

	public int getSize() {
		return size;
	}

	public void active() {
		setBackground(Background.fill(Color.WHITE));
		getNotification().textFillProperty().set(Color.BLACK);
		getNotification().setBackground(Background.fill(Color.WHITE));
		getNotification().setStyle("-fx-font-size:40px;-fx-border-color:BLACK;-fx-border-radius:7px;");
		errorName.textFillProperty().set(Color.BLACK);
		errorName.setBackground(Background.fill(Color.WHITE));
		errorName.setStyle("-fx-font-size:25px;-fx-border-color:BLACK;-fx-border-radius:7px;");

		getHead().setBackground(Background.fill(Color.WHITE));
		for (int i = 0; i < getLabelsOfHead().length; i++) {
			if (i != 3 && i != 4 && i != 5)
				getLabelsOfHead()[i].setStyle(
						"-fx-padding:0 10px 0 10px;-fx-font-size:15px;-fx-border-color:BLACK;-fx-border-radius:7px;");
			getLabelsOfHead()[i].setTextFill(Color.BLACK);

		}
		getLabelsOfHead()[3].setStyle("-fx-font-color:BLACK;-fx-font-size:25px;");

		getLabelsOfHead()[5].setStyle("-fx-border-color:BLACK;-fx-border-radius:7px;");
		getLabelsOfHead()[4].setStyle("-fx-border-color:BLACK;-fx-border-radius:7px;");

		for (int i = 0; i < getButtonWordTest().length; i++) {
			if (getButtonWordTest()[i].getStyle().contains("-fx-background-color")) {
				getButtonWordTest()[i].setStyle(getButtonWordTest()[i].getStyle()
						+ "-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px;");
			} else {
				getButtonWordTest()[i].setStyle(
						"-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px;");
			}
			getButtonWordTest()[i].textFillProperty().set(Color.BLACK);
		}
		for (int i = 0; i < getKeyBoardAlphabet().length; i++) {
			getKeyBoardAlphabet()[i].setTextFill(Color.BLACK);
			if (getKeyBoardAlphabet()[i].getStyle().contains("-fx-background-color")) {
				getKeyBoardAlphabet()[i].setStyle(getKeyBoardAlphabet()[i].getStyle()
						+ "-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px;");

			} else {
				getKeyBoardAlphabet()[i].setStyle(
						"-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px;");

			}

		}
		getKeyBoardAlphabet()[20].setStyle(
				"-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:90px;-fx-font-size:15px;-fx-border-radius:7px;");
		getKeyBoardAlphabet()[27].setStyle(
				"-fx-border-color:BLACK;-fx-pref-height:39px;-fx-pref-width:90px;-fx-font-size:15px;-fx-border-radius:7px;");

	}

	public void desactive() {
		notification.textFillProperty().set(Color.WHITE);
		notification.setBackground(Background.fill(Color.BLACK));
		notification.setStyle("-fx-font-size:40px;-fx-border-color:WHITE;-fx-border-radius:7px;");
		errorName.textFillProperty().set(Color.WHITE);
		errorName.setBackground(Background.fill(Color.BLACK));
		errorName.setStyle("-fx-font-size:25px;-fx-border-color:WHITE;-fx-border-radius:7px;");
		head.setBackground(Background.fill(Color.BLACK));
		setBackground(Background.fill(Color.BLACK));
		for (int i = 0; i < 3; i++) {
			this.labelsOfHead[i].setStyle(
					"-fx-padding:0 10px 0 10px;-fx-font-size:15px;-fx-border-color:#949494;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(148, 148, 148), 10, 0, 0, 0);");
			this.labelsOfHead[i].setTextFill(Color.WHITE);
		}
		this.labelsOfHead[3].setStyle(
				"-fx-font-color:green;-fx-font-size:25px;-fx-effect: dropshadow(gaussian, rgb(148, 148, 148), 10, 0, 0, 0);");
		this.labelsOfHead[3].textFillProperty().set(Color.WHITE);
		this.labelsOfHead[5].setStyle(
				"-fx-border-color:#949494;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(148, 148, 148), 10, 0, 0, 0);");
		this.labelsOfHead[4].setStyle(
				"-fx-border-color:#949494;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(148, 148, 148), 10, 0, 0, 0);");
		for (int i = 0; i < size * 7; i++) {
			if (getButtonWordTest()[i].getStyle().contains("-fx-background-color")) {
				getButtonWordTest()[i].setStyle(getButtonWordTest()[i].getStyle()
						+ "-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(148, 148, 148), 10, 0, 0, 0);");
			} else {
				getButtonWordTest()[i].setStyle(
						"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(148, 148, 148), 10, 0, 0, 0);");
			}
			ButtonWordTest[i].textFillProperty().set(Color.WHITE);
		}
		for (int i = 0; i < keyBoardAlphabet.length; i++) {
			keyBoardAlphabet[i].setTextFill(Color.WHITE);
			if (keyBoardAlphabet[i].getStyle().contains("-fx-background-color")) {
				keyBoardAlphabet[i].setStyle(keyBoardAlphabet[i].getStyle()
						+ "-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(148, 148, 148), 10, 0, 0, 0);");

			} else {
				keyBoardAlphabet[i].setStyle(
						"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:39px;-fx-font-size:15px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(148, 148, 148), 10, 0, 0, 0);");
			}

		}
		keyBoardAlphabet[20].setTextFill(Color.WHITE);
		keyBoardAlphabet[20].setStyle(
				"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:90px;-fx-font-size:15px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(148, 148, 148), 10, 0, 0, 0);");

		keyBoardAlphabet[27].setTextFill(Color.WHITE);
		keyBoardAlphabet[27].setStyle(
				"-fx-border-color:#949494;-fx-pref-height:39px;-fx-pref-width:90px;-fx-font-size:15px;-fx-border-radius:7px;-fx-effect: dropshadow(gaussian, rgb(148, 148, 148), 10, 0, 0, 0);");
	}
}
