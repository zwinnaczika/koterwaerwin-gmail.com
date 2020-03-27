package application;
	
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	Label statusLbl;
	Label distLbl;
	Label effLbl;
	Label resLbl;
	Button calculateBtn;
	TextField tripDistance;
	TextField fuelEff;
	RadioButton octane98;
	RadioButton diesel;
	TextField result;
	
	
	@Override
	public void start(Stage primaryStage) {
		Client a = new Client();
		try {
			primaryStage.setTitle("Application");
			calculateBtn = new Button("Calculate");
			
			statusLbl = new Label("Status: ");
			distLbl = new Label("Trip Distance");
			effLbl = new Label("Fuel Efficiency in MPG");
			resLbl = new Label("Result");
			result = new TextField();
			result.setTooltip(new Tooltip("Result"));
			result.setEditable(false);
			
			tripDistance = new TextField();
			tripDistance.setTooltip(new Tooltip("Trip Distance"));
			
			fuelEff = new TextField();
			fuelEff.setTooltip(new Tooltip("Fuel Efficiency"));
			
			octane98 = new RadioButton("Octane 98 Fuel");
			diesel = new RadioButton("Diesel Fuel");
			
			octane98.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                if (octane98.isSelected())
	                {
	                	diesel.setSelected(false);
	                }
	            }
	        });
			
			diesel.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                if(diesel.isSelected())
	                {
	                	octane98.setSelected(false);
	                }
	            }
	        });
			
			calculateBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                try 
	                {
	                	statusLbl.setText("Status: Good");
	                	String distance = tripDistance.getText();
	                	String efficiency = fuelEff.getText();
	                	Double.parseDouble(distance);
	                	Double.parseDouble(efficiency);
	                	
	                	String fuel = "";
	                	if(octane98.isSelected())
	                	{
	                		fuel = "octane";
	                	}
	                	else if(diesel.isSelected())
	                	{
	                		fuel = "diesel";
	                	}
	                	else
	                	{
	                		statusLbl.setText("Status: Select a fuel type.");
	                	}
	                	
	                	Message outMsg = new Message(distance,efficiency,fuel);
	                	
	                	a.connect();
	                	System.out.println("Send Data.");

	                	System.out.println("Data: "+outMsg.getFuelEff());
	                	a.writeData(outMsg);
	                	Message inMsg;
						try {
							inMsg = a.readData();
							result.setText((float) Double.parseDouble(inMsg.getResult())+" pounds");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            
	                }
	                catch(NumberFormatException e){statusLbl.setText("Status: Enter correct Values."); }
	            }
	        });
			
			GridPane root = new GridPane();
			root.add(statusLbl,0,0);
			root.add(calculateBtn,0,1);
			root.add(tripDistance,1,2);
			root.add(distLbl,0,2);
			root.add(fuelEff,1,3);
			root.add(effLbl,0,3);
			root.add(octane98,0,4);
			root.add(diesel,0,5);
			root.add(result,1,6);
			root.add(resLbl,0,6);
			StackPane mainlayout = new StackPane();
			mainlayout.getChildren().add(root);
			Scene scene = new Scene(mainlayout,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
