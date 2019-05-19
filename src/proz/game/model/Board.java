package proz.game.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Player player;

    public List<Asteroid> asteroids;
    public List<Enemy> enemies;
    public List<Bonus> bonuses;
    public List<EnemyMissile> enemyMissiles;

    private final int PLAYER_SPAWN_X = 300;
    private final int PLAYER_SPAWN_Y = 450;

    public Board(){
        initBoard();
    }

    private void initBoard(){
        player = new Player(PLAYER_SPAWN_X, PLAYER_SPAWN_Y);
        asteroids = new ArrayList<>();
        enemies = new ArrayList<>();
        bonuses = new ArrayList<>();
        enemyMissiles = new ArrayList<>();
    }

    public Player getPlayer(){
        return this.player;
    }

    public List<Asteroid> getAsteroids(){
        return asteroids;
    }

    public List<Enemy> getEnemies() { return enemies;}

    public List<Bonus> getBonuses() {return  bonuses;}

    public List<EnemyMissile> getEnemyMissiles() {return enemyMissiles;}

    public void addAsteroid(Asteroid a){
        asteroids.add(a);
    }

    public void addEnemy(Enemy e) { enemies.add(e);}

    public void addBonus(Bonus b) {bonuses.add(b);}

    public void addEnemyMissile(EnemyMissile em) { enemyMissiles.add(em);}
}
