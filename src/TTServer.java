/**
 * 	Name:	Nicholas Chamansingh
 * 	ID:		8090022423
 * 	Course:	Comp6601
 * 
 * 
 * 				Assignment 2
 */


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


public class TTServer{
	
	private static final String localhost = "localhost";
	private static final int serverPort = 8888;
	private List<CacheRecord> domainServers;
	
	private DatagramSocket clientSocket;
	private InetAddress IPAddress; 
	byte[] sendData;
	byte[] receiveData;
	
	
	public TTServer(){
		this.domainServers = new ArrayList<CacheRecord>();
		this.domainServers.add(new CacheRecord("igov.tt", 2345));		
		
		try{
			this.clientSocket = new DatagramSocket();
			this.IPAddress = InetAddress.getByName(localhost);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	
	public int getAddress(String address) {
		for(CacheRecord c : domainServers){
			if(address.contains(c.getHostname())){
				return c.getIpAddress();
			}
		}
		return 0;
	}

	

	public static void main(String args[]) throws Exception
    {
	
		TTServer ttns = new TTServer();
		DatagramSocket serverSocket = new DatagramSocket(serverPort);

		
		System.out.println(".tt Name Server started...");
		String host;		
		while(true)
        {
			byte[] receiveData = new byte[1024];
			byte[] sendData  = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			serverSocket.receive(receivePacket);
			System.out.println("Request received from");
			InetAddress IPAddress = receivePacket.getAddress();

			int port = receivePacket.getPort();
			
			host = new String(receivePacket.getData()).trim().toLowerCase();
			System.out.println("Data received:  "+host);

			int nameServerAddress = ttns.getAddress(host);
			
			if(nameServerAddress !=0){
				System.out.println("Name Server Found!");
	        	String response = "Name Server:"+host + ","+nameServerAddress;

	            sendData = response.getBytes();

	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

	            serverSocket.send(sendPacket);
			}else{
				
				System.out.println("Name Server Not Found!");
				String response = "Name Server:"+host+"Address Not Found,"+serverPort; //sentence.toUpperCase();

	            sendData = response.getBytes();

	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

	            serverSocket.send(sendPacket);
			}
			

        }
    }

	
	
}
