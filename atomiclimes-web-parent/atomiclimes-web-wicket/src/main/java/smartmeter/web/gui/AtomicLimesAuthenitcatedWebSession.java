package smartmeter.web.gui;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextHolder;

public class AtomicLimesAuthenitcatedWebSession extends AuthenticatedWebSession {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;

	public AtomicLimesAuthenitcatedWebSession(Request request) {
		super(request);
		setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	public String getUsername() {
		return username;
	}

	private void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Roles getRoles() {
		return null;
	}

	@Override
	protected boolean authenticate(String username, String password) {
		return true;
	}

}
