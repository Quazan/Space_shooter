package proz.game.model;

import java.awt.*;

public class Bonus extends GameObject{
    BonusType type;

    public Bonus(Integer startX, Integer startY, BonusType bt){
        super(startX, startY);
        type = bt;
        getImage();
    }

    @Override
    public Image getImage(){
        String imageUrl;
        if(type == null) return null;

        switch (type){
            case shield:
                imageUrl = "assets\\PNG\\Power-ups\\powerupRed_shield.png";
                break;

            case power_UP:
                imageUrl = "assets\\PNG\\Power-ups\\powerupRed_bolt.png";
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        loadImage(imageUrl);
        getDimensions();
        return imageIcon.getImage();
    }

    public BonusType getType() {return type;}
}
