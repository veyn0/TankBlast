package de.tankblast.menu.event;

import de.tankblast.menu.ElementInteractionEvent;
import de.tankblast.menu.Menu;
import de.tankblast.menu.MenuElement;

public class ElementClickEvent implements ElementInteractionEvent {


    private MenuElement element;
    private Menu menu;

    public ElementClickEvent(MenuElement element, Menu menu) {
        this.element = element;
        this.menu = menu;
    }

    @Override
    public MenuElement getElement() {
        return null;
    }

    @Override
    public Menu getMenu() {
        return null;
    }

}
