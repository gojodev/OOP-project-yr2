package org.example.stage1; // Package declaration, grouping related classes

import java.util.Random; // Importing the Random class from java.util package

import javafx.animation.KeyFrame; // Importing KeyFrame class from javafx.animation package
import javafx.animation.Timeline; // Importing Timeline class from javafx.animation package
import javafx.application.Application; // Importing Application class from javafx.application package
import javafx.scene.Scene; // Importing Scene class from javafx.scene package
import javafx.scene.canvas.Canvas; // Importing Canvas class from javafx.scene.canvas package
import javafx.scene.canvas.GraphicsContext; // Importing GraphicsContext class from javafx.scene.canvas package
import javafx.scene.paint.Color; // Importing Color class from javafx.scene.paint package
import javafx.scene.text.Font; // Importing Font class from javafx.scene.text package
import javafx.scene.text.TextAlignment; // Importing TextAlignment enum from javafx.scene.text package
import javafx.stage.Stage; // Importing Stage class from javafx.stage package
import javafx.util.Duration; // Importing Duration class from javafx.util package
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.application.Platform;


/**
 * The type Game.
 */
public class Game extends Application { // Class declaration and inheritance from Application class

    // Variables declaration
    private double WIDTH = 500;
    private double HEIGHT = 500;
    private double PLAYER_HEIGHT = 100;
    private int PLAYER_WIDTH = 15;
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

    private String p1;
    private String p2;
    private float ballSpeed;
    private int scoreLimit;
    private float ballSpeedIncrease;
    private float racketSize;

    private Ball ball;


    /**
     * Instantiates a new Game.
     */
    public Game() {
        this.p1 = "player1";
        this.p2 = "player2";
        this.ballSpeed = 10;
        this.scoreLimit = 1;
        this.ballSpeedIncrease = 1;
        this.racketSize = 1;
        System.out.println("Please run from Menu.java first, to customise your inputs");
    }

    /**
     * Instantiates a new Game.
     *
     * @param p1                the p 1
     * @param p2                the p 2
     * @param scoreLimit        the score limit
     * @param ballSpeedIncrease the ball speed increase
     * @param racketSize        the racket size
     */
    public Game(String p1, String p2, int scoreLimit, int ballSpeedIncrease, int racketSize) {
        this.p1 = p1;
        this.p2 = p2;
        this.scoreLimit = scoreLimit;
        this.ballSpeedIncrease = ballSpeedIncrease;
        this.racketSize = racketSize;
    }

    // Application entry point
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PONG Game Project");
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        ball = Ball.createRandomizedBall(WIDTH / 2, HEIGHT / 2);

        // Set minimum width and height for the scene
        root.setMinWidth(500);
        root.setMinHeight(500);

        Canvas canvas = new Canvas(WIDTH, HEIGHT); // Create a canvas with specified dimensions
        GraphicsContext gc = canvas.getGraphicsContext2D(); // Get the graphics context from the canvas
        root.getChildren().add(canvas);

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(ballSpeed), e -> run(gc))); // Create a timeline for animation
        tl.setCycleCount(Timeline.INDEFINITE); // Set the animation to repeat indefinitely

        // Set up mouse controls
        canvas.setOnMouseMoved(e -> playerOneYPos = e.getY()); // Update player one's paddle position on mouse movement
        canvas.setOnMouseClicked(e -> gameStarted = true); // Start the game on mouse click

        // Adjust positions when window is resized
        ChangeListener<Number> resizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                WIDTH = scene.getWidth();
                HEIGHT = scene.getHeight();
                playerTwoXPos = WIDTH - PLAYER_WIDTH;
                canvas.setWidth(WIDTH);
                canvas.setHeight(HEIGHT);
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
        gc.setFill(Color.BLACK); // Set the background color to black
        gc.fillRect(0, 0, WIDTH, HEIGHT); // Fill the entire canvas with the background color

        gc.setFill(Color.WHITE); // Set text color to white
        gc.setFont(Font.font("Arial", 25)); // Set font size

        if (gameStarted) {
            ball.move(); // Move the ball according to its current speed

            ballXPos = ball.getXPos(); // Update ballXPos with the new x-position
            ballYPos = ball.getYPos(); // Update ballYPos with the new y-position

            // Simple computer opponent following the ball
            if (ballXPos < WIDTH - WIDTH / 4) {
                playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
            } else {
                playerTwoYPos = ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ? playerTwoYPos + 1 : playerTwoYPos - 1;
            }

            // Ensure the ball stays within the canvas boundaries
            if (ballYPos + BALL_R > HEIGHT || ballYPos < 0) {
                ball.reverseYSpeed(); // Reverse the ball's y-speed
            }

            gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R); // Draw the ball

            // Check collision with player one paddle
            if (ballXPos <= playerOneXPos + PLAYER_WIDTH && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT) {
                ball.reverseXSpeed(); // Reverse the ball's x-speed
            }

            // Check collision with player two paddle
            if (ballXPos + BALL_R >= playerTwoXPos && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) {
                ball.reverseXSpeed(); // Reverse the ball's x-speed
            }
        } else {
            gc.setStroke(Color.WHITE); // Set stroke color to white
            gc.setTextAlign(TextAlignment.CENTER); // Set text alignment to center

            if (scoreP1 == scoreLimit) {
                gc.strokeText("Player1 won", WIDTH / 2, HEIGHT / 2); // Display winner message
                Platform.exit(); // Close the game window
                System.out.println("Player1 won");
            } else if (scoreP2 == scoreLimit) {
                gc.strokeText("Player2 won", WIDTH / 2, HEIGHT / 2); // Display winner message
                System.out.println("Player2 won");
                Platform.exit(); // Close the game window
            } else {
                gc.strokeText("Click", WIDTH / 2, HEIGHT / 2); // Display click instruction
            }

            ballXPos = WIDTH / 2; // Reset the ball's x-position
            ballYPos = HEIGHT / 2; // Reset the ball's y-position

            // Randomize the ball's initial speed and direction
            ballXSpeed = new Random().nextInt(3) == 0 ? 1 : -1;
            ballYSpeed = new Random().nextInt(3) == 0 ? 1 : -1;
        }

        // Ensure the ball stays within the canvas boundaries
        if (ballYPos > HEIGHT || ballYPos < 0) ballYSpeed *= -1;

        // If player one misses the ball, player two scores a point
        if (ballXPos < playerOneXPos - PLAYER_WIDTH) {
            scoreP2++;
            gameStarted = false;
        }

        // If player two misses the ball, player one scores a point
        if (ballXPos > playerTwoXPos + PLAYER_WIDTH) {
            scoreP1++;
            gameStarted = false;
        }

        // Increase the speed after the ball hits a paddle
        if (((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) ||
                ((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }

        // Draw the score
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(p1 + ": " + scoreP1, WIDTH / 4, 50);
        gc.fillText(p2 + ": " + scoreP2, WIDTH * 3 / 4, 50);

        // Access the root pane of the existing scene
        Pane root = (Pane) gc.getCanvas().getScene().getRoot();

        // Draw player one and two paddles
        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
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