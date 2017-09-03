package services;

import httpObjects.OldNewPasswordEnvelope;
import httpObjects.PasswordEnvelope;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.WebApplicationException;

import persistence.User;
import results.UserResult;
import security.AuthenticatedUser;
import services.interfaces.UserServiceLocal;

@Stateless
@LocalBean
public class UserService implements UserServiceLocal {

	@Inject
	@AuthenticatedUser
	User authenticatedUser;

	@PersistenceContext
	private EntityManager em;

	public UserService() {

	}

	@Override
	public User authenticate(String login, String password) {
		try {
			return em
					.createQuery(
							"SELECT u FROM User u WHERE u.login = :login AND u.password = :password",
							User.class).setParameter("login", login)
					.setParameter("password", password).getSingleResult();

		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setToken(User user, String token) {
		user.setToken(token);
		user.setLastTokenUpdate(System.currentTimeMillis());
		em.merge(user);
	}

	@Override
	public UserResult getUserByToken(String token) {

		String queryString = "SELECT new results.UserResult(u.id, u.login, u.password, u.firstName, u.lastName, u.email, u.picture, u.lastTokenUpdate, u.keepmeconnected, u.role, u.agentLevel, u.active) FROM User u where u.token =:token";

		try {
			UserResult userResult = null;

			userResult = em.createQuery(queryString, UserResult.class)
					.setParameter("token", token).getSingleResult();

			return userResult;

		} catch (Exception exception) {
			Logger.getLogger(UserService.class.getName()).log(Level.INFO,
					"getUserByToken : no user Found by this token : " + token);
			return null;
		}

	}

	@Override
	public User getUserByLogin(String login) throws WebApplicationException {
		try {
			return em
					.createQuery("SELECT u FROM User u where u.login =:param",
							User.class).setParameter("param", login)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (NonUniqueResultException nure) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public void updatePassword(PasswordEnvelope passwordEnvelope) {

		User user = em
				.createQuery("SELECT u from User u where u.token =:token",
						User.class)
				.setParameter("token", passwordEnvelope.getToken())
				.getSingleResult();
		user.setPassword(passwordEnvelope.getPassword());
		em.merge(user);

	}

	@Override
	public void clearToken(String token) {
		try {
			User user = em
					.createQuery("SELECT u FROM User u where u.token = :param",
							User.class).setParameter("param", token)
					.getSingleResult();
			user.setToken(null);
			em.merge(user);
		} catch (NoResultException nre) {
			Logger.getLogger(UserService.class.getName()).log(Level.WARNING,
					"clearToken : NoResultException : User's Token not found");
		} catch (Exception e) {
			Logger.getLogger(UserService.class.getName()).log(Level.WARNING,
					"clearToken : Exception : User's Token not found");
		}

	}

	@Override
	public void updateLastTokenUpdate(String token) {
		try {
			User user = em
					.createQuery("SELECT u FROM User u where u.token = :param",
							User.class).setParameter("param", token)
					.getSingleResult();
			user.setLastTokenUpdate(System.currentTimeMillis());
			user.setDmj(new Date());
			em.merge(user);
		} catch (NoResultException nre) {
			Logger.getLogger(UserService.class.getName()).log(Level.WARNING,
					"updateToken : NoResultException : User's Token not found");
		} catch (Exception e) {
			Logger.getLogger(UserService.class.getName()).log(Level.WARNING,
					"updateToken : Exception : User's Token not found");
		}

	}

	@Override
	public void mobileUpdatePassword(OldNewPasswordEnvelope passwordEnvelope) {

		User user = em
				.createQuery("SELECT u from User u where u.token = :token",
						User.class)
				.setParameter("token", passwordEnvelope.getToken())
				.getSingleResult();
		user.setPassword(passwordEnvelope.getNewpassword());
		user.setKeepmeconnected(passwordEnvelope.getKeepmeconnected());
		em.merge(user);
	}

	@Override
	public User findUserByToken(String token) {
		return em
				.createQuery("SELECT u FROM User u WHERE u.token =:token",
						User.class).setParameter("token", token)
				.getSingleResult();
	}

	@Override
	public void setKeepMeConnected(User user, Boolean keepmeconnected) {
		user.setKeepmeconnected(keepmeconnected);
		em.merge(user);

	}

}
