package security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import persistence.User;
import services.interfaces.UserServiceLocal;

@RequestScoped
public class AuthenticatedUserProducer {

	@Produces
	@RequestScoped
	@AuthenticatedUser
	private User authenticatedUser;

	@Inject
	private UserServiceLocal userServiceLocal;

	public void handleAuthenticationEvent(
			@Observes @AuthenticatedUser String username) {
		this.authenticatedUser = userServiceLocal.getUserByLogin(username);
	}

}
