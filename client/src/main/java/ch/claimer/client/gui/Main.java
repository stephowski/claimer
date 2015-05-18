package ch.claimer.client.gui;

import java.awt.Toolkit;
import java.io.IOException;



import org.pmw.tinylog.Logger;

import ch.claimer.shared.util.LoggerUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


/**
 * Main-Klasse der Applikation. �ffnet das Login-Fenster.
 * @author Michael L�tscher, Alexander Hauck
 * @since 10.04.2015
 * @version 1.1
 */
public class Main extends Application {

	Scene scene;

	/**
	 * Start des GUI.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
		LoggerUtil.loadConfig();
	}

	/** 
	 * �ffnet das Login-Fenster, setzt das CSS und die Schriftart.
	 */
	public void start(Stage primaryStage) {

		//Bildschirmaufl�sung auslesen
		Integer screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		
		primaryStage.setTitle("Claimer");
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Login.fxml")); // FXML File kann von myLoader geladen werden

		try {
			Pane pane = (Pane) myLoader.load(); // FXML File wird auf das login-Pane geladen
			Scene login = new Scene(pane);
			login.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto+Condensed:400,300,700");
			login.getStylesheets().add(getClass().getResource("/claimer_styles.css").toExternalForm()); // CSS-File wird geladen
			
			if(screenWidth > 1500) {
				login.getStylesheets().add(getClass().getResource("/big_font.css").toExternalForm()); /// CSS-File f�r grosse Bildschirme
			}
			
			primaryStage.setScene(login);			
			primaryStage.show();
		}

		catch (IOException | IllegalStateException e) {
			Logger.info("View \"Login.fxml\" kann nicht geladen werden.");
		} 
	}
}
