import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class ComNameServer{

	private static final String localhost = "localhost";
	private static final int serverPort = 1234;
	private List<String> hostnames;
	private static final String nameServer  = "maps.com";
	
	public ComNameServer(){
		this.hostnames = new ArrayList<String>();
		this.hostnames.add("google.maps.com");
		this.hostnames.add("www.maps.com");
	}
	public boolean getAddress(String address) {
		
		if(this.hostnames.contains(address)){
			return true;
		}		
		
		return false;
	}
	
	
	
	
	public static void main(String args[]) throws IOException{
		
		ComNameServer comns = new ComNameServer();
		DatagramSocket serverSocket = new DatagramSocket(serverPort);

		
		System.out.println("Com Name Server started...");
		String host;
		while(true)
        {
			byte[] receiveData = new byte[1024];
			byte[] sendData  = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			serverSocket.receive(receivePacket);
			System.out.println("Request received from RNS");
			InetAddress IPAddress = receivePacket.getAddress();

			int port = receivePacket.getPort();
			
			host = new String(receivePacket.getData()).trim().toLowerCase();
			System.out.println("RNS sent "+host);
			
			
			
			if(comns.getAddress(host) || host.equals(nameServer)){
				System.out.println("Address Found!");
				String response = "";
				if(host.equals(nameServer)){
					response = "Name Server:"+host + ","+serverPort;
				}else{
					response = "Address:"+host + ","+serverPort;
				}

	            sendData = response.getBytes();

	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

	            serverSocket.send(sendPacket);
	            System.out.println(response+" sent...");
			}else{
				
				System.out.println("Address Not Found!");
	        	String response = "Address:"+host+",Address Not Found"; //sentence.toUpperCase();

	            sendData = response.getBytes();

	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

	            serverSocket.send(sendPacket);
	            System.out.println(response+" sent...");
			}
			

        }
		
		
	}


}
