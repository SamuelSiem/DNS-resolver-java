import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class TTNameServer{
	
	private static final String localhost = "localhost";
	private static final int serverPort = 2345;
	private List<String> hostnames;
	private static final String nameServer  = "google.tt";
	
	public TTNameServer(){
		this.hostnames = new ArrayList<String>();
		this.hostnames.add("mail.google.tt");
		this.hostnames.add("www.google.tt");
	}
	public boolean getAddress(String address) {
		
		if(this.hostnames.contains(address)){
			return true;
		}		
		
		return false;
	}
	
	
	
	
	public static void main(String args[]) throws IOException{
		
		TTNameServer ttns = new TTNameServer();
		DatagramSocket serverSocket = new DatagramSocket(serverPort);

		
		System.out.println("TT Name Server started...");
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
			
			
			
			if(ttns.getAddress(host) || host.equals(nameServer)){
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
