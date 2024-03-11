package org.stage2.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.stage2.model.Player;

public class PlayerListener {

    public static void movePlayer(Scene scene, Player p1, Player p2) {

        int direction = 10;

        double p1Pos = p1.getyPos();
        double p2Pos = p2.getyPos();
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.W) {
                PlayerController.move(p1, direction, p1Pos);
                System.out.println("p1 up");
            }

            if (keyEvent.getCode() == KeyCode.S) {
                PlayerController.move(p1, -direction, p1Pos);
                System.out.println("p1 Down");
            }

            if (keyEvent.getCode() == KeyCode.UP) {
                PlayerController.move(p2, direction, p2Pos);
                System.out.println("p2 up");
            }

            if (keyEvent.getCode() == KeyCode.DOWN) {
                PlayerController.move(p2, -direction, p2Pos);
                System.out.println("p2 Down");
            }
        });
    }
}
