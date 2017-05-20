import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Display extends Application {
	private static final int TILE_SIZE = 30;
	private static final int MAX_ARRAY_SIZE = 10; // change later
	private int arr[][] = new int[MAX_ARRAY_SIZE][MAX_ARRAY_SIZE];
	private static final int W = 300, H = 300;
	
	private Image playerImage;
	private Image boxImage;
	
	private Node player;
	private Node box;
	
	
	private int arrayWidth; // change
	private int arrayHeight; // change
	

	public void dimension(int arrayWidth, int arrayHeight) {
		this.arrayWidth = arrayWidth;
		this.arrayHeight = arrayHeight;
		
	}
	public void movePlayerBy(int dx, int dy) {
		if (dx == 0 && dy == 0) return;
		final double cx = player.getBoundsInLocal().getWidth()  / 2;
		final double cy = player.getBoundsInLocal().getHeight() / 2;
		
		double x = cx + player.getLayoutX() + dx;
        double y = cy + player.getLayoutY() + dy;

        movePlayerTo(x, y);
	}
	private void movePlayerTo(double x, double y) {
        final double cx = player.getBoundsInLocal().getWidth()  / 2;
        final double cy = player.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&
            x + cx <= W &&
            y - cy >= 0 &&
            y + cy <= H) {
            player.relocate(x - cx, y - cy);
        }
	}
	public void moveBoxBy(double dx, double dy) {
		if (dx == 0 && dy == 0) return;
		final double cx = box.getBoundsInLocal().getWidth()  / 2;
		final double cy = box.getBoundsInLocal().getHeight() / 2;
		
		double x = cx + box.getLayoutX() + dx;
        double y = cy + box.getLayoutY() + dy;

        moveBoxTo(x, y);
	}
	private void moveBoxTo(double x, double y) {
        final double cx = player.getBoundsInLocal().getWidth()  / 2;
        final double cy = player.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&
            x + cx <= W &&
            y - cy >= 0 &&
            y + cy <= H) {
            box.relocate(x - cx, y - cy);
        }
	}
	
	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(300, 300);
		Group p = new Group(player);
		Group b = new Group(box);
		// initialise done in backend.
		dimension(10, 10);
		// change the size later
		for(int y = 0; y < arrayHeight; y ++) {
			for (int x = 0; x < arrayWidth; x ++) {
				if (arr[x][y] == 3) {
					movePlayerTo(TILE_SIZE * x, TILE_SIZE * y);
				} else if (arr[x][y] == 4) {
					moveBoxTo(TILE_SIZE * x, TILE_SIZE * y);
				} else {
					Tile tile = new Tile(x, y, arr[x][y]); // change the input
					root.getChildren().add(tile);
				}		
			}
		}
		root.getChildren().add(p);
		root.getChildren().add(b);
		
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
			border.setFill(Color.WHITE);
			this.getChildren().addAll(new Group(setImage()));
			this.setTranslateX(x * TILE_SIZE);
			this.setTranslateY(y * TILE_SIZE);
		}
		private Node setImage() {
			Node retval = null;
			if (contains == 0) retval = new Text();
			if (contains == 1) retval = new ImageView(new Image("file:///images/wall.png"));
			if (contains >= 2) retval = new ImageView(new Image("file:///images/target.png"));
			return retval;
		}
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("puzzle mzaze #pick a name");
		setImage();
		readTxt();
		Scene scene = new Scene(createContent());
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    movePlayerBy(0, -30); break;
                    case DOWN:  movePlayerBy(0, 30); break;
                    case LEFT:  movePlayerBy(-30, 0); break;
                    case RIGHT: movePlayerBy(30, 0); break;
				default:
					break;
                }
            }
        });

        stage.setScene(scene);
        stage.show();
	}
	private void setImage() {
		playerImage = new Image("file:///images/player.png");
		boxImage = new Image("file:///box.png");
		player = new ImageView(playerImage);
		box = new ImageView(boxImage);
	}
	private void readTxt() {
		Scanner sc = null;
		int x = 0, y = 0;
	    try {
	        sc = new Scanner(new FileReader("t1.txt"));
	        while (sc.hasNext()) {
	        	String s = sc.next();
	        	arr[x][y] = Integer.parseInt(s);
	        	x ++;
	        	if (x == 10) {
	        		x = 0;
	        		y ++;
	        		if (sc.hasNextLine()) sc.nextLine();
	        	}
	        }
	    }
	    catch (FileNotFoundException e) {
   	  		System.out.println("The file not found");
     	}
	    finally {
    	 	if (sc != null) sc.close();
     	}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
