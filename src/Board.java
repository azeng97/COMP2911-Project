import java.lang.Object;
/**
 * A 2D array for storing location of objects
 * Obtains Location of a object and converts into row/column index for array
 * @author Avan Zeng
 *
 */
public class Board {
	/**
	 * Create board with number of rows and columns
	 * @param nRows	integer rows
	 * @param nCols	integer columns
	 */
	public Board(int nRows, int nCols)
	{
		this.nRows = nRows;
		this.nCols = nCols;
		board = new Object[nRows][nCols];
	}
	/**
	 * Get object at a certain position on board
	 * @param row	integer row
	 * @param col	integer column
	 * @return	object at position
	 */
	public Object getObj(int row, int col)
	{
		return board[row][col];
	}
	/**
	 * Get object at a certain position on board
	 * @param p	position
	 * @return	object at position
	 */
	public Object retrieveObj(Position p)
	{
		return board[p.getRow()][p.getCol()];
	}
	/**
	 * Set object at position
	 * @param p	position of object
	 * @param o	object at position
	 */
	public void setObj(Position p, Object o)
	{
		board[p.getRow()][p.getCol()] = o;
	}
	/**
	 * Getter for number of columns on board
	 * @return integer columns
	 */
	public int getNCols()
	{
		return nCols;
	}
	/**
	 * Getter for number of rows on board
	 * @return integer rows
	 */
	public int getNRows()
	{
		return nRows;
	}
	

	private int nRows;
	private int nCols;
	public int nBoxes;
	public int nSpaces;
	public int maxScore;
	public int minScore;
	private Object board[][];
	
}
