import java.net.*;
import java.io.*;
/*
class Progress implements Runnable {
	private Thread t;
	private boolean alive = true;
	private boolean paused = false;
	private double progress = 0;

	public Progress(String name) {
		try {
			this.t = new Thread(this, name);
			TR.nowFirst();
			t.start();
		} catch (Exception e) {
		}
	}

	@Override
	public void run() {
		while (alive && progress <= 1) {
			try {
				t.sleep(20);
			} catch (Exception e) {}
			if (paused) {
				continue;
			}
			progress += 0.01;
			}
		
		TR.nowFirst();
	}
	public void pause() {
		paused = !paused;
	}
	public void kill() {
		alive = false;
	}
	public String getProgress() {
		return Double.toString(progress);
	}
}*/

class TR implements Runnable {
	private Thread t;
	private Socket s, s2;
	private InputStream in;
	private OutputStream out;
	private InputStream in2;
	private OutputStream out2;
	private boolean turn = true;
	private int spichki = 37;

	public TR(String name, Socket s, Socket s2) {
		try {
			this.t = new Thread(this, name);
			this.s = s;
			this.s2 = s2;
			in = s.getInputStream();
			out = s.getOutputStream();
			in2 = s2.getInputStream();
			out2 = s2.getOutputStream();
			t.start();
		} catch (Exception e) {
		}
	}

	@Override
	public void run() {
			getObject();
			turn =!turn;
			getObject();
			while(spichki > 0) {
				putObject(spichki+".");
				spichki -= Float.parseFloat(getObject());
				turn = !turn;
			}
			putObject("You lost.");
			turn = !turn;
			putObject("You win.");
			
		}


	private String getObject() {
		byte[] bts;
		while (true) {
			try {
				if(turn) {
					int btsAvailable = in.available();
					bts = new byte[btsAvailable];
					int btsCount = in.read(bts, 0, btsAvailable);
				}else {
					int btsAvailable = in2.available();
					bts = new byte[btsAvailable];
					int btsCount = in2.read(bts, 0, btsAvailable);
				}
				ByteArrayInputStream bis = new ByteArrayInputStream(bts);
				ObjectInputStream ois = new ObjectInputStream(bis);
				Object obj = ois.readObject();
				String str = (String) obj;
				if (str.contains(".")) {
					System.out.print(str);
					return str;
				} else {
					continue;
				}
			} catch (Exception e) {
				try {
					t.sleep(10);
				} catch (InterruptedException e2) {
				}
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
			if(turn) {out.write(bts);}
			else {out2.write(bts);}
		} catch (Exception e) {}
	}

}

class Server {
	public static void main(String args[]) {
		try {
			ServerSocket ss = new ServerSocket(1113);
			while (true) {
				Socket s = ss.accept();
				Socket s2 = ss.accept();
				new TR("Parallel", s, s2);
			}

		} catch (Exception e) {
		}
	}
}
