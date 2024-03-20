package org.stage3.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.stage3.Game;
import org.stage3.model.Ball;
import org.stage3.model.Player;

import java.util.Random;

/**
 * The type Player controller.
 */
public class PlayerController {

    /**
     * The constant isRestarted.
     */
    public static boolean isRestarted = false;

    /**
     * Move player.
     *
     * @param scene the scene
     * @param p1    the p 1
     * @param p2    the p 2
     * @param ball  the ball
     */
    public static void controls(Scene scene, Player p1, Player p2, Ball ball) {
        double centerX = Game.WIDTH / 2;
        double centerY = Game.HEIGHT / 2;
        scene.setOnKeyPressed(keyEvent -> {

            // Player 1 controls
            if (keyEvent.getCode() == KeyCode.W) {
                p1.moveUp();
            }

            if (keyEvent.getCode() == KeyCode.S) {
                p1.moveDown();
            }

            // Player2 controls
            if (keyEvent.getCode() == KeyCode.UP) {
                p2.moveUp();
            }

            if (keyEvent.getCode() == KeyCode.DOWN) {
                p2.moveDown();
            }

            // Pause the game
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                if (Game.isPaused) {
                    System.out.println("Game Resumed");
                    Game.tl.play();
                } else {
                    System.out.println("Game Paused");
                    Game.tl.pause();
                }
                Game.isPaused = !Game.isPaused;
            }

            // Restart the game
            if (keyEvent.getCode() == KeyCode.R) {
                p1.setxPos(0);
                p1.setyPos(centerY);

                p2.setxPos(centerX);
                p2.setyPos(centerY);

                p1.setScore(0);
                p2.setScore(0);

                ball.move();
                // Randomize the ball's initial speed and direction
                Game.ballXSpeed = new Random().nextInt(3) == 0 ? 1 : -1;
                Game.ballYSpeed = new Random().nextInt(3) == 0 ? 1 : -1;

                ball.setXPos(centerX);
                ball.setXPos(centerY);

                isRestarted = true;
            } else {
                PlayerController.isRestarted = false;
            }
        });
    }

    /**
     * Paddle collision.
     *
     * @param ballXPos          the ball x pos
     * @param ballYPos          the ball y pos
     * @param playerOneXPos     the player one x pos
     * @param playerOneYPos     the player one y pos
     * @param playerTwoXPos     the player two x pos
     * @param playerTwoYPos     the player two y pos
     * @param ballSpeedIncrease the ball speed increase
     * @param PLAYER_WIDTH      the player width
     * @param PLAYER_HEIGHT     the player height
     * @param ball              the ball
     * @param BALL_R            the ball r
     */
    public static void PaddleCollision(double ballXPos, double ballYPos, double playerOneXPos, double playerOneYPos,
                                       double playerTwoXPos, double playerTwoYPos, double ballSpeedIncrease,
                                       double PLAYER_WIDTH, double PLAYER_HEIGHT, Ball ball, double BALL_R) {
        // Check collision with player one paddle
        if (ballXPos <= playerOneXPos + PLAYER_WIDTH && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT) {
            ball.reverseXSpeed();
            ball.reverseYSpeed();
            ball.adjustSpeed(ballSpeedIncrease);
        }

        // Check collision with player two paddle
        if (ballXPos + BALL_R >= playerTwoXPos && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) {
            ball.reverseXSpeed();
            ball.reverseYSpeed();
            ball.adjustSpeed(ballSpeedIncrease);
        }
    }

    /**
     * Ball bounds logic.
     *
     * @param ball    the ball
     * @param player1 the player 1
     * @param player2 the player 2
     */
    public static void BallBoundsLogic(Ball ball, Player player1, Player player2) {
        Game.ballXPos = Game.WIDTH / 2; // Reset the ball's x-position
        Game.ballYPos = Game.HEIGHT / 2; // Reset the ball's y-position

        // Ensure the ball stays within the canvas boundaries
        if (Game.ballYPos > Game.HEIGHT || Game.ballYPos < 0) Game.ballYSpeed *= -1;

        // Ensure player paddles stay within the bounds of the game window
        Game.playerOneYPos = Math.max(0, Math.min(player1.getyPos(), Game.HEIGHT - Game.PLAYER_HEIGHT));
        Game.playerTwoYPos = Math.max(0, Math.min(player2.getyPos(), Game.HEIGHT - Game.PLAYER_HEIGHT));

        // If player one misses the ball, player two scores a point
        if (ball.getXPos() < 0) {
            Game.scoreP2++;
            player2.setScore(Game.scoreP2);
            ball.setXPos(Game.WIDTH / 2);
            ball.setYPos(Game.HEIGHT / 2);
            player2.setLastTouched(true);
            Game.gameStarted = false;
        }

        // If player two misses the ball, player one scores a point
        if (ball.getXPos() > Game.WIDTH) {
            Game.scoreP1++;
            player1.setScore(Game.scoreP1);
            ball.setXPos(Game.WIDTH / 2);
            ball.setYPos(Game.HEIGHT / 2);
            player1.setLastTouched(true);
            Game.gameStarted = false;
        }
    }
}