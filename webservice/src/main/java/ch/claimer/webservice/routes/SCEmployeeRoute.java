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

import ch.claimer.shared.models.SCEmployee;
import ch.claimer.shared.models.Subcontractor;
import ch.claimer.webservice.controller.SCEmployeeController;

/**
 * Definiert die verf�gbaren HTTP-Routes der Subunternehmen-Angestellten.
 * L�dt anhand der URL und der HTTP-Anfrage die entsprechende Controller-Methode.
 * Liefert eine HTTP-Antwort mit Statuscode zur�ck.
 * 
 * @author Momcilo Bekcic
 * @author Stephan Beeler
 * @version 1.2
 * @since 1.0
 */

@Path("/")
public class SCEmployeeRoute {	
	
	private SCEmployeeController controller;

	public SCEmployeeRoute() {
		this.controller = new SCEmployeeController();
	}
	
	
	/**
	 * Zeigt alle SU-Sachbearbeitende an
	 * 
	 * @return Response HTTP-Antwort mit SU-Sachbearbeitenden
	 */
	@GET
	@RolesAllowed("admin")
	@Path("scemployee") 
	public Response showAll() {
		return controller.showByProperty("isActive", true);
	}
	
	/**
	 * Zeigt einen bestimmten SU-Sachbearbeitenden an
	 * 
	 * @param id Identifikator des anzuzeigenden SU-Sachbearbeitenden
	 * @return Response HTTP-Antwort mit SU-Sachbearbeitenden
	 */
	@GET
	@RolesAllowed("admin")
	@Path("scemployee/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showById(@PathParam("id") int id) {
		return controller.showById(id);
	}
	
	/**
	 * Zeigt alle SU-Angestellten eines Subunternehmens an
	 * 
	 * @param id Identifikator des Subunternehmens der anzuzeigenden SU-Angestellten
	 * @return Response HTTP-Antwort mit SU-Angestellten
	 */
	@GET
	@RolesAllowed("power")
	@Path("scemployee/subcontractor/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showBySubcontractor(@PathParam("id") int id) {
		return controller.showByRelation(Subcontractor.class, id);
	}
	
	/**
	 * Legt einen neuen SU-Sachbearbeitenden an
	 * 
	 * @param scemployee neu anzulegender Sachbearbeitender
	 * @return Response HTTP-Antwort mit Statusmeldung
	 */
	@POST
	@RolesAllowed("power")
	@Path("scemployee")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(SCEmployee scemployee) {	
		return controller.store(scemployee);
	}
	
	/**
	 * Aktualisiert einen besteheden SU-Sachbearbeitenden
	 * 
	 * @param scemployee zu aktualisierender Sachbearbeitende
	 * @return Response HTTP-Antwort mit Statusmeldung
	 */
	@PUT
	@RolesAllowed("power")
	@Path("scemployee")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(SCEmployee scemployee) {
		return controller.update(scemployee);
	}	
}