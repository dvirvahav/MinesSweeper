package application;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Main extends Application {
	static Image mine = new Image("media/mine.gif");
	static Image closed = new Image("media/closed.png");
	static Image flag = new Image("media/flag.png");
	static Image open = new Image("media/open.png");
	static Image one = new Image("media/1.png");
	static Image two = new Image("media/2.png");
	static Image three = new Image("media/3.png");
	static Image four = new Image("media/4.png");
	static Image five = new Image("media/5.png");
	static Image six = new Image("media/6.png");
	static Image seven = new Image("media/7.png");
	static Image eight = new Image("media/8.png");
	static Image saved = new Image("media/saved.jpg");
	static int width;
	static int height;

	public static class MineButton extends Button {
		int x, y;

		public MineButton(int x, int y) {
			this.x = x;
			this.y = y;
			setPadding(new Insets(4, 4, 4, 4));
			setMaxHeight(22);
			setMaxWidth(28);
		}

		public void Update() {
			switch (Main.newGame.mines[x][y].toString()) {
			case "c": {
				setGraphic(new ImageView(closed));
				break;
			}
			case "e": {
				setGraphic(new ImageView(open));
				break;
			}
			case "m": {
				setGraphic(new ImageView(mine));
				break;
			}
			case "f": {
				setGraphic(new ImageView(flag));
				break;
			}
			case "s": {
				setGraphic(new ImageView(saved));
				break;
			}
			case "1": {
				setGraphic(new ImageView(one));
				break;
			}
			case "2": {
				setGraphic(new ImageView(two));
				break;
			}

			case "3": {
				setGraphic(new ImageView(three));
				break;
			}
			case "4": {
				setGraphic(new ImageView(four));
				break;
			}
			case "5": {
				setGraphic(new ImageView(five));
				break;
			}
			case "6": {
				setGraphic(new ImageView(six));
				break;
			}
			case "7": {
				setGraphic(new ImageView(seven));
				break;
			}
			case "8": {
				setGraphic(new ImageView(eight));
				break;
			}

			}
		}

	}

	static ViewController controller;
	StackPane gamePane;
	static Engine newGame;
	static GridPane grid = new GridPane();
	static MineButton[][] buttons;
	static BorderPane mainPain = new BorderPane();
	static Stage stage;
	static boolean game = true;
	static MediaPlayer playgameOver = new MediaPlayer(new Media(new File("gameover.wav").toURI().toString()));
	static MediaPlayer playFlag = new MediaPlayer(new Media(new File("flag.wav").toURI().toString()));
	static MediaPlayer success = new MediaPlayer(new Media(new File("success.wav").toURI().toString()));

	@Override
	public void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("View.fxml"));
		try {
			mainPain = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		controller = loader.getController();
		// gamePane=controller.GetgamePane();
		controller.GetgamePane().getChildren().clear();
		controller.GetgamePane().getChildren().add(grid);
		success.setVolume(0.2);
		Scene scene = new Scene(mainPain);
		Main.stage = stage;
		SelectedDiff();
		controller.initilize();

		stage.setResizable(false);
		stage.setTitle("MinesSweeper");
		stage.setScene(scene);
		stage.show();
	}

	public static void SelectedDiff() {

		controller.GetgamePane().getChildren().clear();
		controller.counter = 0;

		height = controller.getHeight();
		width = controller.getWidth();

		Main.newGame = new Engine(width, height, controller.getMines());
		Main.buttons = new MineButton[width][height];
		grid = new GridPane();

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				final int icopy = i;
				final int jcopy = j;
				Main.buttons[i][j] = new MineButton(i, j);
				Main.buttons[i][j].Update();
				Main.buttons[i][j].setOnMouseClicked(a -> {
					if (a.getButton() == MouseButton.SECONDARY) {
						Main.newGame.mines[icopy][jcopy].setFlag();

						playFlag.seek(Duration.ZERO);
						playFlag.play();
						updateFrame();
					} else {
						Main.newGame.mines[icopy][jcopy].open();
						if (Main.newGame.mines[icopy][jcopy].toString() == "m") {
							playgameOver.seek(Duration.ZERO);
							playgameOver.play();
							updateFrame();

							Stage gameover = new Stage();
							gameover.setTitle("Game Over!");
							BorderPane gameoverpane = new BorderPane();
							Scene gameoverscene = new Scene(gameoverpane);
							Label text = new Label();
							VBox gameoverVbox = new VBox(8);
							Button exit = new Button("Exit");
							Button tryagain = new Button("Try Again!");
							text.setMaxWidth(280);
							text.setPadding(new Insets(40, 10, 10, 10));
							text.setStyle(
									"-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 12; -fx-text-fill: gray;");
							text.setText("       YOU LOSE, GOOD DAY SIR!");
							text.setWrapText(true);
							tryagain.setStyle(
									"-fx-font-family: \"Comic Sans MS\";-fx-background-color: lightblue; -fx-font-size: 12; -fx-text-fill: gray;");
							tryagain.setOnAction(e -> {
								SelectedDiff();

								gameover.close();
							});
							exit.setStyle(
									"-fx-font-family: \"Comic Sans MS\";-fx-background-color: lightblue; -fx-font-size: 12; -fx-text-fill: gray;");
							exit.setOnAction(e -> {
								gameover.close();
								stage.close();
							});
							gameoverVbox.setAlignment(Pos.BASELINE_CENTER);
							gameoverVbox.setPadding(new Insets(40));
							gameoverVbox.getChildren().addAll(tryagain, exit);
							gameoverpane.setTop(text);
							gameoverpane.setCenter(gameoverVbox);
							gameover.setHeight(300);
							gameover.setWidth(230);
							gameover.setScene(gameoverscene);
							gameover.setResizable(false);
							gameover.setOnCloseRequest(null);
							gameover.initModality(Modality.APPLICATION_MODAL);
							gameover.showAndWait();

						} else if (Main.newGame.mines[icopy][jcopy].toString() != "f"
								&& Main.newGame.mines[icopy][jcopy].toString() != "c") {
							success.seek(Duration.ZERO);
							success.play();
							if (newGame.getWin() == (height * width - controller.getMines())) {
								newGame.openAll();
								updateFrame();
								Stage winGame = new Stage();
								winGame.setTitle("You Win!");
								BorderPane winGameoverpane = new BorderPane();
								Scene winGameoverscene = new Scene(winGameoverpane);
								Label text = new Label();
								VBox gameoverVbox = new VBox(8);
								Button exit = new Button("Exit");
								Button tryagain = new Button("Play Again!");
								text.setMaxWidth(280);
								text.setPadding(new Insets(40, 10, 10, 10));
								text.setStyle(
										"-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 12; -fx-text-fill: gray;");
								text.setText("       YOU WIN!!");
								text.setWrapText(true);
								tryagain.setStyle(
										"-fx-font-family: \"Comic Sans MS\";-fx-background-color: lightblue; -fx-font-size: 12; -fx-text-fill: gray;");
								tryagain.setOnAction(e -> {
									SelectedDiff();

									winGame.close();
								});
								exit.setStyle(
										"-fx-font-family: \"Comic Sans MS\";-fx-background-color: lightblue; -fx-font-size: 12; -fx-text-fill: gray;");
								exit.setOnAction(e -> {
									winGame.close();
									stage.close();
								});
								gameoverVbox.setAlignment(Pos.BASELINE_CENTER);
								gameoverVbox.setPadding(new Insets(40));
								gameoverVbox.getChildren().addAll(tryagain, exit);
								winGameoverpane.setTop(text);
								winGameoverpane.setCenter(gameoverVbox);
								winGame.setHeight(300);
								winGame.setWidth(230);
								winGame.setScene(winGameoverscene);
								winGame.setResizable(false);
								winGame.setOnCloseRequest(null);
								winGame.initModality(Modality.APPLICATION_MODAL);
								winGame.showAndWait();
							}

						}
					}
					updateFrame();

				});

				Main.grid.add(Main.buttons[i][j], i, j);
			}
		grid.setPadding(new Insets(10, 10, 20, 10));
		controller.GetgamePane().getChildren().add(grid);
		stage.sizeToScene();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void updateFrame() {
		controller.getFlag().setText(String.valueOf(newGame.Flags));
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				final int xcopy = x;
				final int ycopy = y;
				Main.buttons[xcopy][ycopy].Update();

			}
	}

}
