package org.stage2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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


    @Override
    public void start(Stage primaryStage) {
        // Create labels and text fields for entering player names
        Label player1Label = new Label("Player 1 Name:");
        player1NameTextField = new TextField("p1");

        Label player2Label = new Label("Player 2 Name:");
        player2NameTextField = new TextField("p2");

        // Create text fields for setting score limit and ball speed increase
        Label scoreLimitLabel = new Label("Score Limit:");
        scoreLimitTextField = new TextField("3");

        Label ballSpeedLabel = new Label("Ball Speed (0.5 recommended):");
        ballSpeedTextField = new TextField("0.5");

        Label ballSpeedIncreaseLabel = new Label("Ball Speed Increase (0.5 recommended):");
        ballSpeedIncreaseTextField = new TextField("0.5");

        Label racketSizeLabel = new Label("Racket SIze(15 recommended): ");
        racketWidthField = new TextField("15");

        // Create a button to start the game
        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(event -> {
            try {
                startGame(primaryStage);
            } catch (Exception e) {
                System.out.println("Error Failed to start the game: " + e.getMessage());
            }
        });

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
                startGameButton,
                quitButton
        );

        // Create a scene with the VBox
        Scene scene = new Scene(menuLayout, 450, 450);

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

        // Instantiate and start the game with selected settings
         Game game = new Game(player1Name, player2Name, scoreLimit, ballSpeed, ballSpeedIncrease, racketSize);
        game.start(primaryStage);
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
// Test the Menu class
    public static void main(String[] args) {
        launch(args);
    }
}
