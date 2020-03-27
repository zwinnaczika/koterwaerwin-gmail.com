package application;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	Socket s;
	ObjectOutputStream os;
	ObjectInputStream is;
	public void connect() {  
		try{      
		s = new Socket("localhost",6666);  
		os = new ObjectOutputStream(s.getOutputStream());
		is = new ObjectInputStream(s.getInputStream());
		
		}catch(Exception e){System.out.println(e);}  
	}
	
	public void disconnect()
	{
		try {
			is.close();
			os.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void writeData(Message outMsg)
	{
		if(os != null)
		{
			try {
				os.writeObject(outMsg);
				os.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Message readData() throws ClassNotFoundException, IOException
	{
			Message inMsg = (Message) is.readObject();
			return inMsg;
					
		
	}
}