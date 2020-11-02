// Monkey Art Generator - Test.java
// Jacob M Bengel 2020-Nov-1
// This class is used for testing
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
public class Test {
public static void main(String[] args) throws IOException {
	//example dummy method
	int width = 1280;
	int height = 720;
	
	
	final BufferedImage image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB); //change to ARGB if you want to switch to png instead of jpg
	final Graphics2D g2D = image.createGraphics();
	g2D.setPaint(Color.WHITE);
	g2D.fillRect(0, 0, width, height);
	g2D.setPaint(Color.BLUE);
	//provided polygon method is somewhat clunky
// 	g2D.fillPolygon(new int[] {0, 100, 100, 0}, new int[] {0, 0, 100, 100}, 4);
	//same functionality
// 	g2D.fill(new Polygon(new int[] {0, 150, 150, 0}, new int[] {0, 0, 150, 150}, 4));
	
	g2D.dispose();
	
	//next two lines display valid imageIO output formats e.g. "png, jpg, gif"
// 	String writerNames[] = ImageIO.getWriterFormatNames();
// 	for (String s : writerNames) {System.out.println(s);}
	String imageName = "The_One_True_Square";
	ImageIO.write (image, "jpg", new File ( "./output/" + imageName + ".jpg" ));
}//end main
}
