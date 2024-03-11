package org.stage2.controller;

import javafx.scene.Scene;
import org.stage2.model.Player;

public class PlayerListener {

    private Player p1;
    private Player p2;

    public void movePlayer(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case W:
                    PlayerController.moveUp(p1, 5);
                    break;

                case S:
                    PlayerController.moveDown(p1, -5);
                    break;

                //p2 will be copy be controlled by the computer
                case UP:
                    PlayerController.moveUp(p2, 5);
                    break;

                case DOWN:
                    PlayerController.moveDown(p2, -5);
                    break;
            }
        });
    }
}
