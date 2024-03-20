package org.stage3.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.stage3.controller.PlayerController;
import org.stage3.model.Ball;
import org.stage3.model.Player;

/**
 * The type View.
 */
public class View {

    private Ball ball;
    private Player player1;
    private Player player2;

    /**
     * The Is paused.
     */
    private boolean isPaused = false;

    /**
     * Instantiates a new View.
     *
     * @param ball    the ball
     * @param player1 the player 1
     * @param player2 the player 2
     */
    public View(Ball ball, Player player1, Player player2) {
        this.ball = ball;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Draw rackets.
     *
     * @param gc            the gc
     * @param PLAYER_WIDTH  the player width
     * @param PLAYER_HEIGHT the player height
     * @param playerTwoXPos the player two x pos
     * @param playerTwoYPos the player two y pos
     */
    public void DrawRackets(GraphicsContext gc, double PLAYER_WIDTH, double PLAYER_HEIGHT, double playerTwoXPos, double playerTwoYPos) {
        gc.setFill(Color.WHITE);
        gc.fillRect(player1.getxPos(), player1.getyPos(), PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    /**
     * Draw score.
     *
     * @param gc      the gc
     * @param scoreP1 the score p 1
     * @param scoreP2 the score p 2
     * @param WIDTH   the width
     */
    public void DrawScore(GraphicsContext gc, int scoreP1, int scoreP2, double WIDTH) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.WHITE);
        if (PlayerController.isRestarted) {
            scoreP1 = 0;
            scoreP2 = 0;
        }
        gc.fillText(player1.getName() + ": " + scoreP1, WIDTH / 4, 50);
        gc.fillText(player2.getName() + ": " + scoreP2, WIDTH * 3 / 4, 50);
    }


    /**
     * Draw ball.
     *
     * @param gc       the gc
     * @param ballXPos the ball x pos
     * @param ballYPos the ball y pos
     * @param BALL_R   the ball r
     */
    public void DrawBall(GraphicsContext gc, double ballXPos, double ballYPos, double BALL_R) {
        gc.setFill(Color.WHITE);
        gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
    }

    // The pause and restart game functionality are in PlayerController
}
