import org.junit.jupiter.api.Test;

/**
 * The type Collision logic test.
 */
class collisionLogicTest {
    private final collisionLogic tests = new collisionLogic();

    /**
     * Paddle collision.
     */
    @Test
    void paddleCollision() {
        tests.paddleCollision();
    }

    /**
     * Ball bounds logic.
     */
    @Test
    void ballBoundsLogic() {
        tests.ballBoundsLogic();
    }
}