package io.atomiclimes.common.helper.wicket;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

public class LabeledTextField extends TextField<Object> {

	private static final long serialVersionUID = 1L;
	private Label formLabel;
	private String formFieldName;

	public LabeledTextField(String id, IModel<Object> model, String formFieldName) {
		super(id, model);
		this.formFieldName = formFieldName;
		this.formLabel = new Label("label", formFieldName);
		this.formLabel.add(AttributeModifier.replace("for", formFieldName));
	}

	public Label getFormLabel() {
		return this.formLabel;
	}

	public String getFormFieldName() {
		return this.formFieldName;
	}

}
