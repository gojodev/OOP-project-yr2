package org.stage2.controller;

import org.stage2.model.Player;

public class PlayerController {
    private Player model;
    public PlayerController(Player model) {
        this.model = model;
    }

    // control model object
    public static void move(Player player, int direction, double currentYpos) {
        player.setyPos(currentYpos + direction);
    }
}
