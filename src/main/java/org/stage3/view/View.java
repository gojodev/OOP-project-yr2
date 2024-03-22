package org.stage3.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.stage3.controller.CollisionTest;
import org.stage3.model.Ball;
import org.stage3.model.Player;

/**
 * The type View.
 */
public class View {
    private Ball ball;
    private Player player1;
    private Player player2;
    private GraphicsContext gc;
    private double width;
    private double height;

    /**
     * Instantiates a new View.
     *
     * @param ball    the ball
     * @param player1 the player 1
     * @param player2 the player 2
     */
    public View(GraphicsContext gc, Ball ball, Player player1, Player player2, double width, double height) {
        this.gc = gc;
        this.ball = ball;
        this.player1 = player1;
        this.player2 = player2;
        this.width = width;
        this.height = height;
    }

    public void DrawRackets() {
        gc.setFill(Color.WHITE);
        gc.fillRect(player1.getxPos(), player1.getyPos(), player1.getPlayerWidth(), player1.getPlayerHeight());
        gc.fillRect(player2.getxPos(), player2.getyPos(), player2.getPlayerWidth(), player2.getPlayerHeight());
    }

    public void DrawScore(int scoreP1, int scoreP2) {
        gc.setStroke(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.WHITE);
        if (CollisionTest.isRestarted) {
            scoreP1 = 0;
            scoreP2 = 0;
        }
        gc.fillText(player1.getName() + ": " + scoreP1, width / 4, 50);
        gc.fillText(player2.getName() + ": " + scoreP2, width * 3 / 4, 50);
    }

    public void DrawBall() {
        gc.setFill(Color.WHITE);
        gc.fillOval(ball.getXPos(), ball.getYPos(), ball.getRadius(), ball.getRadius());
    }

    // The pause and restart game functionality are in PlayerController
}
