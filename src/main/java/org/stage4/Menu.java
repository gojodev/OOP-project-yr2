package org.stage4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.stage4.model.Ball;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * The type Menu.
 */
public class Menu extends Application {

    private TextField player1NameTextField;
    private TextField player2NameTextField;
    private TextField scoreLimitTextField;
    private TextField ballSpeedIncreaseTextField;

    private TextField racketWidthField;
    private TextField ballSpeedTextField;
    private TextField FeedBackField;
    /**
     * The constant settingsFromMenu.
     */
    public static boolean settingsFromMenu = false;
    /**
     * The constant feedbackGiven.
     */
    public static boolean feedbackGiven = false;
    /**
     * The constant feedbackgiven.
     */
    public static String feedbackgiven = "I loved the game";

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Create labels and text fields for entering player names
        Label player1Label = new Label("Player 1 Name:");
        player1NameTextField = new TextField("p1");

        Label player2Label = new Label("Player 2 Name:");
        player2NameTextField = new TextField("p2");

        // Create text fields for setting score limit and ball speed increase
        Label scoreLimitLabel = new Label("Score Limit:");
        scoreLimitTextField = new TextField("3");

        Label ballSpeedLabel = new Label("Ball Speed");
        ballSpeedTextField = new TextField("1");

        Label ballSpeedIncreaseLabel = new Label("Ball Speed Increase");
        ballSpeedIncreaseTextField = new TextField("0.1");

        Label racketSizeLabel = new Label("Racket SIze");
        racketWidthField = new TextField("15");

        Label FeedBackLabel = new Label("FeedBack");
        FeedBackField = new TextField("I loved the game");

        // Create a button to start the game
        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(event -> {
            try {
                startGame(primaryStage);
            } catch (Exception e) {
                System.out.println("Error Failed to start the game: " + e.getMessage());
            }
        });

        Text infoText = new Text("""
                You can press:
                \t-W to move player 1 up
                \t-S to move player 1 down
                                
                \t-UP to move player 2 up
                \t-DOWN to move player 2 down
                                
                \t-ESC to pause the game
                \t-R to restart at any time
                                
                \t-T to save current Game settings
                """);

        // Create a button to quit the game
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(event -> primaryStage.close());

        // Create a VBox to hold the menu items
        VBox menuLayout = new VBox(20);
        menuLayout.setPadding(new Insets(20));
        menuLayout.setAlignment(Pos.CENTER); // Center the menu vertically
        menuLayout.getChildren().addAll(
                player1Label, player1NameTextField,
                player2Label, player2NameTextField,
                scoreLimitLabel, scoreLimitTextField,
                ballSpeedLabel, ballSpeedTextField,
                ballSpeedIncreaseLabel, ballSpeedIncreaseTextField,
                racketSizeLabel, racketWidthField,
                FeedBackLabel, FeedBackField,
                startGameButton,
                infoText,
                quitButton
        );

        // Create a scene with the VBox
        Scene scene = new Scene(menuLayout, 800, 800);

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pong Game Menu");
        primaryStage.show();
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
    }

    // Method to start the game with player names and selected settings
    private void startGame(Stage primaryStage) throws Exception {
        String player1Name = player1NameTextField.getText();
        String player2Name = player2NameTextField.getText();
        int scoreLimit = Integer.parseInt(scoreLimitTextField.getText());
        double ballSpeed = Double.parseDouble(ballSpeedTextField.getText());
        double ballSpeedIncrease = Double.parseDouble(ballSpeedIncreaseTextField.getText());
        int racketSize = Integer.parseInt(racketWidthField.getText());

        // The data to be given to Game.java
        Ball ball = new Ball(ballSpeed, ballSpeedIncrease);
        Game game = new Game(player1Name, player2Name, scoreLimit, racketSize, ball);
        settingsFromMenu = true;
        feedbackGiven = true;
        feedbackgiven = FeedBackField.getText();

        game.start(primaryStage);
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
