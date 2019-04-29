package proz.game.model;

import  proz.game.model.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    /*public int x = 300;
    public int y = 300;

    //lista pocisków
    private List<Missile> missiles = new ArrayList<>(); //to nie jest do konca dobre

    private ImageIcon imageIcon = new ImageIcon("assets\\PNG\\playerShip1_blue.png");  //trzeba opakowac w funkcje
    private int width = imageIcon.getIconWidth();                                                  //to też
    private int height = imageIcon.getIconHeight();                                                 //i to

    public int getWidth() {
        return width;
    }
    public int getHeight(){
        return height;
    }
    public Image getImage(){return imageIcon.getImage();}
    public List<Missile> getMissiles() {return missiles;}
    public void addMissile(Missile m){
        missiles.add(m);
    }
    */
    private Player player;
    List<Asteroid> asteroids;
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
