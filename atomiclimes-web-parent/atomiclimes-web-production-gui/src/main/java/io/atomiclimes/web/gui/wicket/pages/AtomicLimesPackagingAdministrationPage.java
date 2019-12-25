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

import io.atomiclimes.common.dao.entities.Packaging;
import io.atomiclimes.common.dao.repositories.PackagingRepository;
import io.atomiclimes.web.gui.panels.EditItemPanel;

@MountPath("admin/packaging")
public class AtomicLimesPackagingAdministrationPage extends AtomicLimesDefaultWebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private PackagingRepository packagingRepository;

	public AtomicLimesPackagingAdministrationPage() {
		List<Packaging> packagingTypesList = new LinkedList<>();
		getPackagingTypes().forEach(packagingTypesList::add);
		this.add(new PackagingListView(packagingTypesList));
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
			item.add(new EditItemPanel<Packaging>("editItem", Model.of(packaging)) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void edit() {
					setResponsePage(AtomicLimesPackagingPage.class, generatePageParametersFromPackaging(packaging));
				}

				@Override
				protected void delete() {
					packagingRepository.delete(packaging);
					setResponsePage(AtomicLimesPackagingAdministrationPage.class);
				}

			});
		}

	}

	private Iterable<Packaging> getPackagingTypes() {
		return this.packagingRepository.findAll();
	}

	private PageParameters generatePageParametersFromPackaging(Packaging packaging) {
		PageParameters parameters = new PageParameters();
		parameters.add("name", packaging.getName());
		parameters.add("capacity", packaging.getCapacity());
		parameters.add("unit", packaging.getUnit());
		parameters.add("duration", packaging.getDuration().toMillis()/1000);
		parameters.add("packagingOrder", packaging.getPackagingOrder());
		return parameters;
	}

}
