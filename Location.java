/**
 * A location class to hold position on what road for Vehicles normally 
 * @author Jacob Engelbrecht 
 * @since 2018-08-10
 * @version 0.1
 */
public class Location {
	// The road which the location is on 
	private Motorway motorway; 
	
	// How far on the road the location is 
	private double distance; 
	
	/**
	 * A constructor for a location 
	 * @param motorway The motorway
	 * @param distance The distance up the motorway 
	 */
	public Location(Motorway motorway, double distance) {
		this.motorway = motorway;  
		this.distance = distance; 
	}

	public Motorway getMotorway() {
		return motorway;
	}

	public void setMotorway(Motorway motorway) {
		this.motorway = motorway;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
}
