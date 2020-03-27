import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import application.Message;
public class Server {
	
	static double octanePrice;
	static double dieselPrice;
	
	static String tripdistance;
	static String fuelEff;
	static String fueltype;
	
		
	private static void readFile()
	{
		FileInputStream in = null;
		 try {
	         in = new FileInputStream("prices.txt");
	         Scanner scan = new Scanner(in);
	         while (scan.hasNextDouble()) {
	        	 octanePrice = scan.nextDouble();
	        	 dieselPrice = scan.nextDouble();
	         }
		 }
		 catch(Exception e){
			 System.out.println(e);
		 }
	         
	}
	
	private static void writeFile(String tPrice) throws IOException
	{
		FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

 
		try {
	            fw = new FileWriter("output.csv", true);
	            bw = new BufferedWriter(fw);
	            pw = new PrintWriter(bw);

	            pw.println(tripdistance+","+fuelEff+","+fueltype+","+tPrice);
	            
	            System.out.println("Data Successfully appended into file");
	            pw.flush();
	            pw.close();
	            bw.close();
	            fw.close();

	        } finally {
	            try {
	            	if(pw != null)
	            		pw.close();
	            	if(bw != null)
	            		bw.close();
	            	if(fw != null)    	
	            		fw.close();
	            } catch (IOException io) {// can't do anything }
	            }

	        }		
	}
	
	private static double calculate(double distance, double fuelEff, double price)
	{
		double Tprice = (distance/fuelEff)*price;
		return Tprice;
	}
	
	
	public static void main(String[] args) throws Exception{ 
		while (true) { 
			readFile();
			Scanner scan = new Scanner(System.in);
			ServerSocket ss=new ServerSocket(6666);
			Socket s;
			System.out.println("Trying to connect....");
			s=ss.accept();//establishes connection   
			System.out.println("Connected to "+s);
			ObjectOutputStream os;
			ObjectInputStream is;
			os = new ObjectOutputStream(s.getOutputStream());  
			is = new ObjectInputStream(s.getInputStream());
			double price = 0;
			System.out.println("Ready to Recieve data.");
			Message inMsg = (Message) is.readObject();
			Message outMsg = new Message("","","");
			System.out.println("Message Recieved.");
			System.out.println("Message "+inMsg.getFuelEff());
			tripdistance = inMsg.getTripDistance();
			fuelEff = inMsg.getFuelEff();
			fueltype = inMsg.getFuelType();
			if (inMsg.getFuelType().equals("octane"))
			{
				price = octanePrice;
			}
			else if(inMsg.getFuelType().equals("diesel"))
			{
				price = dieselPrice;
			}
			double Result = calculate(Double.parseDouble(inMsg.getTripDistance()),
					Double.parseDouble(inMsg.getFuelEff()),
					price
					);
			try {
				writeFile(Double.toString(Result));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			outMsg.setResult(Double.toString(Result));
			System.out.println("Data out: "+outMsg.getResult());
			os.writeObject(outMsg);
			os.flush();
			System.out.println("Done Sending data.");
			os.close();
			is.close();
			s.close();
			ss.close();
		}	
		
		
		 
		
	}
	
}
