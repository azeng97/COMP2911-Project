
public class Space extends Square{
	public Space(Position p, WarehouseBoss g)
	{
		super(p,g);
	}
	public boolean isEmpty()
	{
		return true; 
	}
	public String getName()
	{
		return "Space";
	}
}
