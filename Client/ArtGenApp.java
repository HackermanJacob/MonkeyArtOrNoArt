// Monkey Art Generator - ArtGenApp.java
// Jacob M Bengel 2020-Nov-1
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
     ArtGenerator monkey = new ArtGenerator();
     monkey.addSimpleSquare(800, 360, 60);
     monkey.createArt();
     System.out.println("Art finished.");
}//end main
}//end class 
