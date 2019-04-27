package proz.game.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.*;

import proz.game.controller.Controller;
import proz.game.model.Player;

public class SwingView extends JPanel implements View{
    //private static final long serialVersionUID = -7729510720848698723L;

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
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g1){
        Graphics2D g = (Graphics2D) g1;
        fillBackground(g);
        paintPlayer(g);
    }

    private void paintPlayer(Graphics2D g){
        g.setColor(Color.magenta);
        ImageIcon ii = new ImageIcon("assets\\PNG\\playerShip1_blue.png");
        g.drawImage(ii.getImage(), player.x, player.y, null);
        //g.fillRect(player.x, player.y, player.width, player.height);
    }

    private void fillBackground(Graphics2D g){
        //g.setColor(Color.white);
        ImageIcon ii = new ImageIcon("assets\\Backgrounds\\darkPurple.png");
        int h = ii.getIconHeight();
        int w = ii.getIconWidth();
        for (int y = 0; y <= getHeight(); y += h)
            for (int x = 0; x <= getWidth(); x += w){
                g.drawImage(ii.getImage(), x, y, this);
            }
        //g.drawImage(ii.getImage(), 0, 0, getWidth(), getHeight(), this);
        //g.fillRect(0, 0, getWidth(), getHeight());
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
