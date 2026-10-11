package WorkoutManager;

import java.util.ArrayList;
import java.util.List;

public class Program {
	private String name;
	private List<Session> sessions;
	
	public Program(String name) {
		this.name=name;
		this.sessions = new ArrayList<Session>();
	}
	
	public void addSession(Session session) {
		sessions.add(session);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}
	
}
