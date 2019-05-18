package proz.game.model;

import java.awt.*;

public class Missile extends GameObject{

    public Missile(int startX, int startY){
        super(startX, startY);
    }

    @Override
    public Image getImage(){
        return this.imageIcon.getImage();
    }

    @Override
    public void setImage(){
        String str = "assets\\PNG\\Lasers\\laserRed03.png";
        loadImage(str);
        getDimensions();
    }
}
