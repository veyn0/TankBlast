package de.tankblast.menu;

import de.tankblast.menu.event.ElementClickEvent;
import de.tankblast.menu.event.ElementStartHoverEvent;
import de.tankblast.menu.event.ElementStopHoverEvent;
import de.tankblast.texture.Texture;

public class InteractableButton extends MenuButton{

    private Texture hoverTexture;
    private Texture texture;
    private Runnable onClick;

    public InteractableButton(MenuElementLocation location, Texture texture, Texture hoverTexture, Runnable onClick) {
        super(location, texture);
        this.onClick = onClick;
        this.texture = texture;
        this.hoverTexture = hoverTexture;
    }

    private void setHovering(boolean hovering){
        if(hovering){
            setTexture(hoverTexture);
        }
        else {
            setTexture(texture);
        }
    }

    public void onClick(){
        onClick.run();
    }

    public void onElementInteractionEvent(ElementInteractionEvent e){
        if(e.getElement().equals(this)){
            if(e instanceof ElementStopHoverEvent){
                setHovering(false);
            }
            else if(e instanceof ElementStartHoverEvent){
                setHovering(true);
            }
            else if(e instanceof ElementClickEvent){
                onClick();
            }
        }
    }

}
