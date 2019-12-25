package io.atomiclimes.web.gui.wicket;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import io.atomiclimes.common.dao.entities.Packaging;
import io.atomiclimes.common.dao.entities.ProductionItem;
import io.atomiclimes.helper.jackson.AtomicLimesJacksonHelper;

public class ProductionItemListView extends ListView<ProductionItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AtomicLimesJacksonHelper smartmeterJacksonHelper;

	public ProductionItemListView(String id, List<ProductionItem> list) {
		super(id, list);
		this.smartmeterJacksonHelper = new AtomicLimesJacksonHelper(ProductionItem.class);
	}

	@Override
	protected void populateItem(ListItem<ProductionItem> item) {
		final ProductionItem productionItem = item.getModelObject();
		productionItem.getProduct();
		Set<Packaging> packagingList = productionItem.getPackaging();
		Iterator<Packaging> packagingIterator = packagingList.iterator();
		while (packagingIterator.hasNext()) {
			packagingIterator.next();
		}
		String productionItemJSON = this.smartmeterJacksonHelper.serialize(productionItem);
		item.add(new Label("product", productionItemJSON));
	}

}
