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
    private Timer timer;
    private int X =  50;
    private int Y  = -100;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;
    private Random rand;
    public SwingView(){
        setSize(800, 600);
        addKeyListener(createKeYListener());
        setFocusable(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);
        rand = new Random();
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

    private class ScheduleTask extends TimerTask{
        @Override
        public void run(){
            Y += 2;
            if(Y > getHeight()){
                Y = -100;
            }
            controller.spawnAsteroid();
            repaint();
        }
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
        ImageIcon ii = new ImageIcon("assets\\PNG\\Meteors\\meteorBrown_med1.png");

        List<Asteroid> asteroids = board.getAsteroids();

        for (Asteroid asteroid: asteroids){
            g.drawImage(asteroid.getImage(), asteroid.x, asteroid.y, this);
            asteroid.y += 2;
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void paintMissiles(Graphics2D g){
        List<Missile> missiles = player.getMissiles();
        for (Missile missile : missiles){
            g.drawImage(missile.getImage(), missile.x, missile.y, this);
            if(missile.y < -20) {
                //missiles.remove(missile); generuje bład
            }
            else{
                missile.y -= 2;
            }
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
