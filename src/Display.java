import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javafx.animation.PathTransition;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Display{
	private static final int TILE_SIZE = 40;
	private static final int MAX_ARRAY_SIZE = 10; // change later
	private int arr[][] = new int[MAX_ARRAY_SIZE][MAX_ARRAY_SIZE];
	private static final int W = 400, H = 400;
	
	private Image playerImage;
	private Image boxImage;
	
	private Node player;
	private Node box;
	
	private WarehouseBoss g;
	private int arrayWidth; // change
	private int arrayHeight; // change
	

	public Display(int arrayWidth, int arrayHeight, WarehouseBoss g) {
		this.arrayWidth = arrayWidth;
		this.arrayHeight = arrayHeight;
		this.g = g;
		
	}
	public void movePlayerBy(double dx, double dy) {
		if (dx == 0 && dy == 0) return;
		final double cx = player.getBoundsInLocal().getWidth()  / 2;
		final double cy = player.getBoundsInLocal().getHeight() / 2;
		
		double x = cx + player.getLayoutX() + dx;
        double y = cy + player.getLayoutY() + dy;
        System.out.println(player.getLayoutX());
        System.out.println(player.getLayoutY());
        System.out.println(x + " " + y);
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
        	//transitionTo(x-cx, y-cy, player);
        }
	}
	public void transitionTo(double x, double y, Node n) {
		Line line = new Line(n.getBoundsInLocal().getMinX(), n.getBoundsInLocal().getMinY(), x, y);
		PathTransition trans = new PathTransition();
		trans.setNode(n);
		trans.setDuration(Duration.seconds(4));
		trans.setPath(line);
		trans.play();
		
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
		root.setPrefSize(TILE_SIZE * MAX_ARRAY_SIZE, TILE_SIZE * MAX_ARRAY_SIZE);
		Group p = new Group(player);
		Group b = new Group(box);
		for(int y = 0; y < arrayHeight; y ++) {
			for (int x = 0; x < arrayWidth; x ++) {
				if (arr[x][y] == 3) {
					player.relocate(TILE_SIZE * x, TILE_SIZE * y);
				} else if (arr[x][y] == 4) {
					box.relocate(TILE_SIZE * x, TILE_SIZE * y);
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
			if (contains == 1) retval = new ImageView(new Image("http://i.imgur.com/nnBEDMn.png", 40, 40, false, false));
			if (contains >= 2) retval = new ImageView(new Image("http://i.imgur.com/DIbYuK3.png", 40, 40, false, false));
			return retval;
		}
	}
	
	public void init(){
		Stage stage = new Stage();
		stage.setTitle("Warehouse Bros");
		setImage();
		readTxt();
		Scene scene = new Scene(createContent());
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    movePlayerBy(0, -40); break;
                    case DOWN:  movePlayerBy(0, 40); break;
                    case LEFT:  movePlayerBy(-40, 0); break;
                    case RIGHT: movePlayerBy(40, 0); break;
				default:
					break;
                }
            }
        });

        stage.setScene(scene);
        stage.show();
	}
	public void setImage() {

		playerImage = new Image("http://i.imgur.com/Q5ZkQhI.png", 40, 40, false, false);
		boxImage = new Image("http://i.imgur.com/urtoFLR.png", 40, 40 , false, false);

		player = new ImageView(playerImage);
		box = new ImageView(boxImage);
	}
	public void readTxt() {
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
}