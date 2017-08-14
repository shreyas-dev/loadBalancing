package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ResultController {
	Stage secondaryStage;
	Main main;
	public void setMain(Main main,Stage secondaryStage){
		this.main=main;
		this.secondaryStage=secondaryStage;
	}
	double[] processTimeTogether;
	double[] processCostTogether;
    @FXML
    private NumberAxis timeTaken;

    @FXML
    private BarChart<?, ?> CostGraph;

    @FXML
    private NumberAxis costOfProcess;

    @FXML
    private BarChart<?, ?> TimeGraph;

    @FXML
    private CategoryAxis processNo;

    public void nextPage() {
    	secondResult();
    }
    public void closeResult() {

    }
	public void setResults(double[] processTimeTogether, double[] processCostTogether) {
		this.processTimeTogether=processTimeTogether;
		this.processCostTogether=processCostTogether;
		Series<?,?> time=new Series<>();
		time.setName("Time");
		Series<?,?> cost=new Series<>();
		cost.setName("Cost");
		
		XYChart.Series set1=new XYChart.Series<>();
		XYChart.Series set2=new XYChart.Series<>();
		for(int m=0;m<processTimeTogether.length;m++){
			time.getData().add(new XYChart.Data("Process "+(m+1),processTimeTogether[m]));
			set1.getData().add(new XYChart.Data("Process "+(m+1),processTimeTogether[m]));
			set2.getData().add(new XYChart.Data("Process "+(m+1),processCostTogether[m]));
			cost.getData().add(new XYChart.Data("Process "+(m+1),processCostTogether[m]));
		}
		TimeGraph.getData().addAll(set1);
		CostGraph.getData().addAll(set2);
	}
	public void secondResult() {
		// TODO Auto-generated method stub
		FXMLLoader loader=new FXMLLoader(Main.class.getResource("../view/DisplayResultsServer.fxml"));
		AnchorPane pane;
		try {
			pane = loader.load();
			Scene scene=new Scene(pane);
			secondaryStage.setScene(scene);
			secondaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void goPrev(){
		FXMLLoader loader=new FXMLLoader(Main.class.getResource("../view/DisplayResultsProcess.fxml"));
		try {
			AnchorPane pane=loader.load();
			Scene scene=new Scene(pane);
			secondaryStage.setScene(scene);
			secondaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}