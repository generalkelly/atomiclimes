package io.atomiclimes.web.gui.wicket.pages;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.atomiclimes.common.dao.entities.Packaging;
import io.atomiclimes.common.dao.entities.Product;
import io.atomiclimes.common.dao.entities.ProductionItem;
import io.atomiclimes.common.dao.repositories.PackagingRepository;
import io.atomiclimes.common.dao.repositories.ProductRepository;
import io.atomiclimes.common.dao.repositories.ProductionItemRepository;

@MountPath("admin/productionItem/type")
public class AtomicLimesProductionItemPage extends AtomicLimesItemPage<ProductionItem> {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProductionItemRepository productionItemRepository;
	@SpringBean
	private ProductRepository productRepository;
	@SpringBean
	private PackagingRepository packagingRepository;

	public AtomicLimesProductionItemPage() {
		this(Model.of(new ProductionItem()));
	}

	public AtomicLimesProductionItemPage(PageParameters pageParameters) {
		super(pageParameters);
	}

	public AtomicLimesProductionItemPage(IModel<ProductionItem> model) {
		super(model);
	}

	@Override
	protected ProductionItem getItemFromRepository(IModel<ProductionItem> model) {
		Long id = model.getObject().getId();
		if (id != null) {
			Optional<ProductionItem> optionalProductionItem = productionItemRepository.findById(id);
			if (optionalProductionItem.isPresent()) {
				return optionalProductionItem.get();
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	protected void saveItem(ProductionItem item) {
		productionItemRepository.save(item);
	}

	@Override
	protected IModel<ProductionItem> mapPageParametersToModel(PageParameters pageParameters) {
		ProductionItem productionItem = new ProductionItem();
		productionItem.setId(pageParameters.get("id").toLong());
		Product product = productRepository.findByName(pageParameters.get("productName").toString());
		productionItem.setProduct(product);
		Set<Packaging> packaging = pageParameters.getValues("packaging").stream()
				.map(stringValue -> packagingRepository.findByName(stringValue.toString())).collect(Collectors.toSet());
		productionItem.setPackaging(packaging);
		return Model.of(productionItem);
	}

	@Override
	protected void mapModelToItemForUpdate(IModel<ProductionItem> model, ProductionItem item) {
		item.setProduct(model.getObject().getProduct());
		item.setPackaging(model.getObject().getPackaging());
	}

	@Override
	protected WebPage generateResponsePage(IModel<ProductionItem> model) {
		return new AtomicLimesProductionItemPage(model);
	}

	@Override
	protected WebPage generateAdministrationResponsePage() {
		return new AtomicLimesProductionItemAdministrationPage();
	}

}
