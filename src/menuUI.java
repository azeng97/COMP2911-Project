import java.io.IOException;

import javax.sound.sampled.Clip;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class menuUI {
	public static Clip clip;
	
	public menuUI (Stage arg, Clip clip) {
		this.clip = clip;
		ConstructUI(arg);
		
	}
	
	public void ConstructUI(Stage primaryStage) {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			MenuController controller =  loader.<MenuController>getController();
			controller.initData(clip);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch (IOException e) { e.printStackTrace(); }
	}
}