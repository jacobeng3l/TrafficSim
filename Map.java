// imports 
import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.paint.Color;

/**
 * The entire mathematical and computation representation of the road 
 * @author Jacob Engelbrecht 
 * @since 2018-08-10
 * @version 0.1
 */
public class Map {
	
	// All the vehicles on the board 
	private LinkedList<Vehicle> vehicles = new LinkedList<>(); 
	
	// All the motorways on the road 
	private LinkedList<Motorway> motorways = new LinkedList<>();

	// Will be constructor 
	public Map() {
		;
	}

	/**
	 * A generic creation to test functionality 
	 */
	public void make() {
		Road road = new Road(50, 30, false);
		Location loc = new Location(road, 10); 
		Car car = new Car(Color.GRAY, loc, 90); 
		Location loc2 = new Location(road, 60); 
		Car car2 = new Car(Color.BEIGE, loc2, -90); 
		
		Road road2 = new Road(80, 55, false); 
		Location loc3 = new Location(road2, 30);
		Car car3 = new Car(Color.GOLD, loc3, 90); 
		
		Road road3 = new Road(50, 45, true); 
		Location loc4 = new Location(road3, 50);
		Car car4 = new Car(Color.RED, loc4, 90);
		Location loc5 = new Location(road3, 20);
		Car car5 = new Car(Color.GREEN, loc5, -90);
		
		vehicles.add(car);
		vehicles.add(car2);
		vehicles.add(car3);
		vehicles.add(car4);
		vehicles.add(car5);
		motorways.add(road);
		motorways.add(road2);
		motorways.add(road3);
	}
	
	/**
	 * Moves all the vehicles on the board 
	 * @param dTime The change in time since last update 
	 */
	public void update(double dTime) {
		Iterator<Vehicle> ves = getVehicles().iterator();
		while(ves.hasNext())
			ves.next().move(dTime);
	}
	
	
	public LinkedList<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(LinkedList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public LinkedList<Motorway> getMotorways() {
		return motorways;
	}

	public void setMotorways(LinkedList<Motorway> motorways) {
		this.motorways = motorways;
	}
	
}
