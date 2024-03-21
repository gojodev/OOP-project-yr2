import org.stage3.Game;
import org.stage3.model.Ball;
import org.stage3.model.Player;

/**
 * The type Collision logic.
 */
class collisionLogic {
    /**
     * The Ball x pos.
     */
    double ballXPos = 0;
    /**
     * The Ball y pos.
     */
    double ballYPos = 0;
    /**
     * The Player one x pos.
     */
    double playerOneXPos = 0;
    /**
     * The Player one y pos.
     */
    double playerOneYPos = 0;
    /**
     * The Player two x pos.
     */
    double playerTwoXPos = 0;
    /**
     * The Player two y pos.
     */
    double playerTwoYPos = 0;
    /**
     * The Player width.
     */
    double PLAYER_WIDTH = 0;
    /**
     * The Player height.
     */
    double PLAYER_HEIGHT = 0;
    /**
     * The Ball r.
     */
    double BALL_R = 0;
    /**
     * The Ball speed increase.
     */

    double ballYSpeed = 0;
    double ballSpeedIncrease = 0;
    /**
     * The Score p 1.
     */
    int scoreP1 = 0;
    /**
     * The Score p 2.
     */
    int scoreP2 = 0;
    /**
     * The Game started.
     */
    boolean gameStarted = false;
    /**
     * The Width.
     */
    double WIDTH = 0;
    /**
     * The Height.
     */
    double HEIGHT = 0;
    /**
     * The Ball.
     */
    Ball ball;
    /**
     * The Player 1.
     */
    Player player1;
    /**
     * The Player 2.
     */
    Player player2;

    /**
     * Instantiates a new Collision logic.
     */
    public collisionLogic() {
        ball = new Ball();
        player1 = new Player();
        player2 = new Player();
    }

    /**
     * Paddle collision.
     */
    public void paddleCollision() {
        // Check collision with player one paddle
        if (ballXPos <= playerOneXPos + PLAYER_WIDTH && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT) {
            ball.reverseXSpeed();
            ball.reverseYSpeed();
            ball.adjustSpeed(ballSpeedIncrease);
        }

        // Check collision with player two paddle
        if (ballXPos + BALL_R >= playerTwoXPos && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) {
            ball.reverseXSpeed();
            ball.reverseYSpeed();
            ball.adjustSpeed(ballSpeedIncrease);
        }
        System.out.println("paddleCollision: Pass");
    }


    /**
     * Ball bounds logic.
     */
    public void ballBoundsLogic() {
        ballXPos = WIDTH / 2; // Reset the ball's x-position
        ballYPos = HEIGHT / 2; // Reset the ball's y-position

        // Ensure player paddles stay within the bounds of the game window
        playerOneYPos = Math.max(0, Math.min(player1.getyPos(), HEIGHT - PLAYER_HEIGHT));
        playerTwoYPos = Math.max(0, Math.min(player2.getyPos(), HEIGHT - PLAYER_HEIGHT));

        // Ensure the ball stays within the canvas boundaries
        if (ballYPos > HEIGHT || ballYPos < 0) ballYSpeed *= -1;

        // If player one misses the ball, player two scores a point
        if (ball.getXPos() < 0) {
            scoreP2++;
            player2.setScore(scoreP2);
            ball.setXPos(WIDTH / 2);
            ball.setYPos(HEIGHT / 2);
            player2.setLastTouched(true);
            gameStarted = false;
        }

        // If player two misses the ball, player one scores a point
        if (ball.getXPos() > WIDTH) {
            scoreP1++;
            player1.setScore(scoreP1);
            ball.setXPos(WIDTH / 2);
            ball.setYPos(HEIGHT / 2);
            player1.setLastTouched(true);
            gameStarted = false;
        }

        // Ensure the ball stays within the canvas boundaries
        if (ballYPos + BALL_R > HEIGHT || ballYPos < 0) {
            ball.reverseYSpeed();
        }
        System.out.println("ballBoundsLogic: Pass");
    }
}