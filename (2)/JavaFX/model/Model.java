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
	private static InputStream in;
	private static OutputStream out;
	private Thread t;
	private Socket s;
	private double progress;

	public Model() {
		try {
			s = new Socket("127.0.0.1", 1112);
			in = s.getInputStream();
			out = s.getOutputStream();
			this.t = new Thread(this, "Model");
			t.start();
		} catch (Exception e) {}

	}
	@Override
	public void run() {
		while(true) {
			try {t.sleep(200);} catch (Exception e) {}
			getServProgress();
		}
	}
	public void startProg() {
		putObject("start.");
	}

	public void stopProg() {
		putObject("stop.");
	}

	public void pauseUnpauseProg() {
		putObject("pause.");
	}

	private void getServProgress() {
		putObject("progress.");
		progress = Double.parseDouble(getObject());
	}
	public double getProgress() {
		return progress;
	}

	private static String getObject() {
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

	public static void putObject(String str) {
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