package org.stage3.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.stage3.Game;
import org.stage3.model.Ball;
import org.stage3.model.Player;

/**
 * The type Player controller.
 */
public class Controller {

    /**
     * The constant isRestarted.
     */
    public static boolean isRestarted = false;

    private Scene scene;

    private Player player1;

    private Player player2;

    private Ball ball;

    private double width;
    private double height;

    /**
     * Instantiates a new Player controller.
     *
     * @param scene   the scene
     * @param player1 the player 1
     * @param player2 the player 2
     * @param ball    the ball
     */
    public Controller(Scene scene, Player player1, Player player2, Ball ball, double width, double height) {
        this.scene = scene;
        this.player1 = player1;
        this.player2 = player2;
        this.ball = ball;
        this.width = width;
        this.height = height;
    }


    public void controls() {
        double centerX = width / 2;
        double centerY = height / 2;

        scene.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode() == KeyCode.W) {
                player1.moveUp();
            }
            else if (keyEvent.getCode() == KeyCode.S) {
                player1.moveDown();
            }
            else if (keyEvent.getCode() == KeyCode.UP) {
                player2.moveUp();
            }
            else if (keyEvent.getCode() == KeyCode.DOWN) {
                player2.moveDown();
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
                player1.setxPos(0);
                player1.setyPos(centerY);

                player2.setxPos(width - player2.getPlayerWidth());
                player2.setyPos(centerY);

                player1.setScore(0);
                player2.setScore(0);

                ball.setXPos(centerX);
                ball.setYPos(centerY);

                ball.RandomDirection(centerX, centerY);

                isRestarted = true;
                Game.gameStarted = false;
                System.out.println("Game reset");
            } else {
                isRestarted = false;
            }
        });
    }

    public void PaddleCollision() {
        // Check collision with player one paddle
        if (ball.getXPos() <= player1.getxPos() + player1.getPlayerWidth() && ball.getYPos() >= player1.getyPos() && ball.getYPos() <= player1.getyPos() + player1.getPlayerHeight()) {
            ball.reverseXSpeed();
            ball.increaseBallSpeed(0.1);
        }

        // Check collision with player two paddle
        if (ball.getXPos() + ball.getRadius() >= player2.getxPos() && ball.getYPos() >= player2.getyPos() && ball.getYPos() <= player2.getyPos() + player2.getPlayerHeight()) {
            ball.reverseXSpeed();
            ball.increaseBallSpeed(0.1);
        }
    }

    /**
     * Ball bounds logic.
     *
     * @param ball    the ball
     * @param player1 the player 1
     * @param player2 the player 2
     */
    public void BallBoundsLogic(Ball ball, Player player1, Player player2) {
        // Ensure the ball stays within the canvas boundaries vertically
        if (ball.getYPos() + ball.getRadius() > height || ball.getYPos() < 0) {
            ball.reverseYSpeed();
        }

        // Ensure player paddles stay within the bounds of the game window
        player1.setyPos(Math.max(0, Math.min(player1.getyPos(), height - player1.getPlayerHeight())));
        player2.setyPos(Math.max(0, Math.min(player2.getyPos(), height - player2.getPlayerHeight())));

        // If player one misses the ball, player two scores a point
        if (ball.getXPos() < 0) {
            player2.increaseScore();
            ball.setXPos(width / 2);
            ball.setYPos(height / 2);
            player2.setLastTouched(true);
            Game.gameStarted = false;
        }

        // If player two misses the ball, player one scores a point
        if (ball.getXPos() > width) {
            player1.increaseScore();
            ball.setXPos(width / 2);
            ball.setYPos(height / 2);
            player1.setLastTouched(true);
            Game.gameStarted = false;
        }
    }
}