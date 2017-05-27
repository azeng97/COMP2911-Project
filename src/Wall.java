/**
 * Class for Wall block
 * @author Avan Zeng
 *
 */
public class Wall extends Square{
	public Wall(Position p, WarehouseBoss g)
	{
		super(p,g);
	}
	/**
	 * Wall is not empty
	 */
	public boolean isEmpty()
	{
		return false;
	}
}
