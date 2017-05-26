import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class WarehouseBoss extends Application {
	public static Clip clip;
	
	public static void main(String args[])
	{
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		Mixer mixer = AudioSystem.getMixer(mixerInfos[0]);
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try { clip = (Clip) mixer.getLine(dataInfo);	}
		catch (LineUnavailableException lue) {	lue.printStackTrace();	}
		
		try {
			URL soundURL = WarehouseBoss.class.getResource("Dungeon_King_Loop.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
			clip.open(audioStream);
		}
		catch (LineUnavailableException lue) {	lue.printStackTrace();	}
		catch (UnsupportedAudioFileException uafe) { uafe.printStackTrace();	}
		catch (IOException ioe) {	ioe.printStackTrace();	}
		
		Task<Void> task = new Task<Void>() {
			@Override protected Void call() throws Exception {
				clip.loop(LOOP_CONTINUOUSLY);
				do {
					try { Thread.sleep(100); } 
					catch (InterruptedException e) { e.printStackTrace(); }
				} while (clip.isActive());
				return null;
			}
		};
		
		Thread thread = new Thread(task);
		thread.start();
		launch();
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Warehouse Bros.");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
			//root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			MenuController controller =  loader.<MenuController>getController();
			controller.initData(clip);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch (IOException e) { e.printStackTrace(); }	}
	
	public void play(Stage primaryStage)
	{
		
		gameOver = false;
		emptyTargets = 0;
		totalMoves = 0;

		this.buildBoard(level);
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
	
	public void resume (Stage stage)
	{
		gameOver = false;
		this.loadGame();
		this.display = new Display(board.getNRows(),board.getNCols(),this);
		//Display display = new Display(10,10,this);
		display.init(stage);
		
		output = new Output(this, board);
//		System.out.println("Game starting. Use W,A,S,D to move. Player is P, boxes are $, and targets are O");
		output.printBoard();
	}
	
	public boolean makeMove(int direction)
	{
		Move move = new Move(direction);
		if (player.makeMove(move))
		{
			moveHistory.add(move);
			if (moveHistory.size() > maxUndos)
			{
				moveHistory.remove(0);
			}
			//System.out.println("moved");
			totalMoves++;
			
			return true;
		}
		else return false; 
	}
	
	public void undoMove(Stage stage)
	{
		int n;
		if(moveHistory.size()>0)
		{			
			Move last = moveHistory.remove(moveHistory.size()-1);
			player.undoMove(last);
			for (n = moveHistory.size()-1; n>=0; n--){
				Move move = moveHistory.elementAt(n);
				if (move.getDirection()!=last.getDirection()) break;
				player.undoMove(move);
				moveHistory.remove(n);
			}
			System.out.print("Index of last move: " + n);
			display.init(stage);
			nUndos++;
			totalMoves++;
		}
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
		case '$':
			square = new Space(pos, this);
			square.addEntity(new Box(square, this));
			break;
		case '.':
			square = new Target(pos, this);
			incrementTargets();
			break;
		case '@':
			square = new Space(pos, this);
			Player p = new Player(square, this);
			square.addEntity(p);
			this.player = p;
			break;
		case 'P':
			square = new Target(pos, this);
			incrementTargets();
			Player q = new Player(square, this);
			square.addEntity(q);
			this.player = q;
			break;
		case 'X':
			square = new Target(pos, this);
			square.addEntity(new Box(square, this));
			break;
		default:
			square = new Space(pos, this);
			break;
		}
		board.setObj(pos, square);
	}
	
	public void saveGame()
	{
		try { 
			PrintWriter writer = new PrintWriter("save.data","UTF-8");
			writer.println(level);
			writer.println(totalMoves);
			writer.println(nUndos);
			writer.println(board.getNRows());
			writer.println(board.getNCols());
			for (int row=0;row<board.getNRows();row++)
			{
				String line = new String();
				for (int col=0;col<board.getNCols();col++)
				{
					Square s = (Square) board.getObj(row,col);
					if (s instanceof Wall) line += "#";
					else if (s instanceof Target)
					{
						if (s.getContents() instanceof Box) line += "X";
						else if (s.getContents() instanceof Player) line += "P";
						else if (s.getContents() == null) line += ".";
					}
					else if (s instanceof Space)
					{
						if (s.getContents() == null) line += " ";
						else if (s.getContents() instanceof Player) line += "@";
						else if (s.getContents() instanceof Box) line += "$";		
					}
				}
				writer.println(line);
			}
			writer.close();
		} catch (IOException e) {
		}
	}
	
	public void loadGame()
	{
		BufferedReader in;
		try
		{
			in = new BufferedReader(new FileReader("save.data"));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Cannot find file \"" + "save.data" + "\".");
			return;
		}
		try
		{
			level = Integer.valueOf(in.readLine().trim()).intValue();
			totalMoves = Integer.valueOf(in.readLine().trim()).intValue();
			nUndos = Integer.valueOf(in.readLine().trim()).intValue();
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
//	public void deleteSave()
//	{
//		try {
//			File f = new File("save.data");
//			f.delete();
//		} catch(Exception e) {}
//	}
	
	public boolean isGameOver()
	{
		return gameOver;
	}
	public Display display;
	public Output output; 
	private boolean gameOver;
	public int totalMoves;
	private Player player;
	private int emptyTargets;
	private int maxUndos = 10;
	private Board board;
	public int level = 0;
	public int nUndos = 0;
	public Vector<Move> moveHistory = new Vector<Move>();
	private static final int LOOP_CONTINUOUSLY = 9999;
	
}
