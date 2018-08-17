import java.util.LinkedList;

public class Cross extends Intersection{

	public Cross(Vector location, LinkedList<Motorway> motorway) {
		super(location, motorway, false);
	}

	/**
	 * Configures the marking points for any intersection
	 */
	@Override
	public void makeGrid() {
		Vector[][] marks = new Vector[3][3]; 
		marks[1][1] = this.getLocation();
		
		marks[0][0] = new Vector(this.getLocation().getX() - this.getMotorway().get(0).getWidth(), this.getLocation().getY() - this.getMotorway().get(1).getWidth());
		marks[0][1] = new Vector(this.getLocation().getX() - this.getMotorway().get(0).getWidth(), this.getLocation().getY()); 
		marks[0][2] = new Vector(this.getLocation().getX() - this.getMotorway().get(0).getWidth(), this.getLocation().getY() + this.getMotorway().get(1).getWidth());
		
		marks[1][0] = new Vector(this.getLocation().getX(), this.getLocation().getY() - this.getMotorway().get(1).getWidth());
		marks[1][2] = new Vector(this.getLocation().getX(), this.getLocation().getY() + this.getMotorway().get(1).getWidth());
		
		marks[2][0] = new Vector(this.getLocation().getX() + this.getMotorway().get(0).getWidth(), this.getLocation().getY() - this.getMotorway().get(1).getWidth());
		marks[2][1] = new Vector(this.getLocation().getX() + this.getMotorway().get(0).getWidth(), this.getLocation().getY()); 
		marks[2][2] = new Vector(this.getLocation().getX() + this.getMotorway().get(0).getWidth(), this.getLocation().getY() + this.getMotorway().get(1).getWidth()); 

		this.setMarks(marks);
	}

}
