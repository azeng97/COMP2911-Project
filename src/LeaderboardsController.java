import java.io.IOException;
import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.util.ArrayList;

public class LeaderboardsController implements Initializable{
	
	@FXML private javafx.scene.control.Button butLeaderboardsBack;
	@FXML private javafx.scene.control.TextArea leaderBoardText;
	
	public void initialize(URL url, ResourceBundle rb) {
		DisplayText();
	}
	
	private void DisplayText()
	{
		ArrayList<Integer> scores = new ArrayList<Integer>();
		try
		{ Scanner s = new Scanner (new File ("leaderboard.txt"));
        while (s.hasNextLine()) {
        	scores.add(Integer.parseInt(s.nextLine().trim()));
        }
        s.close();
		} catch (FileNotFoundException e) {}
		Collections.sort(scores);
		Collections.reverse(scores);
		for (int score: scores){
			leaderBoardText.appendText(score+"\n");
		}
		
	}

	
	
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
