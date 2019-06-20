package io.atomiclimes.web.gui.wicket.pages;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.atomiclimes.common.dao.entities.Packaging;
import io.atomiclimes.common.dao.repositories.PackagingRepository;
import io.atomiclimes.common.helper.annotations.processor.AtomicLimesItemFormProcessor;
import io.atomiclimes.common.helper.enums.PackagingUnit;
import io.atomiclimes.common.helper.wicket.ItemPanel;
import io.atomiclimes.common.helper.wicket.converter.impl.AtomicLimesDurationInSecondsConverter;

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

	public AtomicLimesPackagingPage(PageParameters pageParameters) {
		Packaging packaging = new Packaging();

		packaging.setName(pageParameters.get("name").toString());
		packaging.setCapacity(Double.valueOf(pageParameters.get("capacity").toString()));

		packaging.setUnit(pageParameters.get("unit").to(PackagingUnit.class));
		packaging.setDuration(new AtomicLimesDurationInSecondsConverter()
				.convertToObject(pageParameters.get("duration").toString(), null));
		packaging.setPackagingOrder(pageParameters.get("packagingOrder").toInt());

		AtomicLimesPackagingPage redirectPage = new AtomicLimesPackagingPage(Model.of(packaging));
		setResponsePage(redirectPage);

	}

	public AtomicLimesPackagingPage(IModel<Packaging> packagingModel) {

		Form<Packaging> form = new Form<>("form");

		AtomicLimesItemFormProcessor<Packaging> itemFormProcessor = new AtomicLimesItemFormProcessor<>(packagingModel);

		ItemPanel<Packaging> itemPanel = itemFormProcessor.getItemPanel("packaging");

		form.add(itemPanel);

		form.add(new Button("save") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				Packaging packagingForUpdate = packagingRepository.findByName(packagingModel.getObject().getName());
				if (packagingForUpdate == null) {
					packagingRepository.save(packagingModel.getObject());
				} else {
					packagingForUpdate.setCapacity(packagingModel.getObject().getCapacity());
					packagingForUpdate.setUnit(packagingModel.getObject().getUnit());
					packagingForUpdate.setDuration(packagingModel.getObject().getDuration());
					packagingForUpdate.setPackagingOrder(packagingModel.getObject().getPackagingOrder());
					packagingRepository.save(packagingForUpdate);
				}
				setResponsePage(AtomicLimesPackagingAdministrationPage.class);
			}
		});

		this.add(form);

	}

}
