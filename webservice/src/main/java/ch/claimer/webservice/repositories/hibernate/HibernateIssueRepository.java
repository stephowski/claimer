package ch.claimer.webservice.repositories.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ch.claimer.shared.models.Issue;
import ch.claimer.webservice.repositories.IssueRepository;
import ch.claimer.webservice.util.HibernateUtil;

public class HibernateIssueRepository implements IssueRepository {
	
	private Session session;
	
	public HibernateIssueRepository() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> getByContact(Integer id) {
		Query query = session.createQuery("select i from Issue i inner join i.contact c where c.id = "+ id);  
		List<Issue> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> getBySupervisor(Integer id) {
		Query query = session.createQuery("select i from Issue i inner join i.supervisor s where s.id = "+ id);  
		List<Issue> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> getByState(Integer id) {
		Query query = session.createQuery("select i from Issue i inner join i.state s where s.id = "+ id);  
		List<Issue> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> getByProject(Integer id) {
		Query query = session.createQuery("select i from Issue i inner join i.project p where p.id = "+ id);  
		List<Issue> list = query.list();
		return list;
	}

}
