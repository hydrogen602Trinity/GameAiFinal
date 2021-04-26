package students;

import java.util.Random;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

public class RandomAgent implements Bot {
    private static final Direction[] DIRECTIONS = new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Random random = new Random();
        Direction randomDir = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        return randomDir;
    }

    @Override
    public void cleanup(int winner) {
        // TODO Auto-generated method stub
        
    }
}