package de.tankblast.menu.menus;

import de.tankblast.menu.ElementInteractionEvent;
import de.tankblast.menu.ElementInteractionListener;
import de.tankblast.menu.Menu;
import de.tankblast.menu.MenuElement;

import java.util.List;

public class HomeScreen implements Menu, ElementInteractionListener {

    @Override
    public void onElementInteract(ElementInteractionEvent event) {

    }

    @Override
    public List<MenuElement> getElements() {
        return List.of();
    }

    @Override
    public List<ElementInteractionListener> getInteractionListener() {
        return List.of();
    }
}
