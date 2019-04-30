package proz.game.controller;

import proz.game.model.*;
import proz.game.view.View;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    private Board board;
    private Player player;
    private View view;
    private Timer timer;

    private Random rand;
    private static final int HORIZONTAL_MOVE_DELTA = 10;
    private static final int VERTICAL_MOVE_DELTA = 10;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 20;

    public Controller(Board b){
        board = b;
        player = board.getPlayer();
        rand = new Random();

        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);
    }

    public void setView(View v){
        this.view = v;
    }

    public void moveLeft(){
        player.x -= HORIZONTAL_MOVE_DELTA;
        checkLeftBorder();
    }

    public void moveRight(){
        player.x += HORIZONTAL_MOVE_DELTA;
        checkRightBorder();
    }

    public void moveUp(){
        player.y -= VERTICAL_MOVE_DELTA;
        checkUpperBorder();
    }

    public void moveDown(){
        player.y += VERTICAL_MOVE_DELTA;
        checkDownBorder();
    }

    public void fire() {
        int x = player.x + player.getWidth()/2;
        int y = player.y;

        Missile m = new Missile(x, y);

        player.addMissile(m);
    }

    private void spawnAsteroid(){
        int x = rand.nextInt(view.getWidth());
        int y = 20;
        Asteroid a = new Asteroid(x, y);
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


    private class ScheduleTask extends TimerTask {
        @Override
        public void run(){
            spawnAsteroid();
            view.updateView();
        }
    }

    public void updateAsteroid(Asteroid asteroid){
        if(asteroid.y > view.getHeight() + 30){
            board.asteroids.remove(asteroid);
        }
        else
        {
            asteroid.y += 2;
        }
    }

    public void updateMissile(Missile missile){
        if(missile.y < -30) {
            player.missiles.remove(missile);
        }
        else{
            missile.y -= 2;
        }
    }
}


