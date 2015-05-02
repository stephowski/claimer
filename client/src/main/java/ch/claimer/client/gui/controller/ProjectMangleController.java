package ch.claimer.client.gui.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import ch.claimer.client.proxy.LoginProxy;
import ch.claimer.shared.models.Comment;
import ch.claimer.shared.models.Issue;
import ch.claimer.shared.models.Login;
import ch.claimer.shared.models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Michael L�tscher
 * @since 21.04.2015
 * @version 1.1
 *
 */

public class ProjectMangleController implements Initializable {
	
	// Maincontent, hierhin werden die verschiedenen Views geladen
		@FXML
		private Pane mainContent;
		
		@FXML
		private Label lbl_title;
		
		@FXML
		private Label lbl_date;
		
		@FXML
		private Label lbl_author;
		
		@FXML
		private TextField txt_mangleName;
		
		@FXML
		private TextField txt_addComment;

		@FXML
		private TextField txt_projectName;
		
		@FXML
		private TextField txt_objectName;

		@FXML
		private TextField txt_area;

		@FXML
		private TextArea txt_mangleDescription;

		@FXML
		private TextField txt_contactPerson;

		@FXML
		private TextField txt_principalPhone;

		@FXML
		private ComboBox<?> combo_principalName;
		
		@FXML
		private ComboBox<?> combo_status;
		
		@FXML
		private ComboBox<?> combo_subcontractor;
		
		@FXML
		private Button bttn_saveMangle;
		
		@FXML
		private Button bttn_quitMangle;
		
		@FXML
		private Button bttn_quitComment;
		
		@FXML
		private Button bttn_addComment;
		
		@FXML
		private Button bttn_addPhoto;
		
		@FXML
		private TableView<Comment> commentTableView;
		
		@FXML
		private TableColumn<Comment, String> colComment;
		
		@FXML
		private TableColumn<Comment, String> colAuthor;
		
		@FXML
		private TableColumn<Comment, String> colAdded;
		
		// Zur ProjectAdd-Ansicht wechseln (mainView.xml)
		@FXML
		private void backToProjectAddView(ActionEvent event) throws IOException {
			Pane myPane = FXMLLoader.load(getClass().getResource(
					"../view/ProjectAddView.fxml"));
			mainContent.getChildren().clear();
			mainContent.getChildren().setAll(myPane);

		}
		
		@FXML
		private void addComment(ActionEvent event) throws IOException {
		
		String comment = txt_addComment.getText();
		Comment c1 = new Comment();
		c1.setContent(comment);

		}
		
		public void initialize() {
			ObservableList<Comment> data = FXCollections.observableArrayList();

			// Kommentar als Platzhalter
			Comment c1 = new Comment();
			c1.setId(1);
			c1.setContent("Warten auf Lieferung");
//			c1.setPerson(Person);
		
			

			// Daten zu Observable-List hinzuf�gen
			data.addAll(c1);

			// Spalten-Values definieren (m�ssen den Parameter des Personen-Objekts entsprechen)
			colComment.setCellValueFactory(new PropertyValueFactory<Comment, String>("content"));
			colAdded.setCellValueFactory(new PropertyValueFactory<Comment, String>("created"));
			colAuthor.setCellValueFactory(new PropertyValueFactory<Comment, String>("person"));
			// colProject.setSortTyp(descending);

			// Observable-List, welche die Daten beinhaltet, an die Tabelle �bergeben
			commentTableView.setItems(data);

		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
			Issue issue = new Issue();
		
		}
	


}
