package org.example.stage1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends Application {

    private TextField player1NameTextField;
    private TextField player2NameTextField;
    private Slider ballSpeedSlider;
    private Slider racketSizeSlider;
    private Slider racketThicknessSlider;
    private Slider scoreLimitSlider;
    private Slider ballSpeedIncreaseSlider;

    @Override
    public void start(Stage primaryStage) {
        // Create labels and text fields for entering player names
        Label player1Label = new Label("Player 1 Name:");
        player1NameTextField = new TextField();
        Label player2Label = new Label("Player 2 Name:");
        player2NameTextField = new TextField();

        // Create sliders for setting ball speed, racket size, racket thickness, score limit, and ball speed increase
        Label ballSpeedLabel = new Label("Ball Speed:");
        ballSpeedSlider = new Slider(1, 1, 5); // Min: 1, Max: 10, Initial: 5
        ballSpeedSlider.setShowTickLabels(true);
        ballSpeedSlider.setShowTickMarks(true);

        Label racketSizeLabel = new Label("Racket Size:");
        racketSizeSlider = new Slider(50, 200, 100); // Min: 50, Max: 200, Initial: 100
        racketSizeSlider.setShowTickLabels(true);
        racketSizeSlider.setShowTickMarks(true);

        Label racketThicknessLabel = new Label("Racket Thickness:");
        racketThicknessSlider = new Slider(5, 20, 10); // Min: 5, Max: 20, Initial: 10
        racketThicknessSlider.setShowTickLabels(true);
        racketThicknessSlider.setShowTickMarks(true);

        Label scoreLimitLabel = new Label("Score Limit:");
        scoreLimitSlider = new Slider(1, 10, 5); // Min: 1, Max: 10, Initial: 5
        scoreLimitSlider.setShowTickLabels(true);
        scoreLimitSlider.setShowTickMarks(true);

        Label ballSpeedIncreaseLabel = new Label("Ball Speed Increase:");
        ballSpeedIncreaseSlider = new Slider(1, 5, 1); // Min: 1, Max: 5, Initial: 1
        ballSpeedIncreaseSlider.setShowTickLabels(true);
        ballSpeedIncreaseSlider.setShowTickMarks(true);

        // Create a button to start the game
        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(event -> {
            try {
                startGame(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Create a VBox to hold the menu items
        VBox menuLayout = new VBox(20);
        menuLayout.setPadding(new Insets(20));
        menuLayout.getChildren().addAll(
                player1Label, player1NameTextField,
                player2Label, player2NameTextField,
                ballSpeedLabel, ballSpeedSlider,
                racketSizeLabel, racketSizeSlider,
                racketThicknessLabel, racketThicknessSlider,
                scoreLimitLabel, scoreLimitSlider,
                ballSpeedIncreaseLabel, ballSpeedIncreaseSlider,
                startGameButton
        );

        // Create a scene with the VBox
        Scene scene = new Scene(menuLayout, 800, 800);

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pong Game Settings");
        primaryStage.show();
    }

    // Method to start the game with player names and selected settings
    private void startGame(Stage primaryStage) throws Exception {
        String player1Name = player1NameTextField.getText();
        String player2Name = player2NameTextField.getText();
        int ballSpeed = (int) ballSpeedSlider.getValue();
        double racketSize = racketSizeSlider.getValue();
        double racketThickness = racketThicknessSlider.getValue();
        int scoreLimit = (int) scoreLimitSlider.getValue();
        int ballSpeedIncrease = (int) ballSpeedIncreaseSlider.getValue();

        // Instantiate and start the game with selected settings
        Game game = new Game(player1Name, player2Name, ballSpeed, racketSize, racketThickness, scoreLimit, ballSpeedIncrease);
        game.start(primaryStage);
    }

    // Test the Menu class
    public static void main(String[] args) {
        launch(args);
    }
}
