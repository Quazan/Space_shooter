package proz.game.model;

import java.awt.*;

public class EnemyMissile extends GameObject {

    public EnemyMissile(int startX, int startY){
        super(startX, startY);
    }

    @Override
    public Image getImage(){
        String str = "assets\\PNG\\Lasers\\laserBlue03.png";
        loadImage(str);
        getDimensions();
        return this.imageIcon.getImage();
    }
}
