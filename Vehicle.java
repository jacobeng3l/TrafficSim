// Imports
import java.util.Iterator;
import java.util.LinkedList;

import javafx.scene.paint.Color;

/**
 * Abstract class of any vehicle which will occur in the representation.
 * @author Jacob Engelbrecht 
 * @since 2018-08-10
 * @version 0.1
 */
public abstract class Vehicle {

	// The acceleration value of the car 
	private double maxAcceleration;
	
	private double acceleration; 
	
	// Vehicle Color
	private Color color; 
	
	// Currently unused length of vehicle 
	private double length; 
	
	// location of the vehicle 
	private Location location; 
	
	// The angle the car is pointed 
	private double direction; 
	
	// The velocity that the car is currently traveling at 
	private double velocity;
	
	private double deacceleration; 
	
	private Vector[][] marks = new Vector[3][3];
	
	// The unique car ID
	private int carID; 
	
	private static int totID = 1;
	
	// The front location of the vehicle 
	private double front; 
	
	// The rear location of the vehicle 
	private double rear; 
	
	private LinkedList<Intersection> intersectLoc = new LinkedList<>(); 
	
	/**
	 * A constructor for stationary vehicles 
	 * @param acceleration The maximum acceleration of the vehicle 
	 * @param color The color is Color
	 * @param length The length of the vehicle (0 - 100)
	 * @param location The location of the vehicle 
	 * @param direction The orientation of the vehicle 
	 */
	public Vehicle(double maxAcceleration, double deacceleration, Color color, double length, Location location, double direction) {
		this.maxAcceleration = maxAcceleration; 
		this.deacceleration = deacceleration; 
		this.color = color; 
		this.length = length; 
		this.location = location; 
		this.direction = direction; 
		velocity = 0; 
		carID = totID++; 
		
		makeGrid(); 
	}
	
	/**
	 * A constructor for moving vehicles 
	 * @param acceleration The maximum acceleration of the vehicle 
	 * @param color The color is Color
	 * @param length The length of the vehicle (0 - 100)
	 * @param location The location of the vehicle 
	 * @param direction The orientation of the vehicle 
	 * @param velocity An initial velocity of the vehicle 
	 */
	public Vehicle(double maxAcceleration, double deacceleration, Color color, double length, Location location, double direction, double velocity) {
		this(maxAcceleration, deacceleration, color, length, location, direction); 
		this.velocity = velocity; 
	}

	public double getMaxAcceleration() {
		return maxAcceleration;
	}

	public void setMaxAcceleration(double acceleration) {
		this.maxAcceleration = acceleration;
	}
	
	public Color getColor() {
		return color; 
	}
	
	public void setColor(Color color) {
		this.color = color; 
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public int getCarID() {
		return carID;
	}
	
	/**
	 * The current fundamental motion of the vehicles, based off of physics, acceleration, and speed limit 
	 * @param dtime The change in time since last update. 
	 */
	public void move(double dtime) {
		double prevV = getVelocity(); 
		this.setVelocity(prevV + (this.getAcceleration() * dtime));
		
		if(this.getDirection() > 0)
			getLocation().setDistance(getLocation().getDistance() + (((prevV + this.getVelocity()) / 2) * dtime));
		else
			getLocation().setDistance(getLocation().getDistance() - (((prevV + this.getVelocity()) / 2) * dtime));
		
		if(this.getLocation().getDistance() > 111.)
			this.getLocation().setDistance(-10);
		else if(this.getLocation().getDistance() < -11.)
			this.getLocation().setDistance(110);
		
		if(this.getVelocity() > this.getLocation().getMotorway().getSpeedLimit())
			this.setVelocity(this.getLocation().getMotorway().getSpeedLimit());
		else if(this.getVelocity() < (-1 * this.getLocation().getMotorway().getSpeedLimit()))
			this.setVelocity(-1 * this.getLocation().getMotorway().getSpeedLimit());
	}

	public double getFront() {
		return front;
	}

	public void setFront(double front) {
		this.front = front;
	}

	public double getRear() {
		return rear;
	}

	public void setRear(double rear) {
		this.rear = rear;
	}
	
	/**
	 * An equals method. 
	 * @param object Any object to be compared
	 * @return Boolean whether or not this and that are the same 
	 */
	public boolean equals(Object object) {
		if(object instanceof Vehicle) {
			Vehicle hey = (Vehicle) object; 
			if(this.getCarID() == hey.getCarID())
				return true;
			else
				return false; 
		}
		else
			return false; 
	}

	public Vector[][] getMarks() {
		return marks;
	}

	public void setMarks(Vector[][] marks) {
		this.marks = marks;
	}
	
	public abstract void makeGrid();

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public void configureIntersection(LinkedList<Intersection> intersects) {
		// TODO Auto-generated method stub
		Iterator<Intersection> iter = intersects.iterator(); 
		while(iter.hasNext()) {
			Intersection inter = iter.next(); 
			Iterator<Motorway> motorways = inter.getMotorway().iterator(); 
			while(motorways.hasNext()) {
				if(this.getLocation().getMotorway().equals(motorways.next()))
					this.getIntersectLoc().add(inter);
			}
		}
	}

	public LinkedList<Intersection> getIntersectLoc() {
		return intersectLoc;
	}

	public void setIntersectLoc(LinkedList<Intersection> intersectLoc) {
		this.intersectLoc = intersectLoc;
	}

	public double getDeacceleration() {
		return deacceleration;
	}

	public void setDeacceleration(double deacceleration) {
		this.deacceleration = deacceleration;
	} 
		
}
