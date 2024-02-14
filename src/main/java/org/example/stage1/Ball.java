package org.example.stage1;

import java.util.Random;

public class Ball {
    private double xPos;
    private double yPos;
    private double xSpeed;
    private double ySpeed;

    public Ball(double xPos, double yPos, double xSpeed, double ySpeed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public static Ball createRandomizedBall(double xPos, double yPos) {
        Random random = new Random();
        double xSpeed = random.nextInt(2) == 0 ? 1 : -1;
        double ySpeed = random.nextInt(2) == 0 ? 1 : -1;
        return new Ball(xPos, yPos, xSpeed, ySpeed);
    }
}
