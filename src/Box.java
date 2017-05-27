/**
 * Class for boxes
 * @author Avan
 *
 */
public class Box extends Entity{
	/**
	 * Create box from square and game
	 * @param s	square that box is in
	 * @param g	current game
	 */
	public Box(Square s, WarehouseBoss g)
	{
		super(s,g);
	}
	/**
	 * Get name for subclass (debugging)
	 */
	public String getName()
	{
		return "Box";
	}
}
