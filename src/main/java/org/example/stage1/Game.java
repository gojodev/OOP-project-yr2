package org.example.stage1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.util.Random;

public class Game extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 900;
    private static final int PLAYER_HEIGHT = 100;
    private static final int PLAYER_WIDTH = 15;
    private static final double BALL_RADIUS = 15;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private double playerOneYPos = HEIGHT / 2;
    private double playerTwoYPos = HEIGHT / 2;
    private double ballXPos = WIDTH / 2;
    private double ballYPos = HEIGHT / 2;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted;
    private double playerOneXPos = 0;
    private double playerTwoXPos = WIDTH - PLAYER_WIDTH;
    private int ballSpeed; // New variable to store ball speed

    // Constructor to initialize player names and ball speed
    public Game(String player1Name, String player2Name, int ballSpeed, double racketSize, double racketThickness, int scoreLimit, int ballSpeedIncrease) {
        this.ballSpeed = ballSpeed;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("PONG Game Project");

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(ballSpeed), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseMoved(this::handleMouseMove);
        canvas.setOnMouseClicked(e -> gameStarted = true);

        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setResizable(true);
        stage.show();
        tl.play();
    }

    private void handleMouseMove(MouseEvent event) {
        playerOneYPos = event.getY();
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));

        if (gameStarted) {
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;

            if (ballXPos < WIDTH - WIDTH / 4) {
                playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
            } else {
                playerTwoYPos = ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ? playerTwoYPos + 1 : playerTwoYPos - 1;
            }

            gc.fillOval(ballXPos, ballYPos, BALL_RADIUS, BALL_RADIUS);
        } else {
            gc.setStroke(Color.WHITE);
            gc.strokeText("Click to Start", WIDTH / 2, HEIGHT / 2);

            ballXPos = WIDTH / 2;
            ballYPos = HEIGHT / 2;

            ballXSpeed = new Random().nextBoolean() ? 1 : -1;
            ballYSpeed = new Random().nextBoolean() ? 1 : -1;
        }

        if (ballYPos > HEIGHT - BALL_RADIUS || ballYPos < BALL_RADIUS) {
            ballYSpeed *= -1;
        }

        if (ballXPos < playerOneXPos - PLAYER_WIDTH) {
            scoreP2++;
            gameStarted = false;
        }

        if (ballXPos > playerTwoXPos + PLAYER_WIDTH) {
            scoreP1++;
            gameStarted = false;
        }

        if (((ballXPos + BALL_RADIUS > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) ||
                ((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
            ballYSpeed += Math.signum(ballYSpeed);
            ballXSpeed += Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }

        gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, WIDTH / 2, 100);

        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
