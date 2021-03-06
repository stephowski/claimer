package ch.claimer.client.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.pmw.tinylog.Logger;

import ch.claimer.client.proxy.ContactProxy;
import ch.claimer.client.proxy.StateProxy;
import ch.claimer.client.proxy.SubcontractorProxy;
import ch.claimer.client.util.AuthenticationUtil;
import ch.claimer.client.util.ResteasyClientUtil;
import ch.claimer.shared.models.Comment;
import ch.claimer.shared.models.Contact;
import ch.claimer.shared.models.Issue;
import ch.claimer.shared.models.LogEntry;
import ch.claimer.shared.models.State;
import ch.claimer.shared.models.Subcontractor;
import ch.claimer.shared.models.Supervisor;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Controller f�r die MangelVerwalten-Ansicht
 * 
 * @author Michael L�tscher, Alexander Hauck
 * @since 1.0
 * @version 2.0
 *
 */

public class IssueController implements Initializable {
	
    private ObjectMapper mapper =  new ObjectMapper();
    private ObservableList<LogEntry> logEntryList = FXCollections.observableArrayList();
    private Integer issueId = null;
    private List<Subcontractor> subcontractorList = null;
    private Issue issueContainer = null;
    ObservableList<Comment> commentsList = FXCollections.observableArrayList();
    private Integer dropdownValue = AuthenticationUtil.getLogin().getRole().getValue();

	@FXML
	private Pane mainContent;
	
	@FXML
	private DatePicker dateCreated;
	
	@FXML
	private DatePicker dateEnd;

	@FXML
	private Label lblTitle;

	@FXML
	private TextField txtComment;

	@FXML
	private TextArea txtIssueDescription;

	@FXML
	private ComboBox<String> dropdownState;

	@FXML
	private Button btnSave;
	
	@FXML
	private Button btnSaveComment;

	@FXML
	private TableView<Comment> commentTableView;

	@FXML
	private TableColumn<Comment, String> colComment;

	@FXML
	private TableColumn<Comment, String> colAuthor;

	@FXML
	private TableColumn<Comment, String> colAdded;

	@FXML
	private ComboBox<String> dropdownSubcontractor;
	
	@FXML
	private ComboBox<String> dropdownContact;
	
	@FXML
	private TableView<LogEntry> logTableView;
	
	@FXML
	private TableColumn<LogEntry, String> colLogDate;
	
	@FXML
	private TableColumn<LogEntry, String> colLogDescription;
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Kommentare hinzuf�gen nur f�r Bauleiter und Kontaktpersonen erlauben
		Integer roleValue = AuthenticationUtil.getLogin().getRole().getValue();
		
		if(roleValue > 10) {
			txtComment.setVisible(false);
			btnSaveComment.setVisible(false);
		}
		
		if(roleValue > 5) {
			setDropdownSubcontractor();
		}
		
		if(roleValue == 5) {
			txtIssueDescription.setEditable(false);
			dateCreated.setEditable(false);
			dateEnd.setEditable(false);
			
		}
		
		setDropdownState();
		//Listener,um �nderungen am Subunternehmen-Dropdown zu �berpr�fen.
		dropdownSubcontractor.valueProperty().addListener(new ChangeListener<String>() {
	
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				dropdownContact.getItems().clear();
				for(Subcontractor sc : subcontractorList) {
					if(sc.getName().equals(newValue)) {
						setDropdownContact(sc.getId());
					}
				}
			}
		 });
		
	}
	
	
	/**
	 * F�llt die Textfelder mit den Daten des zu bearbeitenden Mangels.
	 * @param issueToEdit - Der zu bearbeitende Mangel wird hier mitgegeben.
	 */
	public void initData(Issue issueToEdit) {
		issueId = issueToEdit.getId();
		issueContainer = issueToEdit;
		commentsList.addAll(issueContainer.getComments());
		lblTitle.setText("Mangel bearbeiten");	

		dropdownState.setValue(issueToEdit.getState().getName());	
		txtIssueDescription.setText(issueToEdit.getDescription());
		if(issueToEdit.getSubcontractor() != null) {
			dropdownSubcontractor.setValue(issueToEdit.getSubcontractor().getName());
		}
		if(issueToEdit.getContact() != null) {
			dropdownContact.setValue(issueToEdit.getContact().getLastname() + ", " + issueToEdit.getContact().getFirstname());
		}
			
		long timeStart;
		long timeEnd;
		long days;
		Date date = new Date();
		long timenow = date.getTime();
		long daysnow = Math.round( (double)timenow / (24. * 60.*60.*1000.));
		long diff;
			
		//Erstellungsdatum
		timeStart = issueToEdit.getCreated().getTime().getTime();
		days = Math.round( (double)timeStart / (24. * 60.*60.*1000.));
		diff = days - daysnow;
		dateCreated.setValue(LocalDate.now().plusDays(diff));
		
		//Enddatum
		timeEnd = issueToEdit.getSolved().getTime().getTime();
		days = Math.round( (double)timeEnd / (24. * 60.*60.*1000.));
		diff = days - daysnow;
		dateEnd.setValue(LocalDate.now().plusDays(diff));
		fillCommentTableView();
		fillLogTableView();
	
	}

	
	/**
	 * Speichert den Mangel.
	 */
	@FXML
	private void saveIssue() {
		 Issue issue = getTextfieldProperties();
		
		if(issue != null) {
			issue.setComments(commentsList);
			logEntryHandler(issue);
			issue.setLogEntries(logEntryList);
			ProjectAddController.dataTransfer.add(issue);
			ProjectAddController.dataTransfer.clear();
			closeStage();
		}
	}
	
	/**
	 * Die Methode �berpr�ft, ob beim �bergeben String die Mindest- und Maximuml�nge stimmt.
	 * 
	 * @param text - Text der �bergeben wird.
	 * @param minLength - Minimuml�nge die �berpr�ft werden soll.
	 * @param maxLength - Maximuml�nge die �berpr�ft werden soll.
	 * @return true oder false
	 */
	private Boolean checkLength(String text, int minLength, int maxLength) {
		if(text.length() < minLength || text.length() > maxLength) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * �berpr�ft �nderungen am Mangel und protokolliert diese.
	 * @param issue - Das zu protokollierende Projekt.
	 */
	private void logEntryHandler(Issue issue) {
		
		if(issueId != null) {
			if(!issue.getDescription().equals(issueContainer.getDescription())) {
				LogEntry logEntry = new LogEntry();
				logEntry.setDate(new GregorianCalendar());
				logEntry.setDescription("Der Name des Mangels wurde von \"" + issueContainer.getDescription() + "\" auf \"" + issue.getDescription() + "\" ge�ndert.");
				logEntryList.add(logEntry);
			}
			
			if(!issue.getState().getName().equals(issueContainer.getState().getName())) {
				LogEntry logEntry = new LogEntry();
				logEntry.setDate(new GregorianCalendar());
				logEntry.setDescription("Der Status wurde von \"" + issueContainer.getState().getName() + "\" auf \"" + issue.getState().getName() + "\" ge�ndert.");
				logEntryList.add(logEntry);
			}
			
			if(issue.getSubcontractor() != null && issueContainer.getSubcontractor() != null) {
				if(!issue.getSubcontractor().getName().equals(issueContainer.getSubcontractor().getName())) {
					LogEntry logEntry = new LogEntry();
					logEntry.setDate(new GregorianCalendar());
					logEntry.setDescription("Das zust�ndige Subunternehmen wurde von \"" + issueContainer.getSubcontractor().getName() + "\" auf \"" + issue.getSubcontractor().getName() + "\" ge�ndert.");
					logEntryList.add(logEntry);
				}
			}
			
			if(issue.getContact() != null && issueContainer.getContact() != null) {
				if((!issue.getContact().getFirstname().equals(issueContainer.getContact().getFirstname()) && 
						(!issue.getContact().getLastname().equals(issueContainer.getContact().getLastname())))) {
					LogEntry logEntry = new LogEntry();
					logEntry.setDate(new GregorianCalendar());
					logEntry.setDescription("Die Ansprechperson wurde von \"" + issueContainer.getContact().getFirstname() + " " + issueContainer.getContact().getLastname() + 
							"\" auf \"" + issue.getContact().getFirstname() + " " + issue.getContact().getLastname() + "\" ge�ndert.");
					logEntryList.add(logEntry);
				}
			}
			
			for(Comment comment : commentsList) {
				if(!issueContainer.getComments().contains(comment)) {
					LogEntry logEntry = new LogEntry();
					logEntry.setDate(new GregorianCalendar());
					logEntry.setDescription("Ein neuer Kommentar wurde erfasst.");
					logEntryList.add(logEntry);
				}
			}
			
		} else {
			LogEntry logEntry = new LogEntry();
			logEntry.setDate(new GregorianCalendar());
			logEntry.setDescription("Der Mangel \"" + issue.getDescription() + "\" wurde dur erfasst.");
			logEntryList.add(logEntry);
		}
		
	}
	
	
	/**
	 * Liest die Textfelder aus und validiert diese.
	 * @return issue - Gibt den Mangel mit den ausgelesenen Textfeldern zur�ck.
	 */
	private Issue getTextfieldProperties() {

		Issue issue = new Issue();
		Boolean validationError = false;
		
		if(issueId != null) {
			issue.setId(issueId);
		}
		
		String description = txtIssueDescription.getText();
		if(checkLength(description, 1, 255)) {
			validationError = true;
			txtIssueDescription.getStyleClass().add("txtError");
		} else {
			issue.setDescription(description);
		}
		
		//Subunternehmen dem Mangel zuweisen.
		if(dropdownSubcontractor.getValue() != null) { 
			if(dropdownValue == 5) {
				issue.setSubcontractor(issueContainer.getSubcontractor());
			} else {
				try {
					
					SubcontractorProxy scProxy = ResteasyClientUtil.getTarget().proxy(SubcontractorProxy.class);		
					ObjectMapper mapper = new ObjectMapper();	    
					List<Subcontractor> scList = mapper.readValue(scProxy.getAll(), new TypeReference<List<Subcontractor>>(){});
					
					for(Subcontractor sc: scList) {
						if(sc.getName().equals(dropdownSubcontractor.getValue())) {
							issue.setSubcontractor(sc);
						}
					}
					} catch (IOException e1) {
						Logger.error("Subunternehmen k�nnen nicht aus der Datenbank geladen werden.");
					}
			}
			
		}
		if(dropdownContact.getValue() != null && dropdownContact.getValue() != "") {
			
			String contactName = dropdownContact.getValue();
			String[] parts = contactName.split(",");
			String lastname = parts[0];
			String firstname = parts[1].substring(1);
			
			try {
				ContactProxy cProxy = ResteasyClientUtil.getTarget().proxy(ContactProxy.class);			    
			    List<Contact> contactList = mapper.readValue(cProxy.getBySubcontractor(issue.getSubcontractor().getId()), new TypeReference<List<Contact>>(){});
			    
			    for(Contact contact: contactList) {
					if(contact.getFirstname().equals(firstname) && contact.getLastname().equals(lastname))
						issue.setContact(contact);;	
				}
			} catch (IOException e1) {
				Logger.error("Ansprechpersonen k�nnen nicht aus der Datenbank geladen werden.");
			}
		}
		
		// Status dem Mangel zuweisen
		if(dropdownState.getValue() != null) {
			try {
				StateProxy stateProxy = ResteasyClientUtil.getTarget().proxy(StateProxy.class);			    
			    List<State> stateList = mapper.readValue(stateProxy.getAll(), new TypeReference<List<State>>(){});
			    
			    for(State state: stateList) {
					if(state.getName().equals(dropdownState.getValue()))
						issue.setState(state);	
				}
			} catch (IOException e1) {
				Logger.error("Status k�nnen nicht aus der Datenbank geladen werden.");
			}
		} else {
			validationError = true;
			dropdownState.getStyleClass().add("txtError");
		}
		
		//Startdatum generieren
		if(dateCreated.getValue() != null) {
			Integer dayCreated = dateCreated.getValue().getDayOfMonth();
		    Integer monthCreated = dateCreated.getValue().getMonthValue();
		    Integer yearCreated =  dateCreated.getValue().getYear();
		    
		    GregorianCalendar dateCreated = new GregorianCalendar();
		    dateCreated.set(yearCreated, monthCreated - 1, dayCreated);
		    issue.setCreated(dateCreated);
		} else {
			validationError = true;
			dateCreated.getStyleClass().add("txtError");
		}
	    
	    //Startdatum generieren
		if(dateEnd.getValue() != null) {
	  		Integer dayEnd = dateEnd.getValue().getDayOfMonth();
	  	    Integer monthEnd = dateEnd.getValue().getMonthValue();
	  	    Integer yearEnd =  dateEnd.getValue().getYear();
	  	    
	  	    GregorianCalendar dateEnd = new GregorianCalendar();
	  	    dateEnd.set(yearEnd, monthEnd - 1, dayEnd);
	  	    issue.setSolved(dateEnd);
		} else {
			validationError = true;
			dateEnd.getStyleClass().add("txtError");
		}
		
		if(validationError == false) {
			return issue;
		} else {
			return null;
		}
	}
		
	
	/**
	 * Schliesst das aktuelle Fenster.
	 */
	@FXML
	private void closeStage() {
		Stage stage = (Stage) btnSave.getScene().getWindow();
	    stage.close();
	}

		
	/**
	 * Bef�llt das "Status"-Dropdown mit den Inhalten aus der Datenbank.
	 */
	public void setDropdownState()  {

		try {

			StateProxy stateProxy = ResteasyClientUtil.getTarget().proxy(StateProxy.class);		
			ObjectMapper mapper = new ObjectMapper();	    
			List<State> stateList = mapper.readValue(stateProxy.getAll(), new TypeReference<List<State>>(){});

			for(State state: stateList) {
				dropdownState.getItems().add(state.getName());
			}
		} catch (IOException e1) {
			Logger.error("Status k�nnen nicht aus der Datenbank geladen werden.");
		}

		
	}

	/**
	 * Bef�llt das "Subunternehmen"-Dropdown mit den Inhalten aus der Datenbank.
	 */
	private void setDropdownSubcontractor()  {

		try {
			SubcontractorProxy subcontractorProxy = ResteasyClientUtil.getTarget().proxy(SubcontractorProxy.class);		
			subcontractorList = mapper.readValue(subcontractorProxy.getAll(), new TypeReference<List<Subcontractor>>(){});
			
			for(Subcontractor subcontractor: subcontractorList) {
				dropdownSubcontractor.getItems().add(subcontractor.getName());
			}
			
		} catch (IOException e1) {
			Logger.error("Subunternehmen k�nnen nicht aus der Datenbank geladen werden.");
		}
	}
	
	/**
	 * Bef�llt das "Contact"-Dropdown mit den Inhalten aus der Datenbank.
	 */
	private void setDropdownContact(Integer subcontractorId) {
		
		
		try {
			ContactProxy cProxy = ResteasyClientUtil.getTarget().proxy(ContactProxy.class);		
			List<Contact> contactList = mapper.readValue(cProxy.getBySubcontractor(subcontractorId), new TypeReference<List<Contact>>(){});
			
			
			for(Contact contact: contactList) {
				if(contact.isActive())
				{
					dropdownContact.getItems().add(contact.getLastname() + ", " + contact.getFirstname());
				}
			}
		} catch (IOException e) {
			Logger.error("Ansprechpersonen k�nnen nicht aus der Datenbank geladen werden.");
		}
	}
	
	/**
	 * L�dt alle Kommentare aus der Datenbank und bef�llt die Tabelle
	 */
	
	private void fillCommentTableView() {
	
		// Spalten-Values definieren
		colComment.setCellValueFactory(new PropertyValueFactory<Comment, String>("content"));

		colAuthor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Comment, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Comment, String> data) {
				try {
					String output = null;
					if(data.getValue().getContact() != null) {
						output = data.getValue().getContact().getFirstname() + " " + data.getValue().getContact().getLastname();
					} else if (data.getValue().getSupervisor() != null) {
						output = data.getValue().getSupervisor().getFirstname() + " " + data.getValue().getSupervisor().getLastname();
					}
					
					return new SimpleStringProperty(output);
				} catch(NullPointerException e) {
					Logger.error("Problem beim Bef�llen der Kommentare-Tabelle, Spalte \"Author\".");
					return null;
				}
			}
		});
		
		colAdded.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Comment, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Comment, String> data) {
				try {
					SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
					String a = format.format(data.getValue().getCreated().getTime());
					return new SimpleStringProperty(a);
				} catch(NullPointerException e) {
					Logger.error("Problem beim Bef�llen der Kommentare-Tabelle, Spalte \"Datum\"");
					return null;
				}
			}
		});

		commentTableView.setItems(commentsList);

		
	}
	
	/**
	 * Bef�llt die Protokoll-Tabelle mit Daten
	 */
	private void fillLogTableView() {
		
		for(LogEntry logEntry : issueContainer.getLogEntries()) {
			logEntryList.add(logEntry);
		}
		
		colLogDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LogEntry, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(TableColumn.CellDataFeatures<LogEntry, String> data) {
				try {
					SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
					String a = format.format(data.getValue().getDate().getTime());
					return new SimpleStringProperty(a);
				} catch(NullPointerException e) {
					Logger.error("Problem beim Bef�llen der Log-Tabelle, Spalte \"Datum\".");
					return null;
				}
			}
		});
		
		colLogDescription.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("description"));
		
		logTableView.setItems(logEntryList);	
	}
	
	/**
	 * Speichert einen Kommentar
	 */
	@FXML
	private void saveComment() {
		if(!checkLength(txtComment.getText(), 1, 255)) {
			Comment comment = new Comment();
			comment.setContent(txtComment.getText());
			switch(AuthenticationUtil.getPerson().getLogin().getRole().getName()) {
				case("editor-extern"): comment.setContact((Contact)AuthenticationUtil.getPerson());
				break;
				case("editor-intern"): comment.setSupervisor((Supervisor)AuthenticationUtil.getPerson());
			}
			comment.setCreated(new GregorianCalendar());
			commentsList.add(comment);
			txtComment.clear();
			fillCommentTableView();
		} else {
			txtComment.getStyleClass().add("txtError");
		}
	}
	
		
}
		
		
