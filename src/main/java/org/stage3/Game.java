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
     * The constant gameStarted.
     */
    public static boolean gameStarted = false;
    private Canvas canvas;
    private Ball ball = new Ball();

    private Player player1 = new Player();
    private Player player2 = new Player();

    private View view;

    private PlayerController playerController;

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

    private double scoreLimit = 2;

    private double player_width = 15;

    private double player_height = 100;


    /**
     * Instantiates a new Game.
     */
    public Game() {
        player1.setName("player1");
        player2.setName("player2");
        ball.setBallSpeed(1);
        this.scoreLimit = 2;
        ball.setBallSpeedIncrease(1.5);

        player1.setPlayerWidth(player_width);
        player2.setPlayerWidth(player_width);
        player1.setPlayerHeight(100);
        player2.setPlayerHeight(100);
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
                ball.setBallSpeed(reader.nextInt());
                ball.setBallSpeedIncrease(reader.nextInt());
                this.scoreLimit = reader.nextInt();
                double PLAYER_WIDTH = reader.nextInt();
                player1.setPlayerWidth(PLAYER_WIDTH);
                player2.setPlayerWidth(PLAYER_WIDTH);
                System.out.println("Using settings from settings.txt at root");
            }
            reader.close();

            // only save settings if they were modified in the Menu class
            if (modifiedSettings) {
                // write settings
                FileWriter writer = new FileWriter(fileName);
                writer.write(player1.getName());
                writer.write("\n" + player2.getName());
                writer.write("\n" + (int) ball.getBallSpeed());
                writer.write("\n" + (int) ball.getBallSpeedIncrease());
                writer.write("\n" + (int) scoreLimit);
                writer.write("\n" + (int) player1.getPlayerWidth());
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
        ball.setBallSpeed(ballSpeed);
        this.scoreLimit = scoreLimit;
        ball.setBallSpeedIncrease(ballSpeedIncrease);
        player1.setPlayerWidth(racketSize);
        player2.setPlayerWidth(racketSize);

        player1.setPlayerHeight(100);
        player2.setPlayerHeight(100);
    }

    // Application entry point
    public void start(Stage primaryStage) throws FileNotFoundException {
        System.out.println("Please resize game to your liking");
        LoadSettings();
        primaryStage.setTitle("PONG Game Project");
        Pane root = new Pane();
        scene = new Scene(root, WIDTH, HEIGHT);

        ball = Ball.createRandomizedBall(WIDTH / 2, HEIGHT / 2);

        canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D(); // Get the graphics context from the canvas
        root.getChildren().add(canvas);
        view = new View(gc, ball, player1, player2);

        playerController = new PlayerController(scene, player1, player2, ball);
        playerController.controls();

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
            }
        };

        scene.widthProperty().addListener(resizeListener);
        scene.heightProperty().addListener(resizeListener);

        primaryStage.setScene(scene); // Set the scene with the canvas
        primaryStage.show(); // Display the stage

        player1.setyPos(HEIGHT/2);
        player2.setyPos(HEIGHT/2);

        double PLAYER_WIDTH = player1.getPlayerWidth();
        player1.setxPos(0);
        player2.setxPos(WIDTH-PLAYER_WIDTH);

        tl.play(); // Start the animation timeline
    }

    // Method to handle the game logic and drawing
    private void run(GraphicsContext gc) throws InterruptedException {
        // Check if the game window becomes blank
        if (WIDTH == 0 || HEIGHT == 0) {
            System.out.println("Error Game window dimensions are invalid. Please resize the window.");
            return;
        }

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 25));

        if (!gameStarted) {
            if (player1.getScore() == 0 && player2.getScore() == 0) {
                gc.fillText("Click to Start", WIDTH / 2, HEIGHT / 2);
                gc.setTextAlign(TextAlignment.CENTER);
                canvas.setOnMouseClicked(e -> {
                    if (!gameStarted) {
                        gameStarted = true;
                    }
                });
            }

            String message;
            if (player1.isLastTouched() && !PlayerController.isRestarted) {
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
            } else if (player2.isLastTouched() && !PlayerController.isRestarted) {
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
                    if (won) {
                        System.exit(0);
                    }
                });
            }


        } else {
            ball.move();

            // Update the view object everytime the game is running (10ms)
            view = new View(gc, ball, player1, player2);
            playerController = new PlayerController(scene, player1, player2, ball);

            // Randomize the ball's initial speed and direction
            double ballXSpeed = new Random().nextInt(3) == 0 ? 1 : -1;
            double ballYSpeed = new Random().nextInt(3) == 0 ? 1 : -1;

            ball.setXSpeed(ballXSpeed);
            ball.setYSpeed(ballYSpeed);

            playerController.controls();

            view.DrawRackets();
            playerController.PaddleCollision();

            view.DrawScore(player1.getScore(), player2.getScore());

            view.DrawBall();
            playerController.BallBoundsLogic(ball, player1, player2);
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