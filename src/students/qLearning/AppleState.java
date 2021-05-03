package students.qLearning;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

public class AppleState implements State, Serializable {
    
    private Coordinate appleRel;

    private boolean[] nextToWall;

    private Direction facing;

    private static boolean isLethal(Snake sn, Snake sn2, Coordinate pos, Coordinate mazeSize) {
        return sn.elements.contains(pos) || sn2.elements.contains(pos) || !pos.inBounds(mazeSize);
    }

    public AppleState(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Coordinate center = snake.getHead();
        this.appleRel = new Coordinate(apple.x - center.x, apple.y - center.y);
        
        // get all directions but backward cause thats illegal.
        Coordinate head = snake.getHead();

        nextToWall = new boolean[4];
        nextToWall[0] = isLethal(snake, opponent, head.moveTo(Direction.UP), mazeSize); //center.x == 0;
        nextToWall[1] = isLethal(snake, opponent, head.moveTo(Direction.DOWN), mazeSize);
        nextToWall[2] = isLethal(snake, opponent, head.moveTo(Direction.RIGHT), mazeSize);
        nextToWall[3] = isLethal(snake, opponent, head.moveTo(Direction.LEFT), mazeSize);

        /* Get the coordinate of the second element of the snake's body
            * to prevent going backwards */
        Coordinate afterHeadNotFinal = null;
        if (snake.body.size() >= 2) {
            Iterator<Coordinate> it = snake.body.iterator();
            it.next();
            afterHeadNotFinal = it.next();
        }

        facing = null;
        if (afterHeadNotFinal != null) {
            facing = afterHeadNotFinal.getDirection(head);
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

        Direction backwards = UtilStuff.getInverseDir(facing);

        /* The only illegal move is going backwards. Here we are checking for not doing it */
        Direction[] validMoves = Arrays.stream(dirs)
                .filter(d -> d != backwards) // Filter out the backwards move
                .sorted()
                .toArray(Direction[]::new);
        return validMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AppleState) {
            AppleState st = (AppleState) o;
            return appleRel.equals(st.appleRel) && //*/
                nextToWall[0] == st.nextToWall[0] && 
                nextToWall[1] == st.nextToWall[1] && 
                nextToWall[2] == st.nextToWall[2] && 
                nextToWall[3] == st.nextToWall[3];
        }

        return false;
    }

    @Override
    public int hashCode() {
        //https://www.baeldung.com/java-hashcode
        //int starter = wallDistance[0] + wallDistance[1] + wallDistance[2] + wallDistance[3];
        int result = (int) (appleRel.x ^ (appleRel.x >>> 32));
        result = 31 * result + (nextToWall[0] ? 1 : 0);
        result = 31 * result + (nextToWall[1] ? 1 : 0);
        result = 31 * result + (nextToWall[2] ? 1 : 0);
        result = 31 * result + (nextToWall[3] ? 1 : 0);
        //result = 31 * result + appleRel.x;
        result = 31 * result + appleRel.y;
        return result;
    }

    @Override
    public String toString() {
        String s = "([";
        for (boolean e: nextToWall) {
            s += e + ", ";
        }
        if (s.length() > 1) {
            s = s.substring(0, s.length()-2);
        }
        String point = "(" + appleRel.x + ", " + appleRel.y + ")";
        return s + "], " + point + ")";
    }
}
