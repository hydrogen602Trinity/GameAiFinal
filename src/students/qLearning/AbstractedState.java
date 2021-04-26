package students.qLearning;

import java.util.Iterator;

import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

public class AbstractedState implements State {
    // relative to snake head and direction of snake!

    private Coordinate appleRel;

    private int[] wallDistance;

    public AbstractedState(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Coordinate head = snake.getHead();
        //appleRel = new Coordinate(apple.x - center.x, apple.y - center.y);

        //wallDistance = new int[4];

        // need to rotate into the snakes dir

        /* Get the coordinate of the second element of the snake's body
         * to prevent going backwards */
        Coordinate afterHeadNotFinal = null;
        if (snake.body.size() >= 2) {
            Iterator<Coordinate> it = snake.body.iterator();
            it.next();
            afterHeadNotFinal = it.next();
        }

        final Coordinate afterHead = afterHeadNotFinal;
        
        final Direction facing = (afterHead == null) ? Direction.UP : afterHead.getDirection(head);
        if (facing == null) {
            throw new NullPointerException("oi");
        }

        


    }

    /**
     * Computes and returns an array of all valid moves from the current state
     * 
     * Right now it just returns everything (ToDo)
     * 
     * @return all valid directions
     */
    public Direction[] getLegalActions() {
        Direction[] dirs = { Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT };
        return dirs;
    }
}
