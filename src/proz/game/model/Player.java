package proz.game.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import  proz.game.model.Missile;

public class Player {
    public int x = 300;
    public int y = 300;

    //lista pocisków
    private List<Missile> missiles = new ArrayList<>();

    private ImageIcon imageIcon = new ImageIcon("assets\\PNG\\playerShip1_blue.png");
    private int width = imageIcon.getIconWidth();
    private int height = imageIcon.getIconHeight();

    public int getWidth() {
        return width;
    }
    public int getHeight(){
        return height;
    }
    public Image getImage(){return imageIcon.getImage();}
    public List<Missile> getMissiles() {return missiles;}
    public void addMissile(Missile m){
        missiles.add(m);
    }
}
