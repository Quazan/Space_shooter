package proz.game.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Player player;
    public List<Asteroid> asteroids;
    private void initBoard(){
        int playerSpawnY = 300;
        int playerSpawnX = 300;
        player = new Player(playerSpawnX, playerSpawnY);
        asteroids = new ArrayList<>();
    }

    public Player getPlayer(){
        return this.player;
    }

    public List<Asteroid> getAsteroids(){
        return asteroids;
    }

    public Board(){
        initBoard();
    }

    public void addAsteroid(Asteroid a){
        asteroids.add(a);
    }
}
