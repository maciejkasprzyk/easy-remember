package easyRemember;

import java.time.LocalDateTime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main class responsible for instantiating MVC, and initializing the JavaFX.
 * 
 * @author Maciej Kasprzyk
 */
public class Main extends Application {
	private Model model;
	private Controller controller;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("View.fxml"));
			Pane root = (Pane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("Easy Remember");
			primaryStage.setResizable(false);
			primaryStage.show();

			controller = loader.<Controller>getController();
			model = new Model(controller);
			model.getWordDataBase().readFile("EasyWords.txt");
			controller.setModel(model);
			controller.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public void stop() {
		model.getWordDataBase().saveFile("EasyWords.txt");
		// TODO add backups with time stamps
		String time = LocalDateTime.now().toString();
		model.getWordDataBase().saveFile("backups/EasyWords " + time + ".txt");
		return;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
