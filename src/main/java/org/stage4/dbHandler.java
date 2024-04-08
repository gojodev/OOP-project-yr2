package org.stage4;

import org.stage4.model.Ball;
import org.stage4.model.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * The type Db handler.
 */
public class dbHandler {
    private Player player1;
    private Player player2;
    ;
    private Ball ball;

    /**
     * handles writing to the databases (files).
     *
     * @param player1 the player 1
     * @param player2 the player 2
     * @param ball    the ball
     */
    public dbHandler(Player player1, Player player2, Ball ball) {
        this.player1 = player1;
        this.player2 = player2;
        this.ball = ball;
    }

    /**
     * Write settings.
     *
     * @param filename the filename
     */
    public void writeSettings(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(player1.getName());
            writer.write("\n" + player2.getName());
            writer.write("\n" + (int) ball.getBallSpeed());
            writer.write("\n" + (int) ball.getBallSpeedIncrease());
            writer.write("\n" + (int) Game.scoreLimit);
            writer.write("\n" + (int) player1.getPlayerWidth());
            System.out.println("Settings Written to settings.txt");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Write feed back.
     *
     * @param filename the filename
     */
    public void writeFeedBack(String filename) {
        // Write to feedback file from the game menu
        try {
            FileWriter writer = new FileWriter(filename);
            String feedback = Menu.feedbackgiven;

            if (Objects.equals(feedback, "")) {
                feedback = "No Feed back was given.";
                System.out.println(feedback);
            }

            writer.write(feedback);
            writer.close();

            System.out.println("Feedback: " + feedback + " , written to feedback.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
