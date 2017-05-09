
public class Player extends Entity{
	public Player(Square s, WarehouseBoss g)
	{
		super(s,g);
	}
	
	public boolean makeMove(Move m)
	{
		int direction = m.getDirection();
		Position newPos = (Position) square.getPosition().adjacentPos(direction);
		
		Square newSquare = game.squareAt(newPos);
		if(newSquare.pushContents(direction))
		{
			return newSquare.addEntity(this);
		}
		else if (newSquare.isEmpty())
		{
			return newSquare.addEntity(this);
		}
		return false;
	}
	
}
