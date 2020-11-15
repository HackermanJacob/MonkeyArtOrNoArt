

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
public class ArtGenApp {
public static void main(String[] args) throws IOException {
	//TODO make this work with our user input solution
// 	ArtGenerator monkey = new ArtGenerator();
// 	monkey.addSimpleSquare(800, 360, 60);
// 	monkey.addTestingShapes();
	
	//set up static information for file output
	ArtGenerator.setOutputSubdirectory("demo");
	ArtGenerator.makeOutputSubdirectory();
	ArtGenerator.setArtName("demoArt");
	
	
	//initialize arraylist
	ArrayList<ArtGenerator> monkeys = new ArrayList<>();
	int numberOfImagesToOutput = 20;
	for (int i = 0; i < numberOfImagesToOutput; i++) {
		monkeys.add(new ArtGenerator());
	}
	//loop over objects to add unique shapes/colors to each
	for (ArtGenerator monkey : monkeys) {
// 		monkey.setBackgroundColorRGB(106, 90, 205);
		monkey.setBackgroundColor(Color.WHITE);
// 		monkey.addTestingColors();
		monkey.addColorRGB(cNum(), cNum(), cNum());
		monkey.addColorRGB(cNum(), cNum(), cNum());
		monkey.addColorRGB(cNum(), cNum(), cNum());
		monkey.addColorRGB(cNum(), cNum(), cNum());
		monkey.addColorRGB(cNum(), cNum(), cNum());
		
		monkey.addSimpleSquareRandom();
		monkey.addSimpleRectangleRandom();
		monkey.addSimpleTriangleRandom();
		monkey.addRightTriangleRandom();
		monkey.addSimpleEllipseRandom();
		monkey.addSimpleCircleRandom();
		monkey.addSimpleCircleRandom();
		monkey.addSimpleCircleRandom();
		monkey.addSimpleCircleRandom();
		monkey.addSimpleCircleRandom();
		
		monkey.createArt();
	}
	
	
	
	System.out.println("Art finished.");
}//end main
//random value for rgb color
private static int cNum() {
	Random ran = new Random();
	return ran.nextInt(255+1);
}

}//end class 
