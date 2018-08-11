/**
 * Road is a basic implementation of of the motorway  
 * @author Jacob Engelbrecht 
 * @since 2018-08-10
 * @version 0.1
 */
public class Road extends Motorway {

	/**
	 * A basic constructor for a road 
	 * @param location The location on the board the road is placed 
	 * @param speedLimit The speedlimit of the road 
	 * @param isVertical Whether or not the road is vertical 
	 */
	public Road(double location, int speedLimit, boolean isVertical) {
		super(2, true, speedLimit, location, isVertical);
	}

}
