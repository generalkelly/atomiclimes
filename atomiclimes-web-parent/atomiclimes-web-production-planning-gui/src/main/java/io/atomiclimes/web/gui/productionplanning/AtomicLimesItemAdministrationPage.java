package io.atomiclimes.web.gui.productionplanning;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import io.atomiclimes.web.gui.panels.EditItemPanel;

public abstract class AtomicLimesItemAdministrationPage<T> extends AtomicLimesDefaultWebPage {

	private static final long serialVersionUID = 1L;

	private Class<? extends WebPage> itemPageClass;
	private Class<? extends WebPage> itemAdministrationPageClass;

	public AtomicLimesItemAdministrationPage() {
		List<T> itemsList = new LinkedList<>();
		getAllItemsFromRepository().forEach(itemsList::add);
		this.add(new ItemListView(itemsList));
		this.add(generateBookmarkablePageLink());
	}

	private class ItemListView extends ListView<T> {

		private static final long serialVersionUID = 1L;

		public ItemListView(List<T> items) {
			super("items", items);
		}

		@Override
		protected void populateItem(ListItem<T> listItem) {
			T item = listItem.getModelObject();
			listItem.add(new Label("name", getNameFromItem(item)));
			listItem.add(new EditItemPanel<T>("editItem", generateModel(item)) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void edit() {
					setResponsePage(itemPageClass, generateResponsePageParameters(item));
				}

				@Override
				protected void delete() {
					deleteItem(item);
					setResponsePage(itemAdministrationPageClass);
				}

			});
		}

	}

	public void setItemPageClass(Class<? extends WebPage> itemPageClass) {
		this.itemPageClass = itemPageClass;
	}

	public void setItemAdministrationPageClass(Class<? extends WebPage> itemAdministrationPageClass) {
		this.itemAdministrationPageClass = itemAdministrationPageClass;
	}

	protected abstract Iterable<T> getAllItemsFromRepository();

	protected abstract IModel<T> generateModel(T item);

	protected abstract void deleteItem(T item);

	protected abstract String getNameFromItem(T item);

	protected abstract BookmarkablePageLink<? extends WebPage> generateBookmarkablePageLink();

	protected abstract PageParameters generateResponsePageParameters(T item);
}
