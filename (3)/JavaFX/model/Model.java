package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Model implements Runnable {
	private InputStream in;
	private OutputStream out;
	private Thread t;
	private Socket s;

	public Model() {
		try {
			s = new Socket("192.168.1.57", 1113);
			in = s.getInputStream();
			out = s.getOutputStream();
			this.t = new Thread(this, "Model");
			t.start();
		} catch (Exception e) {}

	}
	@Override
	public void run() {
		while(true) {
			try {t.sleep(50);} catch (InterruptedException e) {}
		}
	}

	public String putSpichki(String spichki) {
		putObject(spichki);
		return getObject();
	}
	
	private String getObject() {
		while (true) {
			try {
				int btsAvailable = in.available();
				byte[] bts = new byte[btsAvailable];
				int btsCount = in.read(bts, 0, btsAvailable);
				ByteArrayInputStream bis = new ByteArrayInputStream(bts);
				ObjectInputStream ois = new ObjectInputStream(bis);
				Object obj = ois.readObject();
				String str = (String) obj;
				if (str.contains(".")) {
					return str;
				} else {
					continue;
				}
			} catch (Exception e) {
				continue;
			}
		}
	}

	private void putObject(String str) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			Object obj = new String(str);
			oos.writeObject(obj);
			byte[] bts = bos.toByteArray();
			out.write(bts);
		} catch (Exception e) {}
	}

}