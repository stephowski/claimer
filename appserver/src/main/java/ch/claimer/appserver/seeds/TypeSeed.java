package ch.claimer.appserver.seeds;

import java.util.ArrayList;

import ch.claimer.appserver.repositories.eclipselink.EclipseLinkRepository;
import ch.claimer.shared.models.Type;

/**
 * In der TypeSeed-Klasse werden die Typen erfasst, welche ein Projekt haben
 * kann und in die Datenbank geschrieben. Die Klasse erbt von der Klasse Seed
 * und setzt Type als Typ-Variable.
 * 
 * @author Fabio Baviera
 * @author Raoul Ackermann
 * @version 1.0
 * @since 1.0
 */

public class TypeSeed extends Seed<Type> {

	public TypeSeed() {
		this.repository = new EclipseLinkRepository<Type>(Type.class);
		this.seed = new ArrayList<Type>();
	}

	@Override
	public void setup() {
		Type t1 = new Type();
		t1.setName("Neubau");
		Type t2 = new Type();
		t2.setName("Rennovation");
		Type t3 = new Type();
		t3.setName("Umbau");

		seed.add(t1);
		seed.add(t2);
		seed.add(t3);

		Seed.seeds.put("Type", seed);
	}

}
