package model;

public class Processes {
	int i=0;
	String processLocation;
	Integer processSize;
	Integer processRequest;
	public Processes(String processLocation, Integer processSize,Integer processRequest) {
		this.processLocation = processLocation;
		this.processSize = processSize;
		this.processRequest=processRequest;
	}
	public Integer getProcessRequest() {
		return processRequest;
	}
	public void setProcessRequest(Integer processRequest) {
		this.processRequest = processRequest;
	}
	public String getProcessLocation() {
		return processLocation;
	}
	public void setProcessLocation(String processLocation) {
		this.processLocation = processLocation;
	}
	public Integer getProcessSize() {
		return processSize;
	}
	public void setProcessSize(Integer processSize) {
		this.processSize = processSize;
	}
	public int getInstrumentId() {
		// TODO Auto-generated method stub
		return i;
	}
	public void setInstrumentId(int j) {
		i=j;
	}
	
}
