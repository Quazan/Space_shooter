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
    private Timer buffTimer;

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
        buffTimer = new Timer();
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

        int x = player.x + player.getWidth()/2 - 4;
        int y = player.y;

        Missile m = new Missile(x, y);
        player.addMissile(m);

        if(player.isPoweredUp()){
            x -= 30;
            y += 15;
            m = new Missile(x, y);
            player.addMissile(m);

            x += 60;
            m = new Missile(x, y);
            player.addMissile(m);
        }

        player.reload = true;
        timer.schedule(new Reload(), 200);
        randomEnemy();
        //randomAsteroid();
    }

    private void randomAsteroid(){
        int x = rand.nextInt(view.getWidth());
        int y = -20;
        Asteroid a = new Asteroid(x, y);
        if(checkSpawnCollision(a.getBounds())){
            board.addAsteroid(a);
        }
    }

    private void randomEnemy(){
        int x = rand.nextInt(view.getWidth());
        int y = -20;
        Enemy e = new Enemy(x, y);
        if(checkSpawnCollision(e.getBounds())){
            board.addEnemy(e);
        }
    }

    private void randomBonus(Integer x, Integer y){
        Bonus bonus;
        if(rand.nextInt(10) <= 4){
            bonus = new Bonus(x, y, BonusType.shield);
        }
        else{
            bonus = new Bonus(x, y, BonusType.power_UP);
        }

        board.addBonus(bonus);
    }

    private Boolean checkSpawnCollision(Rectangle bounds){
        if(bounds == null){ return false;}

        for(Asteroid asteroid : board.getAsteroids()){
            Rectangle astr = asteroid.getBounds();
            if(bounds.intersects(astr)){
               return false;
            }
        }

        for(Enemy enemy : board.getEnemies()){
            Rectangle ene = enemy.getBounds();
            if(bounds.intersects(ene)){
                return false;
            }
        }

        return true;
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
        return player.reload;
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run(){
            int chance = rand.nextInt(100);
            //if(chance < 15) randomAsteroid();

            try{
                keyIterator();
            } catch (Exception e) {
                e.printStackTrace();
            }

            checkCollisions();
            view.updateView();
        }
    }

    private class Reload extends TimerTask {
        @Override
        public  void run(){
            player.reload = false;
        }
    }

    private class PowerOff extends TimerTask{
        @Override
        public void run() {player.powerUp = false;
        System.out.println("off");}
    }

    public void updateAsteroid(Asteroid asteroid){
        if(asteroid.y > view.getHeight() + 30 || !asteroid.isVisible()){
            deleteAsteroid(asteroid);
        }
        else {
            asteroid.y += 2;
        }
    }

    public void updateEnemy(Enemy enemy){
        if(enemy.y > view.getHeight() + 30 || !enemy.getVisible()){
            deleteEnemy(enemy);
        }
        else {
            enemy.y += 2;
        }
    }

    public void updateMissile(Missile missile){
        if(missile.y < -30 || !missile.isVisible()) {
            deleteMissile(missile);
        }
        else{
            missile.y -= 8;
        }
    }

    public void updateBonus(Bonus bonus){
        if(bonus.y > view.getHeight() + 30 || !bonus.isVisible()) {
            deleteBonus(bonus);
        }
        else{
            bonus.y += 4;
        }
    }


    private void checkCollisions(){
        Rectangle playerBounds;

        if(player.isShielded()){
            playerBounds = player.getShieldBounds();
        }
        else
        {
            playerBounds = player.getBounds();
        }


        for(Bonus bonus : board.getBonuses()){
            Rectangle bonusBounds = bonus.getBounds();

            if(playerBounds.intersects(bonusBounds)){
                if(bonus.getType().equals(BonusType.power_UP)){
                    if(player.isPoweredUp()){
                        buffTimer.cancel();
                        buffTimer = new Timer();
                    }

                    buffTimer.schedule(new PowerOff(), 5000);
                }
                player.setBonus(bonus);
                bonus.setVisible(false);
            }
        }

        for(Asteroid asteroid : board.getAsteroids()){
            Rectangle asteroidBounds = asteroid.getBounds();

            if(playerBounds.intersects(asteroidBounds)){
                player.takeDamage();
                asteroid.setVisible(false);
            }

            for(Missile missile : player.getMissiles()){
                Rectangle missileBounds = missile.getBounds();

                if(missileBounds.intersects(asteroidBounds)){
                    asteroid.takeDamage();
                    missile.setVisible(false);
                    player.score += 100;
                }
            }
        }

        for(Enemy enemy : board.getEnemies()){
            Rectangle enemyBounds = enemy.getBounds();

            if(playerBounds.intersects(enemyBounds)){
                player.takeDamage();
                enemy.setVisible(false);
            }

            for(Missile missile : player.getMissiles()){
                Rectangle missileBounds = missile.getBounds();

                if(missileBounds.intersects(enemyBounds)){
                    enemy.takeDamage();
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
            if (keyCode == KeyEvent.VK_ESCAPE) {
                stop();
            }
        }
    }


    public void stop(){
        timer.cancel();
    }

    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);
    }

    public void deleteAsteroid(Asteroid asteroid){
        board.asteroids.remove(asteroid);
    }

    public void deleteEnemy(Enemy enemy) {
        if(rand.nextInt(100) < 10){
            randomBonus(enemy.x + enemy.getWidth()/2, enemy.y + enemy.getHeight()/2);
        }
        board.enemies.remove(enemy);
    }

    public void deleteMissile(Missile missile){
        player.missiles.remove(missile);
    }

    public void deleteBonus(Bonus bonus) {board.bonuses.remove(bonus);}

}


