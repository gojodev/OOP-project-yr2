package org.stage4.controller;

import org.stage4.model.Ball;
import org.stage4.model.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The type Movement test.
 */
public class movementTest {

    /**
     * Test collision.
     */
    @Test
    public void testCollision() {
        Player player1 = new Player();
        Player player2 = new Player();

        Ball ball = new Ball();

        double width = 550;
        double height = 550;

        Controller controller = new Controller(Controller.scene, player1, player2, ball, width, height);

        // No collision detected
        assertFalse(controller.checkPaddleCollision());
        assertFalse(controller.CheckBallCollision());

        // The ball touches player1
        ball.setXPos(player1.getxPos());
        ball.setXPos(player1.getyPos());

        player1.setxPos(ball.getXPos());
        player1.setyPos(ball.getYPos());

        assertTrue(controller.checkPaddleCollision());

        // The ball touches player2
        ball.setXPos(player2.getxPos());
        ball.setXPos(player2.getyPos());

        player2.setxPos(ball.getXPos());
        player2.setyPos(ball.getYPos());

        assertTrue(controller.checkPaddleCollision());

        // the ball is below the floor
        ball.setYPos(-1);
        assertFalse(controller.CheckBallCollision());

        // the ball is above the ceiling
        ball.setYPos(ball.getRadius()+height+1);
        assertFalse(controller.CheckBallCollision());
    }
}
