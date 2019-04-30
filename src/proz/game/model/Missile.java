package proz.game.model;

import javax.swing.*;
import java.awt.*;

public class Missile {
    public int x;
    public int y;
    public int dmg = 200;

    private boolean visible;
    private ImageIcon imageIcon;
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }
    public int getHeight(){
        return height;
    }
    public Image getImage(){
        String str = "assets\\PNG\\Lasers\\laserRed03.png";
        loadImage(str);
        getDimensions();
        return this.imageIcon.getImage();
    }

    private void loadImage(String str){
        imageIcon = new ImageIcon(str);
    }

    private void getDimensions(){
        width = imageIcon.getIconWidth();
        height = imageIcon.getIconHeight();
    }

    public Missile(int startX, int startY){
        this.x = startX;
        this.y = startY;
        setVisible(true);
        getImage();
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public int getDamage(){
        return this.dmg;
    }

    public void setVisible(boolean b){
        this.visible = b;
    }

    public boolean getVisible(){
        return visible;
    }
}
