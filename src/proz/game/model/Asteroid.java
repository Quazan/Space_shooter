package proz.game.model;

import javax.swing.*;
import java.awt.*;

public class Asteroid {
    public int x;
    public int y;

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
        String imageUrl = "assets\\PNG\\Meteors\\meteorGrey_med2.png";
        loadImage(imageUrl);
        getDimensions();
        return this.imageIcon.getImage();
    }

    private void loadImage(String str){
        this.imageIcon = new ImageIcon(str);
    }

    private void getDimensions(){
        this.width = this.imageIcon.getIconWidth();
        this.height = this.imageIcon.getIconHeight();
    }

    public Asteroid(int startX, int startY){
        this.x = startX;
        this.y = startY;
        getImage();
    }
}
