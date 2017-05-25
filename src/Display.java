import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Iterator;

public class Display {
	private static final int TILE_SIZE = 40;
	//private static final int MOVE_LENGTH = 40;
//	private static final int MAX_ARRAY_SIZE = 10; // change later
	private int arr[][];
//	private static final int W = 400, H = 400;
	
	private Image playerImage;
	private Image boxImage;
	
	private Node player;
	static private ArrayList<DisplayBox> boxes;
	
	private WarehouseBoss g;
	private int arrayWidth; // change
	private int arrayHeight; // change
	private boolean keyPressAllowed;
	
	private Stage stage; 
	private Pane root;
	//private boolean goNorth, goSouth, goEast, goWest;
	
	//private int timerCounter = 0; 
	
	public int getWidth()
	{
		return arrayWidth;
	}

	public Display(int arrayHeight, int arrayWidth, WarehouseBoss g) {
		this.arrayWidth = arrayWidth;
		this.arrayHeight = arrayHeight;
		this.arr = new int[arrayHeight][arrayWidth];
		this.g = g;
		this.keyPressAllowed = true;
		//this.goNorth = false;
		//this.goSouth = false;
		//this.goEast = false;
		//this.goWest = false;
		
	}
	public void movePlayerBy(double dx, double dy) {
		if (dx == 0 && dy == 0) return;
		//final double cx = player.getBoundsInLocal().getWidth()  / 2;
		//final double cy = player.getBoundsInLocal().getHeight() / 2;
		
		double x = player.getLayoutX() + dx;
        double y = player.getLayoutY() + dy;
        //System.out.println(player.getLayoutX());
        //System.out.println(player.getLayoutY());
        //System.out.println(x + " " + y);
        movePlayerTo(x, y);
	}
	private void movePlayerTo(double x, double y) {
        //final double cx = player.getBoundsInLocal().getWidth()  / 2;
        //final double cy = player.getBoundsInLocal().getHeight() / 2;
        
//        if (x - cx >= 0 &&
//            x + cx <= W &&
//            y - cy >= 0 &&
//            y + cy <= H) {
            player.relocate(x, y);
        	//transitionTo(x-cx, y-cy, player);
//        }
	}
	public void transitionTo(double x, double y, DisplayBox n) {
		Line line = new Line(n.getBoundsInLocal().getMinX(), n.getBoundsInLocal().getMinY(), x, y);
		PathTransition trans = new PathTransition();
		trans.setNode(n);
		trans.setDuration(Duration.seconds(4));
		trans.setPath(line);
		trans.play();
		
	}
//	public void moveBoxBy(double dx, double dy) {
//		if (dx == 0 && dy == 0) return;
//		final double cx = box.getBoundsInLocal().getWidth()  / 2;
//		final double cy = box.getBoundsInLocal().getHeight() / 2;
//		
//		double x = cx + box.getLayoutX() + dx;
//        double y = cy + box.getLayoutY() + dy;
//
//        moveBoxTo(x, y);
//	}
	public void moveBox(Position position, int direction) {
		DisplayBox box = null;
		Iterator<DisplayBox> i = boxes.iterator();
		//System.out.println("Expected: " + position.getCol() + " " + position.getRow());
		while (i.hasNext()){
			box = i.next();
			//System.out.println(box.getPosition().getCol() + " " + box.getPosition().getRow());
			if (box.getPosition().equals(position)) break;
		}
//        final double cx = player.getBoundsInLocal().getWidth()  / 2;
 //       final double cy = player.getBoundsInLocal().getHeight() / 2;

//        if (x - cx >= 0 &&
//            x + cx <= W &&
//            y - cy >= 0 &&
//            y + cy <= H) {
		double x = box.getLayoutX();
		double y = box.getLayoutY();
		switch (direction)
		{
			case North:
				box.relocate(x, y - 40);
				box.setPosition((Position)box.getPosition().adjacentPos(North));
				box.toFront();
				break;
			case South:
				box.relocate(x, y + 40);
				box.setPosition((Position)box.getPosition().adjacentPos(South));
				box.toFront();
				break;
			case West:
				box.relocate(x - 40, y);
				box.setPosition((Position)box.getPosition().adjacentPos(West));
				box.toFront();
				break;
			case East:
				box.relocate(x + 40, y);
				box.setPosition((Position)box.getPosition().adjacentPos(East));
				box.toFront();
				break;
		}
		System.out.println("g.isGameOver() = " + g.isGameOver());
		if (g.isGameOver()) { // check
			System.out.println("game complete");
			root.setEffect(new GaussianBlur());
			keyPressAllowed = false;
			VBox gameOverRoot = new VBox(5);
            gameOverRoot.getChildren().add(new Label("Level Cleared"));
            gameOverRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
            gameOverRoot.setAlignment(Pos.CENTER);
            gameOverRoot.setPadding(new Insets(20));

            Button nextLvlBtn = new Button("New Game");
            nextLvlBtn.setMaxWidth(Double.MAX_VALUE);
            gameOverRoot.getChildren().add(nextLvlBtn);

            Stage popupStage = new Stage(StageStyle.TRANSPARENT);
            popupStage.initOwner(stage);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(gameOverRoot, Color.TRANSPARENT));
            nextLvlBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					System.out.println("Next level");
					
				}
            	
            });
            Button quitBtn = new Button("Quit Game");
            quitBtn.setMaxWidth(Double.MAX_VALUE);
            gameOverRoot.getChildren().add(quitBtn);
            quitBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					System.out.println("Quit Game");
					System.exit(1);
					
				}
            	
            });
            popupStage.show();
            //System.exit(1);
		}
	}
	
	private Parent createContent() {
		this.root = new Pane();
		root.setPrefSize(TILE_SIZE * (arrayWidth + 3.5), TILE_SIZE * arrayHeight);
		playerImage = new Image("http://i.imgur.com/Q5ZkQhI.png", 40, 40, false, false);
		//boxImage = new Image("http://i.imgur.com/urtoFLR.png", 40, 40 , false, false);
		boxImage = new Image("https://opengameart.org/sites/default/files/crates_study_x2.png", 40, 40 , false, false);
		
		Rectangle background = new Rectangle(TILE_SIZE * arrayWidth, TILE_SIZE * arrayHeight);
		Image backgroundImage = new Image("File:///Users/justindaerolee/school/comp2911/workspace/comp2911-project/images/floor.png");
		background.setFill(new ImagePattern(backgroundImage, 0, 0, 1/(float)arrayWidth, 1/(float)arrayHeight, true)); // change the ratio to be dynamic
		//background.setLayoutX(0);
		root.getChildren().add(background);
		player = new ImageView(playerImage);
		Group p = new Group(player);
		boxes = new ArrayList<DisplayBox>();
		for(int y = 0; y < arrayHeight; y ++) {
			for (int x = 0; x < arrayWidth; x ++) {
				if (arr[y][x] == 4) {
					player.relocate(TILE_SIZE * x, TILE_SIZE * y);
				} else if (arr[y][x] == 2) {
					DisplayBox box = new DisplayBox(boxImage,y,x);
					boxes.add(box);
					Group b = new Group(box);
					root.getChildren().add(b);
					box.relocate(TILE_SIZE * x, TILE_SIZE * y);
				} else if (arr[y][x] != 0) {
					Tile tile = new Tile(x, y, arr[y][x]);
					root.getChildren().add(tile);
				}		
			}
		}
		root.getChildren().add(p);
		Image sideMenuImage = new Image("file:///Users/justindaerolee/school/comp2911/workspace/COMP2911-project/images/s29X24F.png");
		Rectangle sideMenu = new Rectangle(TILE_SIZE * 3.5, TILE_SIZE * arrayHeight);
		sideMenu.setLayoutX(TILE_SIZE * arrayWidth);
		sideMenu.setLayoutY(0);
		sideMenu.setFill(new ImagePattern(sideMenuImage, 0, 0, 3, 1, true));
		root.getChildren().add(sideMenu);
		Button saveBtn = new Button("Save Game");
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("game saved");
            }
        });
		saveBtn.setMaxWidth(Double.MAX_VALUE);
		
		Button pauseBtn = new Button("Pause Game");
		pauseBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("pause game");
				keyPressAllowed = false;
				root.setEffect(new GaussianBlur());
				VBox pauseRoot = new VBox(5);
	            pauseRoot.getChildren().add(new Label("Paused"));
	            pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
	            //pauseRoot.setLayoutX(((arrayWidth + 3.5)*TILE_SIZE)/ 2);
	            //pauseRoot.setLayoutY(arrayHeight*TILE_SIZE/2);
	            pauseRoot.setAlignment(Pos.CENTER);
	            pauseRoot.setPadding(new Insets(20));

	            Button resume = new Button("Resume");
	            resume.setMaxWidth(Double.MAX_VALUE);
	            pauseRoot.getChildren().add(resume);

	            Stage popupStage = new Stage(StageStyle.TRANSPARENT);
	            popupStage.initOwner(stage);
	            popupStage.initModality(Modality.APPLICATION_MODAL);
	            popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));
	            resume.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						root.setEffect(null);
		                popupStage.hide();
		                keyPressAllowed = true;
						
					}
	            	
	            });
	            pauseRoot.setOnKeyPressed(new EventHandler<KeyEvent>() {
	                @Override
	                public void handle(KeyEvent event) {
	                    if (event.getCode() == KeyCode.ESCAPE) {
	                    	root.setEffect(null);
			                popupStage.hide();
			                keyPressAllowed = true;
	                    }
	                }            
	            });
	            Button quitBtn = new Button("Quit Game");
	            quitBtn.setMaxWidth(Double.MAX_VALUE);
	            pauseRoot.getChildren().add(quitBtn);
	            quitBtn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						System.out.println("Quit Game");
						
					}
	            	
	            });
	            popupStage.show();

			}
			
		});
		pauseBtn.setMaxWidth(Double.MAX_VALUE);
		Button resetBtn = new Button("Reset");
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("reset game");
            }
        });
		resetBtn.setMaxWidth(Double.MAX_VALUE);
		String numString = "0";
		Label moveCount = new Label();
		moveCount.setText("Moves: " + numString);
		moveCount.setMaxWidth(Double.MAX_VALUE);
		Label undoCount = new Label();
		undoCount.setText("Undos: " + numString);
		pauseBtn.setId("sideMenuButton");
		saveBtn.setId("sideMenuButton");
		resetBtn.setId("sideMenuButton");
		VBox vbButtons = new VBox();
		vbButtons.setSpacing(10);
		vbButtons.setPadding(new Insets(0, 20, 10, 20)); 
		vbButtons.getChildren().addAll(moveCount, undoCount, pauseBtn, saveBtn, resetBtn);
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
			if (contains == 0) retval = new ImageView(new Image("File:///Users/justindaerolee/school/comp2911/workspace/comp2911-project/images/floor.png", 40, 40, false, false));
			if (contains == 1) retval = new ImageView(new Image("http://i.imgur.com/nnBEDMn.png", 40, 40, false, false));
			if (contains == 3) retval = new ImageView(new Image("File:///Users/justindaerolee/school/comp2911/workspace/comp2911-project/images/target.png", 40, 40, false, false));
			return retval;
		}
	}
	
	public void init(Stage primaryStage){
		this.stage = primaryStage;
		
		this.stage.setTitle("Warehouse Bros");
		//setImage();
		constructBoard();
		Scene scene = new Scene(createContent());
		scene.getStylesheets().add
		 (Display.class.getResource("application.css").toExternalForm());
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                    	if (g.makeMove(North) && keyPressAllowed) {
                    		//goNorth = true;
                    		//timerCounter = 0;
                    		//keyPressAllowed = false;
                    		movePlayerBy(0, -40);
                    		break;
                    	}
                    case DOWN:
                    	if (g.makeMove(South) && keyPressAllowed) {
                    		//goSouth = true;
                    		//timerCounter = 0;
                    		//keyPressAllowed = false;
                    		movePlayerBy(0, 40);
                    		break;
                    	}
                    case LEFT:
                    	if (g.makeMove(West) && keyPressAllowed) {
                    		//goEast = true;
                    		//timerCounter = 0;
                    		//keyPressAllowed = false;
                    		movePlayerBy(-40, 0);
                    		break;
                    	}
                    case RIGHT:
                    	if (g.makeMove(East) && keyPressAllowed) {
                    		//goWest = true;
                    		//timerCounter = 0;
                    		//keyPressAllowed = false;
                    		movePlayerBy(40, 0);
                    		break;
                    	}
				default:
					break;
                }
            }            
        });

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
  /*      AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;
                timerCounter ++;
                if (timerCounter == (TILE_SIZE/MOVE_LENGTH))  {
                	goNorth = false; goSouth = false; goEast = false; goWest = false;
                	keyPressAllowed = true;
                	System.out.println(timerCounter * MOVE_LENGTH);
                }
                if (goNorth) dy -= MOVE_LENGTH;
                if (goSouth) dy += MOVE_LENGTH;
                if (goWest)  dx += MOVE_LENGTH;
                if (goEast)  dx -= MOVE_LENGTH;

                movePlayerBy(dx, dy);
            }
        };
        timer.start();*/
	}
//	public void setImage() {
//
//		playerImage = new Image("http://i.imgur.com/Q5ZkQhI.png", 40, 40, false, false);
//		boxImage = new Image("http://i.imgur.com/urtoFLR.png", 40, 40 , false, false);
//
//		player = new ImageView(playerImage);
//		for (int n=0; n<boxes.length; n++)
//		{
//			boxes = new ImageView(boxImage);
//		}	
//	}
	public void constructBoard() {
//		Scanner sc = null;
//		int x = 0, y = 0;
//	    try {
//	        sc = new Scanner(new FileReader("t1.txt"));
//	        while (sc.hasNext()) {
//	        	String s = sc.next();
//	        	arr[x][y] = Integer.parseInt(s);
//	        	x ++;
//	        	if (x == 10) {
//	        		x = 0;
//	        		y ++;
//	        		if (sc.hasNextLine()) sc.nextLine();
//	        	}
//	        }
//	    }
//	    catch (FileNotFoundException e) {
//   	  		System.out.println("The file not found");
//     	}
//	    finally {
//    	 	if (sc != null) sc.close();
//     	}
		Board board = g.getBoard();
		//System.out.println("Height:" + arrayHeight + " " + "Width:" + arrayWidth);
		for (int row=0;row<arrayHeight;row++)
		{
			for (int col=0;col<arrayWidth;col++)
			{
				//System.out.println(row + " " + col);
				Square s = (Square) board.getObj(row,col);
				if (s instanceof Wall) arr[row][col] = 1;
				else if (s instanceof Target) arr[row][col] = 3;
				else if (s instanceof Space) 
				{
					if (s.getContents() == null) arr[row][col] = 0;
					else if (s.getContents().getName().equals("Player")) arr[row][col] = 4;
					else if (s.getContents().getName().equals("Box")) arr[row][col] = 2;		
				}
			}
		}
	}
	public static final int North = 0;
	public static final int East = 1;
	public static final int South = 2;
	public static final int West = 3;
}

