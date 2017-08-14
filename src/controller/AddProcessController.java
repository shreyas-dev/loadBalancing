package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProcessController {
	Main main;
	Stage secondaryStage;
	@FXML ChoiceBox<String> processLocation;
	@FXML Label label;
	@FXML TextField processSize,processRequest;
	ObservableList<String> location=FXCollections.observableArrayList("--Select--","North America","South America","Asia","Europe","Africa");
	public void setMain(Main main,Stage secondaryStage){
		this.main=main;
		this.secondaryStage=secondaryStage;
		processLocation.setItems(location);
		processLocation.getSelectionModel().select(0);
		processSize.textProperty().addListener(new ChangeListener<String>() {
			 @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		            if (!newValue.matches("\\d*")) {
		                processSize.setText(newValue.replaceAll("[^\\d]", ""));
		            }
		        }
		    });
		processRequest.textProperty().addListener(new ChangeListener<String>() {
			 @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				 if (!newValue.matches("\\d*")) {
		                processRequest.setText(newValue.replaceAll("[^\\d]", ""));
		            }
		        }
		    });
	}
	public void cancel(){
		secondaryStage.close();
	}
	public void add(){
		if(processLocation.getSelectionModel().getSelectedIndex()==0) {
			label.setText("Please select the region of the process");
		}
		else if(processSize.getText().trim().isEmpty()){
			label.setText("Please Enter Size of the Process");
		}else if(processRequest.getText().trim().isEmpty()){
			label.setText("Please Enter No. of Requests");
		}else if(processRequest.getText().equals("0")){
			label.setText("Please Enter Valid No. of Requests");
		}else if(processSize.getText().equals("0")){
			label.setText("Please Enter Valid Size of the Process");
		}
		else{
			main.setNewProcess(processLocation.getSelectionModel().getSelectedItem(),Integer.parseInt(processSize.getText()),Integer.parseInt(processRequest.getText()));
			secondaryStage.close();
		}
	}
	public void resetAction() {
		processRequest.setText("1");
		processSize.setText("1");
		processLocation.getSelectionModel().clearAndSelect(0);
	}
}
