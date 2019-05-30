package io.atomiclimes.web.gui.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.head.ResourceAggregator;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.resource.CssUrlReplacer;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.crypt.CharEncoding;
import org.springframework.context.ApplicationContext;

import de.agilecoders.wicket.webjars.WicketWebjars;
import io.atomiclimes.web.gui.wicket.pages.AtomicLimesAdministrationPage;
import io.atomiclimes.web.gui.wicket.pages.AtomicLimesMainPage;
import io.atomiclimes.web.gui.wicket.pages.AtomicLimesPlannedProductionPage;
import io.atomiclimes.web.gui.wicket.pages.AtomicLimesProductAdministrationPage;

public class AtomicLimesWebApplication extends AuthenticatedWebApplication {

	private ApplicationContext applicationContext;

	public AtomicLimesWebApplication(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public RuntimeConfigurationType getConfigurationType() {
		return RuntimeConfigurationType.DEPLOYMENT;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return AtomicLimesMainPage.class;
	}

	@Override
	public void init() {
		super.init();

		getRequestCycleSettings().setResponseRequestEncoding(CharEncoding.UTF_8);
		getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);
		getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext));
		getResourceSettings().setCssCompressor(new CssUrlReplacer());
		setHeaderResponseDecorator(response -> {
			return new ResourceAggregator(new JavaScriptFilteredIntoFooterHeaderResponse(response, "footer-container"));
		});

		WicketWebjars.install(this);

		mountPage("/home", AtomicLimesMainPage.class);
		mountPage("/plannedProductions", AtomicLimesPlannedProductionPage.class);
		mountPage("/admin", AtomicLimesAdministrationPage.class);
		mountPage("admin/products", AtomicLimesProductAdministrationPage.class);

	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return AtomicLimesAuthenitcatedWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return AtomicLimesMainPage.class;
	}

}
