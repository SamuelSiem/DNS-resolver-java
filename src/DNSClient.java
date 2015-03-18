/**
 * 	Name:	Nicholas Chamansingh
 * 	ID:		8090022423
 * 	Course:	Comp6601
 * 
 * 
 * 				Assignment 2
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class DNSClient {
	
	private static final String localhost = "localhost";
	private static final int serverPort = 9876;	
	
	public static void main(String args[]) throws Exception
   {
     
		/**
		 * Buffer reader initialized to accept input from the user
		 */
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		DatagramSocket clientSocket = new DatagramSocket();

		InetAddress IPAddress = InetAddress.getByName(localhost);
		System.out.println(IPAddress);


		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		System.out.println("Please enter adress..");
		String sentence = inFromUser.readLine();
		sendData = sentence.getBytes();
		

		// send packet 
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);
		// packet
		clientSocket.send(sendPacket);
		System.out.println("Data sent...");
		
	
		// received packet
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		System.out.println("Data received from server");
		clientSocket.receive(receivePacket);

		String modifiedSentence = new String(receivePacket.getData());

		System.out.println("FROM SERVER:" + modifiedSentence);
		clientSocket.close();
		System.out.println("Connection closed");
   }
}
