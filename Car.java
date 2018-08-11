import javafx.scene.paint.Color;

/**
 * The Car class, an implementation of Vehicle for the roads 
 * @author Jacob Engelbrecht 
 * @since 2018-08-10
 * @version 0.1
 */
public class Car extends Vehicle {
	
	// The length of the car
	public final double length = 10; 

	/**
	 * A car constructor for where the car is beginning 
	 * @param color The color of the car
	 * @param location The location the car is starting 
	 * @param direction The direction the car is moving
	 */
	public Car(Color color, Location location, double direction) {
		super(7.2, color, 10, location, direction);
	}
	
	/**
	 * Additional move supplement to configure the front and rear of the cars are at all time 
	 * @param dtime The change in time since the last move
	 */
	public void move(double dtime) {
		super.move(dtime);
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

}
