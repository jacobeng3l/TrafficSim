// Imports
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Iterator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The Visual representation of a traffic situation 
 * @author Jacob Engelbrecht 
 * @since 2018-08-10
 * @version 0.1
 */
public class Board extends Application {
	
	// Final viewing stage 
	private Stage primaryStage; 
	
	// Width of the window in pixels, constantly updating 
	private double width;
	
	// Height of window in pixels, constantly updating 
	private double height; 
	
	// The graphics for the window 
	private GraphicsContext gc;

	// The starting time of the animation 
	private long initTime = Long.MAX_VALUE;

	// The current time in seconds of the animation 
	private double currentTime; 
	
	// The hidden layer of information 
	private Map map; 

	@Override
	/**
	 * The required class for JavaFX implementation 
	 */
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage; 
		
		// Gets initial Height and Width of screen because starts in full screen 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth() / 2;
		height = screenSize.getHeight() / 2; 
		
		// General Configurations 
		primaryStage.setTitle("Traffic");
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        
        // Make the background and places default stuff 
        map = new Map(); 
        map.make(); 
        draw(); 
        
        // Show the stuff 
        primaryStage.setScene(new Scene(root));
        primaryStage.setFullScreen(true);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.show();
		
        width = primaryStage.getWidth(); 
		height = primaryStage.getHeight(); 
        
		// Start the animation 
        move(); 
	}
	
	/**
	 * The animation method of the class, keeps track of the current time. Constantly updates the visuals 
	 */
	public void move() {
		currentTime = 0; 
		new AnimationTimer() {
			public void handle(long now) {
				if(initTime > now)
					initTime = now; 
				double prevTime = currentTime; 
				updateTime(now); 
				
				width = primaryStage.getWidth(); 
				height = primaryStage.getHeight(); 
				
				map.update(currentTime - prevTime);
				
				draw(); 
			}
		}.start();
	}

	/**
	 * Draws the board, both the roads and the vehicles 
	 */
	private void draw() {
		// Makes background, needs to be drawn every time in case resized 
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, width, height);
		
		Iterator<Motorway> ways = map.getMotorways().iterator(); 
		while(ways.hasNext()) 
			drawMotorway(ways.next()); 
		
		Iterator<Intersection> inters = map.getIntersects().iterator();
		while(inters.hasNext())
			drawIntersects(inters.next());
		
		Iterator<Vehicle> ves = map.getVehicles().iterator();
		while(ves.hasNext())
			drawVehicles(ves.next()); 

	}

	private void drawIntersects(Intersection next) {
		Vector[][] grid = next.getMarks(); 
		gc.setFill(Color.BLACK);
		// For some reason this is unlike the others, too tired to fix now 
		gc.fillRect(convertToWidth(grid[0][0].getX() - 1), 
				convertToHeight(grid[0][0].getY() - 1), 
				convertToWidth(grid[0][2].getY() + 2) - convertToWidth(grid[0][0].getY()),
				convertToHeight(grid[2][0].getX() + 2) - convertToHeight(grid[0][0].getX()));
	}

	/**
	 * Draws a vehicle on the board. 
	 * @param ve A vehicle to the drawn on the board
	 */
	private void drawVehicles(Vehicle ve) {
		Vector[][] grid = ve.getMarks(); 
		gc.setFill(ve.getColor());
		gc.fillRect(convertToWidth(grid[0][0].getX()), 
				convertToHeight(grid[0][0].getY()), 
				convertToWidth(grid[0][2].getX()) - convertToWidth(grid[0][0].getX()),
				convertToHeight(grid[2][0].getY()) - convertToHeight(grid[0][0].getY()));
	}
/*	private void drawVehicles(Vehicle ve) {
		gc.setFill(ve.getColor());
		if(!ve.getLocation().getMotorway().isVertical()) {
			double dist = convertToWidth(ve.getLocation().getDistance()); 
			int unit = (int) (height / 24); 
			double roadDis = (ve.getLocation().getMotorway().getLocation() / 100.) * height;
			if(ve.getDirection() > 0 && ve.getDirection() != 180)
				gc.fillRoundRect(dist - unit, roadDis + (height / 40), 2 * (width / 40), unit, height / 100, height / 100);
			else if(ve.getDirection() < 0 && ve.getDirection() != 180)
				gc.fillRoundRect(dist + unit, roadDis - ((height / 65) + unit), 2 * (width / 40), unit, height / 100, height / 100);
		}
		else {
			double dist = convertToHeight(ve.getLocation().getDistance()); 
			int unit = (int) (width / 36); 
			double roadDis = (ve.getLocation().getMotorway().getLocation() / 100.) * width;
			if(ve.getDirection() > 0 && ve.getDirection() != 180)
				gc.fillRoundRect(roadDis - ((width / 150) + unit), dist + unit, unit, 2 * (height / 27), width / 150, width / 150);
			else if(ve.getDirection() < 0 && ve.getDirection() != 180)
				gc.fillRoundRect(roadDis + (width / 60), dist - unit, unit, 2 * (height / 27), width / 150, width / 150);
		}
	}*/

	/**
	 * Draws a visual representation of a motorway on the board. 
	 * @param way A Motorway to be drawn on the board. 
	 */
	private void drawMotorway(Motorway way) {
		Vector[][] grid = way.getMarks(); 
		
		gc.setFill(Color.SADDLEBROWN);
		gc.fillRect(convertToWidth(grid[0][0].getX() - 1), 
				convertToHeight(grid[0][0].getY() - 1), 
				convertToWidth(grid[0][2].getX() + 1) - convertToWidth(grid[0][0].getX() - 1),
				convertToHeight(grid[2][0].getY() + 2) - convertToHeight(grid[0][0].getY()) - 1);
		
		gc.setFill(Color.BLACK);
		gc.fillRect(convertToWidth(grid[0][0].getX()), 
				convertToHeight(grid[0][0].getY()), 
				convertToWidth(grid[0][2].getX()) - convertToWidth(grid[0][0].getX()),
				convertToHeight(grid[2][0].getY()) - convertToHeight(grid[0][0].getY()));
		
		gc.setFill(Color.YELLOW);
		if(way.isVertical()) { 
			gc.fillRect(convertToWidth(grid[0][0].getX() + (Road.size - .2)), 
					convertToHeight(grid[0][0].getY()), 
					convertToWidth(0.1),
					convertToHeight(grid[2][0].getY() + 2) - convertToHeight(grid[0][0].getY()) - 1); 
			gc.fillRect(convertToWidth(grid[0][0].getX() + (Road.size + 0.1)), 
					convertToHeight(grid[0][0].getY()), 
					convertToWidth(0.1),
					convertToHeight(grid[2][0].getY() + 2) - convertToHeight(grid[0][0].getY()) - 1); 
		}
		else {
			gc.fillRect(convertToWidth(grid[0][0].getX()), 
					convertToHeight(grid[0][0].getY() + (Road.size + 0.1)), 
					convertToWidth(grid[0][2].getX()) - convertToWidth(grid[0][0].getX()),
					convertToHeight(0.1));
			gc.fillRect(convertToWidth(grid[0][0].getX()), 
					convertToHeight(grid[0][0].getY() + (Road.size - 0.2)), 
					convertToWidth(grid[0][2].getX()) - convertToWidth(grid[0][0].getX()),
					convertToHeight(0.1));
		}
	}
	/*
	private void drawMotorway(Motorway way) {
		if(!way.isVertical()) {
			int widthPerLane = (int) (height / 15); 
			
			gc.setFill(Color.SADDLEBROWN);
			gc.fillRect(0, ((way.getLocation() / 100.) * height) - ((widthPerLane * way.getLanes()) / 2), width, (widthPerLane * way.getLanes()) + (height / 100));
			
			gc.setFill(Color.BLACK);
			gc.fillRect(0, ((way.getLocation() / 100.) * height) - ((widthPerLane * way.getLanes()) / 2) + (height / 200), width, widthPerLane * way.getLanes());
			
			// Need to address uneven laned roads 
			if(way.getLanes() % 2 == 0) {
				gc.setFill(Color.YELLOW);
				gc.fillRect(0, convertToHeight(way.getLocation()) - (height / 500), width, (height / 200));
				gc.fillRect(0, convertToHeight(way.getLocation()) + (height / 180), width, (height / 200));
			}
		}
		else {
			int widthPerLane = (int) (width / 24); 
			
			gc.setFill(Color.SADDLEBROWN);
			gc.fillRect(((way.getLocation() / 100.) * width) - ((widthPerLane * way.getLanes()) / 2), 0, (widthPerLane * way.getLanes()) + (width / 100), height);
			
			gc.setFill(Color.BLACK);
			gc.fillRect(((way.getLocation() / 100.) * width) - ((widthPerLane * way.getLanes()) / 2) + (width / 200), 0, widthPerLane * way.getLanes(), height);
			
			if(way.getLanes() % 2 == 0) {
				gc.setFill(Color.YELLOW);
				gc.fillRect(((way.getLocation() / 100.) * width) - (width / 750), 0, (width / 300), height);
				gc.fillRect(((way.getLocation() / 100.) * width) + (width / 270), 0, (width / 300), height);
			}
		}
	}
	*/
	/**
	 * Takes the long form time from the animation timer and converts it seconds which can be more useful. 
	 * Requires the initial time when the animation timer starts, saved as initTime
	 * @param now The double from the animation timer
	 */
	public void updateTime(long now) {
		currentTime = (long) ((now - initTime) / 10000.);
		currentTime = currentTime / 100000.;
	}
	
	private double convertToWidth(double loc) {
		return ((loc / 100.) * width); 
	}
	
	private double convertToHeight(double loc) {
		return ((loc / 100.) * height);
	}

	/**
	 * The first running method of the program. 
	 * @param args Launch arguments, not used
	 */
	public static void main(String[] args) {
		launch(args); 
	}
}
