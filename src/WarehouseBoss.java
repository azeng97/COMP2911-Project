import java.io.*;
import java.util.*;

public class WarehouseBoss {
	public static void main(String args[])
	{
		WarehouseBoss game = new WarehouseBoss(new Output("Warehouse Boss!"));
		game.play();
	}
	
	public void play()
	{
		gameOver = false;
		emptyTarget = 0;
		
	}
	
	public Square squareAt(Position pos)
	{
		return ((Square) squares.retrieveObj(pos));
	}
	private boolean gameOver;
	private Board squares;
	private Search search;
	private Vector<Move> move;
}
