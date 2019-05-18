package proz.game.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject{
    public Integer score = 0;

    public Integer lives = 1;

    public Boolean reload;
    public Boolean shield;
    public Boolean powerUp;

    private ImageIcon shieldImage;

    public List<Missile> missiles;

    Player(Integer startX, Integer startY){
        super(startX, startY);
        reload = false;
        shield = false;
        powerUp = false;
        missiles = new ArrayList<>();

        setShieldImage();
    }

    @Override
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

    public void takeDamage(){
        if(shield){
            shield = false;
        }
        else{
            lives--;
        }

        if(lives <= 0){
            setVisible(false);
        }
    }

    public void setBonus(Bonus bonus){
        switch (bonus.getType()){

            case power_UP:
                powerUp = true;
                break;

            case shield:
                shield = true;
                break;
        }
    }

    public Boolean isShielded(){
        return shield;
    }

    public Boolean isPoweredUp(){
        return powerUp;
    }

    public Image getShieldImage(){
        return shieldImage.getImage();
    }

    private void setShieldImage(){
        String imageUrl = "assets\\PNG\\Effects\\shield1.png";
        shieldImage = new ImageIcon(imageUrl);
    }

    public Rectangle getShieldBounds(){
        return new Rectangle(x, y, shieldImage.getIconWidth(), shieldImage.getIconHeight());
    }
}
