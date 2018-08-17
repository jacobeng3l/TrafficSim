import java.util.LinkedList;

/**
 * An abstract Motorway class for generic purposes 
 * @author Jacob Engelbrecht 
 * @since 2018-08-10
 * @version 0.1
 */
public abstract class Motorway {
	
	// total number of lanes 
	private int lanes; 
	
	// Unused, whether or not a shoulder exists 
	private boolean shoulder; 
	
	// The speed limit of the road 
	private int speedLimit; 
	
	public double width; 
	
	// The placement of the road on the board 
	private double location; 
	
	LinkedList<Vehicle> vehicles = new LinkedList<>(); 
	
	public LinkedList<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(LinkedList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	// Whether or not it is a simple vertical of horizontal road (to be replaced by enum for more complicated roads )
	private boolean isVertical; 
	
	private Vector[][] marks = new Vector[3][3];
	
	// Unique ID for all roads 
	private int roadID;
	
	private static int totID = 1; 
	
	/**
	 * Constructor for new Motorways
	 * @param lanes Total number of lanes
	 * @param shoulder Whether or not there is a shoulder
	 * @param speedLimit The speedLimit 
	 * @param location Where the road is on the board 
	 * @param isVertical Whether or not the road is vertical 
	 */
	public Motorway(int lanes, boolean shoulder, int speedLimit, double location, boolean isVertical, double width) {
		this.lanes = lanes; 
		this.shoulder = shoulder; 
		this.speedLimit = speedLimit; 
		this.location = location;
		this.isVertical = isVertical; 
		this.width = width; 
		roadID = totID++; 
		
		makeGrid(); 
	}
	
	public int getLanes() {
		return lanes; 
	}
	
	public void setLanes(int lanes) {
		this.lanes = lanes; 
	}
	
	public boolean getShoulder() {
		return shoulder; 
	}
	
	public void setShoulder(boolean shoulder) {
		this.shoulder = shoulder;
	}

	public int getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}

	public double getLocation() {
		return location;
	}

	public void setLocation(double location) {
		this.location = location;
	}

	public int getRoadID() {
		return roadID;
	}

	public boolean isVertical() {
		return isVertical;
	}

	public void setVerticle(boolean isVertical) {
		this.isVertical = isVertical;
	}
	
	/**
	 * An equals method 
	 * @param object Something to be compared to this 
	 * @return Boolean depending on whether the roads are equal 
	 */
	public boolean equals(Object object) {
		if(object instanceof Motorway) {
			Motorway hey = (Motorway) object; 
			if(this.getRoadID() == hey.getRoadID())
				return true;
			else
				return false; 
		}
		else
			return false; 
	}
	
	public abstract void makeGrid(); 

	public Vector[][] getMarks() {
		return marks;
	}

	public void setMarks(Vector[][] marks) {
		this.marks = marks;
	}
	
}
