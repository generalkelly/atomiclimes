package io.atomiclimes.web.gui.productionplanning;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.atomiclimes.common.dao.entities.NonProductionItem;
import io.atomiclimes.common.dao.repositories.NonProductionItemRepository;
import io.atomiclimes.common.helper.wicket.converter.impl.AtomicLimesDurationInSecondsConverter;

@MountPath("admin/nonProductionItem/type")
public class AtomicLimesNonProductionItemPage extends AtomicLimesItemPage<NonProductionItem> {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private NonProductionItemRepository nonProductionItemRepository;

	public AtomicLimesNonProductionItemPage() {
		this(Model.of(new NonProductionItem()));
	}

	public AtomicLimesNonProductionItemPage(PageParameters pageParameters) {
		super(pageParameters);
	}

	public AtomicLimesNonProductionItemPage(IModel<NonProductionItem> model) {
		super(model);
	}

	@Override
	protected NonProductionItem getItemFromRepository(IModel<NonProductionItem> model) {
		return nonProductionItemRepository.findByName(model.getObject().getName());
	}

	@Override
	protected void saveItem(NonProductionItem item) {
		nonProductionItemRepository.save(item);
	}

	@Override
	protected IModel<NonProductionItem> mapPageParametersToModel(PageParameters pageParameters) {
		NonProductionItem nonProductionItem = new NonProductionItem();
		nonProductionItem.setName(pageParameters.get("name").toString());
		nonProductionItem.setDuration(new AtomicLimesDurationInSecondsConverter()
				.convertToObject(pageParameters.get("duration").toString(), null));
		return Model.of(nonProductionItem);
	}

	@Override
	protected void mapModelToItemForUpdate(IModel<NonProductionItem> model, NonProductionItem item) {
		item.setDuration(model.getObject().getDuration());
	}

	@Override
	protected WebPage generateResponsePage(IModel<NonProductionItem> model) {
		return new AtomicLimesNonProductionItemPage(model);
	}

	@Override
	protected WebPage generateAdministrationResponsePage() {
		return new AtomicLimesNonProductionItemAdministrationPage();
	}

}
