package ch.claimer.webservice.routes;


import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.claimer.shared.models.Contact;
import ch.claimer.shared.models.Issue;
import ch.claimer.shared.models.Project;
import ch.claimer.shared.models.Subcontractor;
import ch.claimer.webservice.controller.Controller;


/**
 * Definiert die verf�gbaren HTTP-Routes der M�ngel.
 * L�dt anhand der URL und der HTTP-Anfrage die entsprechende Controller-Methode.
 * Liefert eine HTTP-Antwort mit Statuscode zur�ck.
 * 
 * @author Momcilo Bekcic
 * @version 1.0
 * @since 1.0
 */
@Path("/")
public class IssueRoute {

	private Controller<Issue> controller;

	public IssueRoute() {
		this.controller = new Controller<Issue>(Issue.class);
	}
	
	/**
	 * Zeigt alle M�ngel an
	 * 
	 * @return Response HTTP-Antwort mit M�ngeln
	 */
	@GET
	@RolesAllowed("admin")
	@Path("issue") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAll() {
		return controller.showAll();
	}
	
	/**
	 * Zeigt einen bestimmten Mangel an
	 * 
	 * @param id Identifikator des anzuzeigenden Mangels
	 * @return Response HTTP-Antwort mit dem Mangel
	 */
	@GET
	@RolesAllowed("admin")
	@Path("issue/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showById(@PathParam("id") int id) {
		return controller.showById(id);
	}

	/**
	 * Zeigt alle M�ngel eines Projektes an
	 * 
	 * @param id Identifikator des Projektes der anzuzeigenden M�ngel
	 * 
	 * @return Response HTTP-Antwort mit M�ngeln
	 */
	@GET
	@RolesAllowed("editor-intern")
	@Path("issue/project/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showByProject(@PathParam("id") int id) {
		return controller.showByRelation(Project.class, id);
	}

	/**
	 * Zeigt alle M�ngel, die zu einer Ansprechspersone geh�ren, an
	 * 
	 * @param id Identifikator der Ansprechsperson der anzuzeigenden M�ngel
	 * @return Response HTTP-Antwort mit M�ngeln
	 */
	@GET
	@RolesAllowed("editor-extern")
	@Path("issue/contact/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showByContact(@PathParam("id") int id) {
		return controller.showByRelation(Contact.class, id);
	}
	
	/**
	 * Zeigt alle M�ngel eines Subunternehmens an
	 * 
	 * @param id Identifikator des Subunternehmens der anzuzeigenden M�ngel
	 * @return Response HTTP-Antwort mit M�ngeln
	 */
	@GET
	@RolesAllowed("power")
	@Path("issue/subcontractor/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showBySubcontractor(@PathParam("id") int id) {
		return controller.showByRelation(Subcontractor.class, id);
	
	}
	
	/**
	 * Legt einen neuen Mangel an
	 * 
	 * @param issue neu anzulegender Mangel
	 * @return Response HTTP-Antwort mit Statusmeldung
	 */
	@POST
	@RolesAllowed("editor-intern")
	@Path("issue")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Issue issue) {	
		return controller.store(issue);
	}
	
	/**
	 * Aktualisiert einen bestehenden Mangel
	 * 
	 * @param issue zu aktualisierender Mangel
	 * @return Response HTTP-Antwort mit Statusmeldung
	 */
	@PUT
	@RolesAllowed("editor-extern")
	@Path("issue")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(Issue issue) {
		return controller.update(issue);
	}	
	
}