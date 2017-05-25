import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class WarehouseBoss extends Application {
	public static void main(String args[])
	{
		//Stage arg0 = new Stage();
		//menuUI ui = new menuUI(arg0);
		
		launch();
		
		//WarehouseBoss game = new WarehouseBoss();
		//game.play();
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		Stage s = new Stage();
		s.setTitle("Warehouse Bros.");
		menuUI ui = new menuUI(s);
//		menuUI ui = new menuUI(arg0);
		//ui.showMenu();
		//System.exit(1);		
	}
	
	public void play(Stage primaryStage)
	{
		gameOver = false;
		emptyTargets = 0;
		totalMoves = 0;

		this.buildBoard(0);
		this.display = new Display(board.getNRows(),board.getNCols(),this);
		//Display display = new Display(10,10,this);
		display.init(primaryStage);
		
		output = new Output(this, board);
//		System.out.println("Game starting. Use W,A,S,D to move. Player is P, boxes are $, and targets are O");
		output.printBoard();
		
		//edit test
		//edit test 2
		
//		String input = new String();
//		Scanner in = new Scanner(System.in); 
//		while (gameOver == false)
//		{
//			System.out.println("Make a Move!");
//			input = in.next();
//			Move move = new Move(Move.keyToDirection(input));
//			if (move.getDirection() == -1) continue;
//			totalMoves++;
//			player.makeMove(move);
//			output.printBoard();
//		}
//		in.close();
//		System.out.println("You win! Total moves:" + totalMoves);
//		System.exit(1);
	}
	
	public boolean makeMove(int direction)
	{
		Move move = new Move(direction);
		if (player.makeMove(move))
		{
			//System.out.println("moved");
			totalMoves++;
			return true;
		}
		else return false; 
	}
	
//	public void moveBox(Position pos, int direction)
//	{
//		System.out.println(display.getWidth());
//		display.moveBox(pos, direction);
//	}
	public Square squareAt(Position pos)
	{
		return ((Square) board.retrieveObj(pos));
	}
	public int emptyTargets()
	{
		return this.emptyTargets;
	}
	public void decrementTargets()
	{
		emptyTargets--;
		//System.out.println("Remaining targets: " + this.emptyTargets);
		if(emptyTargets == 0)
		{
			gameOver = true;
			System.out.println("Level Cleared!");
		}
	}
	public void incrementTargets()
	{
		emptyTargets++;
		//System.out.println("Remaining targets: " + this.emptyTargets);
	}
	public Board getBoard()
	{
		return this.board;
	}
	public void buildBoard(int level)
	{
		String levelDirectory = System.getProperty("user.dir") + java.io.File.separator + "Maps" + java.io.File.separator;
		String filename = levelDirectory + "Level" + level + ".data";
		
		BufferedReader in;
		try
		{
			in = new BufferedReader(new FileReader(filename));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Cannot find file \"" + filename + "\".");
			return;
		}
		try
		{
			int numRows = Integer.valueOf(in.readLine().trim()).intValue();
			int numCols = Integer.valueOf(in.readLine().trim()).intValue();
			board = new Board(numRows,numCols);
			for (int row=0;row<numRows;row++)
			{
				for (int col=0;col<numCols;col++)
				{
					this.buildSquare(new Position(row,col), (char) in.read());
				}
				in.readLine();
			}
			in.close();
		}
		catch (IOException e)
		{
			System.out.println("File format incorrect");
			return;
		}
		
	}
	public void buildSquare(Position pos, char c)
	{
		Square square = null;
		switch (c)
		{
		case '#':
			square = new Wall(pos, this);
			break;
		case ' ':
			square = new Space(pos, this);
			break;
		case '*':
			square = new Space(pos, this);
			square.addEntity(new Box(square, this));
			break;
		case '@':
			square = new Target(pos, this);
			incrementTargets();
			break;
		case '$':
			square = new Space(pos, this);
			Player p = new Player(square, this);
			square.addEntity(p);
			this.player = p;
			break;
		}
		board.setObj(pos, square);
	}
	
	public boolean isGameOver()
	{
		return gameOver;
	}
	public Display display;
	public Output output; 
	private boolean gameOver;
	private int totalMoves = 0;
	private Player player;
	private int emptyTargets = 0;
	private Board board;
}
