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

public class AddServerController {
	Main main;
	Stage secondaryStage;
	@FXML ChoiceBox<String> serverLocation;
	@FXML Label label;
	@FXML ChoiceBox<String> serverRam;
	@FXML TextField serverRequests;
	ObservableList<String> location=FXCollections.observableArrayList("--Select--","North America","South America","Asia","Europe","Africa");
	ObservableList<String> ram=FXCollections.observableArrayList("--Select--","1024","2048","4096");
	public void setMain(Main main,Stage secondaryStage){
		this.main=main;
		this.secondaryStage=secondaryStage;
		serverLocation.setItems(location);
		serverLocation.getSelectionModel().select(0);
		serverRam.setItems(ram);
		serverRam.getSelectionModel().select(0);
		serverRequests.textProperty().addListener(new ChangeListener<String>() {
			 @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		            if (!newValue.matches("\\d*")) {
		                serverRequests.setText(newValue.replaceAll("[^\\d]", ""));
		            }
		        }
		    });
	}
	public void cancel(){
		secondaryStage.close();
	}
	public void add(){
		if(serverLocation.getSelectionModel().getSelectedIndex()==0) {
			label.setText("Please select the region of the server");
		}
		else if(serverRam.getSelectionModel().getSelectedIndex()==0) {
			label.setText("Please select the RAM size of the server");
		}
		else if(serverRequests.getText().trim().isEmpty()){
			label.setText("Please Enter the Requests it can handle");
		}else if(serverRequests.getText().equals("0")){
			label.setText("Please Enter Valid Requests it can handle");
		}
		else{
			main.setNewServer(serverLocation.getSelectionModel().getSelectedItem(),
					Integer.parseInt(serverRam.getSelectionModel().getSelectedItem()),Integer.parseInt(serverRequests.getText()));
			secondaryStage.close();
		}
	}
	public void resetAction() {
		serverRequests.setText("1");
		serverRam.getSelectionModel().clearAndSelect(0);
		serverLocation.getSelectionModel().clearAndSelect(0);
	}
	
}
