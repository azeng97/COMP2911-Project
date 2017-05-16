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
	private static final int MAX_ARRAY_SIZE = 10; // change later
	private int arr[][];
	private int arrayWidth; // change
	private int arrayHeight; // change
	

	public void dimension(int arrayWidth, int arrayHeight) {
		this.arrayWidth = arrayWidth;
		this.arrayHeight = arrayHeight;
		
	}
	public void move(int newArr[][]) {
		this.arr = newArr;
	}
	
	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(400, 400);
		
		// initialise done in backend.
		int array[][] = new int[MAX_ARRAY_SIZE][MAX_ARRAY_SIZE];
		for (int x = 0; x < 10; x ++) {
			for (int y = 0; y < 10; y ++) {
				array[x][y] = (int)(Math.random() * 0.5 * 10);
			}
		}
		dimension(10, 10);
		move(array);
		//
		
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
			if (contains == 0) border.setFill(Color.WHITE);
			this.getChildren().addAll(border, text);
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
		scene.getStylesheets().add("application/application.css");
		window.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

/* things to do
	- make a name for the game.
*/
