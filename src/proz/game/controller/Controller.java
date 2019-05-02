package proz.game.controller;

import proz.game.model.*;
import proz.game.view.View;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.HashMap;
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
    public final HashMap<Integer, Timestamp> pressedKeys;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;

    public Controller(Board b){
        board = b;
        player = board.getPlayer();
        rand = new Random();

        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);

        pressedKeys = new HashMap<>();
    }

    public void setView(View v){
        this.view = v;
    }

    private void moveLeft(){
        player.x -= HORIZONTAL_MOVE_DELTA;
        checkLeftBorder();
    }

    private void moveRight(){
        player.x += HORIZONTAL_MOVE_DELTA;
        checkRightBorder();
    }

    private void moveUp(){
        player.y -= VERTICAL_MOVE_DELTA;
        checkUpperBorder();
    }

    private void moveDown(){
        player.y += VERTICAL_MOVE_DELTA;
        checkDownBorder();
    }

    private void fire() {
        if (checkReload()){
            return;
        }

        int x = player.x + player.getWidth()/2;
        int y = player.y;

        Missile m = new Missile(x, y);

        player.addMissile(m);
        randomAsteroid();
    }

    private void randomAsteroid(){
        int x = rand.nextInt(view.getWidth());
        int y = -20;

        checkSpawnCollision(x, y);
    }

    private void checkSpawnCollision(int x, int y){
        Asteroid a = new Asteroid(x, y);
        Rectangle asteroidBounds = a.getBounds();

        for(Asteroid asteroid : board.getAsteroids()){
            if(a == null){ break;}
            Rectangle astr = asteroid.getBounds();
            if(asteroidBounds.intersects(astr)){
               a = null;
            }
        }

        if(a != null) board.addAsteroid(a);
    }

    private void spawnAsteroid(int x, int y){
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

    private boolean checkReload(){
        int missileCount = player.missiles.size();
        if (missileCount > 0){
            Rectangle playerBounds = player.getBounds();
            Missile missile = player.missiles.get(missileCount-1);
            Rectangle missileBounds = missile.getBounds();
            return playerBounds.intersects(missileBounds);
        }
        return false;
    }


    private class ScheduleTask extends TimerTask {
        @Override
        public void run(){
            int chance = rand.nextInt(100);
            //if(chance < 15) randomAsteroid();

            keyIterator();
            checkCollisions();
            view.updateView();
        }
    }

    public void updateAsteroid(Asteroid asteroid){
        if(asteroid.y > view.getHeight() + 30 || !asteroid.getVisible()){
            deleteAsteroid(asteroid);
        }
        else {
            asteroid.y += 2;
        }
    }

    public void updateMissile(Missile missile){
        if(missile.y < -30 || !missile.getVisible()) {
            deleteMissile(missile);
        }
        else{
            missile.y -= 5;
        }
    }

    private void checkCollisions(){
        Rectangle playerBounds = player.getBounds();

        for(Asteroid asteroid : board.getAsteroids()){
            Rectangle asteroidBounds = asteroid.getBounds();

            if(playerBounds.intersects(asteroidBounds)){
                player.takeDamage(asteroid.getDamage());
                asteroid.setVisible(false);
            }
        }

        for(Asteroid asteroid : board.getAsteroids()){
            Rectangle asteroidBounds = asteroid.getBounds();

            for(Missile missile : player.getMissiles()){
                Rectangle missileBounds = missile.getBounds();

                if(missileBounds.intersects(asteroidBounds)){
                    asteroid.takeDamage(missile.getDamage());
                    missile.setVisible(false);
                    player.score += 100;
                }
            }
        }
    }

    private void keyIterator(){
        for(Integer keyCode : pressedKeys.keySet()){
            if (keyCode == KeyEvent.VK_LEFT) {
                if(pressedKeys.containsKey(KeyEvent.VK_RIGHT)) {
                    if(pressedKeys.get(keyCode).after(pressedKeys.get(KeyEvent.VK_RIGHT))) {
                           moveLeft();
                    }
                }
                else{
                    moveLeft();
                }
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                if(pressedKeys.containsKey(KeyEvent.VK_LEFT)) {
                    if (pressedKeys.get(keyCode).after(pressedKeys.get(KeyEvent.VK_LEFT))) {
                        moveRight();
                    }
                }
                else{
                        moveRight();
                }
            }
            if (keyCode == KeyEvent.VK_UP) {
                if(pressedKeys.containsKey(KeyEvent.VK_DOWN)) {
                    if (pressedKeys.get(keyCode).after(pressedKeys.get(KeyEvent.VK_DOWN))) {
                        moveUp();
                    }
                }
                else{
                    moveUp();
                }
            }
            if (keyCode == KeyEvent.VK_DOWN) {
                if(pressedKeys.containsKey(KeyEvent.VK_UP)) {
                    if (pressedKeys.get(keyCode).after(pressedKeys.get(KeyEvent.VK_UP))) {
                        moveDown();
                    }
                }
                else{
                    moveDown();
                }
            }
            if (keyCode == KeyEvent.VK_SPACE) {
                fire();
            }
            if (keyCode == KeyEvent.VK_ESCAPE){
                stop();
            }
        }
    }


    public void stop(){
        timer.cancel();
    }

    public void deleteAsteroid(Asteroid asteroid){
        board.asteroids.remove(asteroid);
    }

    public void deleteMissile(Missile missile){
        player.missiles.remove(missile);
    }

}


