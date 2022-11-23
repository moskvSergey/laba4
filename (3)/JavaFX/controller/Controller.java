package controller;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import model.Model;



class Sw implements Runnable {
	private Thread t;
    private Model model;
    private Controller con;
    private String str;
    private boolean flag = false;

	public Sw(Controller con) {
		try {
			this.t = new Thread(this, "Sw");
		    model = new Model();
		    this.con = con;
			t.start();
		} catch (Exception e) {}

	}
	@Override
	public void run() {
		putSpichki("0.0");
		while(true) {
			if (flag) {flag = false; putSpichki(str); }
			try {t.sleep(20);} catch (InterruptedException e) {}
		}
	}
	
	
	private void putSpichki(String spichki) {
		con.block(true);
		String text = model.putSpichki(spichki);
		con.setText(text);
		con.block(false);
	}
	
	public void setSpichki(String spichki) {
		str = spichki;
		flag = true;
	}

}


public class Controller {

    @FXML
    private Button btn1 = new Button();

    @FXML
    private Button btn2 = new Button();

    @FXML
    private Button btn3 = new Button();

    @FXML
    private Button btn4 = new Button();

    @FXML
    private Button btn5 = new Button();

    @FXML
    public TextArea label = new TextArea();
    
    Sw sw = new Sw(this);

    @FXML
    void click1(ActionEvent event) {
    	sw.setSpichki("1.0");
    }

    @FXML
    void click2(ActionEvent event) {
    	sw.setSpichki("2.0");
    }

    @FXML
    void click3(ActionEvent event) {
    	sw.setSpichki("3.0");
    }

    @FXML
    void click4(ActionEvent event) {
    	sw.setSpichki("4.0");
    }

    @FXML
    void click5(ActionEvent event) {
    	sw.setSpichki("5.0");

    }
    
    void setText(String str) {
    	Platform.runLater(new Runnable(){
    		public void run() {
    		label.setText(str);
    		}
    	});
    }
    
    void block(boolean flag) {
    	Platform.runLater(new Runnable(){
    		public void run() {
    		btn1.setDisable(flag);
        	btn2.setDisable(flag);
        	btn3.setDisable(flag);
        	btn4.setDisable(flag);
        	btn5.setDisable(flag);
    		}
    	});
    	
    }
    

}