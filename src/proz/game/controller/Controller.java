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
    private Boolean pause;
    private Random rand;
    public HashMap<Integer, Timestamp> pressedKeys;

    static final int HORIZONTAL_MOVE_DELTA = 10;
    static final int VERTICAL_MOVE_DELTA = 10;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;
    private final int RELOAD_TIME = 200;
    final int ENEMY_MOVE_DELTA = 2;
    final int ASTEROID_MOVE_DELTA = 2;
    final int PLAYER_MISSILE_MOVE_DELTA = 8;
    final int ENEMY_MISSILE_MOVE_DELTA = 6;
    final int BONUS_MOVE_DELTA = 4;
    private final int START_Y_FOR_OBJECTS = -60;
    private final int LAST_Y = 30;
    final int SCORE_DELTA = 100;
    private final int POWER_UP_TIME = 5000;

    public Controller(Board b) {
        resetController(b);
    }

    public void resetController(Board b) {
        board = b;
        player = board.getPlayer();
        pause = false;
        rand = new Random();

        timer = new Timer();
        buffTimer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);

        pressedKeys = new HashMap<>();
    }

    void moveLeft() {
        player.x -= HORIZONTAL_MOVE_DELTA;
        checkLeftBorder();
    }

    void moveRight() {
        player.x += HORIZONTAL_MOVE_DELTA;
        checkRightBorder();
    }

    void moveUp() {
        player.y -= VERTICAL_MOVE_DELTA;
        checkUpperBorder();
    }

    void moveDown() {
        player.y += VERTICAL_MOVE_DELTA;
        checkDownBorder();
    }

    void fire() {

        if (checkReload()) {
            return;
        }

        int x = player.x + player.getWidth() / 2 - 4;
        int y = player.y;

        Missile m = new Missile(x, y);
        player.addMissile(m);

        if (player.isPoweredUp()) {
            x -= 30;
            y += 15;
            m = new Missile(x, y);
            player.addMissile(m);

            x += 60;
            m = new Missile(x, y);
            player.addMissile(m);
        }

        player.setReload(true);
        timer.schedule(new Reload(), RELOAD_TIME);
    }

    void randomAsteroid() {
        int x = generateNumber(view.getWidth() - 125);
        x += 30;
        Asteroid a = new Asteroid(x, START_Y_FOR_OBJECTS);

        if (checkSpawnCollision(a.getBounds())) {
            board.addAsteroid(a);
        }
    }

    void randomEnemy() {
        int x = generateNumber(view.getWidth() - 125);
        x += 30;
        Enemy enemy = new Enemy(x, START_Y_FOR_OBJECTS);
        if (checkSpawnCollision(enemy.getBounds())) {
            board.addEnemy(enemy);
        }
    }

    void randomBonus(Integer x, Integer y) {
        Bonus bonus;
        if (generateNumber(10) <= 4) {
            bonus = new Bonus(x, y, BonusType.shield);
        } else {
            bonus = new Bonus(x, y, BonusType.power_UP);
        }

        board.addBonus(bonus);
    }

    private Boolean checkSpawnCollision(Rectangle bounds) {
        if (bounds == null) {
            return false;
        }

        for (Asteroid asteroid : board.getAsteroids()) {
            Rectangle asteroidBounds = asteroid.getBounds();
            if (bounds.intersects(asteroidBounds)) {
                return false;
            }
        }

        for (Enemy enemy : board.getEnemies()) {
            Rectangle enemyBounds = enemy.getBounds();
            if (bounds.intersects(enemyBounds)) {
                return false;
            }
        }

        return true;
    }

    private void resetReloadTimer() {
        buffTimer.cancel();
        buffTimer = new Timer();
    }

    private void checkPlayerBonusCollision(Rectangle playerBounds) {
        for (Bonus bonus : board.getBonuses()) {
            Rectangle bonusBounds = bonus.getBounds();

            if (playerBounds.intersects(bonusBounds)) {
                if (bonus.getType().equals(BonusType.power_UP)) {
                    if (player.isPoweredUp()) {
                        resetReloadTimer();
                    }

                    buffTimer.schedule(new PowerOff(), POWER_UP_TIME);
                }
                player.setBonus(bonus);
                bonus.setVisible(false);
            }
        }
    }

    private void checkPlayerEnemyMissileCollision(Rectangle playerBounds) {
        for (EnemyMissile em : board.getEnemyMissiles()) {
            Rectangle missileBounds = em.getBounds();

            if (playerBounds.intersects(missileBounds)) {
                player.takeDamage();
                em.setVisible(false);
            }
        }
    }

    private void checkAsteroidsCollision(Rectangle playerBounds) {
        for (Asteroid asteroid : board.getAsteroids()) {
            Rectangle asteroidBounds = asteroid.getBounds();

            if (playerBounds.intersects(asteroidBounds)) {
                player.takeDamage();
                asteroid.setVisible(false);
            }

            for (Missile missile : player.getMissiles()) {
                Rectangle missileBounds = missile.getBounds();

                if (missileBounds.intersects(asteroidBounds)) {
                    asteroid.takeDamage();
                    addScore();
                    missile.setVisible(false);
                }
            }

            for (EnemyMissile em : board.getEnemyMissiles()) {
                Rectangle missileBounds = em.getBounds();

                if (missileBounds.intersects(asteroidBounds)) {
                    asteroid.takeDamage();
                    em.setVisible(false);
                }
            }
        }
    }

    private void checkEnemyCollision(Rectangle playerBounds) {
        for (Enemy enemy : board.getEnemies()) {
            Rectangle enemyBounds = enemy.getBounds();

            if (playerBounds.intersects(enemyBounds)) {
                player.takeDamage();
                enemy.setVisible(false);
            }

            for (Missile missile : player.getMissiles()) {
                Rectangle missileBounds = missile.getBounds();

                if (missileBounds.intersects(enemyBounds)) {
                    enemy.takeDamage();
                    addScore();
                    missile.setVisible(false);
                }
            }
        }
    }

    private void checkCollisions() {
        Rectangle playerBounds;

        if (player.isShielded()) {
            playerBounds = player.getShieldBounds();
        } else {
            playerBounds = player.getBounds();
        }

        checkPlayerBonusCollision(playerBounds);

        checkPlayerEnemyMissileCollision(playerBounds);

        checkAsteroidsCollision(playerBounds);

        checkEnemyCollision(playerBounds);
    }

    private boolean checkReload() {
        return player.isReloading();
    }

    private void checkLeftBorder() {
        if (player.x < 0) {
            player.x = 0;
        }
    }

    private void checkRightBorder() {
        final int lastPossibleX = view.getWidth() - player.getWidth();
        if (player.x >= lastPossibleX) {
            player.x = lastPossibleX;
        }
    }

    private void checkUpperBorder() {
        if (player.y < 0) {
            player.y = 0;
        }
    }

    private void checkDownBorder() {
        final int lastPossibleY = view.getHeight() - player.getHeight();
        if (player.y >= lastPossibleY) {
            player.y = lastPossibleY;
        }
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            int chance = generateNumber(100);
            if (chance < 5) randomAsteroid();
            if (chance > 95) randomEnemy();

            try {
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
        public void run() {
            player.setReload(false);
        }
    }

    private class PowerOff extends TimerTask {
        @Override
        public void run() {
            player.setPowerUp(false);
        }
    }

    public boolean updateAsteroid(Asteroid asteroid) {
        if (asteroid.y > view.getHeight() + LAST_Y || !asteroid.isVisible()) {
            deleteAsteroid(asteroid);
            return false;
        }

        asteroid.y += ASTEROID_MOVE_DELTA;
        return true;

    }

    public boolean updateEnemy(Enemy enemy) {
        if (enemy.y > view.getHeight() + LAST_Y || !enemy.getVisible()) {
            deleteEnemy(enemy);
            return false;
        }

        if (generateNumber(2500) < 10) {
            int shotPositionX = enemy.x + enemy.getWidth() / 2 - 5;
            int shotPositionY = enemy.y + enemy.getHeight() / 2;
            enemyShot(shotPositionX, shotPositionY);
        }
        enemy.y += ENEMY_MOVE_DELTA;
        return true;

    }

    public boolean updateMissile(Missile missile) {
        if (missile.y < START_Y_FOR_OBJECTS || !missile.isVisible()) {
            deleteMissile(missile);
            return false;
        }

        missile.y -= PLAYER_MISSILE_MOVE_DELTA;
        return true;
    }

    public boolean updateEnemyMissile(EnemyMissile enemyMissile) {
        if (enemyMissile.y < START_Y_FOR_OBJECTS || !enemyMissile.isVisible()) {
            deleteEnemyMissile(enemyMissile);
            return false;
        }

        enemyMissile.y += ENEMY_MISSILE_MOVE_DELTA;
        return true;

    }

    public boolean updateBonus(Bonus bonus) {
        if (bonus.y > view.getHeight() + LAST_Y || !bonus.isVisible()) {
            deleteBonus(bonus);
            return false;
        }

        bonus.y += BONUS_MOVE_DELTA;
        return true;
    }

    private void keyIterator() {
        for (Integer keyCode : pressedKeys.keySet()) {
            if (keyCode == KeyEvent.VK_LEFT) {
                if (pressedKeys.containsKey(KeyEvent.VK_RIGHT)) {
                    if (pressedKeys.get(keyCode).after(pressedKeys.get(KeyEvent.VK_RIGHT))) {
                        moveLeft();
                    }
                } else {
                    moveLeft();
                }
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                if (pressedKeys.containsKey(KeyEvent.VK_LEFT)) {
                    if (pressedKeys.get(keyCode).after(pressedKeys.get(KeyEvent.VK_LEFT))) {
                        moveRight();
                    }
                } else {
                    moveRight();
                }
            } else if (keyCode == KeyEvent.VK_UP) {
                if (pressedKeys.containsKey(KeyEvent.VK_DOWN)) {
                    if (pressedKeys.get(keyCode).after(pressedKeys.get(KeyEvent.VK_DOWN))) {
                        moveUp();
                    }
                } else {
                    moveUp();
                }
            } else if (keyCode == KeyEvent.VK_DOWN) {
                if (pressedKeys.containsKey(KeyEvent.VK_UP)) {
                    if (pressedKeys.get(keyCode).after(pressedKeys.get(KeyEvent.VK_UP))) {
                        moveDown();
                    }
                } else {
                    moveDown();
                }
            } else if (keyCode == KeyEvent.VK_SPACE) {
                fire();
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                pause();
            }
        }
    }

    public void stop() {
        timer.cancel();
    }

    private void pause() {
        setPause(true);
        timer.cancel();
    }

    private void setPause(Boolean b) {
        pause = b;
    }

    public Boolean isPaused() {
        return pause;
    }

    public void start() {
        setPause(false);
        timer = new Timer();
        player.setReload(false);
        pressedKeys = new HashMap<>();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);
    }

    void enemyShot(Integer x, Integer y) {
        EnemyMissile em = new EnemyMissile(x, y);
        board.addEnemyMissile(em);
    }

    public void setView(View v) {
        this.view = v;
    }

    void addScore() {
        player.score += SCORE_DELTA;
    }

    private Integer generateNumber(Integer bound) {
        return rand.nextInt(bound);
    }

    public void deleteAsteroid(Asteroid asteroid) {
        board.asteroids.remove(asteroid);
    }

    public void deleteEnemy(Enemy enemy) {
        if (generateNumber(100) < 10) {
            int startBonusX = enemy.x + enemy.getWidth() / 2;
            int startBonusY = enemy.y + enemy.getHeight() / 2;

            randomBonus(startBonusX, startBonusY);
        }
        board.enemies.remove(enemy);
    }

    public void deleteMissile(Missile missile) {
        player.missiles.remove(missile);
    }

    public void deleteBonus(Bonus bonus) {
        board.bonuses.remove(bonus);
    }

    public void deleteEnemyMissile(EnemyMissile em) {
        board.enemyMissiles.remove(em);
    }

    public void addKey(Integer keyCode, Timestamp timestamp) {
        pressedKeys.put(keyCode, timestamp);
    }

    public void removeKey(Integer keyCode) {
        pressedKeys.remove(keyCode);
    }
}


