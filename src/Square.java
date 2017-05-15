/**
 * Square object representing one grid space on the board
 * stores information about position and contents of the grid spot
 * @author Avan
 *
 */
public class Square extends Object 
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
			boolean wasTarget = (e.getSquare() instanceof Target);
			e.getSquare().removeContents();
			contents = e;
			e.setSquare(this);
			
			if (this instanceof Target && e instanceof Box && !wasTarget)
			{
				game.decrementTargets();
				System.out.println("Remaining targets: " + game.emptyTargets());
			} 
			else if (!(this instanceof Target) && e instanceof Box && wasTarget)
			{
				game.incrementTargets();
				System.out.println("Remaining targets: " + game.emptyTargets());
			}
		}
		return false;
	}
	public boolean isEmpty()
	{
		return (contents==null);
	}
	public boolean canPush(int direction)
	{
		Square neighbour = game.squareAt((Position)position.adjacentPos(direction));
		return (contents != null && neighbour != null && neighbour.isEmpty());
	}
	public Position getPosition()
	{
		return position;
	}
	
	public Entity getContents()
	{
		return this.contents;
	}
	public boolean pushContents(int direction)
	{
		if(!canPush(direction))
		{
			return false;
		}
		Square neighbour = game.squareAt((Position)position.adjacentPos(direction));
		neighbour.addEntity(contents);
		
		return true;
	}
	public void removeContents()
	{
		contents = null;
	}
	protected Position position;
	protected WarehouseBoss game;
	protected Entity contents;
}
