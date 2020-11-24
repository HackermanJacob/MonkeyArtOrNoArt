

import java.awt.Color;
// import java.awt.Component;
// import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.*;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
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
static double sizeFactor = .5; //max area of a shape relative to the total image size
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
	//if you want to change the file type you should change the actual file type as well as the extension
	String extension = "png"; //use jpg to take more storage
	ImageIO.write (image, extension, new File ("./Images" + outputSubdirectory + "/" + artName + id + "." + extension));
// 	ImageIO.write (image, "png", new File ( "./Images" + outputSubdirectory + "/" + artName + id + ".png" ));
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

//adds a square to the shapes list
public void addSimpleSquare(int x, int y, int length) {
     shapes.add(new Rectangle(x, y, length, length));
}
public void addSimpleSquareRandom() {
	int x = ran.nextInt(imageWidth);
	int y = ran.nextInt(imageHeight);
	//randomly select square size up to the largest allowed area
	int length = ran.nextInt((int)Math.pow(shapeMaxArea, .5));
	addSimpleSquare(x, y, length);
}
//adds a rectangle to the shapes list
public void addSimpleRectangle(int x, int y, int width, int height) {
     shapes.add(new Rectangle(x, y, width, height));
}
public void addSimpleRectangleRandom() {
	int x = ran.nextInt(imageWidth);
	int y = ran.nextInt(imageHeight);
	int width = 0;
	int height = 0;
	int area = -1;
	while (area < 0 || area > shapeMaxArea) { //random sizes until valid area
		width = ran.nextInt(imageWidth);
		height = ran.nextInt(imageHeight);
		area = width * height;
	}
	addSimpleRectangle(x, y, width, height);
}
//adds a triangle to the shapes list
public void addSimpleTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
	shapes.add(new Polygon(new int[] {x1, x2, x3}, new int[] {y1, y2, y3}, 3));
}
public void addSimpleTriangleRandom() {
	int x1 = 0;
	int x2 = 0;
	int x3 = 0;
	int y1 = 0;
	int y2 = 0;
	int y3 = 0;
	int area = -1;
	while (area < 0 || area > shapeMaxArea) { //random sizes until valid area
		x1 = ran.nextInt(imageWidth);
		x2 = ran.nextInt(imageWidth);
		x3 = ran.nextInt(imageWidth);
		y1 = ran.nextInt(imageHeight);
		y2 = ran.nextInt(imageHeight);
		y3 = ran.nextInt(imageHeight);
		area = (int)Math.abs(( (x1*(y2-y3)) + (x2*(y1-y3)) + (x3*(y1-y2)) ) / 2);
	}
	addSimpleTriangle(x1, y1, x2, y2, x3, y3);
}
//adds a right triangle to the shapes list
public void addRightTriangleRandom() {
	int x = ran.nextInt(imageWidth);
	int y = ran.nextInt(imageHeight);
	int vertical = 0;
	int horizontal = 0;
	int area = -1;
	while (area < 0 || area > shapeMaxArea) { //random sizes until valid area
		vertical = ran.nextInt(imageHeight);
		horizontal = ran.nextInt(imageWidth);
		area = (int)(.5 * vertical * horizontal);
	}
	int[] temp = new int[] {-1, 1};
	vertical *= temp[ran.nextInt(2)];
	horizontal *= temp[ran.nextInt(2)];
	addSimpleTriangle(x, y, x, (y+vertical), (x+horizontal), y);
}
//adds an ellipse to the shapes list - behaves like rectangle
public void addSimpleEllipse(int x, int y, int width, int height) {
	shapes.add(new Ellipse2D.Double(x, y, width, height));
}
public void addSimpleEllipseRandom() {
	int x = ran.nextInt(imageWidth);
	int y = ran.nextInt(imageHeight);
	int width = 0;
	int height = 0;
	int area = -1;
	while (area < 0 || area > shapeMaxArea) { //random sizes until valid area
		width = ran.nextInt(imageWidth);
		height = ran.nextInt(imageHeight);
		area = (int)(.25 * 3.14 * width * height); //this pi value is good enough
	}
	addSimpleEllipse(x, y, width, height);
}
//adds a circle to the shapes list - behaves like square/rectangle
public void addSimpleCircle(int x, int y, int radius) {
	shapes.add(new Ellipse2D.Double(x, y, radius, radius));
}
public void addSimpleCircleRandom() {
	int x = ran.nextInt(imageWidth);
	int y = ran.nextInt(imageHeight);
	int radius = 0;
	int area = -1;
	while (area < 0 || area > shapeMaxArea) { //random sizes until valid area
		radius = ran.nextInt(Math.min(imageWidth, imageHeight));
		area = (int)(3.14 * radius * radius); //this pi value is good enough
	}
	addSimpleCircle(x, y, radius);
}




}//end class
