package org.stage2.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.stage2.model.Player;

public class PlayerListener {

    public static void movePlayer(Scene scene, Player p1, Player p2) {
        scene.setOnKeyPressed(keyEvent -> {


            if (keyEvent.getCode() == KeyCode.W) {
                p1.moveUp();
                System.out.println("p1 up");
            }

            if (keyEvent.getCode() == KeyCode.S) {
                p1.moveDown();
                System.out.println("p1 Down");
            }

            if (keyEvent.getCode() == KeyCode.UP) {
                p2.moveUp();
                System.out.println("p2 up");
            }

            if (keyEvent.getCode() == KeyCode.DOWN) {
                p2.moveDown();
                System.out.println("p2 Down");
            }
        });
    }
}