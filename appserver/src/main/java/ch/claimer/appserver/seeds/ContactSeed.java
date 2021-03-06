package ch.claimer.appserver.seeds;

import java.util.ArrayList;

import ch.claimer.appserver.repositories.eclipselink.EclipseLinkRepository;
import ch.claimer.shared.models.Contact;
import ch.claimer.shared.models.Login;
import ch.claimer.shared.models.Subcontractor;

/**
 * In der ContactSeed-Klasse werden die Kontaktpersonen der Subunternehmen in
 * die Datenbank geschrieben. Die Klasse erbt von der Klasse Seed und setzt
 * Contact als Typ-Variable.
 * 
 * @author Fabio Baviera
 * @version 1.0
 * @since 1.0
 */

public class ContactSeed extends Seed<Contact> {

	public ContactSeed() {
		this.repository = new EclipseLinkRepository<Contact>(Contact.class);
		this.seed = new ArrayList<Contact>();
	}

	@Override
	public void setup() {
		Contact Contact1 = new Contact();
		Contact1.setLastname("Schmid");
		Contact1.setFirstname("Manfred");
		Contact1.setEmail("manfred.schmid@bluewin.ch");
		Contact1.setPhone("041 111 11 11");
		Contact1.setLogin((Login) Seed.seeds.get("Login").get(10));
		Contact1.setSubcontractor((Subcontractor) Seed.seeds.get(
				"Subcontractor").get(0));

		Contact Contact2 = new Contact();
		Contact2.setLastname("Fankhauser");
		Contact2.setFirstname("Toni");
		Contact2.setEmail("toni.fankhauser@bluewin.ch");
		Contact2.setPhone("041 111 11 99");
		Contact2.setLogin((Login) Seed.seeds.get("Login").get(16));
		Contact2.setSubcontractor((Subcontractor) Seed.seeds.get(
				"Subcontractor").get(1));

		Contact Contact3 = new Contact();
		Contact3.setLastname("Huber");
		Contact3.setFirstname("Ueli");
		Contact3.setEmail("ueli.huber@bluewin.ch");
		Contact3.setPhone("041 111 11 98");
		Contact3.setLogin((Login) Seed.seeds.get("Login").get(17));
		Contact3.setSubcontractor((Subcontractor) Seed.seeds.get(
				"Subcontractor").get(2));

		Contact Contact4 = new Contact();
		Contact4.setLastname("Rodel");
		Contact4.setFirstname("Anna");
		Contact4.setEmail("anna.rodel@bluewin.ch");
		Contact4.setPhone("041 111 11 97");
		Contact4.setLogin((Login) Seed.seeds.get("Login").get(18));
		Contact4.setSubcontractor((Subcontractor) Seed.seeds.get(
				"Subcontractor").get(3));

		Contact Contact5 = new Contact();
		Contact5.setLastname("H�beli");
		Contact5.setFirstname("Kurt");
		Contact5.setEmail("kurt.h�berli@bluewin.ch.ch");
		Contact5.setPhone("041 111 11 95");
		Contact5.setLogin((Login) Seed.seeds.get("Login").get(19));
		Contact5.setSubcontractor((Subcontractor) Seed.seeds.get(
				"Subcontractor").get(4));
		
		Contact Contact6 = new Contact();
		Contact6.setLastname("Ansprechperson");
		Contact6.setFirstname("Standard");
		Contact6.setLogin((Login) Seed.seeds.get("Login").get(24));
		Contact6.setSubcontractor((Subcontractor) Seed.seeds.get(
				"Subcontractor").get(0));

		seed.add(Contact1);
		seed.add(Contact2);
		seed.add(Contact3);
		seed.add(Contact4);
		seed.add(Contact5);
		seed.add(Contact6);

		Seed.seeds.put("Contact", seed);
	}

}
