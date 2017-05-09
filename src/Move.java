
public class Move {
	public Move(int direction)
	{
		this.direction = direction;
	}
	public static int keyToDirection(int move)
	{
	//TODO convert keystroke to direction
	return 0;
	}
	public int getDirection()
	{
		return direction;
	}
	public Entity getPushed()
	{
		return pushed;
	}
	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	public void setPushed(Entity e)
	{
		this.pushed = e;
	}
	private int direction;
	private Entity pushed;
}
