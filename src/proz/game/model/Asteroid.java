package proz.game.model;

import java.awt.*;
import java.util.Random;

public class Asteroid extends GameObject{
    public Integer lives = 1;
    Random rand;

    public Asteroid(Integer startX, Integer startY){
        super(startX, startY);
    }

    @Override
    public Image getImage(){
        return this.imageIcon.getImage();
    }

    @Override
    protected void setImage(){
        rand = new Random();
        String imageUrl;
        int val = rand.nextInt(8);

        switch (val){
            case 0:
                imageUrl = "assets\\PNG\\Meteors\\meteorGrey_med1.png";
                break;
            case 1:
                imageUrl = "assets\\PNG\\Meteors\\meteorGrey_med2.png";
                break;
            case 2:
                imageUrl = "assets\\PNG\\Meteors\\meteorBrown_med1.png";
                break;
            case 3:
                imageUrl = "assets\\PNG\\Meteors\\meteorBrown_med2.png";
                break;
            case 4:
                imageUrl = "assets\\PNG\\Meteors\\meteorGrey_big1.png";
                break;
            case 5:
                imageUrl = "assets\\PNG\\Meteors\\meteorGrey_big2.png";
                break;
            case 6:
                imageUrl = "assets\\PNG\\Meteors\\meteorBrown_big1.png";
                break;
            case 7:
                imageUrl = "assets\\PNG\\Meteors\\meteorBrown_big2.png";
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + val);
        }
        loadImage(imageUrl);
        getDimensions();
    }

    public void takeDamage(){
        this.lives--;
        if(this.lives <= 0){
            setVisible(false);
        }
    }
}
