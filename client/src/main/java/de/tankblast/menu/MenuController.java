package de.tankblast.menu;

import de.tankblast.menu.event.ElementClickEvent;
import de.tankblast.menu.event.ElementHoverEvent;
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
        this.currentMenu = menu;
        this.lastHovered = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentMenu == null) return;
        MenuElement element = findElementAt(e);
        if (element == null) return;
        dispatch(new ElementClickEvent(element, currentMenu));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (currentMenu == null) return;
        MenuElement element = findElementAt(e);
        if (element == lastHovered) return; // nur bei Wechsel feuern
        lastHovered = element;
        if (element == null) return;
        dispatch(new ElementHoverEvent(element, currentMenu));
    }

    private void dispatch(ElementInteractionEvent event) {
        for (ElementInteractionListener l : currentMenu.getInteractionListener()) {
            l.onElementInteract(event);
        }
    }

    private MenuElement findElementAt(MouseEvent e) {
        Component source = e.getComponent();

        // Panel-Pixel -> Image-Pixel (RenderPanel skaliert das Bild auf seine Größe)
        double imgX = (double) e.getX() * imageWidth  / source.getWidth();
        double imgY = (double) e.getY() * imageHeight / source.getHeight();

        // Inverse von VoxelRenderer#render
        double pixelPerUnit = imageHeight / (2.0 * camera.getPosition().getZ());
        double worldX = camera.getPosition().getX() + (imgX - imageWidth  / 2.0) / pixelPerUnit;
        double worldY = camera.getPosition().getY() - (imgY - imageHeight / 2.0) / pixelPerUnit;

        for (MenuElement el : currentMenu.getElements()) {
            MenuElementLocation loc = el.getMenuLocation();
            if (loc != null && loc.isBetween(worldX, worldY)) {
                return el;
            }
        }
        return null;
    }
}