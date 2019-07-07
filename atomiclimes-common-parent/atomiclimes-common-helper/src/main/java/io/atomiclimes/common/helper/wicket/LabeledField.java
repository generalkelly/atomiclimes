package io.atomiclimes.common.helper.wicket;

import java.io.Serializable;

import org.apache.wicket.markup.html.basic.Label;

public interface LabeledField extends Serializable{
	
	public Label getFormLabel();

	public String getFormFieldName();

}
