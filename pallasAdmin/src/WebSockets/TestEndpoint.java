package WebSockets;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.enterprise.event.Observes;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import persistence.PushNotification;

@ServerEndpoint("/testws")
public class TestEndpoint {

	private SessionRegistry sessionRegistry;

	static ScheduledExecutorService timer = Executors
			.newSingleThreadScheduledExecutor();

	private static Set<Session> allSessions;

	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	/**
	 * @throws IOException
	 * @OnOpen allows us to intercept the creation of a new session. The session
	 *         class allows us to send data to the user. In the method onOpen,
	 *         we'll let the user know that the handshake was successful.
	 */
	@OnOpen
	public void onOpen(Session session) throws IOException {
		// sessionRegistry.add(session);

		allSessions = session.getOpenSessions();
		// start the scheduler on the very first connection
		// to call sendTimeToAll every second
		if (allSessions.size() == 1) {
			timer.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					for (Session sess : allSessions) {
						try {
							JsonObject jsonObject = Json
									.createObjectBuilder()
									.add("time",
											LocalTime.now().format(
													timeFormatter)).build();
							sess.getBasicRemote().sendText(
									jsonObject.toString());
						} catch (IOException ioe) {
							System.out.println(ioe.getMessage());
						}
					}
				}
			}, 0, 1, TimeUnit.SECONDS);
		}

	}

	/**
	 * When a user sends a message to the server, this method will intercept the
	 * message and allow us to react to it. For now the message is read as a
	 * String.
	 */
	public void send(@Observes PushNotification notification) {

		Set<Session> sessions = sessionRegistry.getAll();
		for (Session session : sessions) {
			session.getAsyncRemote().sendText(toJson(notification));
		}

		System.out.println("new notification, message : "
				+ notification.getMessage());
	}

	private String toJson(PushNotification notification) {
		final JsonObject jsonObject = Json.createObjectBuilder()
				.add("id", notification.getId())
				.add("message", notification.getMessage()).build();
		return jsonObject.toString();
	}

	/**
	 * The user closes the connection.
	 * 
	 * Note: you can't send messages to the client from this method
	 */
	@OnClose
	public void onClose(Session session) {
		// sessionRegistry.remove(session);
		System.out.println("session closed");
	}
}
