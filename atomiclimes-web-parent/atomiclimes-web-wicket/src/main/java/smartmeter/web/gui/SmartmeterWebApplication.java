package smartmeter.web.gui;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.CssUrlReplacer;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.crypt.CharEncoding;
import org.springframework.context.ApplicationContext;

public class SmartmeterWebApplication extends WebApplication {

	private ApplicationContext applicationContext;

	public SmartmeterWebApplication(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return SmartmeterMainPage.class;
	}

	@Override
	public void init() {
		super.init();

		getRequestCycleSettings().setResponseRequestEncoding(CharEncoding.UTF_8);
		getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);

		getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext));
		mountPage("/MainPage", SmartmeterMainPage.class);

		getResourceSettings().setCssCompressor(new CssUrlReplacer());
		setHeaderResponseDecorator(new JavaScriptToBucketResponseDecorator("footer-container"));

	}

}
