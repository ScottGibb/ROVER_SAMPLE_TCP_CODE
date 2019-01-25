
//Imported Libraries
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;

public class RoverTransceiverClient {
	// Global variables
	private String IpAddress;
	private int Port_Num;
	private Socket Sock;
	private PrintWriter output = null;
	private BufferedReader br = null;

	
	public static RoverTransceiverClient instance;
	// Constructor
	RoverTransceiverClient(String ip_address, int PortNo) {
		// assigning constructors values to the global equivalent
		IpAddress = ip_address;
		Port_Num = PortNo;
	}
	public String getIpaddress() {
		// Method returns the string equivalent of the IP Address the socket is bound too
		return IpAddress;
	}
	public int getPortNumber() {
		// Method returns the port number the socket is bound too
		return Port_Num;
	}
	public void createScoket() {
		System.out.println("Creating Socket on IP Address: " + IpAddress + " on Port: " + Port_Num);
		// Initialise socket
		try {
			Sock = new Socket(IpAddress, Port_Num);
			// Catching socket creation exceptions
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("The Host address could not be found");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("An IO exception occured during the creation of the socket");
			e.printStackTrace();
			System.exit(0);
		} catch (IllegalArgumentException e) {
			System.out.println("The arguments provided are incorrect");
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Socket creation successful");
		// initialise input and output streams
		System.out.println("Initiating input and output streams");
		try {
			// Input
			br = new BufferedReader(new InputStreamReader(Sock.getInputStream()));
			// Output
			output = new PrintWriter(Sock.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("An IO exception occurred during the iniation of the input and output streams");
			// IO Exception catching on input and output stream failure
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Input and Output streams successfully initated");
		System.out.println("");// empty space to break up relevent sections
	}
	public void sendToRover(String section, String commandType, String command, Comparable input) {
		// comparable data type used to allow both strings and integers to be
		// used

		// Packet Declaration
		// SECTION:COMMANDTYPE:COMMAND:INPUT
		if (section == null || commandType == null || command == null || input == null) {
			System.out.println("Arguments are invalid null entered in one of the fields");
		} else {
			System.out.println("Arguments accepted, Packeting...");
			output.println(section + ":" + commandType + ":" + command + ":" + input.toString());
			System.out.println("Packet sent to ROVER");
			System.out.println("");// empty space to break up relevent sections
		}
	}
	public String recieveFromRover() {
		System.out.println("Receiving data from Rover");
		String input = "no input was given";
		try {
			input = br.readLine();
		} catch (IOException e) {
			System.out.println(" An IO exception occurred during receiving of information");
			// exception handling on IO exceptions generated from reading the
			// input from rover
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Data received from Rover is: " + input);
		System.out.println("");// empty space to break up relevent sections
		return input;
	}
	public void closeSocket() {
		// Method closes socket and streams associated
		System.out.println("Closing Socket and streams bound to rover");
		try {
			output.close();
			br.close();
		} catch (IOException e1) {
			System.out.println(" An IO exception occurred during the closing of the output stream");
			e1.printStackTrace();
			System.exit(0);
		}
		// close socket
		try {
			Sock.close();
		} catch (IOException e) {
			System.out.println("Thread currently blocked in an IO operation, thus socket cannot be closed");
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Socket closed successfully");
		System.out.println("");// empty space to break up relevent sections
	}
	
	public static RoverTransceiverClient getInstance(String Ip_address, int port_num){
		
		if(instance==null){
			instance = new RoverTransceiverClient(Ip_address,port_num);
		}
		return instance;
		
	}
}