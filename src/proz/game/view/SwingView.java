package proz.game.view;

import proz.game.controller.Controller;
import proz.game.model.Asteroid;
import proz.game.model.Board;
import proz.game.model.Missile;
import proz.game.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Timestamp;
import java.util.List;

public class SwingView extends JPanel implements View{
    //private static final long serialVersionUID = -7729510720848698723L;

    private Board board;
    private Player player;
    private Controller controller;
    private Image imageBackground;

    public SwingView(){
        setSize(800, 600);
        addKeyListener(createKeYListener());
        setFocusable(true);
        ImageIcon iib = new ImageIcon("assets\\Backgrounds\\darkPurple.png");
        imageBackground = iib.getImage();
    }

    private KeyListener createKeYListener(){
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Integer keyCode = e.getKeyCode();
                controller.pressedKeys.remove(keyCode);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Integer keyCode = e.getKeyCode();
                controller.pressedKeys.put(keyCode, timestamp);
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g1){
        //trzeba trochę ifowania w zależności od stanu controllera
        //pauza
        //game over
        Graphics2D g = (Graphics2D) g1;
        fillBackground(g);
        paintMissiles(g);
        paintAsteroid(g);
        paintPlayer(g);
        paintScore(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void paintPlayer(Graphics2D g){
        if(player.getVisible()) {
            g.drawImage(player.getImage(), player.x, player.y, null);
        }
        else{
            paintGameOver(g);
            controller.stop();
            //String s = JOptionPane.showInputDialog(null, "What's your name?", null);
        }
    }

    private void paintGameOver(Graphics2D g){
        /*
        ImageIcon ij = new ImageIcon("assets\\PNG\\UI\\buttonBlue.png");
        JButton but = new JButton("Hello", ij);
        but.setBounds(100, 100, ij.getIconWidth(), ij.getIconHeight());
        SwingView view = this;
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.remove(but);
                fillBackground(g);
            }
        });
        this.add(but);
        this.setVisible(true);

         */

        fillBackground(g);
        ImageIcon ii = new ImageIcon("assets\\PNG\\game_over_1.png");
        g.drawImage(ii.getImage(), 80, 0 ,null);
        paintScore(g);
    }


    private void paintScore(Graphics2D g){
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString( "Score: " + player.score.toString(), 0, 20);
    }

    private void paintAsteroid(Graphics2D g){
        List<Asteroid> asteroids = board.getAsteroids();
        Asteroid asteroid;
        for (int i = 0; i < asteroids.size(); i++){
            asteroid = asteroids.get(i);
            if(asteroid.getVisible()){
                g.drawImage(asteroid.getImage(), asteroid.x, asteroid.y, this);
                controller.updateAsteroid(asteroid);
            }
            else {
                controller.deleteAsteroid(asteroid);
            }
        }
    }

    private void paintMissiles(Graphics2D g){
        List<Missile> missiles = player.getMissiles();
        Missile missile;
        for (int i = 0; i < missiles.size(); i++){
            missile = missiles.get(i);
            if(missile.getVisible()){
                g.drawImage(missile.getImage(), missile.x, missile.y, this);
                controller.updateMissile(missile);
            }
            else{
                controller.deleteMissile(missile);
            }

        }
    }

    private void fillBackground(Graphics2D g){
        //ImageIcon ii = new ImageIcon("assets\\Backgrounds\\darkPurple.png");
        int h = imageBackground.getHeight(null);
        int w = imageBackground.getWidth(null);
        for (int y = 0; y <= getHeight(); y += h)
            for (int x = 0; x <= getWidth(); x += w){
                g.drawImage(imageBackground, x, y, this);
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
