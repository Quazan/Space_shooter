package proz.game.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public int x;
    public int y;

    private List<Missile> missiles;
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
        String imageUrl = "assets\\PNG\\playerShip1_blue.png";             //trzeba zmienić żeby można było wyswietlać różne pociski
        loadImage(imageUrl);
        getDimensions();
        return imageIcon.getImage();
    }
    public List<Missile> getMissiles() {return missiles;}

    public void addMissile(Missile m){
        missiles.add(m);
    }

    private void loadImage(String str){
        imageIcon = new ImageIcon(str);
    }

    private void getDimensions(){
        width = imageIcon.getIconWidth();
        height = imageIcon.getIconHeight();
    }

    Player(int startX, int startY){
        x = startX;
        y = startY;
        missiles = new ArrayList<>();
    }
}
