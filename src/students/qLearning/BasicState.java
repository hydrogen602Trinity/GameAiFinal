package students.qLearning;

import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

public class BasicState implements State {
    
    private Coordinate appleRel;

    private int[] wallDistance;

    public BasicState(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Coordinate center = snake.getHead();
        this.appleRel = new Coordinate(apple.x - center.x, apple.y - center.y);

        wallDistance = new int[4];
        wallDistance[0] = center.x;
        wallDistance[1] = center.y;
        wallDistance[2] = mazeSize.x - center.x;
        wallDistance[3] = mazeSize.y - center.y;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof BasicState) {
            BasicState st = (BasicState) o;
            return appleRel.equals(st.appleRel) && 
                wallDistance[0] == st.wallDistance[0] && 
                wallDistance[1] == st.wallDistance[1] && 
                wallDistance[2] == st.wallDistance[2] && 
                wallDistance[3] == st.wallDistance[3];
        }

        return false;
    }

    @Override
    public int hashCode() {
        //https://www.baeldung.com/java-hashcode
        int starter = wallDistance[0] + wallDistance[1] + wallDistance[2] + wallDistance[3];
        int result = (int) (starter ^ (starter >>> 32));
        result = 31 * result + wallDistance[0];
        result = 31 * result + wallDistance[1];
        result = 31 * result + wallDistance[2];
        result = 31 * result + wallDistance[3];
        result = 31 * result + appleRel.x;
        result = 31 * result + appleRel.y;
        return result;
    }
}
