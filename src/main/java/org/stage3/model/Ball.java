// Ball.java
package org.stage3.model;

import org.stage3.Game;

import java.util.Random;

/**
 * The type Ball.
 */
public class Ball {
    private double xPos;
    private double yPos;

    private double ballSpeed;
    private double xSpeed;
    private double ySpeed;
    private double ballSpeedIncrease;

    private double maxSpeed = 1.1;

    /**
     * Instantiates a new Ball.
     */
    public Ball() {
        this.xPos = Game.width / 2;
        this.yPos = Game.height / 2;
        this.xSpeed = 0.8;
        this.ySpeed = 0.8;
        this.ballSpeed = 0.8;
        this.ballSpeedIncrease = 0.1;
    }


    /**
     * Instantiates a new Ball.
     *
     * @param ballSpeed         the ball speed
     * @param ballSpeedIncrease the ball speed increase
     */
    public Ball(double ballSpeed, double ballSpeedIncrease) {
        this.xPos = Game.width / 2;
        this.yPos = Game.height / 2;
        this.xSpeed = 0.8;
        this.ySpeed = 0.8;
        this.ballSpeed = ballSpeed;
        this.ballSpeedIncrease = ballSpeedIncrease;
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public double getRadius() {
        return 15;
    }

    /**
     * Gets ball speed.
     *
     * @return the ball speed
     */
    public double getBallSpeed() {
        return ballSpeed;
    }

    /**
     * Sets ball speed.
     *
     * @param ballSpeed the ball speed
     */
    public void setBallSpeed(double ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    /**
     * Gets ball speed increase.
     *
     * @return the ball speed increase
     */
    public double getBallSpeedIncrease() {
        return ballSpeedIncrease;
    }

    /**
     * Sets ball speed increase.
     *
     * @param ballSpeedIncrease the ball speed increase
     */
    public void setBallSpeedIncrease(double ballSpeedIncrease) {
        this.ballSpeedIncrease = ballSpeedIncrease;
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
     * Gets max speed.
     *
     * @return the max speed
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Sets max speed.
     *
     * @param maxSpeed the max speed
     */
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }


    /**
     * Cap speed.
     *
     * @param increase the increase
     */
    public void increaseBallSpeed(double increase) {
        double newXSpeed = Math.abs(this.xSpeed+=increase);
        double newYSpeed = Math.abs(this.ySpeed+=increase);
        this.xSpeed += newXSpeed <= maxSpeed ? newXSpeed : ballSpeed;
        this.ySpeed += newYSpeed <= maxSpeed ? newYSpeed : ballSpeed;
    }

    public void checkSpeed() {
        this.xSpeed = xSpeed <= maxSpeed ? xSpeed : ballSpeed;
        this.ySpeed = ySpeed <= maxSpeed ? ySpeed : ballSpeed;
    }

    /**
     * Create randomized ball.
     *
     * @param xPos the x pos
     * @param yPos the y pos
     */
    public void RandomDirection(double xPos, double yPos) {
        Random random = new Random();
        this.xSpeed = random.nextInt(2) == 0 ? 1 : -1;
        this.ySpeed = random.nextInt(2) == 0 ? 1 : -1;
        setXPos(xPos);
        setYPos(yPos);
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
}
