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
static String outputSubdirectory = ""; //used for organization if set
static String artName = "unnamed";
static int count = 0;
int id;
static int limit = 9999;
//lets keep the size standard for now
static int imageWidth = 1280;
static int imageHeight = 720;
static double sizeFactor = .35; //max area of a shape relative to the total image size
int shapeMaxArea;
final BufferedImage image;
final Graphics2D g2D;
Random ran;
ArrayList<Shape> shapes;
Color backgroundColor;
ArrayList<Color> palette;

//---constructors
//default
public ArtGenerator() {
	id = count++;
	if (count > limit) {
		System.err.println("Too many generators. Limit of " + limit + " exceeded.");
		throw new RuntimeException("Generator limit exceeded"); //just in case someone is about to write more than ten thousand files to their machine
	}
	shapeMaxArea = 1 + (int)(sizeFactor * imageWidth * imageHeight);
	image = new BufferedImage (imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
	g2D = image.createGraphics(); //graphics context created from buffered image object
	ran = new Random();
	shapes = new ArrayList<Shape>();
	backgroundColor = Color.WHITE;
	palette = new ArrayList<Color>();
}
//TODO better constuctors - may be written based off of how the GUI looks/works

//---methods
//set subdirectory for organized output - recommended to use
public static void setOutputSubdirectory(String s) {
	s = s.replace("/", "");
	outputSubdirectory = "/" + s;
}
//creates output subdirectory; does nothing if it exists
public static void makeOutputSubdirectory() {
	File dir = new File("./Images" + outputSubdirectory);
	if (!dir.exists()) {
		dir.mkdir();
	}
}
//set static name for art output - also recommended
public static void setArtName(String s) {
	artName = s;
}

//creates and writes an image file using the object's shapes and color lists
public void createArt() throws IOException {
	//fill in background
	g2D.setPaint(backgroundColor);
	g2D.fillRect(0, 0, imageWidth, imageHeight);
	
	//draw shapes
	for (Shape sh : shapes) {
		g2D.setPaint(getRandomColor());
		g2D.fill(sh);
	}
	
	//save image - will overwrite if same filename
	//png takes less space than jpg on my system - Jacob B
	ImageIO.write (image, "png", new File ( "./Images" + outputSubdirectory + "/" + artName + id + ".png" ));
}

//change max area of generated shapes (by number of pixels)
public void setShapeMaxArea(int area) {
	shapeMaxArea = area;
}
//change background color using color object
public void setBackgroundColor(Color c) {
	backgroundColor = c;
}
//change background color using new rgb color
public void setBackgroundColorRGB(int r, int g, int b) {
	backgroundColor = new Color(r, g, b);
}

//add shapes for testing
public void addTestingShapes() {
// 	shapes.add(new Rectangle(50, 50, 200, 200));
// 	shapes.add(new Rectangle(250, 250, 200, 200));
// 	shapes.add(new Rectangle(450, 450, 200, 200));
	addSimpleRectangle(50, 50, 200, 200);
	addSimpleRectangle(250, 250, 200, 200);
	addSimpleRectangle(450, 450, 200, 200);
}
//add colors for testing
public void addTestingColors() {
// 	palette.add(Color.RED);
// 	palette.add(Color.GREEN);
// 	palette.add(Color.BLUE);
// 	palette.add(new Color(0, 0, 255));
	addColorRGB(255, 000, 000);
	addColorRGB(000, 255, 000);
	addColorRGB(000, 000, 255);
}

//add color object to palette
public void addColor(Color c) {
	palette.add(c);
}
//add new rgb color to palette
public void addColorRGB(int r, int g, int b) {
	palette.add(new Color(r, g, b));
}
//get random color from palette
public Color getRandomColor() {
	return palette.get(ran.nextInt(palette.size()));
}

//adds a square to the shapes list. (x, y) is top left corner
public void addSimpleSquare(int x, int y, int length) {
     shapes.add(new Rectangle(x, y, length, length));
}
public void addSimpleSquareRandom() {
	int x = ran.nextInt(imageWidth);
	int y = ran.nextInt(imageHeight);
	//randomly select square size up to the largest allowed
	int length = ran.nextInt((int)Math.pow(shapeMaxArea, .5));
	addSimpleSquare(x, y, length);
}
//adds a rectangle to the shapes list. (x, y) is top left corner
public void addSimpleRectangle(int x, int y, int width, int height) {
     shapes.add(new Rectangle(x, y, width, height));
}





}//end class
