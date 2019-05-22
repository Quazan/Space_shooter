package proz.game.view;

import proz.game.controller.Controller;
import proz.game.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Timestamp;
import java.util.List;

public class SwingView extends JPanel implements View {

    private Board board;
    private Player player;
    private Controller controller;
    private Image imageBackground;
    private boolean pause = false;
    private boolean endGame = false;

    public SwingView() {
        setSize(800, 600);
        addKeyListener(createKeYListener());
        setFocusable(true);
        ImageIcon iib = new ImageIcon("assets\\Backgrounds\\darkPurple.png");
        imageBackground = iib.getImage();
    }

    private KeyListener createKeYListener() {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Integer keyCode = e.getKeyCode();
                controller.removeKey(keyCode);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Integer keyCode = e.getKeyCode();
                controller.addKey(keyCode, timestamp);
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        fillBackground(g);

        if (player.isVisible()) {
            paintMissiles(g);
            paintBonuses(g);
            paintAsteroid(g);
            paintEnemy(g);
            paintPlayer(g);
            paintHUD(g);
            paintScore(g);
        } else {
            controller.stop();
            paintGameOver(g);
        }


        if (controller.isPaused()) {
            printPauseMenu(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void paintPlayer(Graphics2D g) {
        g.drawImage(player.getImage(), player.x, player.y, this);
        if (player.isShielded()) {
            g.drawImage(player.getShieldImage(), player.x - 10, player.y - 15, this);
        }

        ImageIcon ii;
        switch (player.lives) {
            case 2:
                ii = new ImageIcon("assets\\PNG\\Damage\\playerShip2_damage1.png");
                g.drawImage(ii.getImage(), player.x, player.y, this);
                break;
            case 1:
                ii = new ImageIcon("assets\\PNG\\Damage\\playerShip2_damage2.png");
                g.drawImage(ii.getImage(), player.x, player.y, this);
                break;
            default:
                break;
        }
    }

    private void resetGame() {
        board.resetBoard();
        setModel(board);
        controller.resetController(board);
    }

    private void paintGameOver(Graphics2D g) {
        if(!endGame){
            endGame = true;
            JButton button = new JButton("Play again");
            button.setBounds(getWidth()/2 - 150, 3*getHeight()/4, 300, 50);

            SwingView view = this;
            button.addActionListener(e -> {
                endGame = false;
                view.remove(button);
                resetGame();
            });

            this.add(button);
        }

        ImageIcon ii = new ImageIcon("assets\\PNG\\game_over_1.png");
        g.drawImage(ii.getImage(), getWidth()/2-ii.getIconWidth()/2, 0, this);

        paintScore(g);
    }

    private void printPauseMenu(Graphics2D g) {
        if(!pause){
            pause = true;
            JButton but = new JButton("Continue");
            but.setBounds(getWidth() / 2 - 150, 3 * getHeight() / 4, 300, 50);
            SwingView view = this;
            but.addActionListener(e -> {
                pause = false;
                view.remove(but);
                controller.start();
            });

            this.add(but);
        }

        ImageIcon ii = new ImageIcon("assets\\PNG\\UI\\PauseButton.png");
        g.drawImage(ii.getImage(), getWidth()/2-ii.getIconWidth()/2, 0, this);
    }

    private void paintScore(Graphics2D g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("Score: " + player.score.toString(), 0, 20);
    }

    private void paintAsteroid(Graphics2D g) {
        List<Asteroid> asteroids = board.getAsteroids();
        Asteroid asteroid;
        for (int i = 0; i < asteroids.size(); i++) {
            asteroid = asteroids.get(i);
            if (asteroid.isVisible()) {
                g.drawImage(asteroid.getImage(), asteroid.x, asteroid.y, this);
                if (!controller.updateAsteroid(asteroid)) {
                    i--;
                }
            } else {
                controller.deleteAsteroid(asteroid);
                i--;
            }
        }
    }

    private void paintEnemy(Graphics2D g) {
        List<Enemy> enemies = board.getEnemies();
        Enemy enemy;
        for (int i = 0; i < enemies.size(); i++) {
            enemy = enemies.get(i);
            if (enemy.getVisible()) {
                g.drawImage(enemy.getImage(), enemy.x, enemy.y, this);
                if (!controller.updateEnemy(enemy)) {
                    i--;
                }
            } else {
                controller.deleteEnemy(enemy);
                i--;
            }
        }
    }

    private void paintMissiles(Graphics2D g) {
        List<Missile> missiles = player.getMissiles();
        List<EnemyMissile> enemyMissiles = board.getEnemyMissiles();

        Missile missile;
        for (int i = 0; i < missiles.size(); i++) {
            missile = missiles.get(i);
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.x, missile.y, this);
                if (!controller.updateMissile(missile)) {
                    i--;
                }
            } else {
                controller.deleteMissile(missile);
                i--;
            }
        }

        EnemyMissile enemyMissile;
        for (int i = 0; i < enemyMissiles.size(); i++) {
            enemyMissile = enemyMissiles.get(i);
            if (enemyMissile.isVisible()) {
                g.drawImage(enemyMissile.getImage(), enemyMissile.x, enemyMissile.y, this);
                if (!controller.updateEnemyMissile(enemyMissile)) {
                    i--;
                }
            } else {
                controller.deleteEnemyMissile(enemyMissile);
                i--;
            }
        }
    }

    private void paintBonuses(Graphics2D g) {
        List<Bonus> bonuses = board.getBonuses();
        Bonus bonus;
        for (int i = 0; i < bonuses.size(); i++) {
            bonus = bonuses.get(i);
            if (bonus.isVisible()) {
                g.drawImage(bonus.getImage(), bonus.x, bonus.y, this);
                if (!controller.updateBonus(bonus)) {
                    i--;
                }
            } else {
                controller.deleteBonus(bonus);
                i--;
            }
        }
    }

    private void paintHUD(Graphics2D g) {
        ImageIcon ii = new ImageIcon("assets\\PNG\\UI\\playerLife2_red.png");
        
        int x = ii.getIconWidth();
        int off = 20;
        for (int i = 0; i < player.lives; i++) {
            g.drawImage(ii.getImage(), off, getHeight() - 50, this);
            off += x + 10;
        }

        if (player.isShielded()) {
            ii = new ImageIcon("assets\\PNG\\Power-ups\\shield_silver.png");
            g.drawImage(ii.getImage(), 160, getHeight() - 50, this);
        }

        if (player.isPoweredUp()) {
            ii = new ImageIcon("assets\\PNG\\Power-ups\\bold_silver.png");
            g.drawImage(ii.getImage(), 200, getHeight() - 50, this);
        }

    }

    private void fillBackground(Graphics2D g) {
        int h = imageBackground.getHeight(this);
        int w = imageBackground.getWidth(this);
        for (int y = 0; y <= getHeight(); y += h)
            for (int x = 0; x <= getWidth(); x += w) {
                g.drawImage(imageBackground, x, y, this);
            }
    }

    @Override
    public void updateView() {
        repaint();
    }

    @Override
    public void setModel(Board board) {
        this.board = board;
        this.player = board.getPlayer();
    }

    @Override
    public void setController(Controller c) {
        this.controller = c;
    }

}