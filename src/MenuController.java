import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

	public void newGame () {
		System.out.println("Selected: New Game");
		WarehouseBoss game = new WarehouseBoss();
		game.play();
	}
	
	public void loadGame() {
		System.out.println("Selected: Load Game");
	}
	
	public void settings() {
		System.out.println("Selected: Settings");
		Stage primaryStage = new Stage();
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
