package de.tankblast.menu;

import de.tankblast.render.GraphicsComponent;

import java.util.List;

public abstract class Menu implements GraphicsComponent {

    public abstract List<MenuElement> getElements();

    public abstract List<ElementInteractionListener> getInteractionListener();



}
