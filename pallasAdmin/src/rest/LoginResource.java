package rest;

import httpObjects.MobileLoginEnvelope;
import httpObjects.TokenEnvelope;
import httpObjects.UserLogged;

import java.io.NotActiveException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import persistence.Credentials;
import persistence.User;
import services.interfaces.UserServiceLocal;
import util.TokenUtils;

@RequestScoped
@Path("auth")
public class LoginResource {

	@Inject
	private UserServiceLocal userServiceLocal;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(Credentials credentials) {

		try {
			User userFound = userServiceLocal.authenticate(
					credentials.getUsername(), credentials.getPassword());
			if (userFound == null) {
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}
			userServiceLocal.setToken(userFound, TokenUtils.createToken());
			UserLogged userLogged = new UserLogged();
			userLogged.setToken(userFound.getToken());
			userLogged.setFirstName(userFound.getFirstName());
			userLogged.setLastName(userFound.getLastName());
			return Response.ok(userLogged).build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();

		}
	}

	@Path("auth_mobile")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateMobile(MobileLoginEnvelope mobileLoginEnvelope) {

		try {

			User userFound = userServiceLocal.authenticate(
					mobileLoginEnvelope.getUsername(),
					mobileLoginEnvelope.getPassword());

			if (userFound == null) {

				throw new NotFoundException();
			}

			// if he is a visitor and not active
			if (userFound.getRole().equals("VISITOR")
					&& userFound.getActive() == false) {
				throw new NotActiveException();
			}
			// if he is an agent and not active
			if (userFound.getRole().equals("AGENT")
					&& userFound.getActive() == false) {
				throw new NotActiveException();
			}

			// update the token and the lastTimeTokenUpdate after each login
			userServiceLocal.setToken(userFound, TokenUtils.createToken());

			// update the flag keepmeconnected after each login from the mobile
			// app
			userServiceLocal.setKeepMeConnected(userFound,
					mobileLoginEnvelope.getKeepmeconnected());

			UserLogged userLogged = new UserLogged();

			userLogged.setToken(userFound.getToken());
			userLogged.setFirstName(userFound.getFirstName());
			userLogged.setLastName(userFound.getLastName());
			userLogged.setRole(userFound.getRole());
			return Response.ok(userLogged).build();
		} catch (NotFoundException nfe) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} catch (NotActiveException e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	@Path("checkToken")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean checkToken(TokenEnvelope tokenEnvelope) {
		Boolean result = false;

		try {
			User userFound = userServiceLocal.findUserByToken(tokenEnvelope
					.getToken());
			if (userFound != null && userFound.getKeepmeconnected() == true) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
		}

		return result;

	}

	@Path("logout/{token}")
	@GET
	public void logout(@PathParam("token") String token) {
		userServiceLocal.clearToken(token);
	}

}
