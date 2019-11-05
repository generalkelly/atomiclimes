package io.atomiclimes.web.gui.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.springframework.security.core.context.SecurityContextHolder;

public class HeaderPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HeaderPanel(String id) {
		super(id);
		this.add(new Label("username", " "+SecurityContextHolder.getContext().getAuthentication().getName()));
	}

}
