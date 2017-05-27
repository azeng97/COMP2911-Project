/**
 * This class defines a movement
 * Direction is represented by an integer
 * A boolean represents if there was a push involved in this move
 * @author Avan Zeng
 *
 */
public class Move {
	/**
	 * Create a move
	 * @param direction	integer of direction
	 */
	public Move(int direction)
	{
		this.direction = direction;
	}
	/**
	 * Getter for direction
	 * @return integer direction
	 */
	public int getDirection()
	{
		return direction;
	}
	/**
	 * Getter for whether an item was pushed 
	 * @return true if pushed
	 */
	public boolean getPushed()
	{
		return pushed;
	}
	/**
	 * Setter for direction
	 * @param direction	integer for direction
	 */
	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	/**
	 * Setter for pushed
	 * @param t	true for pushed
	 */
	public void setPushed(boolean t)
	{
		this.pushed = t;
	}
	
	private int direction;
	private boolean pushed;
	
	public static final int North = 0;
	public static final int East = 1;
	public static final int South = 2;
	public static final int West = 3;
}
