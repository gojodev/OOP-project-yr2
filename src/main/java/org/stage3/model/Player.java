package org.stage3.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * The type Player.
 */
public class Player {

    private String name;
    private double xPos;


    private double yPos;
    private int score;

    private double player_width;

    private double player_height;

    private boolean lastTouched;

    /**
     * The constant moveSpeed.
     */
    public static int moveSpeed = 15;


    /**
     * Is last touched boolean.
     *
     * @return the boolean
     */
    public boolean isLastTouched() {
        return lastTouched;
    }

    /**
     * Sets last touched.
     *
     * @param lastTouched the last touched
     */
    public void setLastTouched(boolean lastTouched) {
        this.lastTouched = lastTouched;
    }

    /**
     * Instantiates a new Player.
     */
    public Player() {
        this.name = "player";
        this.xPos = 0;
        this.yPos = 0;
        this.score = 0;
        this.lastTouched = false;
        this.player_width = 15;
        this.player_height = 100;
    }

    /**
     * Instantiates a new Player.
     *
     * @param name  the name
     * @param xPos  the x pos
     * @param yPos  the y pos
     * @param score the score
     */
    public Player(String name, double xPos, double yPos, int score) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.score = score;
    }

    /**
     * Gets player width.
     *
     * @return the player width
     */
    public double getPlayerWidth() {
        return player_width;
    }

    /**
     * Sets player width.
     *
     * @param width the width
     */
    public void setPlayerWidth(double width) {
        this.player_width = width;
    }

    /**
     * Gets player height.
     *
     * @return the player height
     */
    public double getPlayerHeight() {
        return player_height;
    }

    /**
     * Sets player height.
     *
     * @param height the height
     */
    public void setPlayerHeight(double height) {
        this.player_height = height;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets pos.
     *
     * @return the pos
     */
    public double getxPos() {
        return xPos;
    }

    /**
     * Increase score.
     */
    public void increaseScore() {
        this.score++;
    }

    /**
     * Sets pos.
     *
     * @param xPos the x pos
     */
    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    /**
     * Gets pos.
     *
     * @return the pos
     */
    public double getyPos() {
        return yPos;
    }

    /**
     * Sets pos.
     *
     * @param yPos the y pos
     */
    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }


    /**
     * Move up.
     */
    public void moveUp() {
        this.yPos -= moveSpeed;
    }

    /**
     * Move down.
     */
    public void moveDown() {
        this.yPos += moveSpeed;
    }
}
