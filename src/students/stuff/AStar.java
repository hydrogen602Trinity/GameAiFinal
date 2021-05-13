/**
 * Author: Jonathan
 * 
 */
package students.stuff;

import java.util.ArrayList;
import java.util.List;

import snakes.Direction;
import snakes.Coordinate;


class Node {
	public Coordinate p;
	public double heuristic;
	public double costSoFar;
	public Node connection;
	
	public Node(Coordinate p, Node connection, double costSoFar, Game game) {
		this.p = p;
		this.connection = connection; // may be null
		this.costSoFar = costSoFar;
		this.heuristic = computeHeuristic(game);
	}
	
	private double computeHeuristic(Game game) {
		Coordinate goal = game.goal;
        Coordinate now = p;
		//Coordinate now = game.p.getPosition();
		// Manhattan distance
		return Math.abs(goal.x - now.x) + Math.abs(goal.y - now.y);
	}
	
	public double estimatedTotalCost() {
		return heuristic + costSoFar;
	}
	
	public boolean hasConnection() {
		return connection != null;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		else if (o instanceof Coordinate) {
			return p.equals( (Coordinate) o);
		}
		else if (o instanceof Node) {
			return p.equals( ((Node) o).p);
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return "Node(" + p.toString() + ", " + costSoFar +  ")";
	}
}


public class AStar {

	public static int getPathLen(Game game, Coordinate startLoc) {
		Coordinate currLocation = startLoc;
		
		List<Coordinate> path = aStar(currLocation, game.goal, game);
        for (Coordinate c: path) {
            System.out.println("  " + c);
        }
		
		if (!path.get(0).equals(currLocation)) {
			System.err.println("??? " + currLocation.toString() + " " + path.get(0).toString());
		}
		
//		System.out.println("Goal is " + game.goal.toString() + ", start is " + game.p.getPosition().toString());
		
//		for (Coordinate p: path) {
//			System.out.print(p.toString() + " ");
//		}
//		System.out.println();

		//return moveToDir(path.get(0), path.get(1));
        // Direction d = path.get(0).getDirection(path.get(1));
        // if (d == null) {
        //     throw new NullPointerException("Direction is null?");
        // }
        // return d;
        return path.size();
	}
	
	// private static Direction moveToDir(Coordinate src, Coordinate dest) {
	// 	int dx = dest.x - src.x;
	// 	int dy = dest.y - src.y;
		
	// 	if (dy == 1 && dx == 1) { return Direction.DOWN_RIGHT; }
	// 	else if (dy == 1 && dx == -1) { return Direction.DOWN_LEFT; }
	// 	else if (dy == -1 && dx == 1) { return Direction.UP_RIGHT; }
	// 	else if (dy == -1 && dx == -1) { return Direction.UP_LEFT; }
	// 	else if (dy == 1 && dx == 0) { return Direction.DOWN; }
	// 	else if (dy == -1 && dx == 0) { return Direction.UP; }
	// 	else if (dx == 1 && dy == 0) { return Direction.RIGHT; }
	// 	else if (dx == -1 && dy == 0) { return Direction.LEFT; }
	// 	else { throw new RuntimeException("what??? dx=" + dx + ", dy=" + dy); }
	// }
	
	private static Node getNodeInList(Node node, List<Node> ls) {
		for (Node n: ls) {
			if (n.equals(node)) {
				return node;
			}
		}
		return null;
	}
	
//	private static void dumpList(ArrayList<Node> open) {
//		for (Object o: open) {
//			System.out.print(o.toString());
//			System.out.print(" ");
//		}
//		System.out.println();
//	}
	
	private static List<Coordinate> aStar(Coordinate startPoint, Coordinate goal, Game game) {
		// System.out.println("A*");
		Node start = new Node(startPoint, null, 0, game);

		ArrayList<Node> open = new ArrayList<Node>();
		ArrayList<Node> closed = new ArrayList<Node>();
		open.add(start);
		
		Node current = null;
		while (open.size() > 0) {
			current = null;
			// get smallest
			for (Node n: open) {
				if (current == null) {
					current = n;
				}
				else if (current.estimatedTotalCost() > n.estimatedTotalCost()) {
					current = n;
				}
			}
			
			//System.out.println("Analyzing " + current.toString());
			//System.out.println("open: " + open.size() + ", closed: " + closed.size());
			
			//dumpList(open);
			
			if (current.p.equals(goal)) {
				break;
			}
			
			Direction[] all = game.getLegalActions(current.p);
			
			//System.out.print("Points: ");
			for (Direction d: all) {
                Coordinate p = current.p.moveTo(d);
				//System.out.print(p.toString() + " ");
				Node nodeP = new Node(p, current, current.costSoFar + 1, game);
				
				Node nodeInClosed = getNodeInList(nodeP, closed);
				if (nodeInClosed != null) {
					if (nodeInClosed.costSoFar > nodeP.costSoFar) {
						// better path!
						closed.remove(nodeInClosed);
					}
					else {
						// worse path!
						continue;
					}
				}
				else { 
					Node nodeInOpen = getNodeInList(nodeP, open);
					if (nodeInOpen != null) {
						if (nodeInOpen.costSoFar > nodeP.costSoFar) {
							// better path!
							open.remove(nodeInOpen);
						}
						else {
							// worse path!
							continue;
						}
						
					}
					else {
						// not found in either list!
					}
				}
				
				open.add(nodeP);
			}
			//System.out.println();
			
			open.remove(current);
			closed.add(current);
		}
		
		if (current == null) {
			// nothing found
			return null;
		}
		else {
			ArrayList<Coordinate> backTrack = new ArrayList<Coordinate>();
			backTrack.add(current.p);
			while (current.hasConnection()) {
				current = current.connection;
				backTrack.add(current.p);
			}
			
			ArrayList<Coordinate> path = new ArrayList<Coordinate>();
			for (int i = backTrack.size()-1; i >= 0; --i) {
				path.add(backTrack.get(i));
			}
			
			return path;
		}
	}

}
