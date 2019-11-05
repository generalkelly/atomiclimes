package io.atomiclimes.web.gui.wicket.pages;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.atomiclimes.common.dao.entities.NonProductionItem;
import io.atomiclimes.common.dao.repositories.NonProductionItemRepository;
import io.atomiclimes.web.gui.panels.EditItemPanel;

@WicketHomePage
@MountPath("admin/nonProductionItem")
public class AtomicLimesNonProductionItemAdministrationPage extends AtomicLimesDefaultWebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private NonProductionItemRepository nonProductionItemRepository;

	public AtomicLimesNonProductionItemAdministrationPage() {
		List<NonProductionItem> nonProductionItemsList = new LinkedList<>();
		getPackagingTypes().forEach(nonProductionItemsList::add);
		this.add(new NonProductionItemListView(nonProductionItemsList));
		this.add(new BookmarkablePageLink<>("addNonProductionItemPage", AtomicLimesNonProductionItemPage.class));
	}

	private class NonProductionItemListView extends ListView<NonProductionItem> {

		private static final long serialVersionUID = 1L;

		public NonProductionItemListView(List<NonProductionItem> nonProductionItems) {
			super("nonProductionItems", nonProductionItems);
		}

		@Override
		protected void populateItem(ListItem<NonProductionItem> item) {
			NonProductionItem nonProductionItem = item.getModelObject();
			item.add(new Label("name", nonProductionItem.getName()));
			item.add(new EditItemPanel<NonProductionItem>("editItem", Model.of(nonProductionItem)) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void edit() {
					setResponsePage(AtomicLimesNonProductionItemPage.class,
							generatePageParameters(nonProductionItem));
				}

				@Override
				protected void delete() {
					nonProductionItemRepository.delete(nonProductionItem);
					setResponsePage(AtomicLimesNonProductionItemAdministrationPage.class);
				}

			});
		}

		private PageParameters generatePageParameters(NonProductionItem nonProductionItem) {
			PageParameters parameters = new PageParameters();
			parameters.add("name", nonProductionItem.getName());
			parameters.add("duration", nonProductionItem.getDuration().toMinutes());
			return parameters;
		}
	}

	private Iterable<NonProductionItem> getPackagingTypes() {
		return this.nonProductionItemRepository.findAll();
	}

}
