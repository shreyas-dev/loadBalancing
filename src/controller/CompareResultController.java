package controller;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CompareResultController {
	@FXML GridPane gridPane;
	ObservableList<Double> processTime=FXCollections.observableArrayList();
	ObservableList<Double> serverUtilization=FXCollections.observableArrayList();
	ObservableList<Double> cost=FXCollections.observableArrayList();
	ObservableList<String> algorithmUsed=FXCollections.observableArrayList();
	void setData(String algo,Double serverUtilization,Double processTime,Double cost) {
		algorithmUsed.add(algo);
		this.serverUtilization.add(serverUtilization);
		this.processTime.add(processTime);
		this.cost.add(cost);
	}
	void done() {
		final double maxServer=Collections.max(serverUtilization);
		final double timeMax=Collections.min(processTime);
		final double costMax=Collections.min(cost);
		for(int i=0;i<algorithmUsed.size();i++) {
			Label algoLabel=new Label(algorithmUsed.get(i));
			Label serverLabel=new Label(Double.toString(serverUtilization.get(i)));
			Label processLabel=new Label(Double.toString(processTime.get(i)));
			Label costLabel=new Label(Double.toString(cost.get(i)));
			if(serverUtilization.get(i)==maxServer) {
				serverLabel.setStyle("-fx-background-color: #FFAA11;");
			}
			if(processTime.get(i)==timeMax&&processTime.get(i)!=0) {
				processLabel.setStyle("-fx-background-color: #FFAA11;");
			}
			if(cost.get(i)==costMax&&cost.get(i)!=0) {
				costLabel.setStyle("-fx-background-color: #FFAA11;");
			}
			gridPane.add(algoLabel,0,i+1);
			gridPane.add(serverLabel,1,i+1);
			gridPane.add(processLabel,2,i+1);
			gridPane.add(costLabel, 3, i+1);
		}
	}
}
