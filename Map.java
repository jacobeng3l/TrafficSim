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
	
	private LinkedList<Intersection> intersects = new LinkedList<>(); 

	// Will be constructor 
	public Map() {
		;
	}

	/**
	 * A generic creation to test functionality 
	 */
	public void make() {
		Road road = new Road(50, 30, false);
		Location loc = new Location(road, 90); 
		Car car = new Car(Color.GRAY, loc, 90); 
		Location loc2 = new Location(road, 80); 
		Car car2 = new Car(Color.BEIGE, loc2, -90); 
		
		Road road2 = new Road(20, 55, false); 
		Location loc3 = new Location(road2, 30);
		Car car3 = new Car(Color.GOLD, loc3, 90); 
		
		Road road3 = new Road(70, 10, true); 
		Location loc4 = new Location(road3, 90);
		Car car4 = new Car(Color.RED, loc4, 0);
		Location loc5 = new Location(road3, 20);
		Car car5 = new Car(Color.GREEN, loc5, 180);
		
		Road road4 = new Road(50, 15, true);
		Location loc6 = new Location(road4, 10); 
		Car car6 = new Car(Color.BLUEVIOLET, loc6, 0); 
		
		Location loc7 = new Location(road, 70); 
		Car car7 = new Car(Color.AQUA, loc7, 90);
		Location loc8 = new Location(road, 20); 
		Car car8 = new Car(Color.DARKMAGENTA, loc8, 90);
		
		Location loc9 = new Location(road3, 70); 
		Car car9 = new Car(Color.SPRINGGREEN, loc9, 0); 
		Location loc10 = new Location(road3, 80); 
		Car car10 = new Car(Color.WHEAT, loc10, 0); 
		Location loc11 = new Location(road3, 10); 
		Car car11 = new Car(Color.BROWN, loc11, 0); 
		Location loc12 = new Location(road3, 20); 
		Car car12 = new Car(Color.DEEPPINK, loc12, 0); 
		
//		vehicles.add(car);
//		vehicles.add(car2);
//		vehicles.add(car3);
		vehicles.add(car4);
//		vehicles.add(car5);
//		vehicles.add(car6);
//		vehicles.add(car7); 
//		vehicles.add(car8); 
		vehicles.add(car9); 
		vehicles.add(car10); 
		vehicles.add(car11); 
		vehicles.add(car12); 
		motorways.add(road);
		motorways.add(road2);
		motorways.add(road3);

		motorways.add(road4); 
		
		generateIntersection(); 
		Iterator<Vehicle> cars = vehicles.iterator(); 
		while(cars.hasNext())
			cars.next().configureIntersection(this.getIntersects()); 
			
	}
	
	/**
	 * Creates an intersection between this road and any road it collides with 
	 */
	private void generateIntersection() {
		Iterator<Motorway> ways = this.getMotorways().iterator(); 
		while(ways.hasNext()) {
			Motorway way = ways.next();
			if(way.isVertical()) {
				Iterator<Motorway> ways2 = this.getMotorways().iterator(); 
				while(ways2.hasNext()) {
					Motorway way2 = ways2.next();
					if(!way2.isVertical()) {
						LinkedList<Motorway> duo = new LinkedList<>(); 
						duo.add(way);
						duo.add(way2);
						Vector loc = new Vector(way.getLocation(), way2.getLocation());
						Cross inter = new Cross(loc, duo); 
						intersects.add(inter); 
					}
				}
			}
		}
		
	}

	/**
	 * Moves all the vehicles on the board 
	 * @param dTime The change in time since last update 
	 */
	public void update(double dTime) {
		Iterator<Vehicle> ves = getVehicles().iterator();
		Iterator<Motorway> ways;
		while(ves.hasNext()) {
			ways = this.getMotorways().iterator(); 
			Vehicle ve = ves.next(); 
			ve.makeGrid();
			ve.move(dTime);
			while(ways.hasNext()) {
				Motorway way = ways.next();
				if(way.equals(ve.getLocation().getMotorway()))
					way.getVehicles().add(ve);
			}
		}
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

	public LinkedList<Intersection> getIntersects() {
		return intersects;
	}

	public void setIntersects(LinkedList<Intersection> intersects) {
		this.intersects = intersects;
	}
	
}
