package students.qLearning;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import game.TPoint;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

public class AStarState implements State, Serializable{

	private Coordinate appleRel;

    private boolean[] nextToWall;
    
    private int[] wallDistance;

    private Direction facing;

    public AStarState(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Coordinate center = snake.getHead();
        this.appleRel = new Coordinate(apple.x - center.x, apple.y - center.y);

        Coordinate head = snake.getHead();
        
        nextToWall = new boolean[4];
        nextToWall[0] = isLethal(snake, opponent, head.moveTo(Direction.UP), mazeSize); //center.x == 0;
        nextToWall[1] = isLethal(snake, opponent, head.moveTo(Direction.DOWN), mazeSize);
        nextToWall[2] = isLethal(snake, opponent, head.moveTo(Direction.RIGHT), mazeSize);
        nextToWall[3] = isLethal(snake, opponent, head.moveTo(Direction.LEFT), mazeSize);

        
        
        Coordinate enemy = opponent.getHead();
        Direction endir = enemy.getDirection(enemy);

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
       facing = getMove(snake, apple);
  
		
    }
    
    private Direction getMove(Snake snake, Coordinate apple) {
    	double count = 0;
    	
    	HashMap<Double,Direction> moves = new HashMap<Double, Direction>();
    	
    	ArrayList<Coordinate> neighbours = getNearCoordinates(snake.getHead());
    	
    	for(Coordinate c: neighbours) {
    		double st = steps(apple, c);
    		if(st < count)
    			count = st;
    	}
    	
    	
    	return moves.get(count);
    	
    	
    	
    }
    
    public double steps(Coordinate a, Coordinate b) {
    	return Math.abs(Math.sqrt(b.x - a.x))+ Math.sqrt((b.y - a.y));
    }
    
    
    
    ArrayList<Coordinate> getNearCoordinates(Coordinate origin) {
        ArrayList<Coordinate> neighbours = new ArrayList<>();
        
        Direction[] validMoves =getLegalActions();
        for(Direction d: validMoves)
        	neighbours.add(origin.moveTo(d));

        return neighbours;
    }
    
    private static boolean isLethal(Snake sn, Snake sn2, Coordinate pos, Coordinate mazeSize) {
        return sn.elements.contains(pos) || sn2.elements.contains(pos) || !pos.inBounds(mazeSize);
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
        if (o instanceof AStarState) {
            AStarState st = (AStarState) o;
            return appleRel.equals(st.appleRel) && //*/
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

    @Override
    public String toString() {
        String s = "[";
        for (int e: wallDistance) {
            s += e + ", ";
        }
        if (s.length() > 1) {
            s = s.substring(0, s.length()-2);
        }
        return s + "]";
    }

}
