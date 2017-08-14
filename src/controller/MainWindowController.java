package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javafx.scene.Node;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Processes;
import model.Server;

public class MainWindowController {
	Main main;
	Stage primaryStage,secondaryStage;
	int i=0,p=0,q=0;
	int count=0;
	ArrayList<String> serverLocationList=new ArrayList<>();
	ArrayList<Integer> serverRequestsList=new ArrayList<>();
	ArrayList<Integer> serverRamList=new ArrayList<>();
	ArrayList<Integer> processRequestsList=new ArrayList<>();
	ArrayList<String> processLocationList=new ArrayList<>();
	ArrayList<Integer> processSizeList=new ArrayList<>();
	String[] processUsed;
	double[] processTimeTogether;
	double[] cost;
	double[] serverUtilization;
	double[] serverMaxUtilization;
	@FXML TableView<Server> server;
	@FXML ChoiceBox<String> selectedAlgorithm;
	@FXML TableView<Processes> processes;
	@FXML TableColumn<Processes,String> processLocation;
	@FXML TableColumn<Processes,Integer> processRequest;
	@FXML TableColumn<Processes,Integer> processSize;
	@FXML TableColumn<Server,String> serverLocation;
	@FXML TableColumn<Server,Integer> serverRam,serverRequests;
	@FXML MenuBar menuBar;
	Menu file=new Menu("File");
	Menu options=new Menu("Help");
	Menu edit=new Menu("Edit");
	MenuItem help = new MenuItem("Help");
	MenuItem open1=new MenuItem("Load");
	MenuItem save=new MenuItem("Save");
	MenuItem about=new MenuItem("About");
	MenuItem run=new MenuItem("Run");
	MenuItem resetServer = new MenuItem("Reset Server Details");
	MenuItem clearServer = new MenuItem("Clear Server Details");
	MenuItem resetProcess = new MenuItem("Reset Process Details");
	MenuItem clearProcess = new MenuItem("Clear Process Details");
	MenuItem close = new MenuItem("Exit");
	int serverCount=0,processCount;
	final FileChooser fileChooser = new FileChooser();
	ObservableList<Server> serverData=FXCollections.observableArrayList(new Server("North America",2048,2000),new Server("Asia",2048,2000));
	ObservableList<Processes> processData=FXCollections.observableArrayList(new Processes("Asia",1024,1200));
	ObservableList<String> algorithms=FXCollections.observableArrayList("Select","Round Robin ","Honey Bee","Bugs","Execute All");
	public void initialize(){
		server.setRowFactory(tv->new TableRow<Server>(){
		    @Override
		    public void updateItem(Server item, boolean empty) {
		        super.updateItem(item, empty) ;
		        if (item == null) {
		            setStyle("");
		        }else if (item.getInstrumentId()%5==0) {
		            setStyle("-fx-background-color:#CFD8DC;");
		        }else if (item.getInstrumentId()%5==1) {
		            setStyle("-fx-background-color: #FFCCBC;");
		        } else if (item.getInstrumentId()%5==2) {
		            setStyle("-fx-background-color: #64b5f6;");
		        } else if (item.getInstrumentId()%5==3) {
		            setStyle("-fx-background-color: #ffb74d;");
		        } else if (item.getInstrumentId()%5==4) {
		            setStyle("-fx-background-color: #f48fb1;");
		        }
		        getStyleClass().add("table");
		    }
		});
		processes.setRowFactory(tv->new TableRow<Processes>() {
			public void updateItem(Processes item, boolean empty) {
		        super.updateItem(item, empty) ;
		        if (item == null) {
		            setStyle("");
		        }else if (item.getInstrumentId()%5==0) {
		            setStyle("-fx-background-color:#CFD8DC;");
		        }else if (item.getInstrumentId()%5==1) {
		            setStyle("-fx-background-color: #FFCCBC;");
		        } else if (item.getInstrumentId()%5==2) {
		            setStyle("-fx-background-color: #64b5f6;");
		        } else if (item.getInstrumentId()%5==3) {
		            setStyle("-fx-background-color: #ffb74d;");
		        } else if (item.getInstrumentId()%5==4) {
		            setStyle("-fx-background-color: #f48fb1;");
		        }
		    }
		});
		serverData.forEach(server->{
			server.setInstrumentId(serverCount);
			serverCount++;
		});
		processData.forEach(processes->{
			processes.setInstrumentId(processCount);
			processCount++;
		});
		open1.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
		save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		help.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
		close.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
		about.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		run.setAccelerator(KeyCombination.keyCombination("Shift+R"));
		resetServer.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
		resetProcess.setAccelerator(KeyCombination.keyCombination("Alt+R"));
		clearProcess.setAccelerator(KeyCombination.keyCombination("Alt+C"));
		clearServer.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
		server.getStyleClass().add("table");
		open1.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	        	String userDirectoryString = System.getProperty("user.home");
	        	File userDirectory = new File(userDirectoryString);
	        	if(!userDirectory.canRead()) {
	        	    userDirectory = new File("c:/");
	        	}
	        	fileChooser.setInitialDirectory(userDirectory);
	        	fileChooser.getExtensionFilters().clear();
	        	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Load Balancing Simulator(*.lbs)", "*.lbs"));
	        	File file = fileChooser.showOpenDialog(primaryStage);
	        	 if (file != null) {
	        		 	InputStream fis;
						try {
							fis = new FileInputStream(file.getAbsolutePath());
							InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		        		    BufferedReader br = new BufferedReader(isr);
		        		    String line;
		        		    if((line = br.readLine()) != null) {
		        		    	int count=Integer.parseInt(line);
		        		    	int x=0;
		        		    	serverData.clear();
		        		    	while(x<count) {
		        		    		line = br.readLine();
		        		    		System.out.println(line);
		        		    		String words[]=line.split("\t");
		        		    		serverData.add(new Server(words[0],Integer.parseInt(words[1]),Integer.parseInt(words[2])));
		        		    		x++;
		        		    	}
		        		    	br.readLine();
		        		    	line=br.readLine();
		        		    	count=Integer.parseInt(line);
		        		    	x=0;
		        		    	processData.clear();
		        		    	while(x<count) {
		        		    		line = br.readLine();
		        		    		String words[]=line.split("\t");
		        		    		processData.add(new Processes(words[0],Integer.parseInt(words[1]),Integer.parseInt(words[2])));
		        		    		x++;
		        		    	}
		        		    	br.close();
		        		    }
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							Alert alert=new Alert(AlertType.ERROR);
							alert.setTitle("File Error");
							alert.setHeaderText("File not found");
							alert.setContentText("The selected file is not found,please enter another one");
							alert.show();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							Alert alert=new Alert(AlertType.ERROR);
							alert.setTitle("File Error");
							alert.setHeaderText("File is not in proper format or is corrupted");
							alert.setContentText("The selected file is either corrupted or is not in proper format,Please enter another file");
							alert.show();						
						}catch(Exception e) {
							Alert alert=new Alert(AlertType.ERROR);
							alert.setTitle("File Error");
							alert.setHeaderText("File not in proper format or has been modified");
							alert.setContentText("The selected file has been modified or not in proper format,Please enter another file");
							alert.show();
						}
						serverCount=0;
						processCount=0;
						serverData.forEach(server->{
							server.setInstrumentId(serverCount);
							serverCount++;
						});
						processData.forEach(processes->{
							processes.setInstrumentId(processCount);
							processCount++;
						});
	        	 }
	        }
		});
		run.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	        	simulation();
	        }
	    });
		save.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	        	String userDirectoryString = System.getProperty("user.home");
	        	File userDirectory = new File(userDirectoryString);
	        	if(!userDirectory.canRead()) {
	        	    userDirectory = new File("c:/");
	        	}
	        	fileChooser.setInitialDirectory(userDirectory);
	        	fileChooser.getExtensionFilters().clear();
	        	fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Load Balancing Simulator(*.lbs)", "*.lbs"));
	        	File file;
	        	file = fileChooser.showSaveDialog(primaryStage);
	        	if (file != null) {
	        		try {
						FileWriter fw = new FileWriter(file.getAbsolutePath(), true);
						BufferedWriter bw = new BufferedWriter(fw);
						 PrintWriter out = new PrintWriter(bw);
						 out.println(serverData.size());
						 serverData.forEach(server->{
							 out.println(server.getServerLocation()+"\t"+server.getServerRam()+"\t"+server.getServerRequests());
						});
						 out.println();
						 out.println(processData.size());
						 processData.forEach(processes->{
							 out.println(processes.getProcessLocation()+"\t"+processes.getProcessSize()+"\t"+processes.getProcessRequest());
						 });
						 out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	       
	            }
	        }
		});
		about.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	        	main.aboutPage();
	        }
		});
		help.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	        	main.helpMenu();
	        }
		});
		close.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	        	primaryStage.close();
	        }
		});
		resetServer.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	        	serverData.clear();
	        	serverData.addAll(new Server("North America",2048,2000),new Server("Asia",2048,2000));
	        }
		});
		resetProcess.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	        	processData.clear();
	        	processData.addAll(new Processes("Asia",1024,1200));
	        }
		});
		clearServer.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	        	serverData.clear();
	        }
		});
		clearProcess.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent t) {
	        	processData.clear();
	        }
		});

		file.getItems().addAll(open1,save,run,close);
		edit.getItems().addAll(resetServer,resetProcess,clearServer,clearProcess);
		options.getItems().addAll(help,about);
		menuBar.getMenus().clear();
		 menuBar.getMenus().addAll(file,edit,options);
		serverLocation.setCellValueFactory(new PropertyValueFactory<Server,String>("serverLocation"));
		serverRam.setCellValueFactory(new PropertyValueFactory<Server,Integer>("serverRam"));
		serverLocation.setCellFactory(TextFieldTableCell.forTableColumn());
		//serverRam.setCellFactory(TextFieldTableCell.forTableColumn());
		serverRequests.setCellValueFactory(new PropertyValueFactory<Server,Integer>("serverRequests"));
		processLocation.setCellValueFactory(new PropertyValueFactory<Processes,String>("processLocation"));
		processSize.setCellValueFactory(new PropertyValueFactory<Processes,Integer>("processSize"));
		processRequest.setCellValueFactory(new PropertyValueFactory<Processes,Integer>("processRequest"));
		server.setEditable(true);
		ContextMenu cm = new ContextMenu();
	    MenuItem mi1 = new MenuItem("Delete selected Server");
	    cm.getItems().add(mi1);
	    MenuItem mi2 = new MenuItem("Add a new Server");
	    cm.getItems().add(mi2);
	    mi2.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            main.addServer();
	        }
	    });
	    mi1.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            deleteServer();
	        }
	    });
	    server.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	            if(t.getButton() == MouseButton.SECONDARY)
	            {
	            	cm.show(server , t.getScreenX() , t.getScreenY());
	            }
	         }
	        });
	    ContextMenu cm1 = new ContextMenu();
	    MenuItem mi11 = new MenuItem("Delete selected Process");
	    cm1.getItems().add(mi11);
	    MenuItem mi21 = new MenuItem("Add a new Process");
	    cm1.getItems().add(mi21);
	    mi21.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            main.addProcess();
	        }
	    });
	    mi11.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent e) {
	            deleteProcess();
	        }
	    });
	   processes.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t) {
	            if(t.getButton() == MouseButton.SECONDARY)
	            {
	            	cm1.show(processes, t.getScreenX() , t.getScreenY());
	            }
	         }
	        });
	}
	public void setMain(Main main,Stage primaryStage){
		this.main=main;
		this.primaryStage=primaryStage;
		server.setItems(serverData);
		processes.setItems(processData);
		selectedAlgorithm.setItems(algorithms);
		selectedAlgorithm.getSelectionModel().select(0);
	}
	public void setNewServer(String serverLocation,Integer serverRam,Integer serverRequests){
		serverCount=0;
		serverData.add(new Server(serverLocation,serverRam,serverRequests));
		serverData.forEach(server->{
			server.setInstrumentId(serverCount);
			serverCount++;
		});
	}
	public void setNewProcess(String processLocation,Integer processSize,Integer processRequest){
		processCount=0;
		processData.add(new Processes(processLocation,processSize,processRequest));
		processData.forEach(processes->{
			processes.setInstrumentId(processCount);
			processCount++;
		});
	}
	public void deleteServer(){
		ObservableList<Server> removeServer;
		removeServer=server.getSelectionModel().getSelectedItems();
		if(removeServer.size()==0) {
			Alert alert=new Alert(AlertType.WARNING);
			alert.setHeaderText("No Server/Servers selected");
			alert.setContentText("Please select a server/servers to be deleted");
			alert.show();
		}
		removeServer.forEach(serverData::remove);
	}
	public void deleteProcess(){
		ObservableList<Processes> removeProcess;
		removeProcess=processes.getSelectionModel().getSelectedItems();
		if(removeProcess.size()==0) {
			Alert alert=new Alert(AlertType.WARNING);
			alert.setHeaderText("No Process/Processes selected");
			alert.setContentText("Please select a Process/Processes to be deleted");
			alert.show();
		}
		removeProcess.forEach(processData::remove);
	}
	public void newServer(){
		main.addServer();
	}
	public void newProcess(){
		main.addProcess();
	}
	public void simulation(){
		if(serverData.size()==0) {
			Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("No Servers Listed");
			alert.setHeaderText("No Servers Available");
			alert.setContentText("Please provide server details to perform load balancing");
			alert.show();
			return ;
		}else if(processData.size()==0) {
			Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("No Process Given");
			alert.setHeaderText("Please Provide Proess");
			alert.setContentText("Please provide process details to perform load balancing");
			alert.show();
			return ;
		}
		serverData.forEach(server->{
			 serverLocationList.add(server.getServerLocation());
			 serverRequestsList.add(server.getServerRequests());
			 serverRamList.add(server.getServerRam());
		});
		i=0;
		processData.forEach(processes->{
			processLocationList.add(processes.getProcessLocation());
			processSizeList.add(processes.getProcessSize());
			processRequestsList.add(processes.getProcessRequest());
		});
		processUsed=new String[processLocationList.size()];
		processTimeTogether=new double[processLocationList.size()];
		cost=new double[serverLocationList.size()];
		serverUtilization=new double[serverLocationList.size()];
		serverMaxUtilization=new double[serverLocationList.size()];
		if(selectedAlgorithm.getSelectionModel().getSelectedIndex()==0){
			Alert alert=new Alert(AlertType.WARNING);
			alert.setTitle("No Algorithm Selected");
			alert.setHeaderText("Please select an algorithm");
			alert.setContentText("Please select an algorithm from the drop down box selected");
			alert.show();
		}
		else if(selectedAlgorithm.getSelectionModel().getSelectedIndex()==1){
			roundRobin(1);
		}else if(selectedAlgorithm.getSelectionModel().getSelectedIndex()==2){
			honeyBee();
			resultWindow(1);
		}else if(selectedAlgorithm.getSelectionModel().getSelectedIndex()==3){
			my_alog();
			resultWindow(2);
		}else if(selectedAlgorithm.getSelectionModel().getSelectedIndex()==4){
			CompareResultController crc;
			try {
				FXMLLoader loader=new FXMLLoader(Main.class.getResource("../view/compareResultView.fxml"));
				AnchorPane pane=loader.load();
				Stage secondaryStage=new Stage();
				crc=loader.getController();
				roundRobin(0);
				double serverUtilization=getMax(serverMaxUtilization);
				double processTime=getMax(processTimeTogether);
				double costTogether=getSum(cost);
				crc.setData("Round Robin",serverUtilization,processTime, costTogether);
				clear();
				honeyBee();
				serverUtilization=getMax(serverMaxUtilization);
				processTime=getMax(processTimeTogether);
				costTogether=getSum(cost);
				crc.setData("Honey Bee",serverUtilization,processTime, costTogether);
				clear();
				my_alog();
				serverUtilization=getMax(serverMaxUtilization);
				processTime=getMax(processTimeTogether);
				costTogether=getSum(cost);
				crc.setData("Bugs Algorithm",serverUtilization,processTime, costTogether);
				clear();
				crc.done();
				Scene scene=new Scene(pane);
				secondaryStage.setTitle("Comparing all Algorithms");
				//secondaryStage.setResizable(false);
				secondaryStage.setScene(scene);
				secondaryStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//crc.setData("Round Robin", Collections.max(serverUtilization), Collections.max(processTimeTogether));
			//got to it tomorrow
		}
		arrayClear();
	}
	void arrayClear(){
		serverRamList.clear();
		serverLocationList.clear();
		serverRequestsList.clear();
		processLocationList.clear();
		processSizeList.clear();
		processRequestsList.clear();
	}
	void clear() {
		for(int c=0;c<cost.length;c++) {
			cost[c]=0d;
			serverUtilization[c]=0d;
			serverMaxUtilization[c]=Double.MIN_VALUE;
		}
		for(int c=0;c<processUsed.length;c++) {
			processUsed[c]="";
		}
		for(int c=0;c<processTimeTogether.length;c++) {
			processTimeTogether[c]=0d;
		}
	}
	
	double getSum(double[] cost) {
		double sum=cost[0];
		for(int i=1;i<cost.length;i++) {
			sum+=cost[i];
		}
		return sum;
	}
	double getMax(double[] list) {
		double max=list[0];
		for(int i=1;i<list.length;i++) {
			if(list[i]>max) {
				max=list[i];
			}
		}
		return max;
	}
	public void roundRobin(int o){
		int j=0;
		int[] serverExecution=new int[serverLocationList.size()];
		boolean[] processDone=new boolean[processLocationList.size()];
		double[] processTime=new double[processLocationList.size()];
		int maxServer=Collections.max(serverRequestsList);
		int maxRam=Collections.max(serverRamList);
		int maxProcessRam=Collections.max(processSizeList);
		int maxProcess=Collections.max(processRequestsList);
		if(maxProcess>maxServer || maxProcessRam>maxRam) {
			Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Input");
			alert.setHeaderText("Process Too Big");
			alert.setContentText("Round Robin is static algorithm and since the process is too big to handled by the server");
			alert.show();
			return ;
		}
		while(j!=processLocationList.size()){
			for(i=0;j<processDone.length&&i<serverLocationList.size();i++){
				if(!processDone[j]){
					if(serverRamList.get(i)>=processSizeList.get(j)&&serverRequestsList.get(i)>=processRequestsList.get(j)){
						processDone[j]=true;
						cost[i]+=(double)(serverRamList.get(i)*10+serverRequestsList.get(i))/100;
						processTime[j]+=3+serverExecution[i];
						serverUtilization[i]=100*((double)processRequestsList.get(j)/serverRequestsList.get(i));
						if(serverMaxUtilization[i]<serverUtilization[i])
							serverMaxUtilization[i]=serverUtilization[i];
						if(serverLocationList.get(i)!=processLocationList.get(j)){
							processTime[j]+=2;
							serverExecution[i]+=2;
						}
						processUsed[j]=""+(i+1)+"";
						serverExecution[i]+=2;
						j++;
					}
				}
			}
		}
		for(int i=0;i<processTime.length;i++) {
			processTimeTogether[i]=processTime[i];
		}
		if(o==1)
		resultWindow(0);
//		for(double x:processTime){
//			System.out.println(x);
//		}
	}
	
	
	
	public void honeyBee(){
		final double overLoadingFactor=80d;
		double temp=0;
		int temp2=1,temp1;
		int overLoadedProcess[]=new int[processLocationList.size()];
		int k=0;
		int j=0,i=0;
		double maxServer=((double)Collections.max(serverRequestsList)*0.8d);
		for(j=0;j<processLocationList.size();) {
			temp2=1;
			if(processRequestsList.get(j)>maxServer) {
				temp1=(int)Math.ceil(processRequestsList.get(j)/maxServer);
				processRequestsList.set(j, processRequestsList.get(j)/temp1);
				//processSizeList.set(j, processSizeList.get(j)/temp1);
				processSizeList.set(j, processSizeList.get(j)/temp1);
				while(temp2!=temp1) {
					processRequestsList.add(j+temp2, processRequestsList.get(j));
					//processSizeList.set(j, processSizeList.get(j)/temp1);
					processSizeList.add(j+temp2, processSizeList.get(j));
					processLocationList.add(j+temp2,processLocationList.get(j));
					temp2++;
				}
				overLoadedProcess[k++]=temp1-1;
				j+=temp1;
			}else {
				overLoadedProcess[k++]=0;
				j++;
			}
		}
		//System.out.println("Printing overloaded process");
//		for(int x:overLoadedProcess) {
//			System.out.println(x+" ");
//		}
		
		for(int l=0;l<processRequestsList.size();l++) {
			System.out.println(processRequestsList.get(l));
		}
		boolean flag=false;
		boolean[] processDone=new boolean[processLocationList.size()];
		boolean[] serverBusy=new boolean[serverLocationList.size()];
		double[] processTime=new double[processLocationList.size()];
		boolean serverUsed[]=new boolean[serverLocationList.size()];
		int serverExtraTime[]=new int[serverLocationList.size()];
		int serverTimeUsed[]=new int[serverLocationList.size()];
		for(int m=0;m<serverTimeUsed.length;m++) {
			serverTimeUsed[m]=1;
		}
		byte[] processServerUsed=new byte[processLocationList.size()];
		i=0;
		while(!flag) {
			flag=true;
			for(j=0;j<processRequestsList.size();j++) {
				if(!processDone[j])
				{
					for(i=0;i<serverRequestsList.size();i++)
					{
						temp=((double)processRequestsList.get(j)/serverRequestsList.get(i))*100;
						serverUtilization[i]+=temp;
						if(serverUtilization[i]>overLoadingFactor){
							serverUtilization[i]-=temp;
							serverBusy[i]=true;
							//System.out.println("Overloaded due to process "+j);
						}else {
							processDone[j]=true;
							//cost[j];
							if(serverUtilization[i]>serverMaxUtilization[i])
								serverMaxUtilization[i]=serverUtilization[i];
							if(serverLocationList.get(i)!=processLocationList.get(j))
								processTime[j]+=2;
							serverExtraTime[i]+=ramDelay(serverRamList.get(i),processSizeList.get(j));
							serverUsed[i]=true;
							processServerUsed[j]=(byte)i;
							processTime[j]+=3+serverExtraTime[i];
							//System.out.println("Server executed by "+ i);
							break;
						}
					}
				}
			}
			for(i=0;i<serverLocationList.size();i++) {
				serverUtilization[i]=0;
				serverTimeUsed[i]++;
				if(serverUsed[i]==true) {
					cost[i]+=(double)(serverRamList.get(i)*10+serverRequestsList.get(i))/100;
					serverUsed[i]=false;
				}
			}
			for(j=0;j<processDone.length;j++) {
				if(!processDone[j]){
					flag=false;
					processTime[j]++;
				}
			}
		}
//		for(int m=0;m<processRequestsList.size();m++) {
//			System.out.println(m);
//			System.out.println(processTime[m]);
//		}
		rejoin(overLoadedProcess,processServerUsed,processTime);
	}
	
	
	public void min_min(){
		int[] serverExecution=new int[serverLocationList.size()];
		boolean[] processDone=new boolean[processLocationList.size()];
		double[] processTime=new double[processLocationList.size()];
		int j=0;
		
		Collections.sort(serverRequestsList);
		Collections.sort(processSizeList);
		Collections.reverse(serverRequestsList);
		System.out.println(serverRequestsList);
		System.out.println(processSizeList);
		Collections.sort(serverRequestsList);
		while(j!=processLocationList.size()){
			for(i=0;j<processDone.length&&i<serverLocationList.size();i++){
				if(!processDone[j]){
					//serverRamList.get(i)>=processSizeList.get(j)&&
					if(serverRequestsList.get(i)>=processRequestsList.get(j)){
						processDone[j]=true;
						cost[j]=(double)(serverRamList.get(i)*10+serverRequestsList.get(i))/100;
						processTime[j]+=3+serverExecution[i];
						if(serverLocationList.get(i)!=processLocationList.get(j)){
							cost[j]+=1000;
							processTime[j]+=2;
							serverExecution[i]+=2;
						}
						serverExecution[i]++;
						j++;
					}
				}
			}
		}
//		for(double x:processTime){
//			System.out.println(x);
//		}
	}
	
	
	public void my_alog() {
		final double overLoadingFactor=80d;
		double temp=0;
		int temp2=1,temp1;
		int overLoadedProcess[]=new int[processLocationList.size()];
		int k=0;
		int j=0,i=0;
		double minServer=((double)Collections.min(serverRequestsList)*0.8d);
		//spliting
		for(j=0;j<processLocationList.size();){
			temp2=1;
			if(processRequestsList.get(j)>minServer) {
				temp1=(int)Math.ceil(processRequestsList.get(j)/minServer);
				processRequestsList.set(j, processRequestsList.get(j)/temp1);
				//processSizeList.set(j, processSizeList.get(j)/temp1);
				processSizeList.set(j, processSizeList.get(j)/temp1);
				while(temp2!=temp1){
					processRequestsList.add(j+temp2, processRequestsList.get(j));
					//processSizeList.set(j, processSizeList.get(j)/temp1);
					processSizeList.add(j+temp2, processSizeList.get(j));
					processLocationList.add(j+temp2,processLocationList.get(j));
					temp2++;
				}
				overLoadedProcess[k++]=temp1-1;
				j+=temp1;
			}else{
				overLoadedProcess[k++]=0;
				j++;
			}
		}
		boolean flag=false;
		boolean[] processDone=new boolean[processLocationList.size()];
		boolean[] serverBusy=new boolean[serverLocationList.size()];
		double[] processTime=new double[processLocationList.size()];
		byte[] processServerUsed=new byte[processLocationList.size()];
		boolean serverUsed[]=new boolean[serverLocationList.size()];
		int serverTimeUsed[]=new int[serverLocationList.size()];
		int serverExtraTime[]=new int[serverLocationList.size()];
		for(int m=0;m<serverTimeUsed.length;m++) {
			serverTimeUsed[m]=1;
		}
		while(!flag) {
			flag=true;
			for(j=0;j<processRequestsList.size();j++) {
				if(!processDone[j])
				{
					for(i=0;i<serverRequestsList.size();i++)
					{
						if(serverLocationList.get(i)==processLocationList.get(j)) {
							temp=((double)processRequestsList.get(j)/serverRequestsList.get(i))*100;
							serverUtilization[i]+=temp;
							if(serverUtilization[i]>overLoadingFactor){
								serverUtilization[i]-=temp;
								serverBusy[i]=true;
								//System.out.println("Overloaded due to process "+j);
							}else {
								processDone[j]=true;
								//cost[j];
								serverUsed[i]=true;
								if(serverUtilization[i]>serverMaxUtilization[i])
									serverMaxUtilization[i]=serverUtilization[i];
								serverExtraTime[i]+=ramDelay(serverRamList.get(i),processSizeList.get(j));
								processTime[j]+=3+serverExtraTime[i];
								processServerUsed[j]=(byte)i;
								//System.out.println("Server executed by "+ i);
								break;
							}
						}
					}
				}
			}
			for(j=0;j<processRequestsList.size();j++) {
				if(!processDone[j])
				{
					for(i=0;i<serverRequestsList.size();i++)
					{
						if(serverUsed[i]) {
							temp=((double)processRequestsList.get(j)/serverRequestsList.get(i))*100;
							serverUtilization[i]+=temp;
							if(serverUtilization[i]>overLoadingFactor){
								serverUtilization[i]-=temp;
								serverBusy[i]=true;
								//System.out.println("Overloaded due to process "+j);
							}else {
								processDone[j]=true;
								//cost[j];
								serverUsed[i]=true;
								if(serverUtilization[i]>serverMaxUtilization[i])
									serverMaxUtilization[i]=serverUtilization[i];
								if(serverLocationList.get(i)!=processLocationList.get(j))
									processTime[j]+=2;
								serverExtraTime[i]+=ramDelay(serverRamList.get(i),processSizeList.get(j));
								processTime[j]+=3+serverExtraTime[i];
								processServerUsed[j]=(byte)i;
								//System.out.println("Server executed by "+ i);
								break;
							}
						}
					}
				}
			}
			for(j=0;j<processRequestsList.size();j++) {
				if(!processDone[j])
				{
					for(i=0;i<serverRequestsList.size();i++)
					{
						temp=((double)processRequestsList.get(j)/serverRequestsList.get(i))*100;
						serverUtilization[i]+=temp;
						if(serverUtilization[i]>overLoadingFactor){
							serverUtilization[i]-=temp;
							serverBusy[i]=true;
							//System.out.println("Overloaded due to process "+j);
						}else {
							processDone[j]=true;
							//cost[j];
							if(serverUtilization[i]>serverMaxUtilization[i])
								serverMaxUtilization[i]=serverUtilization[i];
							if(serverLocationList.get(i)!=processLocationList.get(j))
								processTime[j]+=2;
							serverExtraTime[i]+=ramDelay(serverRamList.get(i),processSizeList.get(j));
							processTime[j]+=3+serverExtraTime[i];
							serverUsed[i]=true;
							processServerUsed[j]=(byte)i;
							//System.out.println("Server executed by "+ i);
							break;
						}
					}
				}
			}
			for(i=0;i<serverLocationList.size();i++) {
				serverUtilization[i]=0;
				if(serverUsed[i]==true) {
					cost[i]+=(double)(serverRamList.get(i)*10+serverRequestsList.get(i))/100;
					serverUsed[i]=false;
				}
			}
			for(j=0;j<processDone.length;j++) {
				if(!processDone[j]){
					flag=false;
					processTime[j]++;
				}
			}
		}
//		for(int m=0;m<processRequestsList.size();m++) {
//			System.out.println(m);
//			System.out.println(processTime[m]);
//		}
		rejoin(overLoadedProcess,processServerUsed,processTime);
	}
	
	void ant_colony() {
	
	}
	
	private void rejoin(int[] overLoadedProcess,byte[] processServerUsed,double[] processTime){
		int j;
		int processReq=0,processSize=0;
		String processServer="";
		for(int i=0;i<overLoadedProcess.length;i++){
			processReq=0;
			processSize=0;
			processServer="";
			if(overLoadedProcess[i]!=0){
				j=overLoadedProcess[i];
				processTimeTogether[i]=0;
				//System.out.println("Printing i "+i);
				//System.out.println("Printing j "+j);
				while(j>0){
					processReq+=processRequestsList.get(i+j);
					processSize+=processSizeList.get(i+j);
					if(!processServer.contains(Integer.toString(processServerUsed[i+j])))
						processServer+=","+(processServerUsed[i+j]+1);
					if(processTime[i+j]>processTimeTogether[i])
						processTimeTogether[i]=processTime[i+j];
					processRequestsList.remove(i+j);
					processSizeList.remove(i+j);
					processLocationList.remove(i+j);
					j--;
				}
				processTimeTogether[i]+=2;
			}
			processReq+=processRequestsList.get(i);
			processSize+=processSizeList.get(i);
			if(!processServer.contains(Integer.toString(processServerUsed[i])));
				processServer+=","+(processServerUsed[i]+1);
			if(processTime[i]>processTimeTogether[i])
				processTimeTogether[i]=processTime[i];
			processUsed[i]=processServer.substring(1);
			processRequestsList.set(i, processReq);
			processSizeList.set(i,processSize);
			
		}
//		Iterator<Integer> i=processRequestsList.iterator();
//		System.out.println("Requests");
//		while(i.hasNext()){
//			System.out.println(i.next());
//		}
//		System.out.println("Ram");
//		Iterator<Integer> k=processSizeList.iterator();
//		while(k.hasNext()){
//			System.out.println(k.next());
//		}
//		for(int m=0;m<processUsed.length;m++){
//			System.out.println(processUsed[m]);
//		}
	}
	byte ramDelay(int serverRam,int processSize) {
		byte times;
		if(processSize>serverRam)
			times=(byte)Math.ceil(processSize/serverRam);
		else
			return 0;
		return times; 
	}
	void resultWindow(int i){
		main.resultWindow(processTimeTogether,cost,serverMaxUtilization,processUsed,i);
	}
	@FXML void onClickClose() {
		primaryStage.close();
	}
}
