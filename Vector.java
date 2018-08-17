
public class Vector {

	// Stores X coordinate 
	private double x; 
	
	// Stores Y coordinate 
	private double y; 
	
	public Vector(double x, double y) {
		this.setX(x);
		this.setY(y); 
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public String toString() {
		return (x + ", " + y);
	}
}
