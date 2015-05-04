package ch.claimer.client.gui.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import ch.claimer.client.proxy.RoleProxy;
import ch.claimer.client.util.ResteasyClientUtil;
import ch.claimer.shared.models.Person;
import ch.claimer.shared.models.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserAddController implements Initializable{
		
	private Integer personId = null;
	private String personType = null;
	
	@FXML
	private Pane mainContent;
	
	@FXML
	private Label lblTitel;
	
	@FXML
	private TextField txtFirstname;
	
	@FXML
	private TextField txtLastname;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private PasswordField pfPassword;
	
	@FXML
	private TextField txtPhone;
	
	@FXML
	private ComboBox<String> dropdownFunction;
	
	
	@FXML
	private void loadUserMainView() {
		try {
			Pane myPane = FXMLLoader.load(getClass().getResource("../view/UserMainView.fxml"));
			mainContent.getChildren().clear();
			mainContent.getChildren().setAll(myPane);
		} catch (NullPointerException npe) {
			System.out.println("Fehler: View konnte nicht geladen werden");
			// ToDo Eintrag in Log-Datei
			npe.printStackTrace();
		}	catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void saveUser(ActionEvent event) throws IOException {
		
		// TODO Alle Felder auslesen
		
		// TODO �berpr�fen, ob alle Pflichtfelder gesetzt wurden
			
		//Personenobjekt erstellen:
		Person person = null;

		//�berpr�fen, ob Person aktualisiert oder neu eingef�gt werden soll
		if(personId != null) {
			
			//Weiterleiten zum Updaten
			System.out.println("Update");
		} else {
			
			//Weiterleiten zum Einf�gen
			System.out.println("Insert");
		}
		
		// ToDo: Read Data from Textfields, check them and save into Database
	}
	
	@FXML
	private void uploadImage(ActionEvent event) throws IOException {
        final FileChooser fileChooser = new FileChooser();
        Desktop desktop = Desktop.getDesktop();
        Stage stage = new Stage();
		System.out.println("Klick auf Button.");
		// ToDo: Upload-Fenster �ffnen, Bild �berpr�fen, bild speichern
		File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
        	 desktop.open(file);
        }
       
       
	} 
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setDropdownValues();
		
	}
	

	/**
	 * Werte f�r das "Funktionen"-Dropdown setzen
	 */
	public void setDropdownValues()  {
		
		RoleProxy roleProxy = ResteasyClientUtil.getTarget().proxy(RoleProxy.class);		
	    ObjectMapper mapper = new ObjectMapper();	    
	    List<Role> roleList = null;
	    
		try {
			 roleList = mapper.readValue(roleProxy.getAll(), new TypeReference<List<Role>>(){});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Rollen dem Dropdown hinzuf�gen
		for(Role role: roleList) {
			dropdownFunction.getItems().add(role.getName());
		}
	}
	
	
	/**
	 * Detailansicht mit allen Daten der angeklickten Person f�llen
	 * @param personToEdit
	 */
	public void initData(Person personToEdit) {
		lblTitel.setText("Benutzer bearbeiten");
			
		personId = personToEdit.getId();
		personType = personToEdit.getClass().getSimpleName(); //Typ des Objekts auslesen
		
		if(personToEdit.getFirstname() != null) { 
			txtFirstname.setText(personToEdit.getFirstname());	
		}
		
		if(personToEdit.getLastname() != null) {
			txtLastname.setText(personToEdit.getLastname());
		}
		
		if(personToEdit.getEmail() != null) {
		txtEmail.setText(personToEdit.getEmail());
		}
		
		if(personToEdit.getPhone() != null) {
			txtPhone.setText(personToEdit.getPhone());
		}
		
		if(personToEdit.getLogin() != null) {
			if(personToEdit.getLogin().getUsername() != null) {
				txtUsername.setText(personToEdit.getLogin().getUsername());
			}
			
			if(personToEdit.getLogin().getPassword() != null) {
				pfPassword.setText(personToEdit.getLogin().getUsername());
			}
		}
		
		if(personToEdit.getLogin().getRole().getName() != null) {
			dropdownFunction.setValue(personToEdit.getLogin().getRole().getName());
		}
		
		
		
	
	}
	
}
