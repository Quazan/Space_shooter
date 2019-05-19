package proz.game.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Player player;

    public List<Asteroid> asteroids;
    public List<Enemy> enemies;
    public List<Bonus> bonuses;
    public List<EnemyMissile> enemyMissiles;

    public Board(){
        initBoard();
    }

    private void initBoard(){
        int playerSpawnY = 450;
        int playerSpawnX = 300;
        player = new Player(playerSpawnX, playerSpawnY);
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
