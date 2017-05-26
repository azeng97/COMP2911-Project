import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Gui extends Application {
	private static final int TILE_SIZE = 30;
	private static final int MAX_ARRAY_SIZE = 10; // change later
	private static final int MOVE_DISTANCE = 5;
	
	private int arr[][] = new int[MAX_ARRAY_SIZE][MAX_ARRAY_SIZE];
	private static final int W = 300, H = 300;
	private int timerCounter = 0;
	
	private Image playerImage;
	private Image boxImage;
	
	private Node player;
	private ArrayList<Node> boxes;
	
	
	private int arrayWidth; // change
	private int arrayHeight; // change
	private boolean goNorth, goSouth, goEast, goWest;
	private boolean keyPressAllowed;
	

	public void dimension(int arrayWidth, int arrayHeight) {
		this.arrayWidth = arrayWidth;
		this.arrayHeight = arrayHeight;
		goNorth = false;
		goSouth = false;
		goWest = false;
		goEast = false;
		keyPressAllowed = true;
		
	}
	public void movePlayerBy(double dx, double dy) {
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
        	//transitionTo(x-cx, y-cy, player);
        }
	}
	/*public void transitionTo(double x, double y, Node n) {
		Line line = new Line(0, 0, x, y);
		PathTransition trans = new PathTransition();
		trans.setNode(n);
		trans.setDuration(Duration.seconds(4));
		trans.setPath(line);
		trans.play();
		playerX = x;
		playerY = y;
		
	}*/
	public void moveBoxBy(double dx, double dy, Node box) {
		if (dx == 0 && dy == 0) return;
		final double cx = box.getBoundsInLocal().getWidth()  / 2;
		final double cy = box.getBoundsInLocal().getHeight() / 2;
		
		double x = cx + box.getLayoutX() + dx;
        double y = cy + box.getLayoutY() + dy;

        moveBoxTo(x, y, box);
	}
	private void moveBoxTo(double x, double y, Node box) {
        final double cx = player.getBoundsInLocal().getWidth()  / 2;
        final double cy = player.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&
            x + cx <= W &&
            y - cy >= 0 &&
            y + cy <= H) {
            box.relocate(x - cx, y - cy);
        }
	}
	
	private Parent createContent(Stage stage) {
		int boxCounter = 0;
		Pane root = new Pane();
		root.setPrefSize(TILE_SIZE * (MAX_ARRAY_SIZE + 5), TILE_SIZE * MAX_ARRAY_SIZE);
		Group p = new Group(player);
		ArrayList<Group> b = new ArrayList<>();
		// initialise done in backend.
		dimension(10, 10);
		// change the size later
		for(int y = 0; y < arrayHeight; y ++) {
			for (int x = 0; x < arrayWidth; x ++) {
				Tile floor = new Tile(x,y, 0);
				root.getChildren().add(floor);
				if (arr[x][y] == 3) {
					player.relocate(TILE_SIZE * x, TILE_SIZE * y);
				} else if (arr[x][y] == 4) {
					boxes.add(new ImageView(boxImage));
					boxes.get(boxCounter).relocate(TILE_SIZE * x, TILE_SIZE * y);
					b.add(new Group(boxes.get(boxCounter)));
					root.getChildren().add(b.get(boxCounter));
					boxCounter ++;
				} else if (arr[x][y] != 0) {
					Tile tile = new Tile(x, y, arr[x][y]); // change the input
					root.getChildren().add(tile);
				}
			}
		}
		root.getChildren().add(p);
		Image background = new Image("file:///Users/justindaerolee/school/comp2911/workspace/COMP2911-project/images/background.png");
		
		Rectangle sideMenu = new Rectangle(TILE_SIZE * 5, TILE_SIZE * MAX_ARRAY_SIZE);
		sideMenu.setLayoutX(TILE_SIZE * MAX_ARRAY_SIZE);
		sideMenu.setLayoutY(0);
		sideMenu.setFill(new ImagePattern(background, 0, 0, 1, 1, true));
		root.getChildren().add(sideMenu);
		Button saveBtn = new Button("Save Game");
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("game saved");
            }
        });
		saveBtn.setStyle("-fx-focus-color: transparent;");
		saveBtn.setMaxWidth(Double.MAX_VALUE);
		
		Button pauseBtn = new Button("Pause Game");
		pauseBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("pause game");
				keyPressAllowed = false;
				root.setEffect(new GaussianBlur());
				
//				VBox pauseRoot = new VBox(5);
//	            pauseRoot.getChildren().add(new Label("Paused"));
//	            pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
//	            pauseRoot.setAlignment(Pos.CENTER);
//	            pauseRoot.setPadding(new Insets(20));
//
//	            Button resume = new Button("Resume");
//	            pauseRoot.getChildren().add(resume);
//
//	            Stage popupStage = new Stage(StageStyle.TRANSPARENT);
//	            popupStage.initOwner(stage);
//	            popupStage.initModality(Modality.APPLICATION_MODAL);
//	            popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));
	            
	            Parent pauseRoot;
	            Stage popupStage = new Stage(StageStyle.TRANSPARENT);
	    		try {
	    			pauseRoot = FXMLLoader.load(getClass().getResource("SettingMenu.fxml"));
	    			
	    			Scene scene = new Scene(pauseRoot);
	    			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    			
	    			
		            popupStage.initOwner(stage);
		            popupStage.initModality(Modality.APPLICATION_MODAL);
		            popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));
	    			
		            popupStage.setScene(scene);
	    			//primaryStage.show();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		
	    		Button resume = new Button("Resume");
	            resume.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						root.setEffect(null);
		                popupStage.hide();
		                keyPressAllowed = true;
						
					}
	            	
	            });
	            popupStage.show();

			}
			
		});
		pauseBtn.setStyle("-fx-focus-color: transparent; ");
		pauseBtn.setStyle("-fx-background-color: #969696;");
		pauseBtn.setMaxWidth(Double.MAX_VALUE);
		root.getChildren().add(pauseBtn);
		VBox vbButtons = new VBox();
		vbButtons.setSpacing(10);
		vbButtons.setPadding(new Insets(0, 20, 10, 20)); 
		vbButtons.getChildren().addAll(pauseBtn, saveBtn);
		vbButtons.setLayoutX((arrayWidth) * TILE_SIZE);
		vbButtons.setLayoutY(TILE_SIZE);
		root.getChildren().add(vbButtons);
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
			if (contains == 0) retval = new ImageView(new Image("file:///Users/justindaerolee/school/comp2911/workspace/COMP2911-project/images/floor.png"));
			if (contains == 1) retval = new ImageView(new Image("file:///Users/justindaerolee/school/comp2911/workspace/COMP2911-project/images/wall.png"));
			if (contains >= 2) retval = new ImageView(new Image("file:///Users/justindaerolee/school/comp2911/workspace/COMP2911-project/images/target.png"));
			return retval;
		}
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("puzzle mzaze #pick a name");
		setImage();
		readTxt();
		Scene scene = new Scene(createContent(stage));
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    
                    	if (keyPressAllowed) {
                    		timerCounter = 0; goNorth = true; keyPressAllowed = false;
                    	}
                    	break;
                    case DOWN:  
                    	if (keyPressAllowed) {
                    		timerCounter = 0; goSouth = true; keyPressAllowed = false;
                    	}
                    	break;
                    case LEFT:  
                    	if (keyPressAllowed) {
                    		timerCounter = 0; goEast = true; keyPressAllowed = false;
                    	}
                    	break;
                    case RIGHT: 
                    	if (keyPressAllowed) {
                    		timerCounter = 0; goWest = true; keyPressAllowed = false;
                    	}
                    	break;
                    case SHIFT: 
                    	goNorth = false; goSouth = false; goWest = false; goEast = false; // for testing
                    default:
                    	break;
                }
            }
        });
		

        stage.setScene(scene);
        stage.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;
                timerCounter ++;
                if (timerCounter == (TILE_SIZE/MOVE_DISTANCE))  {
                	goNorth = false; goSouth = false; goEast = false; goWest = false;
                	keyPressAllowed = true;
                	System.out.println(timerCounter * MOVE_DISTANCE);
                }
                if (goNorth) dy -= MOVE_DISTANCE;
                if (goSouth) dy += MOVE_DISTANCE;
                if (goWest)  dx += MOVE_DISTANCE;
                if (goEast)  dx -= MOVE_DISTANCE;

                movePlayerBy(dx, dy);
            }
        };
        timer.start();
	}
	private void setImage() {

		playerImage = new Image("file:///Users/justindaerolee/school/comp2911/workspace/COMP2911-project/images/player.png", 30 ,30, false, false);
		boxImage = new Image("file:///Users/justindaerolee/school/comp2911/workspace/COMP2911-project/images/box.png", 30 ,30 , false, false);
		player = new ImageView(playerImage);
		this.boxes = new ArrayList<>();
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
