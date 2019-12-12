package io.atomiclimes.web.gui.wicket;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.ResourceAggregator;
import org.apache.wicket.markup.head.filter.FilteringHeaderResponse;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.resource.CssUrlReplacer;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.crypt.CharEncoding;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.giffing.wicket.spring.boot.starter.app.WicketBootStandardWebApplication;

import de.agilecoders.wicket.webjars.WicketWebjars;
import io.atomiclimes.web.gui.wicket.pages.AtomicLimesMainPage;
import io.atomiclimes.web.gui.wicket.pages.AtomicLimesPlannedProductionPage;

@Component
public class AtomicLimesWebApplication extends WicketBootStandardWebApplication {

//	private ApplicationContext applicationContext;
//
//	public AtomicLimesWebApplication(ApplicationContext applicationContext) {
//		this.applicationContext = applicationContext;
//	}

//	@Override
//	public RuntimeConfigurationType getConfigurationType() {
//		return RuntimeConfigurationType.DEPLOYMENT;
//	}

//	@Override
//	public void init() {
//		super.init();
//		getRequestCycleSettings().setResponseRequestEncoding(CharEncoding.UTF_8);
//		getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);
//		getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext));
//		getResourceSettings().setCssCompressor(new CssUrlReplacer());
//		setHeaderResponseDecorator(response -> new ResourceAggregator(
//				new JavaScriptFilteredIntoFooterHeaderResponse(response, "footer-container")));
//
//		WicketWebjars.install(this);
//	}

//	@Override
//	public Class<? extends Page> getHomePage() {
//		return AtomicLimesMainPage.class;
//	}

	@Override
	public void init() {
		this.getRequestCycleSettings().setResponseRequestEncoding(CharEncoding.UTF_8);
		this.getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);
		this.getResourceSettings().setCssCompressor(new CssUrlReplacer());
		this.setHeaderResponseDecorator(new FilteringHeaderResponseDecorator());
		this.setHeaderResponseDecorator(response -> new ResourceAggregator(
				new JavaScriptFilteredIntoFooterHeaderResponse(response, "footer-container")));

		WicketWebjars.install(this);

		mountPage("/home", AtomicLimesMainPage.class);
		mountPage("/plannedProductions", AtomicLimesPlannedProductionPage.class);
	}

	
	public final class FilteringHeaderResponseDecorator implements IHeaderResponseDecorator {
		@Override
		public IHeaderResponse decorate(IHeaderResponse response) {
			return new FilteringHeaderResponse(response);
		}

	}

}
