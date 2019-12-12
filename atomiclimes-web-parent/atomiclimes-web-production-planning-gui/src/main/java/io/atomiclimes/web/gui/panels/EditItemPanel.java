package io.atomiclimes.web.gui.panels;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public abstract class EditItemPanel<T> extends Panel {

	private static final long serialVersionUID = 1L;

	public EditItemPanel(String id, IModel<T> model) {
		super(id, model);
		IModel<T> compound = new CompoundPropertyModel<>(model);
		Form<T> form = new Form<>("form", compound);
		form.add(new Button("edit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				edit();
			}

		});

		form.add(new Button("delete") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				delete();
			}

		});

		this.add(form);

	}

	protected abstract void edit();

	protected abstract void delete();
}
