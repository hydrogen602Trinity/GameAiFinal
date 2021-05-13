/**
 * Author: Jazmine
 * 
 */
package students.qLearning;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// import game.Point;
// import game.TPoint;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;
import students.qLearning.Node;
import students.qLearning.State;

public class AStarState implements State, Serializable{

	private Coordinate appleRel;

    private int[] wallDistance;
    private boolean[] nextToWall;
    private boolean pathFound = false;
    private Direction facing;
	private List<Node> path = new ArrayList<Node>();
	private Queue<Direction> moves = new LinkedList<Direction>();
	
    public AStarState(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Coordinate center = snake.getHead();
        this.appleRel = new Coordinate(apple.x - center.x, apple.y - center.y);

        Coordinate head = snake.getHead();
        nextToWall = new boolean[4];
        nextToWall[0] = isLethal(snake, opponent, head.moveTo(Direction.UP), mazeSize); //center.x == 0;
        nextToWall[1] = isLethal(snake, opponent, head.moveTo(Direction.DOWN), mazeSize);
        nextToWall[2] = isLethal(snake, opponent, head.moveTo(Direction.RIGHT), mazeSize);
        nextToWall[3] = isLethal(snake, opponent, head.moveTo(Direction.LEFT), mazeSize);


        // get all directions but backward cause thats illegal.

        
        facing = getMove(snake, opponent, mazeSize, apple);
       
   
		
    }
    private Direction getMove(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {

    	Node current = null;
    	Node goal = convertPoint(apple);
    	Node last;
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		List<Node> neighbors = new ArrayList<Node>();
		
		Direction move = Direction.DOWN;
		
		openList.add(convertPoint(snake.getHead()));
		while(openList.size() > 0) {
			last = current;
			current = getsmallestElement(convertPoint(snake.getHead()), convertPoint(apple),openList);
			if(current.x == apple.x && current.y == apple.y) {
				goal.setParent(last);
				break;
			}
			else {
				neighbors = convertPoints(getNearCoordinates(current));
				for(Node n: neighbors) {

					double nDist = current.costsoFar + 1;//4.16
					if(closedList.contains(n) || isLethal(snake, opponent, reversePoint(n), mazeSize)) {
						if(n.costsoFar <= nDist)
							continue;
						else
							closedList.remove(n);
					n.cost = nDist - n.costsoFar;
					}else if(openList.contains(n)){
						if(n.costsoFar <= nDist)
							continue;
						n.cost = nDist - n.costsoFar;
					} else{
						n.cost = calcDist(n,goal);
						n.costsoFar = calcDist(convertPoint(snake.getHead()), n);
						n.setParent(current);
						n.settotalCost(n.costsoFar + n.cost);
						if(!openList.contains(n))
						openList.add(n);
						}
					
					}
				closedList.add(current);
				openList.remove(current);
				}
			
			}
		
			if(current.x != apple.x && current.y != apple.y)
					System.out.print("No Path can be found");
			else{
				pathFound = true;
			
			createPath(convertPoint(snake.getHead()),goal);
			move = moves.remove();
			}
			
		
		return move;
		
    }
public void createPath(Node start, Node goal) {
	//Boolean isDone= false;
	Node pop = transfer(goal);
	
	while(!pop.equals(start)) {
		path.add(pop);
		pop = transfer(pop.parent);			
	}
	
	Collections.reverse(path);	
	path.add(0, start);
	Direction thisMove = Direction.LEFT; 
	int i =0;
	int j = 1;
	while(thisMove != null && path.size() !=1) {
		thisMove = calcMove(path.get(i), path.get(i+1));
		path.remove(0);
		System.out.print(j);
		moves.add(thisMove);
		j++;
	}
}
public Node transfer(Node a) {
	int nx = a.x;
	int ny = a.y;
	Node newPoint = new Node(nx,ny,0,0);
	newPoint.setParent(a.getParent());
	return newPoint;
}

    
    ArrayList<Coordinate> getNearCoordinates(Coordinate origin) {
        ArrayList<Coordinate> neighbours = new ArrayList<>();
        Direction[] validMoves = getLegalActions();
        for (Direction direction : validMoves)
            neighbours.add(origin.moveTo(direction));

        return neighbours;
    }
	public List<Node> convertPoints(List<Coordinate> list){
		List<Node> newPoints = new ArrayList<Node>();
		for(Coordinate p: list) {
			newPoints.add(convertPoint(p));
		}
		return newPoints;
	}
	public Node convertPoint(Coordinate p) {
		int nx = p.x;
		int ny = p.y;
		Node newPoint = new Node(nx, ny, 0, 0);
		return newPoint;
		
	}
	public Coordinate reversePoint(Node t) {
		int ox = t.x;
		int oy = t.y;
		Coordinate newPoint = new Coordinate(ox, oy);
		return newPoint;
	}

    /**
     * Computes and returns an array of all valid moves from the current state
     * 
     * Right now it just returns everything (ToDo)
     * 
     * @return all valid directions
     */
    private double calcDist(Coordinate a, Coordinate b) {
    	return Math.sqrt(Math.pow(b.x-a.x, 2) + Math.pow(b.y-a.y, 2));
    }
    private static boolean isLethal(Snake sn, Snake sn2, Coordinate pos, Coordinate mazeSize) {
        return sn.elements.contains(pos) || sn2.elements.contains(pos) || !pos.inBounds(mazeSize);
    }
	public Node getsmallestElement(Node start, Node goal, List<Node> list ) {
		double f;
		Node small = list.get(0);
		for(Node t: list) {
			f = getCostEstimate(start, t, goal);
			t.settotalCost(f);
		}
		
		for(int i = 0; i <= list.size()-1;i++) {
			if(list.get(i).totalCost < small.totalCost || list.get(i).totalCost == small.totalCost) {
					small = list.get(i);
			}
		}
		return small;
	}
	public double getCostEstimate(Node start, Node now, Node goal) {
		double f;
		double g;
		double h;
		g = calcDist(start, now);
		//now.setcostsoFar(g);
		h = calcDist(now, goal);
		//now.cost(h);
		f = g+ h;
		return f;

	}
	public Direction calcMove(Node a, Node b) {
		//(a.x,a.y)  (b.x,b.y)
		//(0,0)    (1,0) (0,1)
		Direction mv = null;
		
		if(a.x - 1 == b.x && a.y==b.y)
			mv = Direction.LEFT;
		if(a.x + 1 == b.x && a.y==b.y)
			mv = Direction.RIGHT;
		if(a.x==b.x && a.y -1 == b.y)
			mv = Direction.UP;
		if(a.x==b.x && a.y +1 == b.y)
			mv = Direction.DOWN;
		
		return mv;
		
	}
    
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