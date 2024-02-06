package org.example.stage1; // Package declaration, grouping related classes

import java.util.Random; // Importing the Random class from java.util package

import javafx.animation.KeyFrame; // Importing KeyFrame class from javafx.animation package
import javafx.animation.Timeline; // Importing Timeline class from javafx.animation package
import javafx.application.Application; // Importing Application class from javafx.application package
import javafx.scene.Scene; // Importing Scene class from javafx.scene package
import javafx.scene.canvas.Canvas; // Importing Canvas class from javafx.scene.canvas package
import javafx.scene.canvas.GraphicsContext; // Importing GraphicsContext class from javafx.scene.canvas package
import javafx.scene.layout.StackPane; // Importing StackPane class from javafx.scene.layout package
import javafx.scene.paint.Color; // Importing Color class from javafx.scene.paint package
import javafx.scene.text.Font; // Importing Font class from javafx.scene.text package
import javafx.scene.text.TextAlignment; // Importing TextAlignment enum from javafx.scene.text package
import javafx.stage.Stage; // Importing Stage class from javafx.stage package
import javafx.util.Duration; // Importing Duration class from javafx.util package

public class Pong extends Application { // Class declaration and inheritance from Application class

    // Variables declaration
    private static final int width = 800; // Width of the canvas
    private static final int height = 600; // Height of the canvas
    private static final int PLAYER_HEIGHT = 100; // Height of the player paddles
    private static final int PLAYER_WIDTH = 15; // Width of the player paddles
    private static final double BALL_R = 15; // Radius of the ball
    private int ballYSpeed = 1; // Vertical speed of the ball
    private int ballXSpeed = 1; // Horizontal speed of the ball
    private double playerOneYPos = height / 2; // Initial y-position of player one paddle
    private double playerTwoYPos = height / 2; // Initial y-position of player two paddle
    private double ballXPos = width / 2; // Initial x-position of the ball
    private double ballYPos = height / 2; // Initial y-position of the ball
    private int scoreP1 = 0; // Player one's score
    private int scoreP2 = 0; // Player two's score
    private boolean gameStarted; // Flag to indicate if the game has started
    private int playerOneXPos = 0; // Initial x-position of player one paddle
    private double playerTwoXPos = width - PLAYER_WIDTH; // Initial x-position of player two paddle

    // Application entry point
    public void start(Stage stage) throws Exception {
        stage.setTitle("PONG Game Project"); // Set the title of the stage
        Canvas canvas = new Canvas(width, height); // Create a canvas with specified dimensions
        GraphicsContext gc = canvas.getGraphicsContext2D(); // Get the graphics context from the canvas

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc))); // Create a timeline for animation
        tl.setCycleCount(Timeline.INDEFINITE); // Set the animation to repeat indefinitely

        // Set up mouse controls
        canvas.setOnMouseMoved(e -> playerOneYPos = e.getY()); // Update player one's paddle position on mouse movement
        canvas.setOnMouseClicked(e -> gameStarted = true); // Start the game on mouse click

        stage.setScene(new Scene(new StackPane(canvas))); // Set the scene with the canvas
        stage.show(); // Display the stage
        tl.play(); // Start the animation timeline
    }

    // Method to handle the game logic and drawing
    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK); // Set the background color to black
        gc.fillRect(0, 0, width, height); // Fill the entire canvas with the background color

        gc.setFill(Color.WHITE); // Set text color to white
        gc.setFont(Font.font(25)); // Set font size

        if (gameStarted) {
            ballXPos += ballXSpeed; // Update ball's x-position based on speed
            ballYPos += ballYSpeed; // Update ball's y-position based on speed

            // Simple computer opponent following the ball
            if (ballXPos < width - width / 4) {
                playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
            } else {
                playerTwoYPos = ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ? playerTwoYPos + 1 : playerTwoYPos - 1;
            }

            gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R); // Draw the ball
        } else {
            gc.setStroke(Color.WHITE); // Set stroke color to white
            gc.setTextAlign(TextAlignment.CENTER); // Set text alignment to center
            gc.strokeText("Click", width / 2, height / 2); // Display click instruction

            ballXPos = width / 2; // Reset the ball's x-position
            ballYPos = height / 2; // Reset the ball's y-position

            // Randomize the ball's initial speed and direction
            ballXSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
        }

        // Ensure the ball stays within the canvas boundaries
        if (ballYPos > height || ballYPos < 0) ballYSpeed *= -1;

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
        gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);

        // Draw player one and two paddles
        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
