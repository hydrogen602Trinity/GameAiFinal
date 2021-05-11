package snakes;

/**
 * This class is responsible for the game's GUI window
 */
public class SnakesWindowless implements Runnable {
    protected SnakeGame game;
    protected final static int TIME_LIMIT_PER_GAME = 3 * 60 * 1000; // time limit in mills

    protected boolean running = false;

    /**
     * Creates and set ups the window
     * @param game main game flow with all its states within
     */
    public SnakesWindowless(SnakeGame game) {
        this.game = game;
    }

    /**
     * Runs the UI
     */
    public void run() {
        running = true;
        long startTime = System.currentTimeMillis();
        while(running) {
            long t = System.currentTimeMillis();

            try {
                running = game.runOneStep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long elapsed = System.currentTimeMillis() - t;

            // check for time limit
            if (System.currentTimeMillis() - startTime >= TIME_LIMIT_PER_GAME) {
                int snake0_size = game.snake0.body.size();
                int snake1_size = game.snake1.body.size();
                game.gameResult = (snake0_size > snake1_size ? 1 : 0) + " - " + (snake1_size > snake0_size ? 1 : 0);
                running = false;
                System.out.println("Round time left (" + (TIME_LIMIT_PER_GAME / 1000) + "seconds) \n");
            }
        }

        //JOptionPane.showMessageDialog(null, game.gameResult, "Game results", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Closes the frame
     */
    public void closeWindow() {
    }
}

