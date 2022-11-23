import java.net.*;
import java.io.*;

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
}

class TR implements Runnable {
	private Thread t;
	private Socket s;
	private InputStream in;
	private OutputStream out;
	private Progress progress;
	private static boolean first = true;

	public TR(String name, Socket s) {
		try {
			this.t = new Thread(this, name);
			this.s = s;
			in = s.getInputStream();
			out = s.getOutputStream();
			t.start();
		} catch (Exception e) {
		}
	}

	@Override
	public void run() {
		while (true) {
			String command = getObject();
			if (command.contains("start.")) {
				if(first) {progress = new Progress("Progress");}
				else{progress.kill(); progress = new Progress("Progress");}

			}
			else if(command.contains("stop.")) {
				if(!first) {progress.kill(); first = true;}
			}
			else if(command.contains("pause.")) {
				if(!first) {progress.pause();}
			}else if(command.contains("progress.")) {
				if(!first) {putObject(progress.getProgress());}
				else {putObject("0.0");}
			}
		}
	}

	public static void nowFirst() {
		first = !first;
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
			out.write(bts);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

	}

}

class Server {
	public static void main(String args[]) {
		try {
			ServerSocket ss = new ServerSocket(1112);
			while (true) {
				Socket s = ss.accept();
				new TR("Parallel", s);
			}

		} catch (Exception e) {
		}
	}
}
