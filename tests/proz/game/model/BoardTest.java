package proz.game.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class BoardTest {
    private Board board;
    private Asteroid asteroid;
    private Enemy enemy;
    private EnemyMissile enemyMissile;
    private Bonus bonus;

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
        assertEquals(asteroidCount, board.getAsteroids().size());
    }

    @Test
    public void afterAddingAsteroidAndThenRemovingItThereIsNoMoreAsteroids() {
        asteroid = new Asteroid(0, 0);
        board.addAsteroid(asteroid);
        board.removeAsteroid(asteroid);
        final int asteroidCount = 0;
        assertEquals(asteroidCount, board.getAsteroids().size());
    }

    @Test
    public void afterAddingEnemyIsAdded() {
        enemy = new Enemy(0, 0);
        board.addEnemy(enemy);
        final int enemiesCount = 1;
        assertEquals(enemiesCount, board.getEnemies().size());
    }

    @Test
    public void afterAddingEnemyAndThenRemovingItThereIsNoMoreEnemies() {
        enemy = new Enemy(0, 0);
        board.addEnemy(enemy);
        board.removeEnemy(enemy);
        final int enemiesCount = 0;
        assertEquals(enemiesCount, board.getEnemies().size());
    }

    @Test
    public void afterAddingEnemyMissileIsAdded() {
        enemyMissile = new EnemyMissile(0, 0);
        board.addEnemyMissile(enemyMissile);
        final int enemyMissilesCount = 1;
        assertEquals(enemyMissilesCount, board.getEnemyMissiles().size());
    }

    @Test
    public void afterAddingEnemyMissileAndThenRemovingItThereIsNoMoreEnemyMissiles() {
        enemyMissile = new EnemyMissile(0, 0);
        board.addEnemyMissile(enemyMissile);
        board.removeEnemyMissile(enemyMissile);
        final int enemyMissilesCount = 0;
        assertEquals(enemyMissilesCount, board.getEnemyMissiles().size());
    }

    @Test
    public void afterAddingBonusIsAdded() {
        bonus = new Bonus(0, 0, BonusType.power_UP);
        board.addBonus(bonus);
        final int bonusCount = 1;
        assertEquals(bonusCount, board.getBonuses().size());
    }

    @Test
    public void afterAddingBonusThenRemovingItThereIsNoMoreBonuses() {
        bonus = new Bonus(0, 0, BonusType.power_UP);
        board.addBonus(bonus);
        board.removeBonus(bonus);
        final int bonusCount = 0;
        assertEquals(bonusCount, board.getBonuses().size());
    }
}
