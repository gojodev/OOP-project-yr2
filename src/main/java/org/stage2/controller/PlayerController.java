package org.stage2.controller;

import org.stage2.model.Player;
import org.stage2.view.PlayerView;

public class PlayerController {
    // Model Object
    // View Object

    private Player model;
    public PlayerController(Player model) {
        this.model = model;
    }

    // control model object
    public static void move(Player player, int direction, double currentYpos) {
        player.setyPos(currentYpos + direction);
    }

}
