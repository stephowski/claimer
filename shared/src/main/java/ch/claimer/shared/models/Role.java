package ch.claimer.shared.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Repräsentiert die Entität Rolle und gewährleistet Zugriff auf die
 * Eigenschaften der Klasse via Getter- und Setter-Methoden.
 * 
 * @author Stephan Beeler
 * @version 1.0
 * @since 1.0
 */

@Entity
public class Role implements Serializable, Model {

	private static final long serialVersionUID = 5766207295876788122L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int value;

	public Role() {

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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
