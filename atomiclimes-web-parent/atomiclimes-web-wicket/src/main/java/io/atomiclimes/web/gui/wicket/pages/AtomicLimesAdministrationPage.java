package io.atomiclimes.web.gui.wicket.pages;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class AtomicLimesAdministrationPage extends AtomicLimesDefaultWebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtomicLimesAdministrationPage() {
		this.add(new BookmarkablePageLink<>("productAdministrationPage", AtomicLimesProductAdministrationPage.class));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
	}

}
