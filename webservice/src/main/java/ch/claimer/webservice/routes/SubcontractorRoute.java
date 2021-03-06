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

import ch.claimer.shared.models.Subcontractor;
import ch.claimer.webservice.controller.Controller;

/**
 * Definiert die verf�gbaren HTTP-Routes der Subunternehmen.
 * L�dt anhand der URL und der HTTP-Anfrage die entsprechende Controller-Methode.
 * Liefert eine HTTP-Antwort mit Statuscode zur�ck.
 * 
 * @author Momcilo Bekcic
 * @version 1.1
 * @since 1.0
 */

@Path("/")
public class SubcontractorRoute {	
	
	private Controller<Subcontractor> controller;

	public SubcontractorRoute() {
		this.controller = new Controller<Subcontractor>(Subcontractor.class);
	}
	
	/**
	 * Zeigt alle Subunternehmen
	 * 
	 * @return Response HTTP-Antwort mit Subunternehmen
	 */
	@GET
	@RolesAllowed("editor-intern")
	@Path("subcontractor") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response showAll() {
		return controller.showByProperty("isActive", true);
	}
	
	
	/**
	 * Zeigt ein bestimmtes Subunternehmen an
	 * 
	 * @param id Identifikator des anzuzeigenden Subunternehmens
	 * @return Response HTTP-Antwort mit Subunternehmen
	 */
	@GET
	@RolesAllowed("editor-extern")
	@Path("subcontractor/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showById(@PathParam("id") int id) {
		return controller.showById(id);
	}
	
	/**
	 * Legt ein neues Subunternehmen an
	 * 
	 * @param subcontractor neu anzulegendes Subunternehmen
	 * @return Response HTTP-Antwort mit Statusmeldung
	 */
	@POST
	@RolesAllowed("admin")
	@Path("subcontractor")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Subcontractor subcontractor) {	
		return controller.store(subcontractor);
	}
	
	/**
	 * Aktualisiert ein bestehendes Subunternehmen
	 * 
	 * @param subcontractor zu aktualisierendes Subunternehmen
	 * @return Response HTTP-Antwort mit Statusmeldung
	 */
	@PUT
	@RolesAllowed("admin")
	@Path("subcontractor")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(Subcontractor subcontractor) {
		return controller.update(subcontractor);
	}	
}