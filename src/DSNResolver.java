import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class DSNResolver {
	
	private final static int serverPort = 9876;
	private final static int ttPort = 8765;
	private final static int comPort = 7654;
	
	private static Map<String,String> cacheMap = new HashMap<String, String>();
	
	public static void loadCache(){
		System.out.println("Loading Cache....");
		try {
			Scanner in = new Scanner(new FileReader("cache.txt"));			
			while (in.hasNext()){
				String hostname = in.next();
				String ip_address = in.next();
				cacheMap.put(hostname, ip_address);				
			}			
			in.close();
			System.out.println("Loading Stock completed!");
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String args[]) throws Exception
    {
	
		
		DatagramSocket serverSocket = new DatagramSocket(serverPort);


		byte[] receiveData = new byte[1024];
		byte[] sendData  = new byte[1024];
		
		System.out.println("Server started...");
		while(true)
        {

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			serverSocket.receive(receivePacket);
			System.out.println("Request received from client");
			String sentence = new String(receivePacket.getData());

			InetAddress IPAddress = receivePacket.getAddress();

			int port = receivePacket.getPort();

			System.out.println(sentence);
        	String response ="Message Recieved"; //sentence.toUpperCase();

            sendData = response.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

            serverSocket.send(sendPacket);

        }
    }
	
	
}
