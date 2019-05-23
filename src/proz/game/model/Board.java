package proz.game.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Player player;

    private List<Asteroid> asteroids;
    private List<Enemy> enemies;
    private List<Bonus> bonuses;
    private List<EnemyMissile> enemyMissiles;

    public static final int PLAYER_SPAWN_X = 300;
    public static final int PLAYER_SPAWN_Y = 450;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        resetBoard();
    }

    public Player getPlayer() {
        return this.player;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Bonus> getBonuses() {
        return bonuses;
    }

    public List<EnemyMissile> getEnemyMissiles() {
        return enemyMissiles;
    }

    public void addAsteroid(Asteroid a) {
        asteroids.add(a);
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    public void addBonus(Bonus b) {
        bonuses.add(b);
    }

    public void addEnemyMissile(EnemyMissile em) {
        enemyMissiles.add(em);
    }

    public void removeAsteroid(Asteroid asteroid){
        asteroids.remove(asteroid);
    }

    public void removeEnemy(Enemy enemy){
        enemies.remove(enemy);
    }

    public void removeBonus(Bonus bonus){
        bonuses.remove(bonus);
    }

    public void removeEnemyMissile(EnemyMissile enemyMissile){
        enemyMissiles.remove(enemyMissile);
    }

    public void resetBoard() {
        player = new Player(PLAYER_SPAWN_X, PLAYER_SPAWN_Y);
        asteroids = new ArrayList<>();
        enemies = new ArrayList<>();
        bonuses = new ArrayList<>();
        enemyMissiles = new ArrayList<>();
    }
}
