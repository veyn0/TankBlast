package de.tankblast.menu;

import java.util.List;

public interface MenuElement {

    public MenuElementLocation getMenuLocation();

    public List<ElementInteractionListener> getInteractionListener();



}
