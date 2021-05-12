package students.qLearning;

import snakes.Coordinate;

public class Node extends Coordinate{
	public double costsoFar = 0.0;
	public double totalCost = 0.0;
	public double cost = 0.0;
	public Node parent;
	
	
	public Node(int x, int y, double costsoFar, double totalCost) {
		super(x,y);
		this.costsoFar = costsoFar;
		this.totalCost = totalCost;
	}
	
	public Node(Node n) {
		super(n.x,n.y);
		this.costsoFar = n.costsoFar;
		this.totalCost = n.totalCost;
	}
	public void setcostsoFar(double cost) {
		this.costsoFar = cost;
	}
	public void settotalCost(double cost) {
		this.totalCost = cost;
	}
	public void cost(double x) {
		this.cost = x;
	}
	public void setParent(Node pap) {
		this.parent = pap;
	}
	public Node getParent() {
		return parent;
	}
	public  double getCost() {
		return this.cost;
	}
	public double getcostsoFar() {
		return this.costsoFar;
	}
	public double gettotalCost() {
		return this.totalCost;
	}
	public double getF() {
		return this.cost + this.costsoFar;
	}

	

}