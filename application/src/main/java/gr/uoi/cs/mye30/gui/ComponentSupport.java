package gr.uoi.cs.mye30.gui;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JMenu;

public class ComponentSupport {
	public static <T> Optional<T> getParent(Class<T> clazz, Component component) {
		Component parent = component.getParent();
		while (parent != null) {
			if (clazz.isInstance(parent)) {
				return Optional.of(clazz.cast(parent));
			} else {
				parent = parent.getParent();
			}
		}
		return Optional.empty();
	}

	public static <T extends Component> List<T> getChildren(Class<T> clazz, final Container container) {
		Component[] components;
		if (container instanceof JMenu)
			components = ((JMenu) container).getMenuComponents();
		else
			components = container.getComponents();
		List<T> compList = new ArrayList<T>();
		for (Component comp : components) {
			if (clazz.isAssignableFrom(comp.getClass())) {
				compList.add(clazz.cast(comp));
			}
			if (comp instanceof Container)
				compList.addAll(getChildren(clazz, (Container) comp));
		}
		return compList;
	}

	public static List<Component> getChildren(final Container container) {
		return getChildren(Component.class, container);
	}

}
