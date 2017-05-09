
public class Wall extends Square{
	public Wall(Position p, WarehouseBoss g)
	{
		super(p,g);
	}
	public boolean isEmpty()
	{
		return false;
	}
}
