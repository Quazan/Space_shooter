package proz.game.model;

import javax.swing.*;
import java.awt.*;

public class Missile {
    public int x;
    public int y;

    private ImageIcon imageIcon = new ImageIcon("assets\\PNG\\Lasers\\laserRed03.png");
    private int width = imageIcon.getIconWidth();
    private int height = imageIcon.getIconHeight();

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
        getImage();
    }

    public void update(){
        if(this.y < -30){

        }
    }
}
