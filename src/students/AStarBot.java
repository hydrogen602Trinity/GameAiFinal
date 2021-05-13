/**
 * Author: Jonathan
 * 
 */
package students;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;
import students.stuff.AStar;
import students.stuff.Game;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Sample implementation of snake bot
 */
public class AStarBot implements Bot {
    private static final Direction[] DIRECTIONS = new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

    /**
     * Choose the direction (not rational - silly)
     * @param snake    Your snake's body with coordinates for each segment
     * @param opponent Opponent snake's body with coordinates for each segment
     * @param mazeSize Size of the board
     * @param apple    Coordinate of an apple
     * @return Direction of bot's move
     */
    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Coordinate head = snake.getHead();

        /* Get the coordinate of the second element of the snake's body
         * to prevent going backwards */
        Coordinate afterHeadNotFinal = null;
        if (snake.body.size() >= 2) {
            Iterator<Coordinate> it = snake.body.iterator();
            it.next();
            afterHeadNotFinal = it.next();
        }

        final Coordinate afterHead = afterHeadNotFinal;

        /* The only illegal move is going backwards. Here we are checking for not doing it */
        Direction[] validMoves = Arrays.stream(DIRECTIONS)
                .filter(d -> !head.moveTo(d).equals(afterHead)) // Filter out the backwards move
                .sorted()
                .toArray(Direction[]::new);

        /* Just naïve greedy algorithm that tries not to die at each moment in time */
        Direction[] notLosing = Arrays.stream(validMoves)
                .filter(d -> head.moveTo(d).inBounds(mazeSize))             // Don't leave maze
                .filter(d -> !opponent.elements.contains(head.moveTo(d)))   // Don't collide with opponent...
                .filter(d -> !snake.elements.contains(head.moveTo(d)))      // and yourself
                .sorted()
                .toArray(Direction[]::new);

        if (notLosing.length > 0) {
            double bestLen = Double.POSITIVE_INFINITY;
            Direction bestDir = null;
            for (Direction d: notLosing) {
                int len = AStar.getPathLen(new Game(apple, snake, opponent, mazeSize), head.moveTo(d));
                System.out.println(len);
                if (len < bestLen) {
                    bestLen = len;
                    bestDir = d;
                }
            }
            if (bestDir == null) {
                throw new NullPointerException("oi");
            }
            throw new RuntimeException("oi");
            // return bestDir
        }
        else return validMoves[0];
        /* Cannot avoid losing here */
    }

    @Override
    public void cleanup(int winner) {
        // TODO Auto-generated method stub
        
    }
}