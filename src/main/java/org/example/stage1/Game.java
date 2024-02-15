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
import org.example.stage1.Ball;
import org.example.stage1.Player;

import java.util.Random;

public class Game extends Application {

    private int WIDTH = 800;
    private int HEIGHT = 800;
    private int PLAYER_HEIGHT = 100;
    private int PLAYER_WIDTH = 15;
    private double BALL_RADIUS = 15;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private double ballXPos = WIDTH / 2;
    private double ballYPos = HEIGHT / 2;
    private boolean gameStarted;
    private double playerOneYPos = HEIGHT / 2;
    private double playerTwoYPos = HEIGHT / 2;
    private double playerOneXPos = 0;
    private double playerTwoXPos = WIDTH - PLAYER_WIDTH;
    private int ballSpeed;
    private double racketSize;
    private double racketThickness;
    private int scoreLimit;
    private int ballSpeedIncrease;

    private Player player1;
    private Player player2;
    private Ball ball;

    private int scoreP1;
    private int scoreP2;

    // Constructor to initialize player names and game settings
    public Game(String player1Name, String player2Name, int ballSpeed, double racketSize, double racketThickness, int scoreLimit, int ballSpeedIncrease) {
        this.ballSpeed = ballSpeed;
        this.racketSize = racketSize;
        this.racketThickness = racketThickness;
        this.scoreLimit = scoreLimit;
        this.ballSpeedIncrease = ballSpeedIncrease;
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.ball = new Ball(WIDTH / 2, HEIGHT / 2, 0, 0);
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("PONG Game Project");

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Adjust positions when window size changes
        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            WIDTH = newVal.intValue();
            playerTwoXPos = WIDTH - PLAYER_WIDTH;
            ballXPos = WIDTH / 2;
        });

        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            HEIGHT = newVal.intValue();
            playerOneYPos = HEIGHT / 2;
            playerTwoYPos = HEIGHT / 2;
            ballYPos = HEIGHT / 2;
        });

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(ballSpeed), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseMoved(this::handleMouseMove);
        canvas.setOnMouseClicked(e -> gameStarted = true);

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newWidth = newVal.doubleValue();
            canvas.setWidth(newWidth);
            playerOneXPos = 0;
            playerTwoXPos = newWidth - PLAYER_WIDTH;
            ballXPos = newWidth / 2;
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newHeight = newVal.doubleValue();
            canvas.setHeight(newHeight);
            playerOneYPos = newHeight / 2;
            playerTwoYPos = newHeight / 2;
            ballYPos = newHeight / 2;
        });

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
            if (scoreP2 >= scoreLimit) {
                resetGame();
            } else {
                gameStarted = false;
            }
        }

        if (ballXPos > playerTwoXPos + PLAYER_WIDTH) {
            scoreP1++;
            if (scoreP1 >= scoreLimit) {
                resetGame();
            } else {
                gameStarted = false;
            }
        }

        handleBallRacketCollision();

        String player1Name = player1.getName();
        String player2Name = player2.getName();
        String scoreText = scoreP1 + " \t\t " + scoreP2;

        Font font = gc.getFont();
        double textWidth1 = font.getSize() * player1Name.length() * 0.6;
        double textWidth2 = font.getSize() * player2Name.length() * 0.6;
        double textWidthScore = font.getSize() * scoreText.length() * 0.6;

        double xPlayer1 = (WIDTH / 2) - textWidth1 / 2;
        double xPlayer2 = (WIDTH / 2) + textWidth2 / 2;
        double xScore = (WIDTH / 2) - textWidthScore / 2;

        gc.fillText(player1Name, xPlayer1, 50);
        gc.fillText(player2Name, xPlayer2, 50);
        gc.fillText(scoreText, xScore, 100);

        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    private void resetGame() {
        scoreP1 = 0;
        scoreP2 = 0;
        playerOneYPos = HEIGHT / 2;
        playerTwoYPos = HEIGHT / 2;
    }

    private void handleBallRacketCollision() {
        if (((ballXPos + BALL_RADIUS > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) ||
                ((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
            ballYSpeed += Math.signum(ballYSpeed);
            ballXSpeed += Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
