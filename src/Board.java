import java.lang.Object;
/**
 * A 2D array for storing location of objects
 * Obtains Location of a object and converts into row/column index for array
 * @author Avan Zeng
 *
 */
public class Board {
	public Board(int nRows, int nCols)
	{
		this.nRows = nRows;
		this.nCols = nCols;
		board = new Object[nRows][nCols];
	}
	public Object getObj(int row, int col)
	{
		return board[row][col];
	}
	public Object retrieveObj(Position p)
	{
		return board[p.getRow()][p.getCol()];
	}
	public void setObj(Position p, Object o)
	{
		board[p.getRow()][p.getCol()] = o;
	}
	public int getNCols()
	{
		return nCols;
	}
	public int getNRows()
	{
		return nRows;
	}
	
	public void printBoard()
	{
		for (int row=0;row<nRows;row++)
		{
			String line = new String();
			for (int col=0;col<nCols;col++)
			{
				Square s = (Square) board[row][col];
				if (s instanceof Wall) line += "#";
				else if (s instanceof Target)
				{
					if (s.getContents() instanceof Box) line += "X";
					else if (s.getContents() instanceof Player) line += "P";
					else line += "O";
				}
				else if (board[row][col] instanceof Space)
				{
					if (((Square) board[row][col]).getContents() == null) line += " ";
					else if (((Square) board[row][col]).getContents().getName().equals("Player")) line += "P";
					else if (((Square) board[row][col]).getContents().getName().equals("Box")) line += "$";		
				}
			}
			System.out.print(line + "\n");
		}
	}
	private int nRows;
	private int nCols;
	private Object board[][];
	
}
