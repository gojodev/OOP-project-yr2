package org.stage2; // Package declaration, grouping related classes

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
import org.stage2.controller.PlayerListener;
import org.stage2.model.Ball;
import org.stage2.model.Player;

import java.util.Random;


/**
 * The type Game.
 */
public class Game extends Application { // Class declaration and inheritance from Application class

    // Variables declaration
    private double WIDTH = 450;
    private double HEIGHT = 450;
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
    private boolean gameStarted;
    private int playerOneXPos = 0; // Initial x-position of player one paddle
    private double playerTwoXPos = WIDTH - PLAYER_WIDTH;

    private double ballSpeed;
    private int scoreLimit;
    private double ballSpeedIncrease;
    private Ball ball;

    private Player player1;
    private Player player2;


    /**
     * Instantiates a new Game.
     */
    public Game() {
        this.player1 = new Player();
        this.player2 = new Player();
        player1.setName("player1");
        player2.setName("player2");
        this.ballSpeed = 0.5;
        this.scoreLimit = 3;
        this.ballSpeedIncrease = 0.5;
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
        player1.setName("player1");
        player2.setName("player2");
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
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PONG Game Project");
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // Creates the Ball
        ball = Ball.createRandomizedBall(WIDTH / 2, HEIGHT / 2);

        Canvas canvas = new Canvas(WIDTH, HEIGHT); // Create a canvas with specified dimensions
        GraphicsContext gc = canvas.getGraphicsContext2D(); // Get the graphics context from the canvas
        root.getChildren().add(canvas);

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc))); // Create a timeline for animation
        tl.setCycleCount(Timeline.INDEFINITE); // Set the animation to repeat indefinitely

        // Set up mouse controls
        // canvas.setOnMouseMoved(e -> playerOneYPos = e.getY()); // Update player one's paddle position on mouse movement
        PlayerListener listener = new PlayerListener();
        listener.movePlayer(scene, player1, player2);

        canvas.setOnMouseClicked(e -> gameStarted = true); // Start the game on mouse click

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
    private void run(GraphicsContext gc) {
        // Check if the game window becomes blank
        if (WIDTH == 0 || HEIGHT == 0) {
            System.out.println("Error Game window dimensions are invalid. Please resize the window.");
            return;
        }

        gc.setFill(Color.BLACK); // Set the background color to black
        gc.fillRect(0, 0, WIDTH, HEIGHT); // Fill the entire canvas with the background color

        gc.setFill(Color.WHITE); // Set text color to white
        gc.setFont(Font.font("Verdana", 25)); // Set font size and style

        if (gameStarted) {
            ball.move(); // Move the ball according to its current speed

            // Randomize the ball's initial speed and direction
            ballXSpeed = new Random().nextInt(3) == 0 ? 1 : -1;
            ballYSpeed = new Random().nextInt(3) == 0 ? 1 : -1;

            ballXPos = ball.getXPos(); // Update ballXPos with the new x-position
            ballYPos = ball.getYPos(); // Update ballYPos with the new y-position

            // always bet on player2
             playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;

            // Ensure the ball stays within the canvas boundaries
            if (ballYPos + BALL_R > HEIGHT || ballYPos < 0) {
                ball.reverseYSpeed(); // Reverse the ball's y-speed
            }

            gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R); // Draw the ball

            // Check collision with player one paddle
            if (ballXPos <= playerOneXPos + PLAYER_WIDTH && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT) {
                ball.reverseXSpeed();
                ball.reverseYSpeed();
                ball.adjustSpeed(ballSpeedIncrease);
                System.out.println("speed change");
            }

            // Check collision with player two paddle
            if (ballXPos + BALL_R >= playerTwoXPos && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) {
                ball.reverseXSpeed();
                ball.reverseYSpeed();
                ball.adjustSpeed(ballSpeedIncrease);
                System.out.println("speed change");
            }
        } else {
            gc.setStroke(Color.WHITE); // Set stroke color to white
            gc.setTextAlign(TextAlignment.CENTER); // Set text alignment to center

            if (scoreP1 == scoreLimit) {
                gc.strokeText("Player1 won", WIDTH / 2, HEIGHT / 2); // Display winner message
                gc.setTextAlign(TextAlignment.CENTER);
                System.out.println("Player1 won");
                gameStarted = false;
                scoreP1 = 0;
                Runtime.getRuntime().exit(0);

            } if (scoreP2 == scoreLimit) {
                gc.strokeText("Player2 won", WIDTH / 2, HEIGHT / 2); // Display winner message
                gc.setTextAlign(TextAlignment.CENTER);
                System.out.println("Player2 won");
                gameStarted = false;
                scoreP2 = 0;
                Runtime.getRuntime().exit(0);

            } else {
                gc.strokeText("Click", WIDTH / 2, HEIGHT / 2); // Display click instruction
            }

            ballXPos = WIDTH / 2; // Reset the ball's x-position
            ballYPos = HEIGHT / 2; // Reset the ball's y-position
        }

        // Ensure the ball stays within the canvas boundaries
        if (ballYPos > HEIGHT || ballYPos < 0) ballYSpeed *= -1;

        // Ensure player paddles stay within the bounds of the game window
        playerOneYPos = Math.max(0, Math.min(playerOneYPos, HEIGHT - PLAYER_HEIGHT));
        playerTwoYPos = Math.max(0, Math.min(playerTwoYPos, HEIGHT - PLAYER_HEIGHT));


        // If player one misses the ball, player two scores a point
        if (ballXPos < playerOneXPos - PLAYER_WIDTH) {
            scoreP2++;
            player2.setScore(scoreP2);
            gameStarted = false;
        }

        // If player two misses the ball, player one scores a point
        if (ballXPos > playerTwoXPos + PLAYER_WIDTH) {
            scoreP1++;
            player1.setScore(scoreP1);
            gameStarted = false;
        }


        // Draw the score
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(player1.getName() + ": " + scoreP1, WIDTH / 4, 50);
        gc.fillText(player2.getName() + ": " + scoreP2, WIDTH * 3 / 4, 50);

        // Draw player one and two paddles
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
// Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}