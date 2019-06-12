package io.atomiclimes.web.gui.wicket.pages;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.atomiclimes.common.dao.entities.Packaging;
import io.atomiclimes.common.dao.entities.Product;
import io.atomiclimes.common.dao.repositories.PackagingRepository;
import io.atomiclimes.common.helper.annotations.processor.AtomicLimesItemFormProcessor;
import io.atomiclimes.common.helper.wicket.ItemPanel;

@WicketHomePage
@MountPath("admin/packaging/type")
public class AtomicLimesPackagingPage extends AtomicLimesDefaultWebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private PackagingRepository packagingRepository;

	public AtomicLimesPackagingPage() {
		this(Model.of(new Packaging()));
	}

	public AtomicLimesPackagingPage(IModel<Packaging> packagingModel) {

		Form<Product> form = new Form<>("form");

		AtomicLimesItemFormProcessor<Packaging> itemFormProcessor = new AtomicLimesItemFormProcessor<>(packagingModel);

		ItemPanel<Packaging, Object> itemPanel = itemFormProcessor.getItemPanel("packaging");

		form.add(itemPanel);

		form.add(new Button("save") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				packagingRepository.save(packagingModel.getObject());
				setResponsePage(AtomicLimesPackagingAdministrationPage.class);
			}
		});

		this.add(form);

	}

}
