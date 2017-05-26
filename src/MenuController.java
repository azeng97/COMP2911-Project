import java.io.IOException;
import java.io.File;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
	@FXML private javafx.scene.image.ImageView logoText;
	private Stage primaryStage;
	
	public void newGame () {
		this.primaryStage = (Stage) butNewGame.getScene().getWindow();
		
		Task<Void> task = new Task<Void>() {
			@Override protected Void call() throws Exception {
				loadingScreen(primaryStage);
				Platform.runLater(new Runnable() {
					@Override public void run() {
						WarehouseBoss game = new WarehouseBoss();
						game.play(primaryStage);
					}
				});
				return null;
			}
		};
		 task.run();
	}
	
	public void loadingScreen(Stage primaryStage) {
		
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("LoadingScreen.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void loadGame() {
		this.primaryStage = (Stage) butNewGame.getScene().getWindow();
		
		Task<Void> task = new Task<Void>() {
			@Override protected Void call() throws Exception {
				File f = new File("save.data");
				if (f.exists()){ 
				loadingScreen(primaryStage);
				Platform.runLater(new Runnable() {
					@Override public void run() {
						WarehouseBoss game = new WarehouseBoss();
						
							game.resume(primaryStage);
						
					}
				});
				} else {
					System.out.println("No save detected");
				}
				return null;
			}
		};
		 task.run();
	}
	
	public void settings() {
		//System.out.println("Selected: Settings");
		
		Stage primaryStage = (Stage) butSettings.getScene().getWindow();
		primaryStage.setTitle("Warehouse Bros. - Settings");
		
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


	