import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

public class SettingMenuController {
	
	@FXML private javafx.scene.control.Button butBack;

	@FXML
	public void toggleSound () {
		System.out.println("Selected: Settings > Toggle sound");
//		
	}
	
	public void toggleDifficulty() {
		System.out.println("Selected: Settings > Toggle difficulty");
	}
	
	public void back() {
		System.out.println("Selected: Settings");
		Stage stage = (Stage) butBack.getScene().getWindow();
	    stage.close();
		
		Stage primaryStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteSave() {

	}
}
