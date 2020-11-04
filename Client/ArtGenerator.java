

import java.awt.Color;
// import java.awt.Component;
// import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.*;
import java.awt.Shape;
import java.awt.Rectangle;
import java.io.*;
import java.util.*;
import javax.imageio.*;
public class ArtGenerator {
//---variables
static int count = 0;
int id;
static int limit = 9999;
//lets keep the size standard for now
static int width = 1280;
static int height = 720;
final BufferedImage image;
final Graphics2D g2D;
Random ran;
ArrayList<Shape> shapes;
Color backgroundColor;

//---constructors
//default
public ArtGenerator() {
	id = count++;
	if (count > limit) {
		System.err.println("Too many generators. Limit of " + limit + " exceeded.");
		throw new RuntimeException("Generator limit exceeded"); //just in case someone is about to write more than ten thousand files to their machine
	}
	image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
	g2D = image.createGraphics(); //graphics context created from buffered image object
	ran = new Random();
	shapes = new ArrayList<Shape>();
	addTestingShapes();
	backgroundColor = Color.WHITE;
}
//TODO better constuctors - may be written based off of how the GUI looks/works

//---methods

//creates and writes an image file using the shapes declared in the shapes arraylist
public void createArt() throws IOException {
	//fill in background
	g2D.setPaint(backgroundColor);
	g2D.fillRect(0, 0, width, height);
	
	//draw shapes
	g2D.setPaint(Color.BLUE);
	for (Shape sh : shapes) {
		g2D.fill(sh);
		//TODO implement palette (color choosing) solution
// 		g2D.setPaint(Color.RED);
	}
	
	//save image - will overwrite if same filename
	//TODO implement subdirectory solution e.g. ./Images/blueSquares/*.jpg
	String imageName = "generic_image";
	ImageIO.write (image, "jpg", new File ( "./Images/" + imageName + ".jpg" ));
}

public void addTestingShapes() {
// 	shapes.add(new Rectangle(50, 50, 200, 200));
// 	shapes.add(new Rectangle(250, 250, 200, 200));
// 	shapes.add(new Rectangle(450, 450, 200, 200));
	addSimpleRectangle(50, 50, 200, 200);
	addSimpleRectangle(250, 250, 200, 200);
	addSimpleRectangle(450, 450, 200, 200);
}

//adds a square to the arraylist. (x, y) is top left corner
public void addSimpleSquare(int x, int y, int length) {
     shapes.add(new Rectangle(x, y, length, length));
}
//adds a rectangle to the arraylist. (x, y) is top left corner
public void addSimpleRectangle(int x, int y, int width, int height) {
     shapes.add(new Rectangle(x, y, width, height));
}

}//end class
