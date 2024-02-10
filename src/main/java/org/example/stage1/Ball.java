package org.example.stage1;

public class Ball {
    private double Xpos;
    private double Ypos;
    private double Xdir;
    private double Ydir;
    private double speed;

    // Getters and Setters
    public double getXpos() {
        return Xpos;
    }

    public void setXpos(double xpos) {
        Xpos = xpos;
    }

    public double getYpos() {
        return Ypos;
    }

    public void setYpos(double ypos) {
        Ypos = ypos;
    }

    public double getXdir() {
        return Xdir;
    }

    public void setXdir(double xdir) {
        Xdir = xdir;
    }

    public double getYdir() {
        return Ydir;
    }

    public void setYdir(double ydir) {
        Ydir = ydir;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // Other methods for updating position, resetting, etc.
}
