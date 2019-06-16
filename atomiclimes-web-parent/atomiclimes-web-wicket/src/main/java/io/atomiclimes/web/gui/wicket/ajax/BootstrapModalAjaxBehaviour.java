package io.atomiclimes.web.gui.wicket.ajax;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

public class BootstrapModalAjaxBehaviour extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

	private String ajaxFunctionName;
	List<CallbackParameter> callbackParametersList = new LinkedList<>();

	public BootstrapModalAjaxBehaviour(String ajaxFunctionName) {
		this.ajaxFunctionName = ajaxFunctionName;
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		CallbackParameter[] callbackParameters = new CallbackParameter[this.callbackParametersList.size()];

		response.render(OnDomReadyHeaderItem.forScript("window." + this.ajaxFunctionName + " = "
				+ getCallbackFunction(this.callbackParametersList.toArray(callbackParameters))));
	}

	public void addCallbackParameter(String parameterName) {
		this.callbackParametersList.add(CallbackParameter.explicit(parameterName));
	}

}
