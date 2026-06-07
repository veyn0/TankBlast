package de.tankblast.menu.menus;

import de.tankblast.menu.BackGround;
import de.tankblast.menu.Menu;
import de.tankblast.menu.element.*;
import de.tankblast.render.Voxel;

import java.util.ArrayList;
import java.util.List;

public class GenericMenu extends Menu implements ElementInteractionListener{

    private List<InteractableButton> buttons = new ArrayList<>();

    private List<MenuElement> elements = new ArrayList<>();

    private BackGround backGround;

    private final Object lock = new Object();

    public GenericMenu(BackGround backGround) {
        this.backGround = backGround;
    }

    public void addButton(InteractableButton button){
        buttons.add(button);
        elements.add(button);
    }

    public void addElement(MenuElement element){
        elements.add(element);
    }

    @Override
    public List<MenuElement> getElements() {
        synchronized (lock) {
            return new ArrayList<>(elements);
        }
    }

    @Override
    public List<ElementInteractionListener> getInteractionListener() {
        synchronized (lock) {
            return List.of(this);
        }
    }

    @Override
    public List<Voxel> getVoxel() {
        synchronized (lock) {
            List<Voxel> result = backGround == null ? new ArrayList<>() : new ArrayList<>(backGround.getVoxel());
            for (MenuElement m : elements) {
                result.addAll(m.getVoxel());
            }
            return result;
        }
    }

    @Override
    public void onElementInteract(ElementInteractionEvent event) {
        synchronized (lock) {
            for (InteractableButton b : buttons) {
                b.onElementInteractionEvent(event);
            }
            for(MenuElement e : elements){
                if(e instanceof Slider s){
                    s.onEvent(event);
                }
            }
        }
    }

}
