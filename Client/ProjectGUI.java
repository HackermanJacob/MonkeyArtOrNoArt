

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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectGUI extends Application {

    BorderPane root;
    final int SIZE = 70;
    int currentIndex = 0;

    ImageView image = null;


    ArrayList<ArtGenerator> monkeys = new ArrayList<>();

    Stage window;

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
    public void start(Stage primaryStage) throws Exception{
        ArtGenerator.setArtName("art");

        for(int i = 0; i < 50; i++)
            monkeys.add(new ArtGenerator());



        window = primaryStage;
        root = new BorderPane();
        root.setTop(getTopLabel());
        root.setLeft(addVBoxColor());


        Scene scene = new Scene(root, 300, 450);
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

        select = new Button("Select You Favorite Image");

        select.setDisable(true);

        // METHOD


        String userMessage = "The Image generator has \nnow created images.\n" +
                "This has been done according to \nuser specifacations that you entered." +
                "\nYou can now select nyour \nfavorite image to send to \nour server." +
                "This server holds\n the best paintings that we \nhave generated.";
        Text message = new Text(userMessage);
        message.setStyle("-fx-font-size:14");

        search.setPadding(new Insets(20,20,20,20));


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

    //This is where all the handling of the buttons/checkmarks being. Currently passing void but you can change that if needed.
    //For now it will print what was selected. to the command line if you run it.

    public void imageOptions() {
        String message = "The selected traits are:";

        // checkbox.isSelected()

        for(ArtGenerator monkey : monkeys)
        {
            monkey.setBackgroundColorRGB(106, 90, 205);

            if(red.isSelected())
                monkey.addColorRGB(255,0,0);
            if(green.isSelected())
                monkey.addColorRGB(0,255,0);
            if(blue.isSelected())
                monkey.addColorRGB(0,0,255);
            if(yellow.isSelected())
                monkey.addColorRGB(230, 237, 36);
            if(orange.isSelected())
                monkey.addColorRGB(245, 157, 42);
            if(square.isSelected())
                for(int i = 0; i < 5; i++)
                    monkey.addSimpleSquareRandom();

            if(circle.isSelected())
                for(int i = 0; i < 5; i++)
                    monkey.addSimpleCircleRandom();

            if(rectangle.isSelected())
                for(int i = 0; i < 5; i++)
                    monkey.addSimpleRectangleRandom();

            if(triangle.isSelected())
                for(int i = 0; i < 5; i++)
                    monkey.addSimpleTriangleRandom();

            else if(!red.isSelected() && !green.isSelected() && !blue.isSelected() && !yellow.isSelected() && !orange.isSelected())
                monkey.addColor(Color.WHITE);

             try {
                 monkey.createArt();
             }
             catch (IOException ignore) {}

        }

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

        window.close();
    }



    public static void main(String [] args){
        launch(args);

        Scanner s = new Scanner(System.in);
        Socket socket = null;
        DataInputStream input = null;
        DataInputStream serverInput = null;
        DataOutputStream out = null;


        boolean valid = false;
        while (!valid) {
            try {
                s = new Scanner(System.in);
                System.out.print("Enter In a port to send your creation to: ");
                int port = Integer.parseInt(s.nextLine());

                socket = new Socket("localhost", port);
                valid = true;

                System.out.println("Connected");

                input = new DataInputStream(System.in);

                serverInput = new DataInputStream(socket.getInputStream());

                out = new DataOutputStream(socket.getOutputStream());


            } catch (ConnectException c) {
                System.out.println("Invalid Socket");
            } catch (IOException u) {
                System.out.println(u);
            } catch (NumberFormatException n) {
                System.out.println("Must be a number");
            }
        }


        System.out.println("Go to ImagesClient to search for you favorite image");
        System.out.print("What was the number of your favorite image: ");
        int imageIndex = Integer.parseInt(s.nextLine());

        System.out.println();

        try {


            //Send Image
            assert out != null;

            BufferedImage image = ImageIO.read(new File("./ImagesClient/art"+ imageIndex + ".jpg"));


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            out.write(size);
            out.write(byteArrayOutputStream.toByteArray());
            out.flush();

            System.out.println("Sending Image\n");

        }

        catch(IOException i)
        {
            System.out.println(i);
        }

        catch (NullPointerException n) {
            return;
        }


        try {
            //Receive all images from server

            int fileCount = serverInput.readInt();
            File[] files = new File[fileCount];

            for (int i = 0; i < fileCount; i++) {
                String fileName = serverInput.readUTF();

                byte[] sizeAr = new byte[4];
                serverInput.read(sizeAr);
                int sizeReceive = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                byte[] imageAr = new byte[sizeReceive];
                serverInput.read(imageAr);

                BufferedImage imageReceive = ImageIO.read(new ByteArrayInputStream(imageAr));

                ImageIO.write(imageReceive, "jpg", new File("./ReceivedImages/" + fileName));
            }

        }

        catch(IOException i)
        {
            System.out.println(i);
        }

        catch (NullPointerException n) {
            return;
        }

        System.out.println("Open Received Images to explore other the creations of other monkeys");


    }
}
