package rest;

import httpObjects.TokenResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import persistence.Credentials;
import persistence.User;
import services.interfaces.UserServiceLocal;
import util.TokenUtils;

@RequestScoped
@Path("authentication")
public class AuthenticationResource {

	@Inject
	private UserServiceLocal userServiceLocal;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateUser(Credentials credentials) {

		try {
			User userLoggedIn = userServiceLocal.authenticate(
					credentials.getUsername(), credentials.getPassword());
			String token = TokenUtils.createToken();
			TokenResponse tokenResponse = new TokenResponse(token);
			userServiceLocal.setToken(userLoggedIn, token);
			return Response.ok(tokenResponse).build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
