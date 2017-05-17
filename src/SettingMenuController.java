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
	@FXML private javafx.scene.control.Button butSoundToggle;
	@FXML private javafx.scene.control.Label labSoundToggleText;

	@FXML
	public void toggleSound () {
		System.out.println("Selected: Settings > Toggle sound");
		if (labSoundToggleText.getText().equals("Sound on"))
			labSoundToggleText.setText("Sound muted");
		else labSoundToggleText.setText("Sound on");
//		
	}
	
	public void changeDifficultyEasy() {
		System.out.println("Selected: Settings > Difficulty set to Easy");
	}
	
	public void changeDifficultyHard() {
		System.out.println("Selected: Settings > Difficulty set to Hard");
	}
	
	public void back() {
		System.out.println("Selected: Settings > Back");
		
		Stage stage = (Stage) butBack.getScene().getWindow();
		Stage primaryStage = stage;
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
		System.out.println("Selected: Settings > Delete save");
	}
}
