
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.Clip;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;
/**
 * Controller for settings menu
 * @author Cam Horsley
 *
 */

public class SettingMenuController {
	
	@FXML private javafx.scene.control.Button butBack;
	@FXML private javafx.scene.control.ToggleButton butSound;
	@FXML private javafx.scene.image.ImageView unmuteImg;
	@FXML private javafx.scene.image.ImageView muteImg;
	@FXML private javafx.scene.control.Label labDifficultyEasyOff;
	@FXML private javafx.scene.control.Label labDifficultyEasyOn;
	@FXML private javafx.scene.control.Label labDifficultyHardOff;
	@FXML private javafx.scene.control.Label labDifficultyHardOn;
	
	public static Clip clip;

	public void toggleSound () {
		if (unmuteImg.isVisible() == true) {
			unmuteImg.setVisible(false);
			muteImg.setVisible(true);
			clip.stop();
		} else {
			unmuteImg.setVisible(true);
			muteImg.setVisible(false);
			clip.start();
		}
	}
	
	public void toggleSoundImage () {
		if (unmuteImg.isVisible() == true) {
			unmuteImg.setVisible(false);
			muteImg.setVisible(true);
		} else {
			unmuteImg.setVisible(true);
			muteImg.setVisible(false);
		}
	}
	
	public void changeDifficultyEasy() {
		if (labDifficultyEasyOn.isVisible() != true) {
			labDifficultyEasyOff.setVisible(false);
			labDifficultyEasyOn.setVisible(true);
			labDifficultyHardOff.setVisible(true);
			labDifficultyHardOn.setVisible(false);
		}
		WarehouseBoss.setDifficulty(0);
	}
	
	public void changeDifficultyHard() {
		if (labDifficultyHardOn.isVisible() != true) {
			labDifficultyHardOff.setVisible(false);
			labDifficultyHardOn.setVisible(true);
			labDifficultyEasyOff.setVisible(true);
			labDifficultyEasyOn.setVisible(false);
		}
		WarehouseBoss.setDifficulty(1);
	}
	public void setDifficulty() {
		if (WarehouseBoss.difficulty==0) {
			labDifficultyEasyOff.setVisible(false);
			labDifficultyEasyOn.setVisible(true);
			labDifficultyHardOff.setVisible(true);
			labDifficultyHardOn.setVisible(false);
		} else {
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
			e.printStackTrace();
		}
	}
	
	public void back() {
		
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
			e.printStackTrace();
		}
	}
	
	public void deleteSave() {
		try {
			File f = new File("save.data");
			f.delete();
		} catch(Exception e) {}
	}

	public void initData(Clip clip) {
		this.clip = clip;
		if (clip.isActive() && unmuteImg.isVisible() == false) {
			toggleSoundImage();
		}  else if (!clip.isActive() && muteImg.isVisible() == false) {
			toggleSoundImage();
		}
	}
}
