import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController {

	@FXML private javafx.scene.control.Button butNewGame;
	
	@FXML private javafx.scene.control.Button butSettings;
	
	@FXML
	public void newGame () {
		//System.out.println("Selected: New Game");
		
		Stage stage = (Stage) butNewGame.getScene().getWindow();
	    stage.close();
	    
		WarehouseBoss game = new WarehouseBoss();
		
		game.play();
		
		
		
	}
	
	public void loadGame() {
		System.out.println("Selected: Load Game");
	}
	
	public void settings() {
		//System.out.println("Selected: Settings");
		
		Stage stage = (Stage) butNewGame.getScene().getWindow();
		Stage primaryStage = stage;
		primaryStage.setTitle("Settings");
		
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("SettingMenu.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exit() {
		System.exit(0);
	}
}
