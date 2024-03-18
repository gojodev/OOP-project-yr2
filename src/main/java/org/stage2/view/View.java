package org.stage2.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import org.stage2.model.Ball;
import org.stage2.model.Player;

public class View {

    private Ball ball;
    private Player player1;
    private Player player2;

    public View (Ball ball, Player player1, Player player2) {
        this.ball = ball;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void DrawRackets(GraphicsContext gc, double PLAYER_WIDTH, double PLAYER_HEIGHT, double playerTwoXPos, double playerTwoYPos) {
        // Draw player one and two paddles
        gc.fillRect(player1.getxPos(), player1.getyPos(), PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public void DrawScore(GraphicsContext gc, int scoreP1, int scoreP2, double WIDTH) {
        // Draw the score
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(player1.getName() + ": " + scoreP1, WIDTH / 4, 50);
        gc.fillText(player2.getName() + ": " + scoreP2, WIDTH * 3 / 4, 50);
    }

    public void DrawBall(GraphicsContext gc, double ballXPos, double ballYPos, double BALL_R) {
        gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R); // Draw the ball
    }
}
