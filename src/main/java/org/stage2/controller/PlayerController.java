package org.stage2.controller;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.text.TextAlignment;
import org.stage2.Game;
import org.stage2.model.Ball;
import org.stage2.model.Player;
import org.stage2.view.View;

/**
 * The type Player controller.
 */
public class PlayerController {

    public static boolean isRestarted = false;

    /**
     * Move player.
     *
     * @param scene the scene
     * @param p1    the p 1
     * @param p2    the p 2
     */
    public static void controls(Scene scene, Player p1, Player p2, Ball ball) {
        double centerX = Game.WIDTH / 2;
        double centerY = Game.HEIGHT / 2;
        scene.setOnKeyPressed(keyEvent -> {

            // Player 1 controls
            if (keyEvent.getCode() == KeyCode.W) {
                p1.moveUp();
            }

            if (keyEvent.getCode() == KeyCode.S) {
                p1.moveDown();
            }

            // Player2 controls
            if (keyEvent.getCode() == KeyCode.UP) {
                p2.moveUp();
            }

            if (keyEvent.getCode() == KeyCode.DOWN) {
                p2.moveDown();
            }

            // Pause the game
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                if (Game.isPaused) {
                    System.out.println("Game Resumed");
                    Game.tl.play();
                } else {
                    System.out.println("Game Paused");
                    Game.tl.pause();
                }
                Game.isPaused = !Game.isPaused;
            }

            // Restart the game
            if (keyEvent.getCode() == KeyCode.R) {
                System.out.println("Restarted Game");
                ball.setXPos(centerX);
                ball.setXPos(centerY);

                p1.setxPos(0);
                p1.setyPos(centerY);

                p2.setxPos(centerX);
                p2.setyPos(centerY);

                p1.setScore(0);
                p2.setScore(0);
                isRestarted = true;
            }
        });
    }

}