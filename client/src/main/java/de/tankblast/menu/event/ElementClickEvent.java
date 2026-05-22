package de.tankblast.menu.event;

import de.tankblast.menu.element.ElementInteractionEvent;
import de.tankblast.menu.Menu;
import de.tankblast.menu.element.MenuElement;
import de.tankblast.menu.element.MouseLocation;

public class ElementClickEvent implements ElementInteractionEvent {

    private MenuElement element;
    private Menu menu;
    private MouseLocation mouseLocation;

    public ElementClickEvent(MouseLocation element, Menu menu) {
        this.element = element.getElement();
        this.menu = menu;
        this.mouseLocation = element;
    }

    public MouseLocation getMouseLocation() {
        return mouseLocation;
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
