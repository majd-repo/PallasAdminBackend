package security;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import results.UserResult;
import services.interfaces.UserServiceLocal;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Inject
	private UserServiceLocal userServiceLocal;

	@Inject
	@AuthenticatedUser
	Event<String> userAuthenticatedEvent;

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		String token = requestContext
				.getHeaderString(HttpHeaders.AUTHORIZATION);
		Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.INFO,
				"AuthenticationFilter : Token : " + token);

		if (token == null) {
			throw new NotAuthorizedException(
					"Authorization header must be provided");
		}

		try {
			String loginName = validateToken(token).getLogin();
			System.out.println("After authentication, this is the username : "
					+ loginName);
			userAuthenticatedEvent.fire(loginName);
		} catch (Exception e) {
			requestContext.abortWith(Response.status(
					Response.Status.UNAUTHORIZED).build());
		}

	}

	/*
	 * Validate the token and return the user elsewhere throw Exception
	 */
	private UserResult validateToken(String token) throws Exception {

		UserResult userByToken = userServiceLocal.getUserByToken(token);

		if (userByToken == null) {
			throw new Exception();
		}

		// if he is a visitor and not active
		if (userByToken.getRole().equals("VISITOR")
				|| userByToken.getRole().equals("AGENT")) {
			if (userByToken.getActive() == false) {
				throw new Exception();
			}
		}
		if (System.currentTimeMillis() - userByToken.getLastTokenUpdate() > 1200000
				&& ((userByToken.getKeepmeconnected() == false) || (userByToken
						.getKeepmeconnected() == null))) {
			System.out.println("this why we throw an exception : "
					+ (System.currentTimeMillis() - userByToken
							.getLastTokenUpdate()));
			throw new Exception();
		} else {
			userServiceLocal.updateLastTokenUpdate(token);
			return userByToken;
		}

	}
}