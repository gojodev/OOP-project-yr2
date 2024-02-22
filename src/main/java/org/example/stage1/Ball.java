// Ball.java
package org.example.stage1;

import java.util.Random;

/**
 * The type Ball.
 */
public class Ball {
    private double xPos;
    private double yPos;
    private double xSpeed = 1;
    private double ySpeed = 1;

    /**
     * Instantiates a new Ball.
     *
     * @param xPos   the x pos
     * @param yPos   the y pos
     * @param xSpeed the x speed
     * @param ySpeed the y speed
     */
    public Ball(double xPos, double yPos, double xSpeed, double ySpeed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    /**
     * Gets x pos.
     *
     * @return the x pos
     */
    public double getXPos() {
        return xPos;
    }

    /**
     * Gets y pos.
     *
     * @return the y pos
     */
    public double getYPos() {
        return yPos;
    }


    /**
     * Sets x pos.
     *
     * @param xPos the x pos
     */
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    /**
     * Sets y pos.
     *
     * @param yPos the y pos
     */
    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    /**
     * Sets x speed.
     *
     * @param xSpeed the x speed
     */
    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    /**
     * Sets y speed.
     *
     * @param ySpeed the y speed
     */
    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    // Method to adjust the ball's speed by a given percentage
    public void adjustSpeed(double increase) {
        // Increase the speed by the given percentage
        this.xSpeed += increase;
        this.ySpeed += increase;
    }

    /**
     * Create randomized ball.
     *
     * @param xPos the x pos
     * @param yPos the y pos
     * @return the ball
     */
    public static Ball createRandomizedBall(double xPos, double yPos) {
        Random random = new Random();
        double xSpeed = random.nextInt(3) == 0 ? 1 : -1;
        double ySpeed = random.nextInt(3) == 0 ? 1 : -1;
        return new Ball(xPos, yPos, xSpeed, ySpeed);
    }

    /**
     * Reverse x speed.
     */
// Method to reverse the xSpeed of the ball
    public void reverseXSpeed() {
        xSpeed *= -1;
    }

    /**
     * Reverse y speed.
     */
// Method to reverse the ySpeed of the ball
    public void reverseYSpeed() {
        ySpeed *= -1;
    }

    /**
     * Move.
     */
    public void move() {
        xPos += xSpeed;
        yPos += ySpeed;
    }

}
