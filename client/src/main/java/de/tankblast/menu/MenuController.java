package de.tankblast.menu;

import de.tankblast.menu.element.*;
import de.tankblast.menu.event.ElementClickEvent;
import de.tankblast.menu.event.ElementStartHoverEvent;
import de.tankblast.menu.event.ElementStopHoverEvent;
import de.tankblast.render.Camera;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuController extends MouseAdapter {

    private final Camera camera;
    private final int imageWidth;
    private final int imageHeight;

    private Menu currentMenu;
    private MenuElement lastHovered;

    public MenuController(Camera camera, int imageWidth, int imageHeight) {
        this.camera = camera;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public void setCurrentMenu(Menu menu) {
        if (lastHovered != null && currentMenu != null) {
            dispatch(new ElementStopHoverEvent(lastHovered, currentMenu));
        }
        this.currentMenu = menu;
        this.lastHovered = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentMenu == null) return;
        MouseLocation element = findElementAt(e);
        if (element.getElement() == null) return;
        dispatch(new ElementClickEvent(element, currentMenu));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (currentMenu == null) return;
        updateHover(findElementAt(e).getElement());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (currentMenu == null) return;
        updateHover(null);
    }

    private void updateHover(MenuElement element) {
        if (element == lastHovered) return;

        if (lastHovered != null) {
            dispatch(new ElementStopHoverEvent(lastHovered, currentMenu));
        }
        lastHovered = element;
        if (element != null) {
            dispatch(new ElementStartHoverEvent(element, currentMenu));
        }
    }

    private void dispatch(ElementInteractionEvent event) {
        for (ElementInteractionListener l : currentMenu.getInteractionListener()) {
            l.onElementInteract(event);
        }
    }

    private MouseLocation findElementAt(MouseEvent e) {
        Component source = e.getComponent();

        double imgX = (double) e.getX() * imageWidth  / source.getWidth();
        double imgY = (double) e.getY() * imageHeight / source.getHeight();

        double pixelPerUnit = imageHeight / (2.0 * camera.getPosition().getZ());
        double worldX = camera.getPosition().getX() + (imgX - imageWidth  / 2.0) / pixelPerUnit;
        double worldY = camera.getPosition().getY() - (imgY - imageHeight / 2.0) / pixelPerUnit;

        for (MenuElement el : currentMenu.getElements()) {
            MenuElementLocation loc = el.getMenuLocation();
            if (loc != null && loc.isBetween(worldX, worldY)) {
                double elementX = worldX - loc.getX();
                double elementY = worldY - loc.getY();
                return new MouseLocation(el, elementX, elementY);
            }
        }
        return new MouseLocation(null, worldX, worldY);
    }

}