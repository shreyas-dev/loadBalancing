package controller;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	Stage primaryStage;
	MainWindowController mwc;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		mainWindow();
	}
	public void mainWindow(){
		try {
			FXMLLoader loader=new FXMLLoader(Main.class.getResource("../view/MainWindowController.fxml"));
			AnchorPane pane=loader.load();
			mwc=loader.getController();
			mwc.setMain(this,primaryStage);
			Scene scene=new Scene(pane);
			primaryStage.setTitle("Load Balacing Stimulation");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			this.getClass().getResource("application.css").toExternalForm();
			//scene.getStylesheets().add("application.css");
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addServer(){
		try {
			FXMLLoader loader=new FXMLLoader(Main.class.getResource("../view/AddServerView.fxml"));
			AnchorPane pane=loader.load();
			Stage secondaryStage=new Stage();
			secondaryStage.initModality(Modality.APPLICATION_MODAL);
			AddServerController asc=loader.getController();
			asc.setMain(this,secondaryStage);
			Scene scene=new Scene(pane);
			secondaryStage.setTitle("Load Balacing Stimulation");
			secondaryStage.setResizable(false);
			secondaryStage.setScene(scene);
			secondaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addProcess(){
		try {
			FXMLLoader loader=new FXMLLoader(Main.class.getResource("../view/AddProcessView.fxml"));
			AnchorPane pane=loader.load();
			Stage secondaryStage=new Stage();
			secondaryStage.initModality(Modality.APPLICATION_MODAL);
			AddProcessController aac=loader.getController();
			aac.setMain(this,secondaryStage);
			Scene scene=new Scene(pane);
			secondaryStage.setTitle("Load Balacing Stimulation");
			secondaryStage.setResizable(false);
			secondaryStage.setScene(scene);
			secondaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setNewServer(String serverLocation,Integer serverRam,Integer serverRequests){
		mwc.setNewServer(serverLocation,serverRam, serverRequests);
	}
	public void setNewProcess(String processLocation,Integer processSize,Integer processRequest){
		mwc.setNewProcess(processLocation,processSize,processRequest);
	}
	public static void main(String[] args) {
		launch(args);
	}
	public void resultWindow(double[] processTimeTogether,double[] processCostTogether,double[] serverMaxUtilization,String[] processUsed,int i) {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader=new FXMLLoader(Main.class.getResource("../view/ResultPage.fxml"));
			AnchorPane pane=loader.load();
			ResultPageController rc=loader.getController();
			Stage secondaryStage=new Stage();
			rc.setMain(this,secondaryStage);
			rc.setResults(processTimeTogether,processCostTogether,serverMaxUtilization,processUsed,i);
			Scene scene=new Scene(pane);
			secondaryStage.setTitle("Load Balancing Results");
			secondaryStage.setResizable(false);
			secondaryStage.setScene(scene);
			secondaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void helpMenu() {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader=new FXMLLoader(Main.class.getResource("../view/HelpDisplay.fxml"));
			AnchorPane pane=loader.load();
			Stage helpStage=new Stage();
			Scene scene=new Scene(pane);
			helpStage.initModality(Modality.APPLICATION_MODAL);
			helpStage.setTitle("Help Window");
			helpStage.setScene(scene);
			helpStage.setResizable(false);
			helpStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void aboutPage() {
		try {
			FXMLLoader loader=new FXMLLoader(Main.class.getResource("../view/AboutPage.fxml"));
			AnchorPane pane=loader.load();
			Stage aboutStage=new Stage();
			Scene scene=new Scene(pane);
			aboutStage.initModality(Modality.APPLICATION_MODAL);
			aboutStage.setTitle("About Page");
			aboutStage.setScene(scene);
			aboutStage.setResizable(false);
			aboutStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
