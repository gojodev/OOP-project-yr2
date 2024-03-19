// Ball.java
package org.stage2.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * The type Ball.
 */
public class Ball  {
    private double xPos;
    private double yPos;
    private double xSpeed = 1;
    private double ySpeed = 1;

    private double BALL_R = 15;

    private Circle ballz;


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
        this.ballz = new Circle(BALL_R * 2);
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


    /**
     * Gets speed.
     *
     * @return the speed
     */
    public double getxSpeed() {
        return this.xSpeed;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public double getySpeed() {
        return this.ySpeed;
    }

    /**
     * Adjust speed.
     *
     * @param increase the increase
     */
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
        double xSpeed = random.nextInt(2) == 0 ? 1 : -1;
        double ySpeed = random.nextInt(2) == 0 ? 1 : -1;
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
     * Move the ball with whatever speed it currently has.
     */
    public void move() {
        xPos += xSpeed;
        yPos += ySpeed;
    }

    /**
     * Gets balls.
     *
     * @return the balls
     */
    public Circle getBalls() {
        return ballz;
    }
}
