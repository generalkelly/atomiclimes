package io.atomiclimes.web.gui.productionplanning;

import java.net.URL;
import java.util.Collection;
import java.util.regex.Pattern;

import org.apache.commons.codec.CharEncoding;
import org.apache.wicket.markup.head.ResourceAggregator;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.CssUrlReplacer;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;

import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.collectors.AssetPathCollector;
import de.agilecoders.wicket.webjars.collectors.ClasspathAssetPathCollector;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;

@ApplicationInitExtension
public class AtomicLimesGuiInitConfiguration implements WicketApplicationInitConfiguration {
	@Override
	public void init(WebApplication webApplication) {
		webApplication.getRequestCycleSettings().setResponseRequestEncoding(CharEncoding.UTF_8);
		webApplication.getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);
		webApplication.getResourceSettings().setCssCompressor(new CssUrlReplacer());
		webApplication.setHeaderResponseDecorator(response -> {
			return new ResourceAggregator(new JavaScriptFilteredIntoFooterHeaderResponse(response, "footer-container"));
		});
		WicketWebjars.install(webApplication);
	}

}
