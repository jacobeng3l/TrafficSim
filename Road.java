/**
 * Road is a basic implementation of of the motorway  
 * @author Jacob Engelbrecht 
 * @since 2018-08-10
 * @version 0.1
 */
public class Road extends Motorway { 
	
	public static final int size = 4; 

	/**
	 * A basic constructor for a road 
	 * @param location The location on the board the road is placed 
	 * @param speedLimit The speedlimit of the road 
	 * @param isVertical Whether or not the road is vertical 
	 */
	public Road(double location, int speedLimit, boolean isVertical) {
		super(2, true, speedLimit, location, isVertical, size);
	}

	/**
	 * Creates grid of marking points 
	 */
	@Override
	public void makeGrid() {
		Vector[][] marks = new Vector[3][3]; 
		if(this.isVertical()) {
			marks[0][0] = new Vector(this.getLocation() - size, 0); 
			marks[0][1] = new Vector(this.getLocation(), 0); 
			marks[0][2] = new Vector(this.getLocation() + size, 0); 
			marks[1][0] = new Vector(this.getLocation() - size, 50); 
			marks[1][1] = new Vector(this.getLocation(), 50); 
			marks[1][2] = new Vector(this.getLocation() + size, 50); 
			marks[2][0] = new Vector(this.getLocation() - size, 100); 
			marks[2][1] = new Vector(this.getLocation(), 100); 
			marks[2][2] = new Vector(this.getLocation() + size, 100); 
		}
		else {
			marks[0][0] = new Vector(0, this.getLocation() - size); 
			marks[0][1] = new Vector(50, this.getLocation() - size); 
			marks[0][2] = new Vector(100, this.getLocation() - size); 
			marks[1][0] = new Vector(0, this.getLocation()); 
			marks[1][1] = new Vector(50, this.getLocation()); 
			marks[1][2] = new Vector(100, this.getLocation()); 
			marks[2][0] = new Vector(0, this.getLocation() + size); 
			marks[2][1] = new Vector(50, this.getLocation() + size); 
			marks[2][2] = new Vector(100, this.getLocation() + size); 
		}
		this.setMarks(marks);
	}

}
