

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class ProjectGUI extends Application {

    BorderPane root;
    final int SIZE = 70;
    int currentIndex = 0;

    ImageView image = null;
    EchoClient client = null;

    CheckBox red;
    CheckBox green;
    CheckBox blue;
    CheckBox yellow;
    CheckBox orange;

    CheckBox square;
    CheckBox circle;
    CheckBox rectangle;
    CheckBox triangle;

    Button select;
    Button generate;


    @Override
    public void start(Stage primaryStage) throws  Exception{

        client = new EchoClient();

        client.createSocket();

        Scene scene2 = new Scene(secondWindow(),1280,790);
        Stage window = primaryStage;
        root = new BorderPane();
        root.setTop(getTopLabel());
        root.setLeft(addVBoxColor());
        root.setCenter(addVboxLook(window, scene2));

        Scene scene = new Scene(root, 1720, 790);
        primaryStage.setTitle("Monkey Gen.2000");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Creates a VBox with all of the colors
    public VBox addVBoxColor() {
        VBox colors = new VBox();
        colors.setPrefWidth(150);
        colors.setSpacing(8);

        Label title = new Label("Colors");
        title.setStyle("-fx-font-size: 14;-fx-border-style: solid; -fx-border-width: 0 0 0 0;-fx-font-weight: bold;");
        title.setUnderline(true);
        title.setAlignment(Pos.TOP_LEFT);
        title.setPrefWidth(150);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        colors.getChildren().add(title);

         red = new CheckBox("Red");
         green = new CheckBox("Green");
         blue = new CheckBox("Blue");
         yellow = new CheckBox("Yellow");
         orange = new CheckBox("Orange");


        colors.setPadding(new Insets(20,20,0,20));
        colors.getChildren().addAll(red,green,blue,yellow,orange);

        Label title2 = new Label("Shapes");
        title2.setStyle("-fx-font-size: 14;-fx-border-style: solid; -fx-border-width: 0 0 0 0;-fx-font-weight: bold;");
        title2.setUnderline(true);
        title2.setAlignment(Pos.CENTER_LEFT);
        title2.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        colors.getChildren().add(title2);

         square = new CheckBox("Square");
         circle = new CheckBox("Circle");
         rectangle = new CheckBox("Rectangle");
         triangle = new CheckBox("Triangle");



        generate = new Button("Generate Image");
        generate.setOnAction(e -> imageOptions());

        colors.setPadding(new Insets(20,20,20,20));
        colors.getChildren().addAll(square, circle,rectangle,triangle,generate);
        return colors;
    }

    //Vbox Panel for searching through
    public VBox addVboxLook(Stage window, Scene scene2){
        VBox search = new VBox();
        search.setPrefWidth(150);
        search.setSpacing(20);

        HBox leftRight = new HBox();


        Label title3 = new Label("Select Image to Send");
        title3.setStyle("-fx-font-size: 14;-fx-border-style: solid; -fx-border-width: 0 0 0 0;-fx-font-weight: bold;");
        title3.setUnderline(true);
        title3.setAlignment(Pos.TOP_LEFT);
        title3.setPrefWidth(150);
        title3.setFont(Font.font("Arial", FontWeight.BOLD, 14));


        //This uses lambda functions, but if you don't know how this black magic works (like me), it basically just calls the method the
        //arrow is pointing to.

        Button leftArrow = new Button("<--");
        leftArrow.setOnAction(e ->leftArrow(leftArrow));
        Button rightArrow = new Button("-->");
        rightArrow.setOnAction(e ->rightArrow(rightArrow));
        select = new Button("Select You Favorite Image");

        select.setDisable(true);

        // METHOD
        select.setOnAction(e -> {
            try {
                selectMethod(window, scene2);
            } catch (IOException ignored) {
            }
        });

        String userMessage = "The Image generator has \nnow created images.\n" +
                "This has been done according to \nuser specifacations that you entered." +
                "\nYou can now select nyour \nfavorite image to send to \nour server." +
                "This server holds\n the best paintings that we \nhave generated.";
        Text message = new Text(userMessage);
        message.setStyle("-fx-font-size:14");

        search.setPadding(new Insets(20,20,20,20));


        leftRight.getChildren().addAll(leftArrow,rightArrow);
        search.getChildren().addAll(title3,leftRight,select, message);
        return search;


    }

    //Top Label
    public Label getTopLabel(){
        Label lbl = new Label("Monkey Art Generator 2000");
        lbl.setPrefHeight(SIZE);
        lbl.prefWidthProperty().bind(root.widthProperty());
        //border width is done clockwise around the string, so the third 1 is the bottom.
        lbl.setStyle("-fx-border-style: dotted; -fx-border-width: 0 0 1 0;-fx-font-weight: bold; -fx-font-size: 18; ");
        lbl.setAlignment(Pos.BASELINE_CENTER);
        lbl.setUnderline(true);
        return lbl;
    }



    //This is where the method is for displaying an image for the user to select.
    // It will currently be displaying an image in the source file, but I'm guessing
    //this will be changed. It returns an HBox (Horizontal Box).

    public Pane imageDisplay(){
        Pane displayImage = new HBox();
        image = new ImageView("ImagesClient/art0.jpg");
        displayImage.getChildren().add(image);
        return displayImage;
    }

    //Below here is gonna be a second image method (because I'm guessing we need one for the server images and one for the local images)
    //It is not being used but can be.

    public Pane imageDisplay2(){
        Pane displayImage = new HBox();
        image = new ImageView("ReceivedImages/test0.jpg");
        displayImage.getChildren().add(image);
        return displayImage;
    }

    //This is where all the handling of the buttons/checkmarks being. Currently passing void but you can change that if needed.
    //For now it will print what was selected. to the command line if you run it.

    public void imageOptions(){
        String message = "The selected traits are:";

        // checkbox.isSelected()

        red.setDisable(true);
        green.setDisable(true);
        blue.setDisable(true);
        yellow.setDisable(true);
        orange.setDisable(true);
        square.setDisable(true);
        circle.setDisable(true);
        rectangle.setDisable(true);
        triangle.setDisable(true);
        generate.setDisable(true);
        select.setDisable(false);

        root.setRight(imageDisplay());
    }

    public void leftArrow(Button leftArrow){
        System.out.println("Left Pressed");

        if(currentIndex > 0) {
            currentIndex--;
            image.setImage(new Image("ImagesClient/art" + currentIndex + ".jpg"));
        }
    }

    public void rightArrow(Button rightArrow){
        System.out.println("Right Pressed");
        if(currentIndex < 49) {
            currentIndex++;
            image.setImage(new Image("ImagesClient/art" + currentIndex + ".jpg"));
            image.setPreserveRatio(true);
        }
    }


    public void leftArrow2(Button leftArrow){
        System.out.println("Left Pressed");

        if(currentIndex > 0) {
            currentIndex--;
            image.setImage(new Image("ReceivedImages/test" + currentIndex + ".jpg"));
        }

    }

    public void rightArrow2(Button rightArrow){
        System.out.println("Right Pressed");
        if(currentIndex < 49) {
            currentIndex++;
            image.setImage(new Image("ReceivedImages/test" + currentIndex + ".jpg"));
            image.setPreserveRatio(true);
        }
    }

    public VBox secondWindow(){
        VBox second = new VBox();
        HBox leftRight = new HBox();
        HBox imageView = new HBox();

        imageView.setPadding(new Insets(20,20,20,20));


        Button leftArrow = new Button("<--");
        leftArrow.setOnAction(e ->leftArrow2(leftArrow));
        Button rightArrow = new Button("-->");
        rightArrow.setOnAction(e ->rightArrow2(rightArrow));
        leftRight.getChildren().addAll(leftArrow,rightArrow);
        leftRight.setAlignment(Pos.TOP_CENTER);
        imageView.getChildren().addAll(imageDisplay2());
        imageView.setAlignment(Pos.TOP_CENTER);
        second.getChildren().addAll(leftRight,image);
        return second;
    }

    public void selectMethod(Stage window, Scene scene2) throws IOException {
        client.send("art" + currentIndex + ".jpg");
        client.receive();
        window.setScene(scene2);
        currentIndex = 0;
        image.setImage(new Image("ReceivedImages/test0.jpg"));
    }

    public static void main(String [] args){
        launch(args);
    }
}
