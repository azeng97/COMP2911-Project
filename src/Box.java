/**
 * Class for boxes
 * @author Avan
 *
 */
public class Box extends Entity{
	public Box(Square s, WarehouseBoss g)
	{
		super(s,g);
	}
	public String getName()
	{
		return "Box";
	}
}
