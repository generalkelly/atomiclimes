package io.atomiclimes.web.gui.productionplanning;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;
import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;
import io.atomiclimes.web.gui.panels.HeaderPanel;

public class AtomicLimesDefaultWebPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HeaderPanel headerPanel;

	public AtomicLimesDefaultWebPage() {
		this.add(headerPanel = new HeaderPanel("headerPanel"));
		this.add(new HeaderResponseContainer("footer-container", "footer-container"));
	}

	public Component getHeaderPanel() {
		return headerPanel;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(
				JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("jquery/current/jquery.js")));
		response.render(JavaScriptHeaderItem
				.forReference(new WebjarsJavaScriptResourceReference("bootstrap/current/js/bootstrap.js")));
		response.render(
				CssHeaderItem.forReference(new WebjarsCssResourceReference("bootstrap/current/css/bootstrap.min.css")));
		response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference(
				"/atomiclimes-planned-production/0.1/src/AtomiclimesProductionPlanning.bundle.js")));
		response.render(JavaScriptHeaderItem.forReference(
				new WebjarsJavaScriptResourceReference("/atomiclimes-buttons/0.1/src/AtomicLimesButtons.js")));
	}

}
