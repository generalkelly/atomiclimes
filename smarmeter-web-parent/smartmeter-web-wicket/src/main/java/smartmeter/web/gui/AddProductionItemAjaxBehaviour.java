package smartmeter.web.gui;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.string.StringValue;

public class AddProductionItemAjaxBehaviour extends AbstractDefaultAjaxBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void respond(AjaxRequestTarget target) {
		StringValue productionItem = getComponent().getRequest().getRequestParameters()
				.getParameterValue("productionItem");
		StringValue dateTime = getComponent().getRequest().getRequestParameters().getParameterValue("dateTime");

		System.out.println(productionItem.toString());
		System.out.println(dateTime.toString());
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		List<CallbackParameter> callbackParametersList = new LinkedList<>();

		callbackParametersList.add(CallbackParameter.explicit("productionItem"));
		callbackParametersList.add(CallbackParameter.explicit("dateTime"));
		CallbackParameter[] callbackParameters = new CallbackParameter[callbackParametersList.size()];

		response.render(OnDomReadyHeaderItem.forScript("var submitPlannedProduction = "
				+ getCallbackFunction(callbackParametersList.toArray(callbackParameters))));
	}

}
