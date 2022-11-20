import java.net.*;
import java.io.*;


class TR implements Runnable {
	private Thread t;
	private Socket s;
	private InputStream in;
	private OutputStream out;
	private float firstNumb, secondNumb;
	private String result, str, operation;
	private String[] numbers;
	
	public TR(String name, Socket s) {
		try {	   		   		    
		    this.t = new Thread(this,name);
			this.s = s;
			in = s.getInputStream();
			out = s.getOutputStream();
			t.start();
		}	catch (Exception e)
		{
			System.out.println("Error: " + e);	
		}	   
		
	}
	
	@Override
	public void run() {
		while(true) {
			str = getObject();
			numbers = str.strip().split("[+\\*-/]");
			if (str.contains("+")) {
				operation = "+";
			}
			if (str.contains("-")) {
				operation = "-";
			}
			if (str.contains("/")) {
				operation = "/";
			}
			if (str.contains("*")) {
				operation = "*";
			}
			firstNumb = Float.parseFloat(numbers[0]);
			secondNumb = Float.parseFloat(numbers[1]);
			putObject(count());
			
		}
	}
	
	private String getObject(){
		while (true) {
		try {	   		   		    
		    int btsAvailable = in.available();
			byte[] bts = new byte[btsAvailable];
			int btsCount =in.read(bts,0,btsAvailable);           
			ByteArrayInputStream bis =new ByteArrayInputStream(bts);
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object obj =ois.readObject();
			String str = (String)obj;
			if (str.contains("+") ||  str.contains("-") ||
					str.contains("*") || str.contains("/")) {
				return str;
			}
			else {
				continue;
			}
		}	catch (Exception e)
		{
			try {
				t.sleep(10);
			} catch (InterruptedException e2) {
			}
			continue;	
		}
		}
	}
	
	private void putObject(String str){
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
	
	
		
	private String count(){
		if (operation == "+"){
			result = Float.toString(firstNumb + secondNumb);
		}else if(operation == "-"){
			result = Float.toString(firstNumb - secondNumb);
		}else if(operation == "*"){
			result = Float.toString(firstNumb * secondNumb);
		}else if(operation == "/"){
			if(secondNumb == 0){result = "Zero Devision.";}
			else{result = Float.toString(firstNumb / secondNumb);}
		}else{
			result = "Incorrect input";
		}
		return result;
	}
}


class Server {
	public static void main(String args[]) {		
		try {	   		   		    
		   ServerSocket ss = new ServerSocket(1112);  
		   while(true){
			Socket s = ss.accept();
			new TR("Parallel", s);
		  }
		
		}	catch (Exception e)
		{
			System.out.println("Error: " + e);	
		}	   
		
		
	}
}

