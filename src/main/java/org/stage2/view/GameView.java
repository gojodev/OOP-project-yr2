package org.stage2.view;

public class GameView {
    public void printGameDetails(String p1, String p2, int scoreLimit, double ballSpeed, double ballSpeedIncrease, int racketSize) {
        System.out.println("p1 : " + p1);
        System.out.println("p2 : " + p2);
        System.out.println("scoreLimit : " + scoreLimit);
        System.out.println("ballSpeed : " + ballSpeed);
        System.out.println("ballSpeedIncrease : " + ballSpeedIncrease);
        System.out.println("racketSize : " + racketSize);
    }
}
