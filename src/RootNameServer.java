import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class RootNameServer implements NameServerInterface{
	
	private static final String localhost = "localhost";
	private static final int serverPort = 4569;
	private List<CacheRecord> domainServers;
	
	private DatagramSocket clientSocket;
	private InetAddress IPAddress; 
	byte[] sendData;
	byte[] receiveData;
	
	
	public RootNameServer(){
		this.domainServers = new ArrayList<CacheRecord>();
		this.domainServers.add(new CacheRecord(".com", 1234));
		this.domainServers.add(new CacheRecord(".tt",2345));
		this.sendData = new byte[1024];
		this.receiveData = new byte[1024];
		try{
			this.clientSocket = new DatagramSocket();
			this.IPAddress = InetAddress.getByName(localhost);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public String getDomain(String host){
		if(host.endsWith(".com")){
			return ".com";
		}else if(host.endsWith(".tt")){
			return ".tt";
		}
		return "Host Not found";
	}
	

	@Override
	public int getAddress(String address) {
		for(CacheRecord c : domainServers){
			if(c.getHostname().equals(address)){
				return c.getIpAddress();
			}
		}
		return 0;
	}

	
	public String callServer(int ipAddress) {
		
		this.sendData = (""+ipAddress).getBytes();
		
		// send packet 
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, ipAddress);
		// packet
		try {
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Data sent...");
		
	
		// received packet
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		System.out.println("Data received from server");
		try {
			clientSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String modifiedSentence = new String(receivePacket.getData());

		System.out.println("FROM SERVER:" + modifiedSentence);
		clientSocket.close();
		System.out.println("Connection closed");
		
		return modifiedSentence;
	}

	public static void main(String args[]) throws Exception
    {
	
		RootNameServer rns = new RootNameServer();
		DatagramSocket serverSocket = new DatagramSocket(serverPort);

		
		System.out.println("Root Name Server started...");
		String host[];
		String domain;
		while(true)
        {
			byte[] receiveData = new byte[1024];
			byte[] sendData  = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			serverSocket.receive(receivePacket);
			System.out.println("Request received from DNS");
			InetAddress IPAddress = receivePacket.getAddress();

			int port = receivePacket.getPort();
			
			host = new String(receivePacket.getData()).trim().toLowerCase().split(",");
			System.out.println("Dns sent "+host[0]);
			domain = rns.getDomain(host[0]);
			int nameServerAddress = rns.getAddress(domain);
			
			if(nameServerAddress !=0){
				System.out.println("Name Server Found!");
	        	String response = host[0] + ","+nameServerAddress;

	            sendData = response.getBytes();

	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

	            serverSocket.send(sendPacket);
			}else{
				
				System.out.println("Name Server Not Found!");
	        	String response = host[0]+",Host Not Found"; //sentence.toUpperCase();

	            sendData = response.getBytes();

	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

	            serverSocket.send(sendPacket);
			}
			

        }
    }

	
	
}
