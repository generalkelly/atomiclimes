package io.atomiclimes.web.gui.wicket.pages;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class AtomicLimesAdministrationPage extends AtomicLimesDefaultWebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtomicLimesAdministrationPage() {
		this.add(new BookmarkablePageLink<>("productAdministrationPage", AtomicLimesProductAdministrationPage.class));
		this.add(new BookmarkablePageLink<>("packagingAdministrationPage",
				AtomicLimesPackagingAdministrationPage.class));
	}

}
    