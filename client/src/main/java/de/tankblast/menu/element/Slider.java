package de.tankblast.menu.element;

import de.tankblast.menu.MenuButton;
import de.tankblast.menu.event.ElementClickEvent;
import de.tankblast.render.Voxel;
import de.tankblast.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class Slider extends MenuButton {

    /*
    TODO: slider texture is currently requiered in the same size as background.
     - add deadzone
     */

    private int stepCount;
    private Texture texture;
    private int currentStep;
    private int offset;

    public Slider(MenuElementLocation location, Texture texture, Texture sliderTexture, int stepCount, int initialValue) {
        super(location, texture);
        this.stepCount = stepCount;
        this.texture = sliderTexture;
        this.currentStep = initialValue;
        this.offset = 3;
    }

    public void onEvent(ElementInteractionEvent event){
        if(event.getElement() != this) return;
        if(event instanceof ElementClickEvent e){
            MenuElementLocation elementLocation = super.getMenuLocation();
            MouseLocation mouseLocation = e.getMouseLocation();
            double stepPerUnit = (stepCount) / elementLocation.getWidth();
            int step = (int)(mouseLocation.getX() * stepPerUnit);
            currentStep = Math.clamp(step, 0, stepCount);
            System.out.println(currentStep);
        }
    }

    @Override
    public List<Voxel> getVoxel(){
        List<Voxel> result = new ArrayList<>(super.getVoxel());
        result.addAll(getSlider());
        return result;
    }

    private List<Voxel> getSlider(){
        int texWidth = texture.getWidth();
        int texHeight = texture.getHeight();
        int[] data = texture.getData();

        MenuElementLocation elementLocation = super.getMenuLocation();
        double unitPerStep = elementLocation.getWidth() / stepCount;
        double currentXOffset = (unitPerStep * currentStep) - offset;
        MenuElementLocation location = new MenuElementLocation(elementLocation.getX() + currentXOffset, elementLocation.getY(), elementLocation.getWidth(), elementLocation.getHeight());

        double pixelSize = location.getWidth() / texWidth;

        List<Voxel> result = new ArrayList<>(texWidth * texHeight);
        for (int py = 0; py < texHeight; py++) {
            for (int px = 0; px < texWidth; px++) {
                int colour = data[py * texWidth + px];
                if ((colour >>> 24) == 0) continue;

                double worldX = location.getX() + (px + 0.5) * pixelSize;
                double worldY = location.getY() + location.getHeight() - (py + 0.5) * pixelSize;

                result.add(new Voxel(colour, worldX, worldY, pixelSize, 0));
            }
        }
        return result;
    }

}
