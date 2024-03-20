package org.stage3; // Package declaration, grouping related classes

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
import org.stage3.controller.PlayerController;
import org.stage3.model.Ball;
import org.stage3.model.Player;
import org.stage3.view.View;

import java.io.FileWriter;
import java.util.Random;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


/**
 * The type Game.
 */
public class Game extends Application { // Class declaration and inheritance from Application class

    /**
     * The constant WIDTH.
     */
// Variables declaration
    public static double WIDTH = 550;
    /**
     * The constant HEIGHT.
     */
    public static double HEIGHT = 550;
    /**
     * The constant PLAYER_HEIGHT.
     */
    public static double PLAYER_HEIGHT = 100;
    /**
     * The constant PLAYER_WIDTH.
     */
    public static float PLAYER_WIDTH;
    private double BALL_R = 15;
    /**
     * The constant ballYSpeed.
     */
    public static double ballYSpeed = 1;
    /**
     * The constant ballXSpeed.
     */
    public static double ballXSpeed = 1;
    /**
     * The constant playerOneYPos.
     */
    public static double playerOneYPos = HEIGHT / 2;
    /**
     * The constant playerTwoYPos.
     */
    public static double playerTwoYPos = HEIGHT / 2;
    /**
     * The constant ballXPos.
     */
    public static double ballXPos = WIDTH / 2;
    /**
     * The constant ballYPos.
     */
    public static double ballYPos = HEIGHT / 2;
    /**
     * The constant scoreP1.
     */
    public static int scoreP1 = 0;
    /**
     * The constant scoreP2.
     */
    public static int scoreP2 = 0;
    /**
     * The constant gameStarted.
     */
    public static boolean gameStarted = false;
    /**
     * The constant playerOneXPos.
     */
    public static double playerOneXPos =  PLAYER_WIDTH;
    /**
     * The constant playerTwoXPos.
     */
    public static double playerTwoXPos = WIDTH - PLAYER_WIDTH;
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

    /**
     * The constant isPaused.
     */
    public static boolean isPaused = false;

    private boolean won = false;

    /**
     * The constant modifiedSettings.
     */
    public static boolean modifiedSettings = false;

    /**
     * Instantiates a new Game.
     */
    public Game() {
        this.player1 = new Player();
        this.player2 = new Player();
        player1.setName("player1");
        player2.setName("player2");
        this.ballSpeed = 1;
        this.scoreLimit = 2;
        this.ballSpeedIncrease = 1.5;
        PLAYER_WIDTH = 15;
        System.out.println("Please run from Menu.java first, to customise your inputs");
    }

    /**
     * Load settings.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public void LoadSettings() throws FileNotFoundException {
        String fileName = "settings.txt";
        try {
            File settingsObj = new File(fileName);
            settingsObj.createNewFile();
            Scanner reader = new Scanner(settingsObj);

            // Only use new settings if there are any
            if (reader.hasNextLine()) {
                player1.setName(reader.nextLine());
                player2.setName(reader.nextLine());
                ballSpeed = reader.nextInt();
                ballSpeedIncrease = reader.nextInt();
                scoreLimit = reader.nextInt();
                PLAYER_WIDTH = reader.nextInt();
                System.out.println("Using settings from settings.txt at root");
            }
            reader.close();

            // only save settings if they were modified in the Menu class
            if (modifiedSettings) {
                // write settings
                FileWriter writer = new FileWriter(fileName);
                writer.write(player1.getName());
                writer.write("\n" + player2.getName());
                writer.write("\n" + (int) ballSpeed);
                writer.write("\n" + (int) ballSpeedIncrease);
                writer.write("\n" + scoreLimit);
                writer.write("\n" + (int) PLAYER_WIDTH);
                writer.close();
                System.out.println("Written to file");
            }
            else {
                System.out.println("Using default settings");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    /**
     * Instantiates a new Game.
     *
     * @param p1                player1
     * @param p2                player2
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
    }

    // Application entry point
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
        System.out.println("Please resize game to your liking");
        LoadSettings();
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
                gc.fillText("Click to Start", WIDTH / 2, HEIGHT / 2);
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
                    ballSpeed = 1;
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
            } else if (player2.isLastTouched()) {
                if (player2.getScore() == scoreLimit) {
                    message = "Player2 won";
                    won = true;
                } else {
                    message = "Player2 scored";
                    ballSpeed = 1;
                }

                gc.strokeText(message, WIDTH / 2, HEIGHT / 2);
                gc.setTextAlign(TextAlignment.CENTER);
                canvas.setOnMouseClicked(e -> {
                    if (!gameStarted) {
                        gameStarted = true;
                    }
                    if (won) {
                        System.exit(0);
                    }
                });
            }


        } else {
            ball.move();

            // Randomize the ball's initial speed and direction
            ballXSpeed = new Random().nextInt(3) == 0 ? 1 : -1;
            ballYSpeed = new Random().nextInt(3) == 0 ? 1 : -1;

            ballXPos = ball.getXPos(); // Update ballXPos   with the new x-position
            ballYPos = ball.getYPos(); // Update ballYPos with the new y-position

            PlayerController.controls(scene, player1, player2, ball);

            // Ensure the ball stays within the canvas boundaries
            if (ballYPos + BALL_R > HEIGHT || ballYPos < 0) {
                ball.reverseYSpeed();
            }

            view.DrawScore(gc, scoreP1, scoreP2, WIDTH);

            view.DrawBall(gc, ballXPos, ballYPos, BALL_R);

            PlayerController.PaddleCollision(ballXPos, ballYPos, playerOneXPos, playerOneYPos, playerTwoXPos, playerTwoYPos, ballSpeedIncrease, PLAYER_WIDTH, PLAYER_HEIGHT, ball, BALL_R);

            gc.setStroke(Color.WHITE); // Set stroke color to white
            gc.setTextAlign(TextAlignment.CENTER); // Set text alignment to center

            PlayerController.BallBoundsLogic(ball, player1, player2);

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