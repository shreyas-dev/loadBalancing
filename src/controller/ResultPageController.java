package controller;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ResultPageController {

    Stage secondaryStage;
    Main main;
    String[] processUsed;
    @FXML Label caption,costLabel;
    @FXML Label heading;
    double[] processTimeTogether,cost,serverMaxUtilization;    
	@FXML
    private BarChart<?,?> processBarGraph;
	@FXML 
	private GridPane gridPane;
    @FXML
    private CategoryAxis processes;
    @FXML
    PieChart serverPieChart;
    @FXML
    private NumberAxis time;
    
    public void setMain(Main main,Stage secondaryStage) {
    	this.secondaryStage=secondaryStage;
    	this.main=main;
    }
	public void setResults(double[] processTimeTogether2, double[] cost,double[] serverMaxUtilization,String[] processUsed,int k) {
    	if(k==0) {
    		heading.setText("Showing Result's For Round Robin");
    	}else if(k==1) {
    		heading.setText("Showing Result's For Honey Bee");
    	}else if(k==2) {
    		heading.setText("Showing Result's For Bugs Algorithm");
    	}
		this.processTimeTogether=processTimeTogether2;
    	this.cost=cost;
    	this.processUsed=processUsed;
    	this.serverMaxUtilization=serverMaxUtilization;
    	caption.setTextFill(Color.DARKORANGE);
    	caption.setStyle("-fx-font: 24 arial;");
    	XYChart.Series set =new XYChart.Series<>();
    	for(int i=0;i<processTimeTogether.length;i++) {
    		set.getData().add(new XYChart.Data("Process "+(i+1),processTimeTogether[i]));
    	}
    	processBarGraph.getData().addAll(set);
    	ObservableList<PieChart.Data> data=FXCollections.observableArrayList();
    	for(int i=0;i<serverMaxUtilization.length;i++) {
    		System.out.println(serverMaxUtilization[i]);
    		data.add(new PieChart.Data("Server "+(i+1)+"(used)", serverMaxUtilization[i]));
    		data.add(new PieChart.Data("Server "+(i+1)+"(unused)", 100-serverMaxUtilization[i]));
    	}
    	serverPieChart.getData().addAll(data);    	
    	for (final PieChart.Data data1 : serverPieChart.getData()) {
    	    data1.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
    	        new EventHandler<MouseEvent>() {
    	            @Override public void handle(MouseEvent e) {
    	                caption.setText(String.valueOf(data1.getName()+" - "+data1.getPieValue()) + "%");
    	             }
    	        });
    	}
    	for(int j=0;j<processUsed.length;j++) {
    		Label name=new Label("     Process "+(j+1));
    		Label used=new Label("     "+processUsed[j]);
    		gridPane.add(name, 0,j+1);
    		gridPane.add(used, 1,j+1);
    	}
    	double sum = 0;
    	for(double x:cost) {
    		sum+=x;
    	}
    	costLabel.setText(Double.toString(sum));
    }
	public void Close() {
		secondaryStage.close();
	}
	public void printResults() {
		final FileChooser fileChooser = new FileChooser();
		Stage stage=new Stage();
    	fileChooser.getExtensionFilters().clear();
    	fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG(.png)", "*.png"));
    	final File file;
    	file = fileChooser.showSaveDialog(stage);
    	if (file != null) {
    		try {
    			Scene scene=secondaryStage.getScene();
    			WritableImage snapshot = scene.snapshot(null);
    			try {
    			    ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
    			} catch (IOException e) {
    			    e.printStackTrace();
    			}
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
	}
	
}