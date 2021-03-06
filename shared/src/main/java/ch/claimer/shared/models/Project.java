package ch.claimer.shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Repräsentiert die Entität Projekt und gewährleistet Zugriff auf die
 * Eigenschaften der Klasse via Getter- und Setter-Methoden.
 * 
 * @author Stephan Beeler
 * @version 1.0
 * @since 1.0
 */

@Entity
public class Project implements Serializable, Model {

	private static final long serialVersionUID = -8619970776064713003L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar start;
	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar end;
	private String street;
	private String zip;
	private String place;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<LogEntry> logEntries = new ArrayList<LogEntry>();
	@OneToOne(fetch = FetchType.EAGER)
	private Supervisor supervisor;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Contact> contacts = new ArrayList<Contact>();
	@OneToOne(fetch = FetchType.EAGER)
	private Category category;
	@OneToOne(fetch = FetchType.EAGER)
	private Type type;
	@OneToOne(fetch = FetchType.EAGER)
	private State state;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Principal> principals = new ArrayList<Principal>();

	public Project() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GregorianCalendar getStart() {
		return start;
	}

	public void setStart(GregorianCalendar start) {
		this.start = start;
	}

	public GregorianCalendar getEnd() {
		return end;
	}

	public void setEnd(GregorianCalendar end) {
		this.end = end;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public List<LogEntry> getLogEntries() {
		return logEntries;
	}

	public void setLogEntries(List<LogEntry> logEntries) {
		this.logEntries = logEntries;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<Principal> getPrincipals() {
		return principals;
	}

	public void setPrincipals(List<Principal> principals) {
		this.principals = principals;
	}
}
