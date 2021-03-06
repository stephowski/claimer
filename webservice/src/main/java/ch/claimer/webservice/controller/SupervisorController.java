package ch.claimer.webservice.controller;

import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.pmw.tinylog.Logger;

import ch.claimer.shared.models.Supervisor;
import ch.claimer.webservice.services.ResponseHandlerService;

/**
 * Behandelt Anfragen spezifisch f�r Bauleiter Generalunternehmung, die
 * �ber die HTTP-Routes vermittelt wurden. Initiert eine Verbindung zur
 * RMI-Schnittstelle und ruft die entsprechenden Methoden. N�tig da
 * GenericEntity nicht mit generischen Typen ausgestattet werden knann.
 * 
 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/ws/rs/core/GenericEntity.html">GenericEntity</a>
 * 
 * @author Stephan Beeler
 * @version 1.0
 * @since 1.0
 */
public class SupervisorController extends Controller<Supervisor> {
	
	public SupervisorController() {
		super(Supervisor.class);
	}

	@Override
	public Response showByProperty(String name, Object value) {
		GenericEntity<List<Supervisor>> genericModels = null;
		try {
			List<Supervisor> models = method.getByProperty(name, value);
			genericModels = new GenericEntity<List<Supervisor>>(models){};
			return ResponseHandlerService.success(genericModels);
		} catch (RemoteException e) {
			Logger.error(e, "Verbindung mit RMI-Dienst fehlgeschlagen");
		}
		return ResponseHandlerService.success(genericModels);
		
	}
}
