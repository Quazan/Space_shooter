package proz.game.model;

import javax.swing.*;
import java.awt.*;

public abstract class GameObject {
    public Integer x;
    public Integer y;
    protected Integer width;
    protected Integer height;
    protected ImageIcon imageIcon;
    boolean visible;

    public GameObject(Integer X, Integer Y){
        this.x = X;
        this.y = Y;
        setVisible(true);
        setImage();
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    protected void loadImage(String str){
        imageIcon = new ImageIcon(str);
    }

    protected void getDimensions(){
        width = imageIcon.getIconWidth();
        height = imageIcon.getIconHeight();
    }

    protected abstract Image getImage();

    protected abstract void setImage();

    public int getWidth() {
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setVisible(boolean b){
        visible = b;
    }

    public boolean isVisible(){
        return visible;
    }
}
