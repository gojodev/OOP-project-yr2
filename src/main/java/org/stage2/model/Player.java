package org.stage2.model;

public class Player {

    private String name;
    private double xPos;


    private double yPos;
    private int score;

    public Player() {
        this.name = "player";
        this.xPos = 0;
        this.yPos = 0;
        this.score = 0;
    }

    public Player(String name) {
        this.name = name;
        this.xPos = 0;
        this.yPos = 0;
        this.score = 0;
    }

    public  Player(String name, double xPos, double yPos, int score) {
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
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
