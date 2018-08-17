import java.util.Iterator;
import java.util.LinkedList;

import javafx.scene.paint.Color;

/**
 * The Car class, an implementation of Vehicle for the roads 
 * @author Jacob Engelbrecht 
 * @since 2.018-08-10
 * @version 0.1
 */
public class Car extends Vehicle {
	
	private static final int size = 3; 
	
	// The length of the car
	public final double length = 10; 
	
	private boolean isStopping = false; 
	
	private double stoppingDistance = 40; 
	
	private double buffer = 1.5; 
	
	private double elaspedTime = 0;
	
	private double delayTime; 
	
	private double nearest = 121; 

	/**
	 * A car constructor for where the car is beginning 
	 * @param color The color of the car
	 * @param location The location the car is starting 
	 * @param direction The direction the car is moving
	 */
	public Car(Color color, Location location, double direction) {
		super(4, 2, color, 10, location, direction);
	}
	
	/**
	 * Additional move supplement to configure the front and rear of the cars are at all time 
	 * @param dtime The change in time since the last move
	 */
	public void move(double dtime) {
		boolean b = false; 
		// know where to look for whether or not to stop 
		updateStoppingDistance(); 
		// if not stopping, check to see if we need to stop
		if(!isStopping) {
			this.setAcceleration(this.getMaxAcceleration());
			checkCollision();
			b = checkIntersection(); 
		}
		else {
			double pause = 1; 
			elaspedTime += dtime; 
			updateDelayTime(); 
			if(elaspedTime > pause)
				this.setAcceleration(this.getMaxAcceleration());
			else {
				this.setAcceleration(0);
				this.setVelocity(0);
			}
			if(elaspedTime > pause + 0.4) {
				isStopping = false; 
				elaspedTime = 0; 
			}
		}
		
		// if stopped, pause
		if(this.getVelocity() <= 0) {
			if(b)
				isStopping = true; 
		}
		
		// move
		super.move(dtime);
		
		// check if moved off screen
		if(this.getDirection() > 0 && this.getDirection() != 180)
			this.setRear(this.getLocation().getDistance());
		if(!this.getLocation().getMotorway().isVertical()) {
			if(this.getDirection() < 0 && this.getDirection() != 180)
				this.setRear(this.getLocation().getDistance() + 10);
			this.setFront(this.getLocation().getDistance() + 5);
		}
		else {
			if(this.getDirection() < 0 && this.getDirection() != 180)
				this.setFront(this.getLocation().getDistance() - 9);
			else
				this.setFront(this.getLocation().getDistance() + 7);
			this.setRear(this.getLocation().getDistance());
		}
	}
	
	private void checkCollision() {
		LinkedList<Vehicle> ves= this.getLocation().getMotorway().getVehicles();
		Iterator<Vehicle> iter = ves.iterator(); 
		this.setNearest(121);
		while(iter.hasNext()) {
			Vehicle inspect = iter.next(); 
			if(inspect.getDirection() == this.getDirection()) {
				if(!this.equals(inspect)) {
					double delta = 121; 
					switch((int)this.getDirection()) {
					case 0 : 
						delta = this.getMarks()[0][0].getY() - inspect.getMarks()[2][2].getY() - 1; 
						break;
					case 90 : 
						delta = inspect.getMarks()[1][0].getX() - this.getMarks()[1][2].getX() - 1;
						break;
					case -180 : 
					case 180 :
						delta = inspect.getMarks()[0][0].getY() - this.getMarks()[2][2].getY() - 1; 
						break;
					case 270 :
					case -90 : 
						delta = this.getMarks()[0][0].getX() - inspect.getMarks()[2][2].getX() - 1;
						break; 
					}
					if(delta < 0)
						delta += 121;
					if(delta < this.getNearest())
							this.setNearest(delta);
					if(this.getNearest() < this.getStoppingDistance())
						this.setAcceleration((Math.pow(inspect.getVelocity(), 2) - Math.pow(this.getVelocity(), 2)) / (2 * this.getNearest()));
				}
			}
		}
		if(this.getNearest() < 0.1) {
			this.setAcceleration(0);
			this.setVelocity(0);
		}
//		if(this.getCarID() == 4)
//			System.out.println("1. " + this.getNearest());
	}
	
	private boolean checkIntersection() {
		boolean temp = false; 
		Iterator<Intersection> inters = this.getIntersectLoc().iterator(); 
		while(inters.hasNext()) {
			Intersection inspect = inters.next(); 
			double delta = 121; 
			switch((int)this.getDirection()) {
				case 0 : 
					delta = this.getMarks()[0][0].getY() - inspect.getMarks()[2][2].getY() - 1; 
					break;
				case 90 : 
					delta = inspect.getMarks()[0][0].getX() - this.getMarks()[1][2].getX() - 1;
					break;
				case -180 : 
				case 180 :
					delta = inspect.getMarks()[0][0].getY() - this.getMarks()[2][2].getY() - 1; 
					break;
				case 270 :
				case -90 : 
					delta = this.getMarks()[0][0].getX() - inspect.getMarks()[2][2].getX() - 1;
					break; 
			}
			if(delta < 0)
				delta += 121;
			if(delta < this.getNearest()) {
				temp = true; 
				this.setNearest(delta);
				if(this.getNearest() < this.getStoppingDistance()) 
					this.setAcceleration((0 - Math.pow(this.getVelocity(), 2)) / (2 * this.getNearest()));
			}
		}
//		if(this.getCarID() == 4)
//			System.out.println("2. " + this.getNearest());
		return temp; 
	}

	/**
	 * The time it would take to travel across the intersection when accelerating at max accelerations
	 */
	private void updateDelayTime() {
		double time = Math.sqrt(20/this.getMaxAcceleration()); 
		this.setDelayTime(time);
	}

	/**
	 * The minimum stopping distance for a car
	 */
	private void updateStoppingDistance() {
		double distance = Math.pow(this.getVelocity(), 2) / (2 * this.getMaxAcceleration() * this.getDeacceleration());
		this.setStoppingDistance(distance + 9);
	}

	/**
	 * Whether or not there is a conflict with an intersection which needs to halt the vehicle. Scans at stopping distance away 
	 * @return boolean depending on whether or not it is safe to proceed 
	 */
	public boolean shouldStop() {
		Iterator<Intersection> iter = this.getIntersectLoc().iterator();
		boolean temp = false; 
		while(iter.hasNext()) {
			Vector vec = iter.next().getMarks()[1][1];
			if(this.getDirection() == 90) {
				if((vec.getX() - getStoppingDistance()) < 0)
					if(this.getLocation().getDistance() > (vec.getX() - getStoppingDistance() + 121) && this.getLocation().getDistance() < ((vec.getX() - getStoppingDistance()) + buffer + 121)) 
						temp = true; 
				if(this.getLocation().getDistance() > vec.getX() - getStoppingDistance() && this.getLocation().getDistance() < (vec.getX() - getStoppingDistance()) + buffer) 
						temp = true; 
			}
			if (this.getDirection() == -90 || this.getDirection() == 270) {
				if((vec.getX() + getStoppingDistance()) > 111)
					if(this.getLocation().getDistance() > (vec.getX() + getStoppingDistance() - 121) && this.getLocation().getDistance() < ((vec.getX() + getStoppingDistance()) - buffer - 121)) 
						temp = true; 
				if(this.getLocation().getDistance() < vec.getX() + getStoppingDistance() && this.getLocation().getDistance() > (vec.getX() + getStoppingDistance()) - buffer)
					temp = true; 
			}
			if(this.getDirection() == 180 || this.getDirection() == -180) {
				if((vec.getY() - getStoppingDistance()) < 0)
					if(this.getLocation().getDistance() > (vec.getY() - getStoppingDistance() + 121) && this.getLocation().getDistance() < ((vec.getY() - getStoppingDistance()) + buffer + 121)) 
						temp = true; 
				if(this.getLocation().getDistance() > vec.getY() - getStoppingDistance() && this.getLocation().getDistance() < (vec.getY() - getStoppingDistance()) + buffer)
					temp = true;
			}
			if(this.getDirection() == 0) {
				if((vec.getY() + getStoppingDistance()) > 111)
					if(this.getLocation().getDistance() < (vec.getY() + getStoppingDistance() - 121) && this.getLocation().getDistance() > ((vec.getY() + getStoppingDistance()) - buffer - 121)) 
						temp = true; 
				if(this.getLocation().getDistance() < vec.getY() + getStoppingDistance() && this.getLocation().getDistance() > (vec.getY() + getStoppingDistance()) - buffer) 
					temp = true;
			}
		}
		return temp; 
	}

	/**
	 * Configures the nine marking points of the vehicle
	 */
	@Override
	public void makeGrid() {
		Vector[][] marks = new Vector[3][3]; 
		if(this.getDirection()  == 90) {
			marks[0][0] = new Vector(this.getLocation().getDistance() - size, this.getLocation().getMotorway().getMarks()[1][1].getY() + (Road.size / 2.) - (size / 2.)); 
			marks[1][0] = new Vector(this.getLocation().getDistance() - size, this.getLocation().getMotorway().getMarks()[1][1].getY() + (Road.size / 2.)); 
			marks[2][0] = new Vector(this.getLocation().getDistance() - size, this.getLocation().getMotorway().getMarks()[1][1].getY() + (Road.size / 2.) + (size / 2.)); 
			marks[0][1] = new Vector(this.getLocation().getDistance(), this.getLocation().getMotorway().getMarks()[1][1].getY() + (Road.size / 2.) - (size / 2.)); 
			marks[1][1] = new Vector(this.getLocation().getDistance(), this.getLocation().getMotorway().getMarks()[1][1].getY() + (Road.size / 2.)); 
			marks[2][1] = new Vector(this.getLocation().getDistance(), this.getLocation().getMotorway().getMarks()[1][1].getY() + (Road.size / 2.) + (size / 2.)); 
			marks[0][2] = new Vector(this.getLocation().getDistance() + size, this.getLocation().getMotorway().getMarks()[1][1].getY() + (Road.size / 2.) - (size / 2.)); 
			marks[1][2] = new Vector(this.getLocation().getDistance() + size, this.getLocation().getMotorway().getMarks()[1][1].getY() + (Road.size / 2.)); 
			marks[2][2] = new Vector(this.getLocation().getDistance() + size, this.getLocation().getMotorway().getMarks()[1][1].getY() + (Road.size / 2.) + (size / 2.)); 
		}
		else if (this.getDirection() == 0){
			marks[0][0] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() + (Road.size / 2.) - (size / 2.), this.getLocation().getDistance() - size); 
			marks[0][1] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() + (Road.size / 2.), this.getLocation().getDistance() - size); 
			marks[0][2] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() + (Road.size / 2.) + (size / 2.), this.getLocation().getDistance() - size); 
			marks[1][0] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() + (Road.size / 2.) - (size / 2.), this.getLocation().getDistance()); 
			marks[1][1] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() + (Road.size / 2.), this.getLocation().getDistance()); 
			marks[1][2] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() + (Road.size / 2.) + (size / 2.), this.getLocation().getDistance()); 
			marks[2][0] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() + (Road.size / 2.) - (size / 2.), this.getLocation().getDistance() + size); 
			marks[2][1] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() + (Road.size / 2.), this.getLocation().getDistance() + size); 
			marks[2][2] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() + (Road.size / 2.) + (size / 2.), this.getLocation().getDistance() + size);
		}
		else if (this.getDirection() == 180 || this.getDirection() == -180) {
			marks[0][0] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() - (Road.size / 2.) - (size / 2.), this.getLocation().getDistance() - size); 
			marks[0][1] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() - (Road.size / 2.), this.getLocation().getDistance() - size); 
			marks[0][2] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() - (Road.size / 2.) + (size / 2.), this.getLocation().getDistance() - size); 
			marks[1][0] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() - (Road.size / 2.) - (size / 2.), this.getLocation().getDistance()); 
			marks[1][1] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() - (Road.size / 2.), this.getLocation().getDistance()); 
			marks[1][2] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() - (Road.size / 2.) + (size / 2.), this.getLocation().getDistance()); 
			marks[2][0] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() - (Road.size / 2.) - (size / 2.), this.getLocation().getDistance() + size); 
			marks[2][1] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() - (Road.size / 2.), this.getLocation().getDistance() + size); 
			marks[2][2] = new Vector(this.getLocation().getMotorway().getMarks()[1][1].getX() - (Road.size / 2.) + (size / 2.), this.getLocation().getDistance() + size);
		}
		else {
			marks[0][0] = new Vector(this.getLocation().getDistance() - size, this.getLocation().getMotorway().getMarks()[1][1].getY() - (Road.size / 2.) - (size / 2.)); 
			marks[1][0] = new Vector(this.getLocation().getDistance() - size, this.getLocation().getMotorway().getMarks()[1][1].getY() - (Road.size / 2.)); 
			marks[2][0] = new Vector(this.getLocation().getDistance() - size, this.getLocation().getMotorway().getMarks()[1][1].getY() - (Road.size / 2.) + (size / 2.)); 
			marks[0][1] = new Vector(this.getLocation().getDistance(), this.getLocation().getMotorway().getMarks()[1][1].getY() - (Road.size / 2.) - (size / 2.)); 
			marks[1][1] = new Vector(this.getLocation().getDistance(), this.getLocation().getMotorway().getMarks()[1][1].getY() - (Road.size / 2.)); 
			marks[2][1] = new Vector(this.getLocation().getDistance(), this.getLocation().getMotorway().getMarks()[1][1].getY() - (Road.size / 2.) + (size / 2.)); 
			marks[0][2] = new Vector(this.getLocation().getDistance() + size, this.getLocation().getMotorway().getMarks()[1][1].getY() - (Road.size / 2.) - (size / 2.)); 
			marks[1][2] = new Vector(this.getLocation().getDistance() + size, this.getLocation().getMotorway().getMarks()[1][1].getY() - (Road.size / 2.)); 
			marks[2][2] = new Vector(this.getLocation().getDistance() + size, this.getLocation().getMotorway().getMarks()[1][1].getY() - (Road.size / 2.) + (size / 2.));
		}
		this.setMarks(marks);
	}
	
	public String toString() {
		return("Car #" + this.getCarID()); 
	}

	public double getStoppingDistance() {
		return stoppingDistance;
	}

	public void setStoppingDistance(double stoppingDistance) {
		this.stoppingDistance = stoppingDistance;
	}

	public double getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(double delayTime) {
		this.delayTime = delayTime;
	}

	public double getNearest() {
		return nearest;
	}

	public void setNearest(double nearest) {
		this.nearest = nearest;
	}

}
