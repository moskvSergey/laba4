package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.application.Platform;
import javafx.animation.AnimationTimer;

import model.Model;

class StartProgressBar implements Runnable {
	private Thread t;
	private Model model;
	private Controller con;
	public StartProgressBar(String name, Controller con)
	{
		t = new Thread(this, name);
		this.con = con;
		model = new Model();
		t.start();
	}
	
	public void run()
	{
		while(true) {
			try {t.sleep(300);} catch (Exception e) {}
			at.handle(20);
		}
	}
	
	public void startP() {
		model.startProg();
	}
	
	public void stopP() {
		model.stopProg();
	}
	
	public void pauseP() {
		model.pauseUnpauseProg();
	}
	
	protected AnimationTimer at = new AnimationTimer(){
        @Override
        public void handle(long now) {
        	Platform.runLater(new Runnable() {
                public void run() {	
                 con.getBar().setProgress(model.getProgress());
                }
               });
        }
    };

}




 
 public class Controller {
	boolean paused = false;
	@FXML
	private ProgressBar bar = new ProgressBar(0);
	public ProgressBar getBar() {
		return bar;
	}
	
	private StartProgressBar t = new StartProgressBar("Conroller", this);
	
	@FXML
    private Button btnPause = new Button();
    @FXML
    private void clickStart(ActionEvent event) {
    	if(paused) {btnPause.setText("pause");paused = false;}
    	t.startP();
    }
    @FXML
    private void clickPause(ActionEvent event) {
    	if(paused) {btnPause.setText("pause");}
    	else {btnPause.setText("continue");}
    	paused = !paused;
    	t.pauseP();
    }
    @FXML
    private void clickStop(ActionEvent event) {
    	if(paused) {btnPause.setText("pause");paused = false;}
    	t.stopP();
    }
    
    
}