
public class CacheRecord {
	
	private String hostname;
	private int ipAddress;
	
	public CacheRecord(String hostname, int ipAddress){
		this.hostname = hostname;
		this.ipAddress = ipAddress;
	}

	public String getHostname() {
		return hostname;
	}

	public int getIpAddress() {
		return ipAddress;
	}
	
	
	
}
