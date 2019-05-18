package proz.game.view;

import proz.game.controller.Controller;
import proz.game.model.*;

import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Timestamp;
import java.util.List;

public class SwingView extends JPanel implements View{

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
        paintBonuses(g);
        paintAsteroid(g);
        paintEnemy(g);
        paintPlayer(g);
        paintLives(g);
        paintScore(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void paintPlayer(Graphics2D g){
        if(player.isVisible()) {
            g.drawImage(player.getImage(), player.x, player.y, null);
            if(player.isShielded()){
                g.drawImage(player.getShieldImage(), player.x - 10, player.y - 15, null);
            }
        }
        else{
            controller.stop();
            paintGameOver(g);
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
                controller.start();
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
            if(asteroid.isVisible()){
                g.drawImage(asteroid.getImage(), asteroid.x, asteroid.y, this);
                controller.updateAsteroid(asteroid);
            }
            else {
                controller.deleteAsteroid(asteroid);
            }
        }
    }

    private void paintEnemy(Graphics2D g){
        List<Enemy> enemies = board.getEnemies();
        Enemy enemy;
        for (int i = 0; i < enemies.size(); i++){
            enemy = enemies.get(i);
            if(enemy.getVisible()){
                g.drawImage(enemy.getImage(), enemy.x, enemy.y, this);
                controller.updateEnemy(enemy);
            }
            else {
                controller.deleteEnemy(enemy);
            }
        }
    }

    private void paintMissiles(Graphics2D g){
        List<Missile> missiles = player.getMissiles();
        List<EnemyMissile> enemyMissiles = board.getEnemyMissiles();

        Missile missile;
        for (int i = 0; i < missiles.size(); i++){
            missile = missiles.get(i);
            if(missile.isVisible()){
                g.drawImage(missile.getImage(), missile.x, missile.y, this);
                controller.updateMissile(missile);
            }
            else{
                controller.deleteMissile(missile);
            }
        }

        EnemyMissile enemyMissile;
        for (int i = 0; i < enemyMissiles.size(); i++){
            enemyMissile = enemyMissiles.get(i);
            if(enemyMissile.isVisible()){
                g.drawImage(enemyMissile.getImage(), enemyMissile.x, enemyMissile.y, this);
                controller.updateEnemyMissile(enemyMissile);
            }
            else{
                controller.deleteEnemyMissile(enemyMissile);
            }
        }
    }

    private void paintBonuses(Graphics2D g){
        List<Bonus> bonuses = board.getBonuses();
        Bonus bonus;
        for (int i = 0; i < bonuses.size(); i++){
            bonus = bonuses.get(i);
            if(bonus.isVisible()){
                g.drawImage(bonus.getImage(), bonus.x, bonus.y, this);
                controller.updateBonus(bonus);
            }
            else{
                controller.deleteBonus(bonus);
            }
        }
    }

    private void paintLives(Graphics2D g){
        ImageIcon ii = new ImageIcon("assets\\PNG\\UI\\playerLife2_red.png");
        Image liveImage = ii.getImage();
        int x = ii.getIconWidth();
        //int y = ii.getIconHeight();
        int off = 20;
        for(int i = 0; i < player.lives; i++){
            g.drawImage(liveImage, off, getHeight() - 100, this);
            off += x + 10;
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