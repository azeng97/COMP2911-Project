import java.io.*;
import java.util.*;

public class WarehouseBoss {
	public static void main(String args[])
	{
		WarehouseBoss game = new WarehouseBoss();
		game.play();
	}
	
	public void play()
	{
		gameOver = false;
		emptyTargets = 0;
		//TODO: GET INPUTS
		System.exit(1);
	}
	
	public Square squareAt(Position pos)
	{
		return ((Square) squares.retrieveObj(pos));
	}
	public int emptyTargets()
	{
		return this.emptyTargets;
	}
	public void decrementTargets()
	{
		emptyTargets--;
		if(emptyTargets == 0)
		{
			gameOver = true;
			System.out.println("Level Cleared!");
		}
	}
	public void incrementTargets()
	{
		emptyTargets++;
	}
	private boolean gameOver;
	private int emptyTargets = 0;
	private Board squares;
	private Search search;
	private Vector<Move> move;
}
