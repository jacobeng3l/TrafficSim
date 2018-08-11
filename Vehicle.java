// Imports
import javafx.scene.paint.Color;

/**
 * Abstract class of any vehicle which will occur in the representation.
 * @author Jacob Engelbrecht 
 * @since 2018-08-10
 * @version 0.1
 */
public abstract class Vehicle {

	// The acceleration value of the car 
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
	
	// The unique car ID
	private int carID; 
	
	private static int totID = 1;
	
	// The front location of the vehicle 
	private double front; 
	
	// The rear location of the vehicle 
	private double rear; 
	
	/**
	 * A constructor for stationary vehicles 
	 * @param acceleration The maximum acceleration of the vehicle 
	 * @param color The color is Color
	 * @param length The length of the vehicle (0 - 100)
	 * @param location The location of the vehicle 
	 * @param direction The orientation of the vehicle 
	 */
	public Vehicle(double acceleration, Color color, double length, Location location, double direction) {
		this.acceleration = acceleration; 
		this.color = color; 
		this.length = length; 
		this.location = location; 
		this.direction = direction; 
		velocity = 0; 
		carID = totID++; 
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
	public Vehicle(double acceleration, Color color, double length, Location location, double direction, double velocity) {
		this(acceleration, color, length, location, direction); 
		this.velocity = velocity; 
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
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
		
}
