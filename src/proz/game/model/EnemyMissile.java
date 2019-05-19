package proz.game.model;

import java.awt.*;

public class EnemyMissile extends GameObject {

    public EnemyMissile(int startX, int startY){
        super(startX, startY);
    }

    @Override
    public Image getImage(){
        return imageIcon.getImage();
    }

    @Override
    protected void setImage(){
        String str = "assets\\PNG\\Lasers\\laserBlue03.png";
        loadImage(str);
        getDimensions();
    }
}
