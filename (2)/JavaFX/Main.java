import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

 
public class Main extends Application{
     
    public static void main(String[] args) {
        		
        Application.launch(args);
		
    }
     
    @Override
    public void start(Stage stage) throws Exception {
         
        Parent root = FXMLLoader.load(getClass().getResource("view/View.fxml")); 
        Scene scene = new Scene(root);
         
        stage.setScene(scene);
         
        stage.setTitle("Hello JavaFX");
        stage.setWidth(500);
        stage.setHeight(400);
         
        stage.show();
    }
}