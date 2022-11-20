import java.net.*;
import java.io.*;
import java.util.Scanner;

class Client
{
	private static InputStream in;
    private static OutputStream out;
    
    
	private static String getObject(){
		while(true) {
			try {	   	
				int btsAvailable = in.available();
				byte[] bts = new byte[btsAvailable];
				int btsCount =in.read(bts,0,btsAvailable);           
				ByteArrayInputStream bis =new ByteArrayInputStream(bts);
				ObjectInputStream ois = new ObjectInputStream(bis);
				Object obj =ois.readObject();
				String str = (String)obj;
				if (str.contains(".")) {
					return str;
				}
				else {
					continue;
				}
		}	catch (Exception e) {
			try {
				Thread.currentThread().sleep(10);
			} catch (InterruptedException e2) {
			}
		{
			
		}
		
	}
		}
	}
	
	
	public static void putObject(String str){
		try {	   		   		    
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);		   
			Object obj = new String(str);			            
			oos.writeObject(obj);
			byte[] bts = bos.toByteArray();
			out.write(bts);
		}	catch (Exception e)
		{
			System.out.println("Error: " + e);	
		}
		
	}
	
	
	public static void main(String args[])
	{	
		String s0;
		Scanner in2 = new Scanner(System.in);
		try
		{	   	
			
			Socket s = new Socket("127.0.0.1",1112);		
		    in = s.getInputStream();
		    out = s.getOutputStream();
		    while(true) {
		    	System.out.print("Input expression : ");
				s0 = in2.nextLine();
				putObject(s0);
				System.out.print(getObject()+"\n");
		    }
		   	   		   
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e);	
		}
		
	}
}