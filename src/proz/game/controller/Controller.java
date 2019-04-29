package proz.game.controller;

import proz.game.model.*;
import proz.game.view.View;

import java.util.Random;

public class Controller {
    private Board board;
    private Player player;
    private View view;

    private Random rand;
    private static final int HORIZONTAL_MOVE_DELTA = 10;
    private static final int VERTICAL_MOVE_DELTA = 10;

    public Controller(Board board){
        this.board = board;
        this.player = board.getPlayer();
        this.rand = new Random();
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

    public void spawnAsteroid(){
        int x = rand.nextInt(view.getWidth());
        int y = 20;
        Asteroid a = new Asteroid();
        a.x = x;
        a.y = y;
        board.addAsteroid(a);
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
