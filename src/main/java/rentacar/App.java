package rentacar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application 
{
	
	
	public void start(Stage primaryStage) {
		try {			
			Parent root = FXMLLoader.load(getClass().getResource("view/LoginView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("/photos/icon11.png"));
			primaryStage.setTitle("Коли под наем");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public static void main( String[] args )
    {    	
    

    	launch(args);
    	
    	
    	
    	System.exit(0);
    	
    }



    
}
