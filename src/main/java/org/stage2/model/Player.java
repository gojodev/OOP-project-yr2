package org.stage2.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Player {

    private String name;
    private double xPos;


    private double yPos;
    private int score;

    private double player_width;

    private Rectangle paddle;

    private boolean lastTouched;


    public boolean isLastTouched() {
        return lastTouched;
    }

    public void setLastTouched(boolean lastTouched) {
        this.lastTouched = lastTouched;
    }

    public Player() {
        this.name = "player";
        this.xPos = 0;
        this.yPos = 0;
        this.score = 0;
        this.lastTouched = false;
        this.paddle = new Rectangle(50, 50);
    }

    public Player(String name) {
        this.name = name;
        this.xPos = 0;
        this.yPos = 0;
        this.score = 0;
    }

    public Player(String name, double xPos, double yPos, int score) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
        paddle.setX(xPos);
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
        paddle.setY(yPos);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Rectangle getPaddle(GraphicsContext gc) {
        gc.fillRect(xPos, yPos, player_width, player_width);
        return paddle;
    }

    public void moveUp() {
        this.yPos -= 15;
    }
    public void moveDown() {
        this.yPos += 15;
    }
}
