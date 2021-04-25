package students;

import java.util.Optional;
import java.util.Random;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;
import students.qLearning.BasicState;
import students.qLearning.State;

public class QLearningBot implements Bot {
    private static final Direction[] DIRECTIONS = new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

    private QLearning qStuff;

    public QLearningBot() {
        qStuff = new QLearning();
    }



    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        State st = new BasicState(snake, opponent, mazeSize, apple);

        Optional<Direction> d = qStuff.computeBestAction(st);

        if (d.isPresent()) {
            return d.get();
        }
        // returned nothing, so random
        Random random = new Random();
        Direction randomDir = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        return randomDir;
    }
}