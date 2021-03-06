package ch.claimer.client.filter;

import java.io.IOException;
import java.util.Base64;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;

import ch.claimer.client.util.AuthenticationUtil;

/**
 * @author Stephan Beeler
 * @since 1.0
 * @version 1.0
 */

@Provider
public class AuthenticationFilter implements ClientRequestFilter {

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		
		if(AuthenticationUtil.getLogin() == null) {
			return;
		}
		String username = AuthenticationUtil.getLogin().getUsername();
		String password = AuthenticationUtil.getLogin().getPassword();
		
		String basic = username + ":" + password;
		
		basic = new String(Base64.getEncoder().encode(basic.getBytes()));		
		requestContext.getHeaders().add("Authorization", "Basic " + basic);
	}

}