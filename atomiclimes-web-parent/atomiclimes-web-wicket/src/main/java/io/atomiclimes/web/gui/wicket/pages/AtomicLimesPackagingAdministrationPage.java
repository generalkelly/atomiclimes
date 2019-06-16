package io.atomiclimes.web.gui.wicket.pages;

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

import io.atomiclimes.common.dao.entities.Packaging;
import io.atomiclimes.common.dao.repositories.PackagingRepository;
import io.atomiclimes.web.gui.panels.EditItemPanel;

@WicketHomePage
@MountPath("admin/packaging")
public class AtomicLimesPackagingAdministrationPage extends AtomicLimesDefaultWebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private PackagingRepository packagingRepository;

	public AtomicLimesPackagingAdministrationPage() {
		this.add(new PackagingListView(getPackagingTypes()));
		this.add(new BookmarkablePageLink<>("addPackagingPage", AtomicLimesPackagingPage.class));
	}

	private class PackagingListView extends ListView<Packaging> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PackagingListView(List<Packaging> packagingTypes) {
			super("packagingTypes", packagingTypes);
		}

		@Override
		protected void populateItem(ListItem<Packaging> item) {
			Packaging packaging = item.getModelObject();
			item.add(new Label("name", packaging.getName()));
			item.add(new EditItemPanel<Packaging>("editItem", Model.of(packaging), packagingRepository) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void edit() {
					PageParameters parameters = new PageParameters();
					parameters.add("name", packaging.getName());
					setResponsePage(AtomicLimesPackagingPage.class, parameters);
				}

				@Override
				protected void delete() {
					packagingRepository.delete(packaging);
					setResponsePage(AtomicLimesPackagingAdministrationPage.class);
				}

			});
		}

	}

	private List<Packaging> getPackagingTypes() {
		return this.packagingRepository.findAll();
	}

}
