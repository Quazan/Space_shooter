package proz.game.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import proz.game.model.*;
import proz.game.view.*;

import java.awt.event.KeyEvent;
import java.sql.Timestamp;

public class ControllerTest {

    private Controller controller;
    private Board board;
    private MockView view;
    private Player player;
    private Asteroid asteroid;
    private Enemy enemy;
    private Missile missile;
    private EnemyMissile enemyMissile;
    private Bonus bonus;

    private class MockView implements View {
        boolean updateViewCalled = false;

        @Override
        public void updateView() {
            updateViewCalled = true;
        }

        @Override
        public void setModel(Board board) {
        }

        @Override
        public void setController(Controller c) {
        }

        @Override
        public int getWidth() {
            return 800;
        }

        @Override
        public int getHeight() {
            return 600;
        }
    }

    @Before
    public void setUp() {
        board = new Board();
        controller = new Controller(board);
        view = new MockView();
        controller.setView(view);
        player = board.getPlayer();
    }

    @Test
    public void afterMovingToLeftPlayerPositionUpdates() {
        final int originalX = player.x;
        controller.moveLeft();
        int delta = player.x - originalX;
        assertEquals(-Controller.HORIZONTAL_MOVE_DELTA, delta);
    }

    @Test
    public void afterMovingToRightPlayerPositionUpdates() {
        final int originalX = player.x;
        controller.moveRight();
        int delta = player.x - originalX;
        assertEquals(Controller.HORIZONTAL_MOVE_DELTA, delta);
    }

    @Test
    public void playerDoesNotMoveToLeftWhenOnLeftBorder() {
        player.x = 0;
        final Integer originalX = player.x;

        controller.moveLeft();

        assertEquals(originalX, player.x);
    }

    @Test
    public void playerDoesNotMoveToRightWhenOnRightBorder() {
        player.x = view.getWidth() - player.getWidth();
        final Integer originalX = player.x;

        controller.moveRight();

        assertEquals(originalX, player.x);
    }

    @Test
    public void afterMovingUpPlayerPositionUpdates() {
        final int originalY = player.y;
        controller.moveUp();
        int delta = player.y - originalY;
        assertEquals(-Controller.VERTICAL_MOVE_DELTA, delta);
    }

    @Test
    public void afterMovingDownPlayerPositionUpdates() {
        final int originalY = player.y;
        controller.moveDown();
        int delta = player.y - originalY;
        assertEquals(Controller.VERTICAL_MOVE_DELTA, delta);
    }

    @Test
    public void playerDoesNotMoveUpWhenOnUpperBorder() {
        player.y = 0;
        final Integer originalY = player.y;

        controller.moveUp();

        assertEquals(originalY, player.y);
    }

    @Test
    public void playerDoesNotMoveDownWhenOnLowerBorder() {
        player.y = view.getHeight() - player.getHeight();
        final Integer originalY = player.y;

        controller.moveDown();

        assertEquals(originalY, player.y);
    }

    @Test
    public void afterFiringShotIsFired() {
        controller.fire();
        final int missileCount = 1;
        assertEquals(missileCount, player.getMissiles().size());
    }

    @Test
    public void afterCreatingAsteroidIsCreated() {
        controller.randomAsteroid();
        final int asteroidCount = 1;
        assertEquals(asteroidCount, board.getAsteroids().size());
    }

    @Test
    public void afterCreatingEnemyIsCreated() {
        controller.randomEnemy();
        final int enemyCount = 1;
        assertEquals(enemyCount, board.getEnemies().size());
    }

    @Test
    public void afterCreatingBonusIsCreated() {
        controller.randomBonus(0, 0);
        final int bonusCount = 1;
        assertEquals(bonusCount, board.getBonuses().size());
    }

    @Test
    public void afterFiringReloadIsOn() {
        controller.fire();
        assertTrue(player.isReloading());
    }

    @Test
    public void afterEnemyShotsIsMade() {
        controller.enemyShot(0, 0);
        final int enemyShotsCount = 1;
        assertEquals(enemyShotsCount, board.getEnemyMissiles().size());
    }

    @Test
    public void afterUpdatingAsteroidItMoves() {
        asteroid = new Asteroid(0, 0);
        final int originalY = asteroid.y;
        controller.updateAsteroid(asteroid);
        int delta = asteroid.y - originalY;
        assertEquals(Controller.ASTEROID_MOVE_DELTA, delta);
    }

    @Test
    public void afterUpdatingEnemyItMoves() {
        enemy = new Enemy(0, 0);
        final int originalY = enemy.y;
        controller.updateEnemy(enemy);
        int delta = enemy.y - originalY;
        assertEquals(Controller.ENEMY_MOVE_DELTA, delta);
    }

    @Test
    public void afterUpdatingPlayerMissileItMoves() {
        missile = new Missile(0, 0);
        final int originalY = missile.y;
        controller.updateMissile(missile);
        int delta = missile.y - originalY;
        assertEquals(-Controller.PLAYER_MISSILE_MOVE_DELTA, delta);
    }

    @Test
    public void afterUpdatingEnemyMissileItMoves() {
        enemyMissile = new EnemyMissile(0, 0);
        final int originalY = enemyMissile.y;
        controller.updateEnemyMissile(enemyMissile);
        int delta = enemyMissile.y - originalY;
        assertEquals(Controller.ENEMY_MISSILE_MOVE_DELTA, delta);
    }

    @Test
    public void afterUpdatingBonusItMoves() {
        bonus = new Bonus(0, 0, BonusType.power_UP);
        final int originalY = bonus.y;
        controller.updateBonus(bonus);
        int delta = bonus.y - originalY;
        assertEquals(Controller.BONUS_MOVE_DELTA, delta);
    }

    @Test
    public void afterAddingScorePlayerScoreIncreases() {
        final int originalScore = player.getScore();
        controller.addScore();
        int delta = player.getScore() - originalScore;

        assertEquals(Controller.SCORE_DELTA, delta);
    }

    @Test
    public void afterDeletingAsteroidIsDeleted() {
        asteroid = new Asteroid(0, 0);
        board.addAsteroid(asteroid);
        controller.deleteAsteroid(asteroid);

        assertEquals(0, board.getAsteroids().size());
    }

    @Test
    public void afterDeletingEnemyIsDeleted() {
        enemy = new Enemy(0, 0);
        board.addEnemy(enemy);
        controller.deleteEnemy(enemy);

        assertEquals(0, board.getEnemies().size());
    }

    @Test
    public void afterDeletingMissileIsDeleted() {
        missile = new Missile(0, 0);
        player.addMissile(missile);
        controller.deleteMissile(missile);

        assertEquals(0, player.getMissiles().size());
    }

    @Test
    public void afterDeletingEnemyMissileIsDeleted() {
        enemyMissile = new EnemyMissile(0, 0);
        board.addEnemyMissile(enemyMissile);
        controller.deleteEnemyMissile(enemyMissile);

        assertEquals(0, board.getEnemyMissiles().size());
    }

    @Test
    public void afterDeletingBonusIsDeleted() {
        bonus = new Bonus(0, 0, BonusType.shield);
        board.addBonus(bonus);
        controller.deleteBonus(bonus);

        assertEquals(0, board.getBonuses().size());
    }

    @Test
    public void afterAddingKeyToMapKeyIsAdded() {
        controller.addKey(KeyEvent.VK_UP, new Timestamp(System.currentTimeMillis()));

        assertEquals(1, controller.getPressedKeys().size());
    }

    @Test
    public void afterDeletingKeyFromMapKeyIsEmpty() {
        controller.addKey(KeyEvent.VK_UP, new Timestamp(System.currentTimeMillis()));
        controller.removeKey(KeyEvent.VK_UP);

        assertEquals(0, controller.getPressedKeys().size());
    }

    @Test
    public void generatedNumberIsLowerThanBound() {
        final int bound = 1;
        int generatedNumber = controller.generateNumber(bound);

        assertNotEquals(bound, generatedNumber);
    }

    @Test
    public void afterAddingKeyToMapPlayerMoves() {
        final int originalY = player.y;
        controller.addKey(KeyEvent.VK_UP, new Timestamp(System.currentTimeMillis()));
        controller.keyIterator();
        int delta = originalY - player.y;

        assertEquals(Controller.VERTICAL_MOVE_DELTA, delta);
    }

    @Test
    public void afterAddingTwoContraryKeysToMapPlayerMovesToTheLatestKey() {
        final int originalY = player.y;
        controller.addKey(KeyEvent.VK_UP, new Timestamp(System.currentTimeMillis()));
        controller.addKey(KeyEvent.VK_DOWN, new Timestamp(System.currentTimeMillis() + 10));
        controller.keyIterator();
        int delta = originalY - player.y;

        assertEquals(-Controller.VERTICAL_MOVE_DELTA, delta);
    }

    @Test
    public void afterAddingPlayerAndEnemyInTheSamePlacePlayerLosesLife() {
        int originalLifeCount = player.getLives();
        enemy = new Enemy(Board.PLAYER_SPAWN_X, Board.PLAYER_SPAWN_Y);
        board.addEnemy(enemy);
        controller.checkCollisions();

        int delta = originalLifeCount - player.getLives();
        assertEquals(1, delta);
    }

    @Test
    public void afterAddingPlayerAndEnemyInDifferentPlacesNothingHappens() {
        int originalLifeCount = player.getLives();
        enemy = new Enemy(Board.PLAYER_SPAWN_X + 200, Board.PLAYER_SPAWN_Y + 200);
        board.addEnemy(enemy);
        controller.checkCollisions();

        int delta = originalLifeCount - player.getLives();
        assertEquals(0, delta);
    }

    @Test
    public void afterAddingPlayerAndAsteroidInTheSamePlacePlayerLosesLife() {
        int originalLifeCount = player.getLives();
        asteroid = new Asteroid(Board.PLAYER_SPAWN_X, Board.PLAYER_SPAWN_Y);
        board.addAsteroid(asteroid);
        controller.checkCollisions();

        int delta = originalLifeCount - player.getLives();
        assertEquals(1, delta);
    }

    @Test
    public void afterAddingPlayerAndAsteroidInDifferentPlacesNothingHappens() {
        int originalLifeCount = player.getLives();
        asteroid = new Asteroid(Board.PLAYER_SPAWN_X + 200, Board.PLAYER_SPAWN_Y + 200);
        board.addAsteroid(asteroid);
        controller.checkCollisions();

        int delta = originalLifeCount - player.getLives();
        assertEquals(0, delta);
    }

    @Test
    public void afterAddingPlayerAndEnemyMissileInTheSamePlacePlayerLosesLife() {
        int originalLifeCount = player.getLives();
        enemyMissile = new EnemyMissile(Board.PLAYER_SPAWN_X, Board.PLAYER_SPAWN_Y);
        board.addEnemyMissile(enemyMissile);
        controller.checkCollisions();

        int delta = originalLifeCount - player.getLives();
        assertEquals(1, delta);
    }

    @Test
    public void afterAddingPlayerAndEnemyMissileInDifferentPlacesNothingHappens() {
        int originalLifeCount = player.getLives();
        enemyMissile = new EnemyMissile(Board.PLAYER_SPAWN_X + 200, Board.PLAYER_SPAWN_Y + 200);
        board.addEnemyMissile(enemyMissile);
        controller.checkCollisions();

        int delta = originalLifeCount - player.getLives();
        assertEquals(0, delta);
    }

    @Test
    public void afterAddingPlayerAndBonusInTheSamePlacePlayerGetsBonus() {
        bonus = new Bonus(Board.PLAYER_SPAWN_X, Board.PLAYER_SPAWN_Y, BonusType.shield);
        board.addBonus(bonus);
        controller.checkCollisions();

        assertTrue(player.isShielded());
    }

    @Test
    public void afterAddingPlayerAndBonusInDifferentPlacesNothingHappens() {
        bonus = new Bonus(Board.PLAYER_SPAWN_X + 200, Board.PLAYER_SPAWN_Y + 200, BonusType.shield);
        board.addBonus(bonus);
        controller.checkCollisions();

        assertFalse(player.isShielded());
    }

}

