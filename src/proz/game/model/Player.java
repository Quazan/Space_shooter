package proz.game.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public int x;
    public int y;
    public Integer score = 0;
    private int hp = 100;
    public int lives = 3;

    boolean visible;
    public List<Missile> missiles;
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
        String imageUrl = "assets\\PNG\\playerShip2_red.png";
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
        setVisible(true);
        missiles = new ArrayList<>();
        getImage();
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public void takeDamage(int damage){
        hp -= damage;
        if(hp <= 0){
            setVisible(false);
        }
    }

    public void setVisible(boolean b){
        visible = b;
    }

    public boolean getVisible(){
        return visible;
    }
}
