/**
 * Square object representing one grid space on the board
 * stores information about position and contents of the grid spot
 * @author Avan
 *
 */
public class Square 
{
	public Square(Position p, WarehouseBoss g)
	{
		this.position = p;
		this.game = g;
	}
	public boolean addEntity(Entity e)
	{
		if (isEmpty())
		{
			boolean was
		}
	}
	public boolean isEmpty()
	{
		return (contents==null);
	}
	public boolean canPush(int direction)
	{
		Square neighbour = game.squareAt(position.adjacentPos(direction));
		return (contents != null && neighbour != null && neighbour.isEmpty());
	}
	public Position getPosition()
	{
		return position;
	}
	
	public boolean pushContents(int direction)
	{
		if(!canPush(direction))
		{
			return false;
		}
		Square neighbour = game.squareAt(position.adjacentPos(direction));
		neighbour.addEntity(contents);
		
		return true;
	}
	public void removeContents()
	{
		contents = null;
		this.display();
	}
	protected Position position;
	protected WarehouseBoss game;
	protected Entity contents;
}
