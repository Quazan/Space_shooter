package proz.game.model;

import javax.swing.*;
import java.awt.*;

public class Asteroid {
    public int x = 300;
    public int y = 300;

    private ImageIcon imageIcon = new ImageIcon("assets\\PNG\\Meteors\\meteorGrey_med2.png");
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
