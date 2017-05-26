import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

public class SettingMenuController {
	
	@FXML private javafx.scene.control.Button butBack;
	@FXML private javafx.scene.control.ToggleButton butSound;
	@FXML private javafx.scene.image.ImageView unmuteImg;
	@FXML private javafx.scene.image.ImageView muteImg;
	@FXML private javafx.scene.control.Label labDifficultyEasyOff;
	@FXML private javafx.scene.control.Label labDifficultyEasyOn;
	@FXML private javafx.scene.control.Label labDifficultyHardOff;
	@FXML private javafx.scene.control.Label labDifficultyHardOn;

	public void toggleSound () {
		//System.out.println("Selected: Settings > Toggle sound");
		if (unmuteImg.isVisible() == true) {
			unmuteImg.setVisible(false);
			muteImg.setVisible(true);
		} else {
			unmuteImg.setVisible(true);
			muteImg.setVisible(false);
		}
//		
	}
	
	public void changeDifficultyEasy() {
		//System.out.println("Selected: Settings > Difficulty set to Easy");
		if (labDifficultyEasyOn.isVisible() != true) {
			labDifficultyEasyOff.setVisible(false);
			labDifficultyEasyOn.setVisible(true);
			labDifficultyHardOff.setVisible(true);
			labDifficultyHardOn.setVisible(false);
		}
		
	}
	
	public void changeDifficultyHard() {
		//System.out.println("Selected: Settings > Difficulty set to Hard");
		if (labDifficultyHardOn.isVisible() != true) {
			labDifficultyHardOff.setVisible(false);
			labDifficultyHardOn.setVisible(true);
			labDifficultyEasyOff.setVisible(true);
			labDifficultyEasyOn.setVisible(false);
		}
	}
	
	public void leaderboards() {
		Stage primaryStage = (Stage) butBack.getScene().getWindow();
		primaryStage.setTitle("Warehouse Bros. - Leaderboards");
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Leaderboards.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void back() {
		//System.out.println("Selected: Settings > Back");
		
		Stage primaryStage = (Stage) butBack.getScene().getWindow();
		primaryStage.setTitle("Warehouse Bros.");
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
		//System.out.println("Selected: Settings > Delete save");
		try {
			File f = new File("save.data");
			f.delete();
		} catch(Exception e) {}
	}
}
