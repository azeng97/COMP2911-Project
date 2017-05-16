import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class menuUI {

	public menuUI (Stage arg) {
		ConstructUI(arg);
	}
	

	public void ConstructUI(Stage primaryStage) {
//		Button newGameBut = new Button("New Game");
//		newGameBut.setStyle("-fx-font: 19 arial; -fx-base: #b6e7c9;");
//		newGameBut.setPrefSize(200, 50);
//		newGameBut.setOnAction(e -> System.out.println("New Game selected"));
//		
//		Button loadGameBut = new Button("Load Game");
//		loadGameBut.setStyle("-fx-font: 19 arial; -fx-base: #b6e7c9;");
//		loadGameBut.setPrefSize(200, 50);
//		loadGameBut.setOnAction(e -> System.out.println("Load Game selected"));
//		
//		Button settingsBut = new Button("Settings");
//		settingsBut.setStyle("-fx-font: 19 arial; -fx-base: #b6e7c9;");
//		settingsBut.setPrefSize(200, 50);
//		settingsBut.setOnAction(e -> System.out.println("Settings selected"));
//		
//		Button exitBut = new Button("Exit");
//		exitBut.setStyle("-fx-font: 19 arial; -fx-base: #b6e7c9;");
//		exitBut.setPrefSize(200, 50);
//		exitBut.setOnAction(e -> System.exit(1));
//		
//		VBox root = new VBox();
//		root.getChildren().addAll(newGameBut, loadGameBut, settingsBut, exitBut);
		
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
	
	
}
