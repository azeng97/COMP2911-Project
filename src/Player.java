/**
 * Player class, handles movement
 * @author Avan
 *
 */
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
			m.setPushed(true);
			return newSquare.addEntity(this);			
		}
		else if (newSquare.isEmpty())
		{
			return newSquare.addEntity(this);
		}
		return false;
	}
	
	public void undoMove(Move m)
	{
		int direction1 = m.getDirection();
		Position boxPos = (Position) square.getPosition().adjacentPos(direction1);
		int direction = Position.reverseDirection(m.getDirection());
		Position newPos = (Position) square.getPosition().adjacentPos(direction);
		Square boxSquare = game.squareAt(boxPos);
		Square newSquare = game.squareAt(newPos);
		newSquare.addEntity(this);
		if (m.getPushed() == true) boxSquare.pushContents(direction);
	}
	public String getName()
	{
		return "Player";
	}
}
