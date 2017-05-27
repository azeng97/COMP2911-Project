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
/**
 * Main Game class: handles most connections between back end and front end
 * 
 * @author Avan Zeng
 *
 */
public class WarehouseBoss extends Application {
	/**
	 * Main function: plays sound and launches the menu UI
	 * @param args
	 */
	public static void main(String args[])
	{
		initSound();
		launch();
	}
	/**
	 * Plays music clip
	 */
	public static void initSound()
	{
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		Mixer mixer = AudioSystem.getMixer(mixerInfos[0]);
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try { clip = (Clip) mixer.getLine(dataInfo);	}
		catch (LineUnavailableException lue) {	lue.printStackTrace();	}
		
		try {
			URL soundURL = WarehouseBoss.class.getClassLoader().getResource("Dungeon_King_Loop.wav");
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
	}
	/**
	 * Start by initiating main menu UI
	 */
	@Override
	public void start(Stage arg0) throws Exception {
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Warehouse Bros.");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());

			MenuController controller =  loader.<MenuController>getController();
			controller.initData(clip);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch (IOException e) { e.printStackTrace(); }	
		totalScore = 0;
		
	}
	/**
	 * Initiate a game
	 * @param primaryStage UI of game window
	 */
	public void play(Stage primaryStage)
	{
		gameOver = false;
		emptyTargets = 0;
		totalMoves = 0;
		nUndos = 0;
		this.buildBoard(level);
		this.display = new Display(board.getNRows(),board.getNCols(),this);
		levelScore = board.maxScore;
		display.init(primaryStage);

	}
	/**
	 * Transition to next level
	 * @param stage	UI of game window
	 */
	public void nextLevel(Stage stage)
	{
		level++;
		totalScore += levelScore;
		display.stopTimer();
		play(stage);
	}
	/**
	 * Check if game is over (all levels for set difficulty are completed)
	 * @return true if final level is reached
	 */
	public boolean endGame()
	{
		if (level == finalLevel)
		{
			try 
			{
				FileWriter writer = new FileWriter("leaderboard.txt", true);
				writer.write(totalScore + "\n");
				writer.close();
			} catch (IOException e) {};
			return true;
		}
		else return false;
	}
	/**
	 * Resume game from a save
	 * @param stage UI of game window
	 */
	public void resume (Stage stage)
	{
		gameOver = false;
		this.loadGame();
		changeDifficulty();
		this.display = new Display(board.getNRows(),board.getNCols(),this);
		display.init(stage);
		display.setTimerCounter(timerCounter);
	}
	/**
	 * Make a move, add move to move history
	 * @param direction	direction of move
	 * @return	whether move was successfully made
	 */
	public boolean makeMove(int direction)
	{
		Move move = new Move(direction);
		if (player.makeMove(move))
		{
			moveHistory.add(move);
			if (moveHistory.size() > maxMoves)
			{
				moveHistory.remove(0);
			}
			totalMoves++;
			changeScore(-2);
			return true;
		}
		else return false; 
	}
	/**
	 * Undo a move, find the last move that was a change of direction or push of box
	 * and return to the state before that move
	 * @param stage	UI of game window
	 */
	public void undoMove(Stage stage)
	{
		int n;
		if (nUndos>=maxUndos) return;
		if(moveHistory.size()>0)
		{			
			Move last = moveHistory.remove(moveHistory.size()-1);
			player.undoMove(last);
			for (n = moveHistory.size()-1; n>=0; n--){
				Move move = moveHistory.elementAt(n);
				if (move.getDirection()!=last.getDirection() || move.getPushed() != last.getPushed()) break;
				player.undoMove(move);
				moveHistory.remove(n);
			}
			display.stopTimer();
			display.init(stage);
			nUndos++;
			display.setUndos();
			totalMoves++;
			changeScore(-10);
		}
	}
	
	/**
	 * Retrieve square at position
	 * @param pos	position of square
	 * @return	square requested
	 */
	public Square squareAt(Position pos)
	{
		return ((Square) board.retrieveObj(pos));
	}
	/**
	 * Getter for number of non filled targets
	 * @return integer
	 */
	public int emptyTargets()
	{
		return this.emptyTargets;
	}
	/**
	 * Decrement number of non filled targets
	 */
	public void decrementTargets()
	{
		emptyTargets--;
		if(emptyTargets == 0)
		{
			gameOver = true;
		}
	}
	/**
	 * Incrememnt number of non filled targets
	 */
	public void incrementTargets()
	{
		emptyTargets++;
	}
	/**
	 * Change score if the score is above the minimum score of the level
	 * @param i integer to change the score (negative for decrease)
	 */
	public void changeScore(int i)
	{
		if (levelScore>board.minScore) levelScore+= i;
	}
	/**
	 * Return current board of the game
	 * @return
	 */
	public Board getBoard()
	{
		return this.board;
	}
	/**
	 * Builds board from map file (txt)
	 * Start at level 0 for easy, level 20 for hard
	 * @param level	integer level
	 */
	public void buildBoard(int level)
	{
		String levelDirectory = System.getProperty("user.dir") + java.io.File.separator + "Maps" + java.io.File.separator;
		if (difficulty ==1) level+=20;
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
			board.maxScore = (board.nBoxes*numRows*numCols*100*(level+5))/(board.nSpaces);
			board.minScore = (board.nBoxes*numRows*numCols*10)/board.nSpaces;
		}
		catch (IOException e)
		{
			System.out.println("File format incorrect");
			return;
		}
		
		
	}
	/**
	 * Create squares and entities based on symbols in map file
	 * @param pos	position of square
	 * @param c		character corresponding to square:
	 * # for wall, " " for space, $ for box in space, . for target, @ for player in space, P for player in target,
	 * X for box in target 
	 */
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
			this.board.nSpaces++;
			break;
		case '$':
			square = new Space(pos, this);
			square.addEntity(new Box(square, this));
			this.board.nBoxes++;
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
	/**
	 * Save a game by writing various game statistics to text file
	 * - current level
	 * - current timer
	 * - difficulty of game
	 * - total score
	 * - score of current level
	 * - total moves used
	 * - number of undos used
	 * - details of board
	 */
	public void saveGame()
	{
		try { 
			PrintWriter writer = new PrintWriter("save.data","UTF-8");
			writer.println(level);
			writer.println(display.getTimerCounter());
			writer.println(difficulty);
			writer.println(totalScore);
			writer.println(levelScore);
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
	/**
	 * load a game from a save
	 */
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
			timerCounter = Integer.valueOf(in.readLine().trim()).intValue();
			difficulty = Integer.valueOf(in.readLine().trim()).intValue();
			totalScore = Integer.valueOf(in.readLine().trim()).intValue();
			levelScore = Integer.valueOf(in.readLine().trim()).intValue();
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
	/**
	 * set difficulty of game
	 * @param d	0 for easy, 1 for difficult
	 */
	public static void setDifficulty (int d)
	{
		difficulty = d;
		changeDifficulty();
	}
	/**
	 * set number of undos based on difficulty
	 */
	public static void changeDifficulty ()
	{
		if (difficulty == 1)  
			{ maxUndos = 3;
			}
		else 
			{ maxUndos = 6;
			}
	}
	/**
	 * Return remaining undos for level
	 * @return	integer number of undos
	 */
	public int undosRemaining()
	{
		return maxUndos-nUndos;
	}
	/**
	 * Check if current level is over (all targets filled)
	 * @return	true for level over
	 */
	public boolean isGameOver()
	{
		return gameOver;
	}
	/**
	 * Getter for total score
	 * @return	integer total score
	 */
	public int getTotalScore()
	{
		return totalScore;
	}
	/**
	 * Setter f or total score
	 * @param n	integer score
	 */
	public void setTotalScore(int n)
	{
		totalScore = n;
	}
	/**
	 * Getter for level score
	 * @return	integer level score
	 */
	public int getLevelScore()
	{
		return levelScore;
	}
	/**
	 * Setter for level score
	 * @param n	integer score
	 */
	public void setLevelScore(int n)
	{
		levelScore = n;
	}
	public static Clip clip;
	public static int difficulty = 0;
	private int timerCounter = 0;
	private int totalScore = 0;
	private int levelScore = 0;
	private static int finalLevel = 15;
	public Display display;
	private boolean gameOver;
	public int totalMoves;
	private Player player;
	private int emptyTargets;
	private int maxMoves = 100;
	public static int maxUndos = 6;
	private Board board;
	public static int level = 0;
	public int nUndos = 0;
	public Vector<Move> moveHistory = new Vector<Move>();
	private static final int LOOP_CONTINUOUSLY = 9999;
	
}
