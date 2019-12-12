package io.atomiclimes.web.gui.wicket.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.atomiclimes.common.dao.entities.Packaging;
import io.atomiclimes.common.dao.repositories.PackagingRepository;
import io.atomiclimes.common.helper.enums.PackagingUnit;
import io.atomiclimes.common.helper.wicket.converter.impl.AtomicLimesDurationInSecondsConverter;

@MountPath("admin/packaging/type")
public class AtomicLimesPackagingPage extends AtomicLimesItemPage<Packaging> {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private PackagingRepository packagingRepository;

	public AtomicLimesPackagingPage() {
		super(Model.of(new Packaging()));
	}

	public AtomicLimesPackagingPage(IModel<Packaging> packagingModel) {
		super(packagingModel);
	}

	public AtomicLimesPackagingPage(PageParameters pageParameters) {
		super(pageParameters);
	}	

	@Override
	protected Packaging getItemFromRepository(IModel<Packaging> model) {
		return packagingRepository.findByName(model.getObject().getName());
	}

	@Override
	protected void saveItem(Packaging item) {
		packagingRepository.save(item);
	}

	@Override
	protected IModel<Packaging> mapPageParametersToModel(PageParameters pageParameters) {
		Packaging packaging = new Packaging();
		packaging.setName(pageParameters.get("name").toString());
		packaging.setCapacity(Double.valueOf(pageParameters.get("capacity").toString()));
		packaging.setUnit(pageParameters.get("unit").to(PackagingUnit.class));
		packaging.setDuration(new AtomicLimesDurationInSecondsConverter()
				.convertToObject(pageParameters.get("duration").toString(), null));
		packaging.setPackagingOrder(pageParameters.get("packagingOrder").toInt());
		return Model.of(packaging);
	}

	@Override
	protected void mapModelToItemForUpdate(IModel<Packaging> model, Packaging item) {
		item.setCapacity(model.getObject().getCapacity());
		item.setUnit(model.getObject().getUnit());
		item.setDuration(model.getObject().getDuration());
		item.setPackagingOrder(model.getObject().getPackagingOrder());
	}

	@Override
	protected WebPage generateResponsePage(IModel<Packaging> model) {
		return new AtomicLimesPackagingPage(model);
	}

	@Override
	protected WebPage generateAdministrationResponsePage() {
		return new AtomicLimesPackagingAdministrationPage();
	}

}
