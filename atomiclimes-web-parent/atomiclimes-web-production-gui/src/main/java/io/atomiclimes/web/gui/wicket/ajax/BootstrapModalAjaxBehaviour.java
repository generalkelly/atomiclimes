package io.atomiclimes.web.gui.wicket.ajax;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxAttributeName;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.ajax.json.JSONFunction;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.github.openjson.JSONArray;
import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;

/**
 * @author Mirko Pohland
 *
 */
public abstract class BootstrapModalAjaxBehaviour extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

	private String ajaxFunctionName;
	private List<String> callbackParametersList = new LinkedList<>();

	public BootstrapModalAjaxBehaviour(String ajaxFunctionName) {
		this.ajaxFunctionName = ajaxFunctionName;
	}

	@Override
	protected abstract void respond(AjaxRequestTarget target);

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		CallbackParameter[] callbackParameters = this.callbackParametersList.stream().map(CallbackParameter::explicit)
				.collect(Collectors.toList()).toArray(new CallbackParameter[callbackParametersList.size()]);
		response.render(OnDomReadyHeaderItem
				.forScript("window." + this.ajaxFunctionName + " = " + getCallbackFunction(callbackParameters)));
	}

	public void addCallbackParameter(String parameterName) {
		this.callbackParametersList.add(parameterName);
	}

	@Override
	public CharSequence getCallbackFunctionBody(CallbackParameter... extraParameters) {
		AjaxRequestAttributes attributes = getAttributes();
		attributes.setEventNames();
		CharSequence attrsJson = renderAjaxAttributes(getComponent(), attributes);
		StringBuilder sb = new StringBuilder();
		sb.append("var attrs = ");
		sb.append(attrsJson);
		sb.append(";\n");
//		JSONArray jsonArray = new JSONArray();
		for (CallbackParameter curExtraParameter : extraParameters) {
			if (curExtraParameter.getAjaxParameterName() != null) {
				try {
					sb.append("attrs.").append(curExtraParameter.getAjaxParameterName()).append(" = ")
							.append(curExtraParameter.getAjaxParameterCode()).append(";\n");
//					JSONObject object = new JSONObject();
//					object.put(curExtraParameter.getAjaxParameterName(),
//							new JSONFunction(curExtraParameter.getAjaxParameterCode()));
//					jsonArray.put(object);
				} catch (JSONException e) {
					throw new WicketRuntimeException(e);
				}
			}
		}
//		sb.append("var params = ").append(jsonArray).append(";\n");
//		sb.append("attrs").append(" = params.concat(attrs").append(" || []);\n");
		sb.append("return AtomicLimes.Ajax.ajax(attrs);\n");
		return sb;
	}
}
