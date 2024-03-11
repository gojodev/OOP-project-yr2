package org.stage2.controller;

import org.stage2.model.Player;
import org.stage2.view.PlayerView;

public class PlayerController {
    // Model Object
    // View Object

    private Player model;
    private PlayerView view;

    public PlayerController(Player model, PlayerView view) {
        this.model = model;
        this.view = view;
    }

    // control model object
    public static void move(Player player, int direction, double currentYpos) {
        player.setyPos(currentYpos + direction);
    }
    public void updateView() {
        view.printPlayerDetails(model.getName(), model.getxPos(), model.getyPos(), model.getScore());
    }

}
