package proz.game.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private Integer score = 0;
    private Integer lives = 3;

    private Boolean reload;
    private Boolean shield;
    private Boolean powerUp;

    private ImageIcon shieldImage;

    private List<Missile> missiles;

    Player(Integer startX, Integer startY) {
        super(startX, startY);
        reload = false;
        shield = false;
        powerUp = false;
        missiles = new ArrayList<>();

        setShieldImage();
    }

    @Override
    public Image getImage() {
        return imageIcon.getImage();
    }

    @Override
    protected void setImage() {
        String imageUrl = "assets\\PNG\\playerShip2_red.png";
        loadImage(imageUrl);
        getDimensions();
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public void addMissile(Missile m) {
        missiles.add(m);
    }

    public void removeMissile(Missile missile){
        missiles.remove(missile);
    }

    public void takeDamage() {
        if (shield) {
            shield = false;
        } else {
            lives--;
        }

        if (lives <= 0) {
            setVisible(false);
        }
    }

    public Integer getLives(){
        return lives;
    }

    public void setBonus(Bonus bonus) {
        switch (bonus.getType()) {

            case power_UP:
                powerUp = true;
                break;

            case shield:
                shield = true;
                break;
        }
    }

    public void setReload(Boolean b) {
        reload = b;
    }

    public Boolean isReloading() {
        return reload;
    }

    public Boolean isShielded() {
        return shield;
    }

    public void setPowerUp(Boolean b) {
        powerUp = b;
    }

    public Boolean isPoweredUp() {
        return powerUp;
    }

    public Image getShieldImage() {
        return shieldImage.getImage();
    }

    private void setShieldImage() {
        String imageUrl = "assets\\PNG\\Effects\\shield1.png";
        shieldImage = new ImageIcon(imageUrl);
    }

    public Rectangle getShieldBounds() {
        return new Rectangle(x, y, shieldImage.getIconWidth(), shieldImage.getIconHeight());
    }

    public Integer getScore(){
        return score;
    }

    public void addScore(Integer value){
        score += value;
    }
}
