package model;

public class Server {
	int i=0;
	String serverLocation;
	Integer serverRam,serverRequests;
	public Server(String serverLocation, Integer serverRam,Integer serverRequests) {
		i++;
 		this.serverLocation = serverLocation;
		this.serverRequests = serverRequests;
		this.serverRam = serverRam;
	}
	public String getServerLocation() {
		return serverLocation;
	}
	public void setServerLocation(String serverLocation) {
		this.serverLocation = serverLocation;
	}
	public Integer getServerRam() {
		return serverRam;
	}
	public void setServerRam(Integer serverRam) {
		this.serverRam = serverRam;
	}
	public Integer getServerRequests() {
		return serverRequests;
	}
	public void setServerRequests(Integer serverRequests) {
		this.serverRequests = serverRequests;
	}
	public int getInstrumentId() {
		// TODO Auto-generated method stub
		
		return i;
	}
	public void setInstrumentId(int i) {
		this.i=i;
	}
	
}
