package proz.game.controller;

import proz.game.model.*;
import proz.game.view.View;

public class Controller {
    private Player player;
    private View view;

    private static final int HORIZONTAL_MOVE_DELTA = 10;
    private static final int VERTICAL_MOVE_DELTA = 10;

    public Controller(Player player){
        this.player = player;
    }

    public void setView(View v){
        this.view = v;
    }

    public void moveLeft(){
        player.x -= HORIZONTAL_MOVE_DELTA;
        checkLeftBorder();
        view.updateView();
    }

    public void moveRight(){
        player.x += HORIZONTAL_MOVE_DELTA;
        checkRightBorder();
        view.updateView();
    }

    public void moveUp(){
        player.y -= VERTICAL_MOVE_DELTA;
        checkUpperBorder();
        view.updateView();
    }

    public void moveDown(){
        player.y += VERTICAL_MOVE_DELTA;
        checkDownBorder();
        view.updateView();
    }

    public void fire() {
        Missile m = new Missile();
        m.x = player.x + player.getWidth()/2;
        m.y = player.y;

        player.addMissile(m);
    }

    private void checkLeftBorder(){
        if(player.x < 0){
            player.x = 0;
        }
    }

    private void checkRightBorder(){
        final int lastPossibleX = view.getWidth() - player.getWidth();
        if(player.x >= lastPossibleX){
            player.x = lastPossibleX;
        }
    }

    private void checkUpperBorder(){
        if(player.y < 0){
            player.y = 0;
        }
    }

    private void checkDownBorder(){
        final int lastPossibleY = view.getHeight() - player.getHeight();
        if(player.y >= lastPossibleY){
            player.y = lastPossibleY;
        }
    }
}
