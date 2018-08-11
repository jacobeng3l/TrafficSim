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
	
	// The placement of the road on the board 
	private double location; 
	
	// Whether or not it is a simple vertical of horizontal road (to be replaced by enum for more complicated roads )
	private boolean isVertical; 
	
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
	public Motorway(int lanes, boolean shoulder, int speedLimit, double location, boolean isVertical) {
		this.lanes = lanes; 
		this.shoulder = shoulder; 
		this.speedLimit = speedLimit; 
		this.location = location;
		this.isVertical = isVertical; 
		roadID = totID++; 
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
	
}
