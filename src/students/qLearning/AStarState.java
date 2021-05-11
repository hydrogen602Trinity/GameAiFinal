package students.qLearning;

import java.io.Serializable;
// import java.util.ArrayList;
import java.util.Arrays;
// import java.util.HashMap;
import java.util.Iterator;

import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

public class AStarState implements State, Serializable {

    private boolean[] nextToWall;

    private Direction appleDir;

    private Direction facing;

    public AStarState(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Coordinate head = snake.getHead();
        nextToWall = new boolean[4];
        nextToWall[0] = isLethal(snake, opponent, head.moveTo(Direction.UP), mazeSize); // center.x == 0;
        nextToWall[1] = isLethal(snake, opponent, head.moveTo(Direction.DOWN), mazeSize);
        nextToWall[2] = isLethal(snake, opponent, head.moveTo(Direction.RIGHT), mazeSize);
        nextToWall[3] = isLethal(snake, opponent, head.moveTo(Direction.LEFT), mazeSize);

        // get all directions but backward cause thats illegal.

        /*
         * Get the coordinate of the second element of the snake's body to prevent going
         * backwards
         */

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

        appleDir = getMove(snake, apple);
        if (appleDir == null) {
            throw new NullPointerException("Apple direction is null");
        }
    }

    private Direction getMove(Snake snake, Coordinate apple) {
        // double count = 0;
        // ArrayList<Coordinate> neighbours = new ArrayList<>();
        // HashMap<Double, Direction> score = new HashMap<Double, Direction>();
        // neighbours.add(snake.getHead());
        // while (!neighbours.isEmpty()) {
            // ArrayList<Coordinate> check = new ArrayList<>();
            // check = getNearCoordinates(snake.getHead());
        
        double bestDis = Double.POSITIVE_INFINITY;
        Direction bestDir = null;
        for (Direction d : getLegalActions()) {
            Coordinate c = snake.getHead().moveTo(d);
            double dist = calcDist(c, apple);
            // Direction d = snake.getHead().getDirection(apple); doesnt work

            // score.put(dist, d);
            if (dist < bestDis) {
                bestDis = dist;
                bestDir = d;
            }
                

        }
            // neighbours.remove(0);
        // }

        return bestDir; // score.get(count);

    }

    // private ArrayList<Coordinate> getNearCoordinates(Coordinate origin) {
    //     ArrayList<Coordinate> neighbours = new ArrayList<>();
    //     Direction[] validMoves = getLegalActions();
    //     for (Direction direction : validMoves)
    //         neighbours.add(origin.moveTo(direction));

    //     return neighbours;
    // }

    /**
     * Computes and returns an array of all valid moves from the current state
     *
     * Right now it just returns everything (ToDo)
     *
     * @return all valid directions
     */
    private double calcDist(Coordinate a, Coordinate b) {
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }

    private static boolean isLethal(Snake sn, Snake sn2, Coordinate pos, Coordinate mazeSize) {
        return sn.elements.contains(pos) || sn2.elements.contains(pos) || !pos.inBounds(mazeSize);
    }

    public Direction[] getLegalActions() {
        Direction[] dirs = { Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT };

        Direction backwards = UtilStuff.getInverseDir(facing);

        /*
         * The only illegal move is going backwards. Here we are checking for not doing
         * it
         */
        Direction[] validMoves = Arrays.stream(dirs).filter(d -> d != backwards) // Filter out the backwards move
                .sorted().toArray(Direction[]::new);
        return validMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AStarState) {
            AStarState st = (AStarState) o;
            return appleDir.equals(st.appleDir) && //*/
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
        int result = appleDir.hashCode();
        result = 31 * result + (nextToWall[0] ? 1 : 0);
        result = 31 * result + (nextToWall[1] ? 1 : 0);
        result = 31 * result + (nextToWall[2] ? 1 : 0);
        result = 31 * result + (nextToWall[3] ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        String s = "[";
        for (boolean e : nextToWall) {
            s += e + ", ";
        }
        if (s.length() > 1) {
            s = s.substring(0, s.length() - 2);
        }
        return s + "]";
    }
}
