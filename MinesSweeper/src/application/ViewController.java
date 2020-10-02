package application;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewController {

	private int height = 9, width = 9, mines = 10; // default
	@FXML
	private BorderPane mainContainer;

	@FXML
	private StackPane gamePane;

	@FXML
	private Pane controlPane;
	@FXML
	private Button CreditButton;

	@FXML
	private Button OptionsButton;
	@FXML
	private MenuButton MainMenu;

	@FXML
	private MenuItem Begginer;

	@FXML
	private MenuItem Intermediate;

	@FXML
	private MenuItem Expert;
	@FXML
	private TextFlow FlagsCount;

	@FXML
	private TextFlow TimeCount;
	@FXML
	private Button reset;

	int getMines() {
		return mines;
	}

	int getHeight() {
		return height;
	}

	int getWidth() {
		return width;
	}

	@FXML
	void CreditClicked(ActionEvent event) {
		Stage credit = new Stage();
		Pane back = new Pane();
		Scene creditscene = new Scene(back);
		Label text = new Label();
		text.setMaxWidth(280);
		text.setPadding(new Insets(40, 10, 10, 10));
		text.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 13; -fx-text-fill: gray;");
		text.setText("-MinesSweeper-\n\nDeveloped by Dvir Vahav, as part of self-project to extend JavaFX experience."
				+ " \n\n\nBuild Date: 30.9.2020 \nDvirvah@gmail.com"
				+ "\n\n Credits: \n Success Sound: FreeSound - JustInvoke - Success Jingle\n");
		text.setWrapText(true);

		back.getChildren().add(text);
		credit.setHeight(370);
		credit.setWidth(300);
		credit.setScene(creditscene);
		credit.initModality(Modality.APPLICATION_MODAL);
		credit.showAndWait();
	}

	@FXML
	void OptionsClick(ActionEvent event) {

	}

	@FXML

	void resetClick(ActionEvent event) {
		Main.SelectedDiff();
		flag.setText(String.valueOf(Main.newGame.Flags));
	}

	@FXML
	void BegginerSelected(ActionEvent event) {
		MainMenu.setText("Begginer");
		this.height = 9;
		this.width = 9;
		this.mines = 10;

	}

	@FXML
	void ExpertSelected(ActionEvent event) {
		MainMenu.setText("Expert");
		this.height = 16;
		this.width = 24;
		this.mines = 99;
	}

	@FXML
	void IntermediateSelected(ActionEvent event) {
		MainMenu.setText("Intermediate");
		this.height = 16;
		this.width = 16;
		this.mines = 40;
	}

	public StackPane GetgamePane() {
		return gamePane;
	}

	public MenuButton getMainMenu() {
		return MainMenu;
	}

	static int counter = 0;


	static Label time = new Label();
	static Label flag = new Label();

	public Label getFlag() {
		return flag;
	}

	void initilize() {
		MainMenu.setText("Begginer");
		FlagsCount.getChildren().add(flag);
		TimeCount.getChildren().add(time);
		flag.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 18; -fx-text-fill: gray;");
		flag.setText(String.valueOf(Main.newGame.Flags));
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.setImplicitExit(false);
				Platform.runLater(() -> {
					time.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 18; -fx-text-fill: gray;");
					time.setText(String.format("%03d", counter++));

				});

			}

		}, 1000, 1000);
	}
}