/**
 * Position class
 * Provides row/col position of an object
 * @author Avan
 *
 */
public class Position {
	/**
	 * Create Position from row and column
	 * @param row
	 * @param col
	 */
	public Position(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	/**
	 * Compare two positions
	 */
	public boolean equals(Object o)
	{
		return ((o instanceof Position) && ((Position) o).row == this.row && ((Position) o).col == this.col);
	}
	/**
	 * Getter for column
	 * @return integer column
	 */
	public int getCol()
	{
		return col;
	}
	/**
	 * Getter for row
	 * @return integer row
	 */
	public int getRow(){
		return row;
	}
	/**
	 * Reverse direction
	 * @param whichDir	direction (integer)
	 * @return	integer for opposite direction
	 */
	public static int reverseDirection(int whichDir) {
		switch (whichDir) {
		case North:
			return South;
		case South:
			return North;
		case East:
			return West;
		case West:
			return East;
		case Northeast:
			return Southwest;
		case Northwest:
			return Southeast;
		case Southeast:
			return Northwest;
		case Southwest:
			return Northeast;
		default:
			throw new IndexOutOfBoundsException();
		}
	}
	/**
	 * Returns adjacent position in given direction
	 * @param direction integer direction
	 * @return	position adjacent
	 */
	public Object adjacentPos(int direction) {
		switch (direction) {
		case North:
			return new Position(row - 1, col);
		case South:
			return new Position(row + 1, col);
		case East:
			return new Position(row, col + 1);
		case West:
			return new Position(row, col - 1);
		case Northeast:
			return new Position(row - 1, col + 1);
		case Northwest:
			return new Position(row - 1, col - 1);
		case Southeast:
			return new Position(row + 1, col + 1);
		case Southwest:
			return new Position(row + 1, col - 1);
		default:
			throw new IndexOutOfBoundsException();
		}
	}
	private int row;
	private int col;
	
	public static final int North = 0;
	public static final int East = 1;
	public static final int South = 2;
	public static final int West = 3;
	public static final int Northwest = 4;
	public static final int Northeast = 5;
	public static final int Southwest = 6;
	public static final int Southeast = 7;

}
