package rest;

import httpObjects.OldNewPasswordEnvelope;
import httpObjects.PasswordEnvelope;
import httpObjects.TokenEnvelope;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import persistence.User;
import results.UserResult;
import security.Role;
import security.Secured;
import services.interfaces.UserServiceLocal;

//@Secured({ Role.ADMIN, Role.VISITOR, Role.AGENT })
@RequestScoped
@Path("users")
public class UserResource {
	@Inject
	private UserServiceLocal userServiceLocal;

	// @Secured({ Role.ADMIN })
	@GET
	@Path("userByLogin/{login}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User doFindUserByLogin(@PathParam("login") String login) {
		return userServiceLocal.getUserByLogin(login);
	}

	// @Secured({ Role.VISITOR, Role.AGENT })
	@POST
	@Path("userByToken")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserResult doFindUserByToken(TokenEnvelope tokenEnvelope) {
		return userServiceLocal.getUserByToken(tokenEnvelope.getToken());
	}

	@Secured({ Role.ADMIN })
	@Path("updatePassword")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void doUpdatePassword(PasswordEnvelope passwordEnvelope) {
		userServiceLocal.updatePassword(passwordEnvelope);
	}

	@Secured({ Role.VISITOR })
	@Path("mobileUpdatePassword")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void doNewUpdatePassword(OldNewPasswordEnvelope passwordEnvelope) {
		userServiceLocal.mobileUpdatePassword(passwordEnvelope);
	}

}
