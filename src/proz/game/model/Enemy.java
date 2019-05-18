package proz.game.model;

import java.awt.*;

public class Enemy extends GameObject{
    public Integer lives = 1;

    public Enemy(Integer startX, Integer startY){
        super(startX, startY);
    }

    @Override
    public Image getImage(){
        String imageUrl = "assets\\PNG\\Enemies\\enemyBlack1.png";
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

    public boolean getVisible(){
        return visible;
    }
}
