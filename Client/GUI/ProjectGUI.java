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

public class ProjectGUI extends Application {

    BorderPane root;
    final int SIZE = 70;
    @Override
    public void start(Stage primaryStage) throws  Exception{

        Scene scene2 = new Scene(secondWindow(),600,600);
        Stage window = primaryStage;
        root = new BorderPane();
        root.setTop(getTopLabel());
        root.setLeft(addVBoxColor());
        root.setCenter(addVboxLook(window, scene2));
        root.setRight(imageDisplay());
        root.setBottom(getBottomLabel());

        Scene scene = new Scene(root, 900, 600);
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

        CheckBox red = new CheckBox("Red");
        CheckBox green = new CheckBox("Green");
        CheckBox blue = new CheckBox("Blue");
        CheckBox yellow = new CheckBox("Yellow");
        CheckBox orange = new CheckBox("Orange");

        colors.setPadding(new Insets(20,20,0,20));
        colors.getChildren().addAll(red,green,blue,yellow,orange);

        Label title2 = new Label("Shapes");
        title2.setStyle("-fx-font-size: 14;-fx-border-style: solid; -fx-border-width: 0 0 0 0;-fx-font-weight: bold;");
        title2.setUnderline(true);
        title2.setAlignment(Pos.CENTER_LEFT);
        title2.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        colors.getChildren().add(title2);

        CheckBox square = new CheckBox("Square");
        CheckBox circle = new CheckBox("Circle");
        CheckBox rectangle = new CheckBox("Rectangle");
        CheckBox triangle = new CheckBox("Triangle");

        Button generate = new Button("Generate Image");
        generate.setOnAction(e -> imageOptions(red,green,blue,yellow,orange,square,circle,rectangle,triangle,generate));

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

        Button rightArrow = new Button("<--");
        rightArrow.setOnAction(e ->rightArrow(rightArrow));
        Button leftArrow = new Button("-->");
        leftArrow.setOnAction(e ->leftArrow(leftArrow));
        Button select = new Button("Select You Favorite Image");
        // METHOD
        select.setOnAction(e -> selectMethod(window, scene2));

        String userMessage = "The Image generator has \nnow created images.\n" +
                "This has been done according to \nuser specifacations that you entered." +
                "\nYou can now select nyour \nfavorite image to send to \nour server." +
                "This server holds\n the best paintings that we \nhave generated.";
        Text message = new Text(userMessage);
        message.setStyle("-fx-font-size:14");

        search.setPadding(new Insets(20,20,20,20));


        leftRight.getChildren().addAll(rightArrow,leftArrow);
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

    //bottom label
    public Label getBottomLabel(){
        Label lbl = new Label("Bottom Label");
        lbl.setPrefHeight(SIZE);
        lbl.prefWidthProperty().bind(root.widthProperty());
        //border width is done clockwise around the string, so the third 1 is the bottom.
        lbl.setStyle("-fx-border-style: dotted; -fx-border-width: 1 0 0 0;-fx-font-weight: bold;");
        lbl.setAlignment(Pos.BASELINE_CENTER);
        return lbl;
    }

    //This is where the method is for displaying an image for the user to select.
    // It will currently be displaying an image in the source file, but I'm guessing
    //this will be changed. It returns an HBox (Horizontal Box).

    public Pane imageDisplay(){
        Pane displayImage = new HBox();
        ImageView image = new ImageView("th.jpg");
        displayImage.getChildren().add(image);
        return displayImage;
    }

    //Below here is gonna be a second image method (because I'm guessing we need one for the server images and one for the local images)
    //It is not being used but can be.

    public Pane imageDisplay2(){
        Pane displayImage = new HBox();
        ImageView image = new ImageView("th.jpg");
        displayImage.getChildren().add(image);
        return displayImage;
    }

    //This is where all the handling of the buttons/checkmarks being. Currently passing void but you can change that if needed.
    //For now it will print what was selected. to the command line if you run it.

    public void imageOptions(CheckBox red,CheckBox green,CheckBox blue,CheckBox yellow,CheckBox orange,CheckBox square,CheckBox
            circle,CheckBox rectangle,CheckBox triangle, Button generate){
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

        System.out.println(message);
    }

    public void leftArrow(Button leftArrow){
        System.out.println("Left Pressed");
    }

    public void rightArrow(Button rightArrow){
        System.out.println("Right Pressed");
    }


    public void leftArrow2(Button leftArrow){
        System.out.println("Left Pressed");
    }

    public void rightArrow2(Button rightArrow){
        System.out.println("Right Pressed");
    }

    public VBox secondWindow(){
        VBox second = new VBox();
        HBox leftRight = new HBox();
        HBox image = new HBox();

        image.setPadding(new Insets(20,20,20,20));

        Button rightArrow = new Button("<--");
        rightArrow.setOnAction(e ->rightArrow2(rightArrow));
        Button leftArrow = new Button("-->");
        leftArrow.setOnAction(e ->leftArrow2(leftArrow));
        leftRight.getChildren().addAll(rightArrow,leftArrow);
        leftRight.setAlignment(Pos.TOP_CENTER);
        image.getChildren().addAll(imageDisplay());
        image.setAlignment(Pos.TOP_CENTER);
        second.getChildren().addAll(leftRight,image);
        return second;
    }

    public void selectMethod(Stage window, Scene scene2){
        window.setScene(scene2);
    }

    public static void main(String [] args){
        launch(args);
    }
}
