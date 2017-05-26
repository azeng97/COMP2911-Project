import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

public class LeaderboardsController {
	
	@FXML private javafx.scene.control.Button butLeaderboardsBack;
	

	
	
	public void back() {
		
		Stage primaryStage = (Stage) butLeaderboardsBack.getScene().getWindow();
		primaryStage.setTitle("Warehouse Bros. - Settings");
		
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("SettingMenu.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
