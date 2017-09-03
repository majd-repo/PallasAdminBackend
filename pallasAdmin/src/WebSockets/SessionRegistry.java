package WebSockets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.websocket.Session;

@Singleton
public class SessionRegistry {
	private final Set<Session> sessions = new HashSet<>();

	@Lock(LockType.READ)
	public Set<Session> getAll() {
		return Collections.unmodifiableSet(sessions);
	}

	@Lock(LockType.WRITE)
	public void add(Session session) {
		sessions.add(session);
	}

	@Lock(LockType.WRITE)
	public void remove(Session session) {
		sessions.remove(session);
	}
}
