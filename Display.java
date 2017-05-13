import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Display extends Application {
	private static final int TILE_SIZE = 40;
	private static final int MAX_ARRAY_SIZE = 10;
	private int arr[MAX_ARRAY_SIZE][MAX_ARRAY_SIZE];
	private int arrayWidth;
	private int arrayHeight;
	
	public Display(int arr[][], int arrayWidth, int arrayHeight) {
		this.arr = arr;
		this.arrayWidth = arrayWidth;
		this.arrayHeight = arrayHeight;
	}
	public void move(int newArr[][]) {
		for (int y = 0; y < arrayHeight; y ++) {
			for (int x = 0; x < arrayWidth; x ++) {
				this.arr[x][y] = newArr[x][y];
			}
		}
		createContent();
	}
	
	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(400, 400);
		// change the size later
		for(int y = 0; y < arrayHeight; y ++) {
			for (int x = 0; x < arrayWidth; x ++) {
				Tile tile = new Tile(x, y, arr[x][y]); // change the input
				root.getChildren().add(tile);
			}
		}
		return root;
	}
	private class Tile extends StackPane {
		private int contains; // 0 = empty, 1 = wall, 2 = box, 3 = target, 4 = player.
		
		private Rectangle border = new Rectangle(TILE_SIZE, TILE_SIZE);

		public Tile(int x, int y, int contains) {
			this.contains = contains;
			Text text = new Text();
			text.setFont(Font.font(18));
            text.setText(String.valueOf(contains));
            text.setVisible(true);
       
			border.setStroke(Color.LIGHTGREY);
			if (contains != 0) border.setId(setImage());
			this.getChildren().addAll(border);
			this.setTranslateX(x * TILE_SIZE);
			this.setTranslateY(y * TILE_SIZE);
		}
		private String setImage() {
			String retval = "";
			if (contains == 1) retval = "display_wall";
			if (contains == 2) retval = "display_box";
			if (contains == 3) retval = "display_target";
			if (contains == 4) retval = "display_player";
			return retval;
		}
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage window = primaryStage;
		window.setTitle("puzzle mzaze #pick a name");
		Scene scene = new Scene(createContent());
		window.setScene(scene);
		scene.getStylesheets().add("application.css");
		window.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
