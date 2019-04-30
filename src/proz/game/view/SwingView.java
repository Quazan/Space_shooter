package proz.game.view;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;

import javax.swing.*;

import proz.game.controller.Controller;
import proz.game.model.*;

public class SwingView extends JPanel implements View{
    //private static final long serialVersionUID = -7729510720848698723L;

    private Board board;
    private Player player;
    private Controller controller;
    public SwingView(){
        setSize(800, 600);
        addKeyListener(createKeYListener());
        setFocusable(true);
    }

    private KeyListener createKeYListener(){
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    controller.moveLeft();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    controller.moveRight();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    controller.moveUp();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    controller.moveDown();
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    controller.fire();
                }
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g1){
        Graphics2D g = (Graphics2D) g1;
        fillBackground(g);
        paintPlayer(g);
        paintAsteroid(g);
        paintMissiles(g);
    }

    private void paintPlayer(Graphics2D g){
        g.drawImage(player.getImage(), player.x, player.y, null);
    }

    private void paintAsteroid(Graphics2D g){
        List<Asteroid> asteroids = board.getAsteroids();
        Asteroid asteroid;
        for (int i = 0; i < asteroids.size(); i++){
            asteroid = asteroids.get(i);
            g.drawImage(asteroid.getImage(), asteroid.x, asteroid.y, this);
            controller.updateAsteroid(asteroid);
        }
    }

    private void paintMissiles(Graphics2D g){
        List<Missile> missiles = player.getMissiles();
        Missile missile;
        for (int i = 0; i < missiles.size(); i++){
            missile = missiles.get(i);
            g.drawImage(missile.getImage(), missile.x, missile.y, this);
            controller.updateMissile(missile);
        }
    }

    private void fillBackground(Graphics2D g){
        ImageIcon ii = new ImageIcon("assets\\Backgrounds\\darkPurple.png");
        int h = ii.getIconHeight();
        int w = ii.getIconWidth();
        for (int y = 0; y <= getHeight(); y += h)
            for (int x = 0; x <= getWidth(); x += w){
                g.drawImage(ii.getImage(), x, y, this);
            }
    }

    @Override
    public void updateView(){
        repaint();
    }

    @Override
    public void setModel(Board board){
        this.board = board;
        this.player = board.getPlayer();
    }

    @Override
    public void setController(Controller c){
        this.controller = c;
    }

}
