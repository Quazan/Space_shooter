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
    public Image getImage(){return imageIcon.getImage();}

}
