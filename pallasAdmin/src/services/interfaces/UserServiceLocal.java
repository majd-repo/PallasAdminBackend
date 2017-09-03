package services.interfaces;

import httpObjects.OldNewPasswordEnvelope;
import httpObjects.PasswordEnvelope;

import javax.ejb.Local;

import persistence.User;
import results.UserResult;

@Local
public interface UserServiceLocal {

	public User authenticate(String login, String password);

	public void setToken(User user, String token);

	public void setKeepMeConnected(User user, Boolean keepmeconnected);

	public UserResult getUserByToken(String token);

	public User getUserByLogin(String login);

	public User findUserByToken(String token);

	public void updatePassword(PasswordEnvelope passwordEnvelope);

	public void clearToken(String token);

	public void updateLastTokenUpdate(String token);

	public void mobileUpdatePassword(OldNewPasswordEnvelope passwordEnvelope);

}
