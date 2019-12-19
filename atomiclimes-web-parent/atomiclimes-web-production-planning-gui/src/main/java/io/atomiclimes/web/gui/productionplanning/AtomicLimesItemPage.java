package io.atomiclimes.web.gui.productionplanning;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.giffing.wicket.spring.boot.starter.app.WicketBootSecuredWebApplication;
import com.giffing.wicket.spring.boot.starter.app.WicketBootStandardWebApplication;

import io.atomiclimes.common.helper.annotations.processor.AtomicLimesItemFormProcessor;
import io.atomiclimes.common.helper.wicket.ItemPanel;

@Service
public abstract class AtomicLimesItemPage<T> extends AtomicLimesDefaultWebPage {

	private static final long serialVersionUID = 1L;

	public AtomicLimesItemPage(PageParameters pageParameters) {
		IModel<T> model = mapPageParametersToModel(pageParameters);
		setResponsePage(generateResponsePage(model));
	}

	public AtomicLimesItemPage(IModel<T> model) {
		ApplicationContext applicationContext = ((WicketBootSecuredWebApplication) getApplication())
				.getApplicationContext();

		Form<T> form = new Form<>("form");
		AtomicLimesItemFormProcessor<T> itemFormProcessor = new AtomicLimesItemFormProcessor<>(model,
				applicationContext);
		ItemPanel<T> itemPanel = itemFormProcessor.getItemPanel("item");

		form.add(itemPanel);
		form.add(new Button("save") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				T itemForUpdate = getItemFromRepository(model);
				if (itemForUpdate == null) {
					saveItem(model.getObject());
				} else {
					mapModelToItemForUpdate(model, itemForUpdate);
					saveItem(itemForUpdate);
				}
				setResponsePage(generateAdministrationResponsePage());
			}

		});
		this.add(form);
	}

	protected abstract T getItemFromRepository(IModel<T> model);

	protected abstract void saveItem(T item);

	protected abstract IModel<T> mapPageParametersToModel(PageParameters pageParameters);

	protected abstract void mapModelToItemForUpdate(IModel<T> model, T item);

	protected abstract WebPage generateResponsePage(IModel<T> model);

	protected abstract WebPage generateAdministrationResponsePage();

}
