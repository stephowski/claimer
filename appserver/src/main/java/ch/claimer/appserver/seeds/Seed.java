package ch.claimer.appserver.seeds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.claimer.appserver.repositories.Repository;

/**
 * 
 * @author Stephan Beeler
 * @author Raoul Ackermann
 */
public abstract class Seed<T> {
	
	protected Map<String, List<T>> seeds = new HashMap<String, List<T>>();
	protected List<T> list;
	protected Repository<T> repository;
	
	public static void main(String[] args) {
		CategorySeed categorySeed = new CategorySeed();
		categorySeed.setup();
		categorySeed.execute();
		
		StateSeed stateSeed = new StateSeed();
		stateSeed.setup();
		stateSeed.execute();
		
		TypeSeed typeSeed = new TypeSeed();
		typeSeed.setup();
		typeSeed.execute();
		
		LogEntrySeed logEntrySeed = new LogEntrySeed();
		logEntrySeed.setup();
		logEntrySeed.execute();
		
		RoleSeed roleSeed = new RoleSeed();
		roleSeed.setup();
		roleSeed.execute();
		
		LoginSeed loginSeed = new LoginSeed();
		loginSeed.setup();
		loginSeed.execute();
		
		SubcontractorSeed subcontractorSeed = new SubcontractorSeed();
		subcontractorSeed.setup();
		subcontractorSeed.execute();
		
		SupervisorSeed supervisorSeed = new SupervisorSeed();
		supervisorSeed.setup();
		supervisorSeed.execute();
		
		GCEmployeeSeed gcemployeeSeed = new GCEmployeeSeed();
		gcemployeeSeed.setup();
		gcemployeeSeed.execute();
		
		SCEmployeeSeed scemployeeSeed = new SCEmployeeSeed();
		scemployeeSeed.setup();
		scemployeeSeed.execute();
		
		ContactSeed contactSeed = new ContactSeed();
		contactSeed.setup();
		contactSeed.execute();
		
		ProjectSeed projectSeed = new ProjectSeed();
		projectSeed.setup();
		projectSeed.execute();
		
		IssueSeed issueSeed = new IssueSeed();
		issueSeed.setup();
		issueSeed.execute();
		
		CommentSeed commentSeed = new CommentSeed();
		commentSeed.setup();
		commentSeed.execute();
		
		
	}
	
	public abstract void setup();
	public void execute() {
		for(List<T> seed : seeds.values()) {
			for(T t : seed) {
				repository.create(t);
			}
		}
	}
}