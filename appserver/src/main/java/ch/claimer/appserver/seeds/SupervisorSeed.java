package ch.claimer.appserver.seeds;

import java.util.ArrayList;

import ch.claimer.appserver.repositories.eclipselink.EclipseLinkRepository;
import ch.claimer.shared.models.Login;
import ch.claimer.shared.models.Supervisor;

/**
 * In der SupervisorSeed-Klasse werden die Bauleiter in die Datenbank
 * geschrieben und ein Login zugewiesen. Die Klasse erbt von der Klasse Seed und
 * setzt Supervisor als Typ-Variable.
 * 
 * @author Fabio Baviera
 * @version 1.0
 * @since 1.0
 */

public class SupervisorSeed extends Seed<Supervisor> {

	public SupervisorSeed() {
		this.repository = new EclipseLinkRepository<Supervisor>(
				Supervisor.class);
		this.seed = new ArrayList<Supervisor>();
	}

	@Override
	public void setup() {
		Supervisor Supervisor1 = new Supervisor();
		Supervisor1.setLastname("M�ller");
		Supervisor1.setFirstname("Sebastian");
		Supervisor1.setEmail("sebastian.mueller@bluewin.ch.ch");
		Supervisor1.setPhone("041 111 11 11");
		Supervisor1.setLogin((Login) seeds.get("Login").get(9));

		Supervisor Supervisor2 = new Supervisor();
		Supervisor2.setLastname("Ferrari");
		Supervisor2.setFirstname("Enzo");
		Supervisor2.setEmail("enzo.ferrari@bluewin.ch.ch");
		Supervisor2.setPhone("041 111 11 12");
		Supervisor2.setLogin((Login) seeds.get("Login").get(20));
		
		Supervisor Supervisor3 = new Supervisor();
		Supervisor3.setLastname("Bauleiter");
		Supervisor3.setFirstname("Stanard");
		Supervisor3.setLogin((Login) seeds.get("Login").get(25));

		seed.add(Supervisor1);
		seed.add(Supervisor2);
		seed.add(Supervisor3);

		Seed.seeds.put("Supervisor", seed);
	}

}
