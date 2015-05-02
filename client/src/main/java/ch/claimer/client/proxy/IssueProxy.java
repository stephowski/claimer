package ch.claimer.client.proxy;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author Kevin Stadelmann
 *
 */

public interface IssueProxy {
	@GET
	@Path("/issue/project/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	String getByProject();
	
	@GET
	@Path("/issue/contact/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	String getByContact();
	
	@GET
	@Path("/issue/subcontractor/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	String getBySubcontractor();
	
	@PUT
	@Path("/issue/project/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	String updateByProject();
}