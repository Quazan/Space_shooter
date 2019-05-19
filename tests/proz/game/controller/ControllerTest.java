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
        ball = new Ball();
        controller = new Controller(ball);
        view = new MockView();
        controller.setView(view);
    }

    @Test
    public void afterMovingToLeftBallPositionUpdates() {
        final int originalX = ball.x;
        controller.moveLeft();
        int delta = ball.x - originalX;
        assertEquals(-Controller.HORIZONTAL_MOVE_DELTA, delta);
    }

    @Test
    public void afterMovingToRightBallPositionUpdates() {
        final int originalX = ball.x;
        controller.moveRight();
        int delta = ball.x - originalX;
        assertEquals(Controller.HORIZONTAL_MOVE_DELTA, delta);
    }

    @Test
    public void ballDoesNotMoveToLeftWhenOnLeftBorder() {
        ball.x = 0;
        final int originalX = ball.x;

        controller.moveLeft();

        assertEquals(originalX, ball.x);
    }

    @Test
    public void ballDoesNotMoveToRightWhenOnRightBorder() {
        ball.x = view.getWidth() - ball.getWidth();
        final int originalX = ball.x;

        controller.moveRight();

        assertEquals(originalX, ball.x);
    }

    @Test
    public void afterMovingViewIsRefreshed() {
        controller.moveLeft();
        assertTrue(view.updateViewCalled);
    }

}

