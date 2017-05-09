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
	private int nRows;
	private int nCols;
	private Object board[][];
	
}
