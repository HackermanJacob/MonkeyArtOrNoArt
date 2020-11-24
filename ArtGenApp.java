

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
	
	//set up static/meta information
	ArtGenerator.setOutputSubdirectory("");
	ArtGenerator.makeOutputSubdirectory();
	ArtGenerator.setArtName("demoArt");
	
	ArrayList<ArtGenerator> monkeys = new ArrayList<>();
	int numberOfImagesToOutput = 20;
	for (int i = 0; i < 20; i++) {
		monkeys.add(new ArtGenerator());
	}
	for (ArtGenerator monkey : monkeys) {
		monkey.setBackgroundColorRGB(106, 90, 205);
		monkey.addSimpleSquareRandom();
		monkey.addSimpleSquareRandom();
		monkey.addSimpleSquareRandom();
		monkey.addSimpleSquareRandom();
		monkey.addSimpleSquareRandom();
		monkey.addTestingColors();
		monkey.createArt();
	}
	System.out.println("Art finished.");
}//end main
}//end class 
