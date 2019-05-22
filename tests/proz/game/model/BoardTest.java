package proz.game.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class BoardTest {
    Board board;
    Asteroid asteroid;
    Enemy enemy;
    EnemyMissile enemyMissile;
    Bonus bonus;

    @Before
    public void setUP() {
        board = new Board();
    }

    @Test
    public void constructedBoardHaveProperPlayerPosition() {
        final Integer positionX = 300;
        final Integer positionY = 450;
        assertEquals(positionX, board.getPlayer().x);
        assertEquals(positionY, board.getPlayer().y);
    }

    @Test
    public void afterAddingAsteroidIsAdded() {
        asteroid = new Asteroid(0, 0);
        board.addAsteroid(asteroid);
        final int asteroidCount = 1;
        assertEquals(asteroidCount, board.asteroids.size());
    }

    @Test
    public void afterAddingEnemyIsAdded() {
        enemy = new Enemy(0, 0);
        board.addEnemy(enemy);
        final int enemiesCount = 1;
        assertEquals(enemiesCount, board.enemies.size());
    }

    @Test
    public void afterAddingEnemyMissileIsAdded() {
        enemyMissile = new EnemyMissile(0, 0);
        board.addEnemyMissile(enemyMissile);
        final int enemyMissilesCount = 1;
        assertEquals(enemyMissilesCount, board.enemyMissiles.size());
    }

    @Test
    public void afterAddingBonusIsAdded() {
        bonus = new Bonus(0, 0, BonusType.power_UP);
        board.addBonus(bonus);
        final int bonusCount = 1;
        assertEquals(bonusCount, board.bonuses.size());
    }
}
