package io.atomiclimes.web.gui.productionplanning;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("admin")
public class AtomicLimesAdministrationPage extends AtomicLimesDefaultWebPage {

	private static final long serialVersionUID = 1L;

	public AtomicLimesAdministrationPage() {
		this.add(new BookmarkablePageLink<>("productAdministrationPage", AtomicLimesProductAdministrationPage.class));
		this.add(new BookmarkablePageLink<>("packagingAdministrationPage",
				AtomicLimesPackagingAdministrationPage.class));
		this.add(new BookmarkablePageLink<>("nonProductionItemAdministrationPage",
				AtomicLimesNonProductionItemAdministrationPage.class));
		this.add(new BookmarkablePageLink<>("productionItemAdministrationPage",
				AtomicLimesProductionItemAdministrationPage.class));
	}

}
    