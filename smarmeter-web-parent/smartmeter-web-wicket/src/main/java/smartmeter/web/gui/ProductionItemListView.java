package smartmeter.web.gui;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import smartmeter.common.dao.entities.Packaging;
import smartmeter.common.dao.entities.ProductionItem;

public class ProductionItemListView extends ListView<ProductionItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductionItemListView(String id, List<ProductionItem> list) {
		super(id, list);
	}

	@Override
	protected void populateItem(ListItem<ProductionItem> item) {
		final ProductionItem productionItem = item.getModelObject();
		item.add(new Label("product", productionItem.getProduct().getName()));
		List<Packaging> packagingList = productionItem.getPackaging();
		Iterator<Packaging> packagingIterator = packagingList.iterator();
		String[] packagingOrderTypes = { "1", "2", "3" };

		for (String packagingOrder : packagingOrderTypes) {
			String packagingName = "";
			if (packagingIterator.hasNext()) {
				packagingName = packagingIterator.next().getName();
			}
			item.add(new Label("packagingOfOrder" + packagingOrder, packagingName));
		}
	}

}
