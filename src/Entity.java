
abstract class Entity {
	protected Square square;
	protected WarehouseBoss game;
	
	public Entity(Square s, WarehouseBoss g)
	{
		this.square = s;
		this.game = g;
	}
	
	public Position getPosition()
	{
		return this.square.getPosition();
	}
	public Square getSquare()
	{
		return this.square;
	}
	public void setSquare(Square s)
	{
		this.square = s;
	}
	abstract public String getName();
}
