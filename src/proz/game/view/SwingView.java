package proz.game.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.TimerTask;

import javax.swing.*;

import proz.game.controller.Controller;
import proz.game.model.Missile;
import proz.game.model.Player;

public class SwingView extends JPanel implements View{
    //private static final long serialVersionUID = -7729510720848698723L;

    private Player player;
    private Controller controller;
    private Timer timer;
    private int X =  50;
    private int Y  = -100;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;
    public SwingView(){
        setSize(800, 600);
        addKeyListener(createKeYListener());
        setFocusable(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);
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
        Toolkit.getDefaultToolkit().sync();
    }

    private void paintPlayer(Graphics2D g){
        g.drawImage(player.getImage(), player.x, player.y, null);
    }

    private void paintAsteroid(Graphics2D g){
        ImageIcon ii = new ImageIcon("assets\\PNG\\Meteors\\meteorBrown_big1.png");
        g.drawImage(ii.getImage(), X, Y, null);
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
    public void setModel(Player player){
        this.player = player;
    }

    @Override
    public void setController(Controller c){
        this.controller = c;
    }

}
