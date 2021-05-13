/**
 * Author: Jonathan
 * 
 */
package students.stuff;

import java.util.Arrays;

import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

public class Game {
    
    public Coordinate goal;
    public Snake snake;
    public Snake enemy;
    private Coordinate mazeSize;

    public Game(Coordinate apple, Snake snake, Snake enemy, Coordinate mazeSize) {
        goal = apple;
        this.snake = snake;
        this.enemy = enemy;
        this.mazeSize = mazeSize;
    }

    public boolean isLegal(Coordinate c) {
        return mazeSize.inBounds(mazeSize) && !snake.elements.contains(c) && !enemy.elements.contains(c);
    }

    public Direction[] getLegalActions(Coordinate c) {
        Direction[] dirs = { Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT };

        Direction[] validMoves = Arrays.stream(dirs)
                .filter(d -> isLegal(c.moveTo(d))) // Filter out the bad moves
                .sorted()
                .toArray(Direction[]::new);
        return validMoves;
    }
}
