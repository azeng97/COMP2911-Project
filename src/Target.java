/**
 * Target class, subclass of square
 * @author Avan
 *
 */
public class Target extends Square{
	public Target(Position p, WarehouseBoss g)
	{
		super(p,g);
	}
	public String getName()
	{
		return "Target";
	}
}
