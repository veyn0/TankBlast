package de.tankblast.menu;

import java.util.List;

public interface MenuElement {

    public MenuLocation getMenuLocation();

    public List<ElementInteractionListener> getInteractionListener();



}
