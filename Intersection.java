import java.util.LinkedList;

public abstract class Intersection {
	
	// Central location of the intersection 
	private Vector location;
	
	// whether or not there are turn lanes for the intersection 
	private boolean turnLanes; 
	
	// The motorways that are involved in the intersection 
	private LinkedList<Motorway> motorway = new LinkedList<>(); 
	
	// Grid of marking points for intersection 
	private Vector[][] marks = new Vector[3][3];
	
	public Intersection(Vector location, LinkedList<Motorway> motorway) {
		this.location  = location; 
		this.motorway = motorway; 
		
		makeGrid(); 
	}
	
	public Intersection(Vector location, LinkedList<Motorway> motorway, boolean turnLanes) {
		this(location, motorway);
		this.turnLanes = turnLanes; 
	}

	public Vector getLocation() {
		return location;
	}

	public void setLocation(Vector location) {
		this.location = location;
	}

	public LinkedList<Motorway> getMotorway() {
		return motorway;
	}

	public void setMotorway(LinkedList<Motorway> motorway) {
		this.motorway = motorway;
	}

	public boolean isTurnLanes() {
		return turnLanes;
	}

	public void setTurnLanes(boolean turnLanes) {
		this.turnLanes = turnLanes;
	}

	public Vector[][] getMarks() {
		return marks;
	}

	public void setMarks(Vector[][] marks) {
		this.marks = marks;
	}
	
	public abstract void makeGrid(); 	
	
}
