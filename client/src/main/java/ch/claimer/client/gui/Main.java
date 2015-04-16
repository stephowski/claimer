package ch.claimer.client.gui;

import java.io.IOException;

import ch.claimer.client.gui.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Main extends Application {

	Scene scene;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Mängelmanager");
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource(
				"view/RootLayout.fxml")); // FXML File kann von myLoader geladen werden
									

		try {
			Pane pane = (Pane) myLoader.load(); // FXML File wird auf das login-Pane geladen

			/*LoginController controller = (LoginController) myLoader
					.getController();
			controller.setPrevStage(primaryStage); // übergibt die primaryStage der LoginController Klasse*/
			Scene login = new Scene(pane);
			login.getStylesheets().add(
					getClass().getResource("claimer_styles.css").toExternalForm()); // CSS-File wird geladen
			primaryStage.setScene(login);
			primaryStage.show();
			//primaryStage.setResizable(false);
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
