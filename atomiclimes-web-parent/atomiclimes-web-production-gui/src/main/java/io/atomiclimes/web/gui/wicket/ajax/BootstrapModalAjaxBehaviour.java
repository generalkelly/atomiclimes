package io.atomiclimes.web.gui.wicket.ajax;

import java.io.IOException;
import java.util.Arrays;
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
import org.apache.wicket.request.cycle.RequestCycle;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.openjson.JSONArray;
import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;

import io.atomiclimes.common.dao.entities.PlannedProduction;
import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.web.gui.log.AtomicLimesGuiLogMessages;
import io.atomiclimes.web.gui.wicket.pages.AtomicLimesPlannedProductionPage;

/**
 * @author Mirko Pohland
 *
 */
public abstract class BootstrapModalAjaxBehaviour extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(BootstrapModalAjaxBehaviour.class);

	private String ajaxFunctionName;
	private List<String> callbackParametersList = new LinkedList<>();

	public BootstrapModalAjaxBehaviour(String ajaxFunctionName) {
		this.ajaxFunctionName = ajaxFunctionName;
	}

	public BootstrapModalAjaxBehaviour(String ajaxFunctionName, String... callbackParameterNames) {
		this(ajaxFunctionName);
		this.callbackParametersList.addAll(Arrays.asList(callbackParameterNames));
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
		for (CallbackParameter curExtraParameter : extraParameters) {
			if (curExtraParameter.getAjaxParameterName() != null) {
				try {
					sb.append("attrs.").append(curExtraParameter.getAjaxParameterName()).append(" = ")
							.append(curExtraParameter.getAjaxParameterCode()).append(";\n");
				} catch (JSONException e) {
					throw new WicketRuntimeException(e);
				}
			}
		}
		sb.append("return AtomicLimes.Ajax.ajax(attrs);\n");
		return sb;
	}

	protected <T> T getParameterValue(String parameterName, TypeReference<T> typeReference) {
		String jsonString = RequestCycle.get().getRequest().getRequestParameters().getParameterValue(parameterName)
				.toString();
		T parameterValue = null;
		ObjectMapper objectMapper = new ObjectMapper();
		if (!jsonString.equals("{}")) {
			try {
				parameterValue = objectMapper.readValue(jsonString, typeReference);
			} catch (IOException e) {
				LOG.debug(AtomicLimesGuiLogMessages.FAILED_TO_DESERIALIZE_JSON, e, jsonString);
			}
		}
		return parameterValue;
	}

	protected String getParameterValue(String parameterName) {
		return RequestCycle.get().getRequest().getRequestParameters().getParameterValue(parameterName).toString();
	}
}
