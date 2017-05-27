/**
 * Class for entity - player and boxes
 * Defines square the entity is in and position
 * @author Avan Zeng
 *
 */
abstract class Entity {
	protected Square square;
	protected WarehouseBoss game;
	/**
	 * Create Entity object
	 * @param s	square that entity is situated in
	 * @param g	current game
	 */
	public Entity(Square s, WarehouseBoss g)
	{
		this.square = s;
		this.game = g;
	}
	/**
	 * Getter for position
	 * @return	position of entity
	 */
	public Position getPosition()
	{
		return this.square.getPosition();
	}
	/**
	 * Getter for square of entity
	 * @return	square entity is in
	 */
	public Square getSquare()
	{
		return this.square;
	}
	/**
	 * Set square entity is in
	 * @param s	square 
	 */
	public void setSquare(Square s)
	{
		this.square = s;
	}
	abstract public String getName();
}
