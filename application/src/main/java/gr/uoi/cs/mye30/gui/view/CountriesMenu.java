package gr.uoi.cs.mye30.gui.view;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import gr.uoi.cs.mye30.entity.Country;
import gr.uoi.cs.mye30.gui.ComponentSupport;

@SuppressWarnings("serial")
public class CountriesMenu extends JMenu {
	public CountriesMenu() {
		super("Country");
	}

	public void showCountries(Collection<Country> countries) {
		removeAll();
		countries.stream().map(CountryMenuItem::new).forEach(this::add);
	}

	public List<JMenuItem> getCountryMenuItems() {
		return ComponentSupport.getChildren(CountryMenuItem.class, this).stream().collect(toList());
	}

	public Collection<Country> getSelectedCountries() {
		//@formatter:off
		return ComponentSupport.getChildren(CountryMenuItem.class, this)
				.stream()
				.filter(CountryMenuItem::isSelected)
				.map(CountryMenuItem::getCountry)
				.collect(toList());
		//@formatter:on
	}

	private static class CountryMenuItem extends JCheckBoxMenuItem {
		private Country country;

		public CountryMenuItem(Country country) {
			super();
			setText(String.format("%s [%s]", country.getName(), country.getCode()));
			this.country = country;
		}

		public Country getCountry() {
			return country;
		}
	}
}
