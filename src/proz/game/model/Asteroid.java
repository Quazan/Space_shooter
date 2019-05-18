package proz.game.model;

import java.awt.*;

public class Asteroid extends GameObject{
    public Integer lives = 1;

    public Asteroid(Integer startX, Integer startY){
        super(startX, startY);
    }

    @Override
    public Image getImage(){
        String imageUrl = "assets\\PNG\\Meteors\\meteorGrey_med2.png";
        loadImage(imageUrl);
        getDimensions();
        return this.imageIcon.getImage();
    }

    public void takeDamage(){
        this.lives--;
        if(this.lives <= 0){
            setVisible(false);
        }
    }
}
