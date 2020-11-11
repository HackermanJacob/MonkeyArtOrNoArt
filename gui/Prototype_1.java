package application;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.text.Text;


public class Prototype_1 extends Application{
	
	Stage window;
	Button generateButton;
	
	public static void main(String[]args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)throws Exception{
		window = primaryStage;
		window.setTitle("Prototype1");
		Pane pane =new Pane();
		
		GridPane grid1 = new GridPane();
		
		grid1.setPadding(new Insets(20,30,30,20));
		grid1.setVgap(5);
		grid1.setHgap(6);
		
		String shapesLabel = "Shapes";
		Text textShapes = new Text(12,12, shapesLabel);
		textShapes.setUnderline(true);
		GridPane.setConstraints(textShapes,0,0);
		
		String colorsLabel = "Colors";
		Text textColor = new Text(12,12, colorsLabel);
		textColor.setUnderline(true);
		GridPane.setConstraints(textColor,0,19);
		
		//checkboxes
		CheckBox colorBox1 = new CheckBox("Blue");
		GridPane.setConstraints(colorBox1,0,1);
		CheckBox colorBox2 = new CheckBox("Green");
		GridPane.setConstraints(colorBox2,1,1);
		CheckBox colorBox3 = new CheckBox("Red");
		GridPane.setConstraints(colorBox3,0,2);
		
		CheckBox box3 = new CheckBox("Triangle");
		GridPane.setConstraints(box3,0,20);
		CheckBox box4 = new CheckBox("Rectange");
		GridPane.setConstraints(box4,1,20);

		
		
		//submit Button
		generateButton = new Button();
		generateButton.setText("Generate images");
		GridPane.setConstraints(generateButton,0, 30);
		
		generateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Temporary text, but it will generate images after pressing this");
			}
		});
		
		
		grid1.getChildren().addAll(colorBox1,colorBox2,colorBox3,box3,box4,textShapes,textColor,generateButton);
		pane.getChildren().addAll(grid1);
		grid1.setStyle("-fx-background-color: grey");
		Scene scene = new Scene(pane, 600,300);
		window.setScene(scene);
		window.show();
	}
}
