package proz.game.controller;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import proz.game.model.*;
import proz.game.view.*;
public class ControllerTest {

    Controller controller;
    Board board;
    MockView view;
    Player player;

    private class MockView implements View {
        public boolean updateViewCalled = false;

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
        public int getHeight() {return 600;}
    };

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
    public void afterFiringShotIsFired(){
        controller.fire();
        final int missileCount = 1;
        assertEquals(missileCount, player.getMissiles().size());
    }

    @Test
    public void afterCreatingAsteroidIsCreated(){
        controller.randomAsteroid();
        final int asteroidCount = 1;
        assertEquals(asteroidCount, board.getAsteroids().size());
    }

    @Test
    public void afterCreatingEnemyIsCreated(){
        controller.randomEnemy();
        final int enemyCount = 1;
        assertEquals(enemyCount, board.getEnemies().size());
    }

    @Test
    public void afterCreatingBonusIsCreated(){
        controller.randomBonus(0, 0);
        final int bonusCount = 1;
        assertEquals(bonusCount, board.getBonuses().size());
    }

    @Test
    public void afterFiringReloadIsOn(){
        controller.fire();
        assertTrue(player.isReloading());
    }

    @Test
    public void afterEnemyShotsIsMade(){
        controller.enemyShot(0 ,0);
        final int enemyShotsCount = 1;
        assertEquals(enemyShotsCount, board.getEnemyMissiles().size());
    }
}

