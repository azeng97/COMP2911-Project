/**
 * Square object representing one grid space on the board
 * stores information about position and contents of the grid spot
 * Also handles movements of entities throughout 
 * @author Avan
 *
 */
public class Square extends Object 
{
	/**
	 * Create a square from position
	 * @param p	position	
	 * @param g	current game
	 */
	public Square(Position p, WarehouseBoss g)
	{
		this.position = p;
		this.game = g;
		this.contents = null;
	}
	/**
	 * Add entity to square, check if it is empty
	 * Also check if square is a target
	 * @param e	Entity to be added
	 * @return	true if successfully added
	 */
	public boolean addEntity(Entity e)
	{
		if (isEmpty())
		{
			boolean wasTarget = (e.getSquare() instanceof Target);
			e.getSquare().removeContents();
			contents = e;
			e.setSquare(this);
			
			if (this instanceof Target && e instanceof Box && !wasTarget)
			{
				isInsideTarget = true;
				game.decrementTargets();
				game.display.changeBox(this.position);
			} 
			else if (!(this instanceof Target) && e instanceof Box && wasTarget)
			{
				game.incrementTargets();
				game.display.changeBoxBack(this.position);
			}
			return true;
		}
		return false;
	}
	/**
	 * Check if square can be entered
	 * @return	true if square is empty
	 */
	public boolean isEmpty()
	{
		return (contents==null);
	}
	/**
	 * Check whether entity in square can be pushed in given direction
	 * @param direction	integer for direction	
	 * @return	true for can be pushed
	 */
	public boolean canPush(int direction)
	{
		if (this instanceof Wall) return false;
		Square neighbour = game.squareAt((Position)position.adjacentPos(direction));
		return (contents != null && neighbour != null && neighbour.isEmpty());
	}
	/**
	 * Getter for position
	 * @return	position of square
	 */
	public Position getPosition()
	{
		return position;
	}
	/**
	 * Getter for contents of square
	 * @return	entity in square, null for empty
	 */
	public Entity getContents()
	{
		return this.contents;
	}
	/**
	 * Push contents of square in given direction
	 * @param direction	integer representing direction
	 * @return	true for successful push
	 */
	public boolean pushContents(int direction)
	{
		if(!canPush(direction))
		{
			return false;
		}
		Square neighbour = game.squareAt((Position)position.adjacentPos(direction));
		game.display.moveBox(this.position,direction);
		neighbour.addEntity(contents);
		return true;
	}
	/**
	 * remove contents from square
	 */
	public void removeContents()
	{
		contents = null;
	}
	/**
	 * check if square is inside target
	 * @return	true for inside target
	 */
	public boolean getIsInsideTarget() {
		return this.isInsideTarget;
	}
	private boolean isInsideTarget = false;
	protected Position position;
	protected WarehouseBoss game;
	protected Entity contents;
}
