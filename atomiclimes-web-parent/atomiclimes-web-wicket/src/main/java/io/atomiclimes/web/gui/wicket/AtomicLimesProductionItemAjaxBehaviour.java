package io.atomiclimes.web.gui.wicket;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.string.StringValue;

public class AtomicLimesProductionItemAjaxBehaviour extends AbstractDefaultAjaxBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void respond(AjaxRequestTarget target) {
		StringValue plannedProduction = getComponent().getRequest().getRequestParameters()
				.getParameterValue("plannedProduction");

		System.out.println(plannedProduction.toString());
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		List<CallbackParameter> callbackParametersList = new LinkedList<>();

		callbackParametersList.add(CallbackParameter.explicit("plannedProduction"));
		CallbackParameter[] callbackParameters = new CallbackParameter[callbackParametersList.size()];

		response.render(OnDomReadyHeaderItem.forScript("window.submitPlannedProduction = "
				+ getCallbackFunction(callbackParametersList.toArray(callbackParameters))));
	}

}
