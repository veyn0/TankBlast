package de.tankblast.menu.event;

import de.tankblast.menu.ElementInteractionEvent;
import de.tankblast.menu.Menu;
import de.tankblast.menu.MenuElement;

public class ElementHoverEvent implements ElementInteractionEvent {

    private MenuElement element;
    private Menu menu;

    public ElementHoverEvent(MenuElement element, Menu menu) {
        this.element = element;
        this.menu = menu;
    }

    @Override
    public MenuElement getElement() {
        return element;
    }

    @Override
    public Menu getMenu() {
        return menu;
    }
}
