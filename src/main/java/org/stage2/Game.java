package org.stage2; // Package declaration, grouping related classes

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.stage2.controller.PlayerController;
import org.stage2.model.Ball;
import org.stage2.model.Player;
import org.stage2.view.View;

import java.util.Random;


/**
 * The type Game.
 */
public class Game extends Application { // Class declaration and inheritance from Application class

    // Variables declaration
    public static double WIDTH = 500;
    public static double HEIGHT = 500;
    private double PLAYER_HEIGHT = 100;
    private float PLAYER_WIDTH;
    private double BALL_R = 15;
    private double ballYSpeed = 1;
    private double ballXSpeed = 1;
    private double playerOneYPos = HEIGHT / 2;
    private double playerTwoYPos = HEIGHT / 2;
    private double ballXPos = WIDTH / 2;
    private double ballYPos = HEIGHT / 2;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted = false;
    private int playerOneXPos = 0; // Initial x-position of player one paddle
    private double playerTwoXPos = WIDTH - PLAYER_WIDTH;
    private double ballSpeed;
    private int scoreLimit;
    private double ballSpeedIncrease;
    private Canvas canvas;
    private Ball ball;

    private Player player1;
    private Player player2;

    private View view;

    /**
     * The constant tl.
     */
    public static Timeline tl;

    /**
     * The constant scene.
     */
    public static Scene scene;

    public static boolean isPaused = false;

    private boolean won = false;

    /**
     * Instantiates a new Game.
     */
    public Game() {
        this.player1 = new Player();
        this.player2 = new Player();

        player1.setName("player1");
        ;
        player2.setName("player2");
        this.ballSpeed = 1;
        this.scoreLimit = 2;
        this.ballSpeedIncrease = 1.5;
        this.PLAYER_WIDTH = 15;
        System.out.println("Please run from Menu.java first, to customise your inputs");
    }


    /**
     * Instantiates a new Game.
     *
     * @param p1                the p 1
     * @param p2                the p 2
     * @param scoreLimit        the score limit
     * @param ballSpeed         the ball speed
     * @param ballSpeedIncrease the ball speed increase
     * @param racketSize        the racket size
     */
    public Game(String p1, String p2, int scoreLimit, double ballSpeed, double ballSpeedIncrease, int racketSize) {
        this.player1 = new Player();
        this.player2 = new Player();

        player1.setName(p1);
        player2.setName(p2);
        this.ballSpeed = ballSpeed;
        this.scoreLimit = scoreLimit;
        this.ballSpeedIncrease = ballSpeedIncrease;
        this.PLAYER_WIDTH = racketSize;

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(scoreLimit);
        System.out.println(ballSpeedIncrease);
        System.out.println(racketSize);
    }

    // Application entry point
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PONG Game Project");
        Pane root = new Pane();
        scene = new Scene(root, WIDTH, HEIGHT);

        view = new View(ball, player1, player2);


        ball = Ball.createRandomizedBall(WIDTH / 2, HEIGHT / 2);
        PlayerController.controls(scene, player1, player2, ball);

        canvas = new Canvas(WIDTH, HEIGHT); // Create a canvas with specified dimensions
        GraphicsContext gc = canvas.getGraphicsContext2D(); // Get the graphics context from the canvas
        root.getChildren().add(canvas);

        tl = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            try {
                run(gc);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        })); // Create a timeline for animation
        tl.setCycleCount(Timeline.INDEFINITE); // Set the animation to repeat indefinitely


        // Adjust positions when window is resized
        ChangeListener<Number> resizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                WIDTH = scene.getWidth();
                HEIGHT = scene.getHeight();
                canvas.setWidth(WIDTH);
                canvas.setHeight(HEIGHT);
                playerTwoXPos = WIDTH - PLAYER_WIDTH;
            }
        };

        scene.widthProperty().addListener(resizeListener);
        scene.heightProperty().addListener(resizeListener);

        primaryStage.setScene(scene); // Set the scene with the canvas
        primaryStage.show(); // Display the stage

        tl.play(); // Start the animation timeline
    }

    // Method to handle the game logic and drawing
    private void run(GraphicsContext gc) throws InterruptedException {
        // Check if the game window becomes blank
        if (WIDTH == 0 || HEIGHT == 0) {
            System.out.println("Error Game window dimensions are invalid. Please resize the window.");
            return;
        }

        gc.setFill(Color.BLACK); // Set the background color to black
        gc.fillRect(0, 0, WIDTH, HEIGHT); // Fill the entire canvas with the background color

        gc.setFill(Color.WHITE); // Set text color to white
        gc.setFont(Font.font("Arial", 25)); // Set font size

        if (!gameStarted) {
            if (scoreP1 == 0 && scoreP2 == 0) {
                gc.fillText("CLick to Start", WIDTH / 2, HEIGHT / 2);
                gc.setTextAlign(TextAlignment.CENTER);
                canvas.setOnMouseClicked(e -> {
                    if (!gameStarted) {
                        gameStarted = true;
                    }
                });

            }

            String message;
            if (player1.isLastTouched()) {
                if (player1.getScore() == scoreLimit) {
                    message = "Player1 won";
                    won = true;
                } else {
                    message = "Player1 scored";
                }
                gc.strokeText(message, WIDTH / 2, HEIGHT / 2);
                gc.setTextAlign(TextAlignment.CENTER);
                canvas.setOnMouseClicked(e -> {
                    if (!gameStarted) {
                        gameStarted = true;
                    }
                    // easier than using timeline
                    if (won) {
                        System.exit(0);
                    }
                });
            }

            else if (player2.isLastTouched()) {
                if (player2.getScore() == scoreLimit) {
                    message = "Player2 won";
                    won = true;
                } else {
                    message = "Player2 scored";
                }

                gc.strokeText(message, WIDTH / 2, HEIGHT / 2);
                gc.setTextAlign(TextAlignment.CENTER);
                canvas.setOnMouseClicked(e -> {
                    if (!gameStarted) {
                        gameStarted = true;
                    }
                    // easier than using timeline
                    if (won) {
                        System.exit(0);
                    }
                });
            }


        } else {
            ball.move(); // Move the ball according to its current speed

            // Randomize the ball's initial speed and direction
            ballXSpeed = new Random().nextInt(3) == 0 ? 1 : -1;
            ballYSpeed = new Random().nextInt(3) == 0 ? 1 : -1;

            ballXPos = ball.getXPos(); // Update ballXPos with the new x-position
            ballYPos = ball.getYPos(); // Update ballYPos with the new y-position

            PlayerController.controls(scene, player1, player2, ball);

            // Ensure the ball stays within the canvas boundaries
            if (ballYPos + BALL_R > HEIGHT || ballYPos < 0) {
                ball.reverseYSpeed();
            }

            view.DrawScore(gc, scoreP1, scoreP2, WIDTH);

            view.DrawBall(gc, ballXPos, ballYPos, BALL_R);

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

            gc.setStroke(Color.WHITE); // Set stroke color to white
            gc.setTextAlign(TextAlignment.CENTER); // Set text alignment to center

            ballXPos = WIDTH / 2; // Reset the ball's x-position
            ballYPos = HEIGHT / 2; // Reset the ball's y-position


            // Ensure the ball stays within the canvas boundaries
            if (ballYPos > HEIGHT || ballYPos < 0) ballYSpeed *= -1;

            // Ensure player paddles stay within the bounds of the game window
            playerOneYPos = Math.max(0, Math.min(player1.getyPos(), HEIGHT - PLAYER_HEIGHT));
            playerTwoYPos = Math.max(0, Math.min(player2.getyPos(), HEIGHT - PLAYER_HEIGHT));


            // If player one misses the ball, player two scores a point
            if (ball.getXPos() < 0) {
                scoreP2++;
                player2.setScore(scoreP2);
                ball.setXPos(WIDTH / 2);
                ball.setYPos(HEIGHT / 2);
                player2.setLastTouched(true);
                gameStarted = false;
            }

            // If player two misses the ball, player one scores a point
            if (ball.getXPos() > WIDTH) {
                scoreP1++;
                player1.setScore(scoreP1);
                ball.setXPos(WIDTH / 2);
                ball.setYPos(HEIGHT / 2);
                player1.setLastTouched(true);
                gameStarted = false;
            }

            view.DrawRackets(gc, PLAYER_WIDTH, PLAYER_HEIGHT, playerTwoXPos, playerTwoYPos);

        }
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}