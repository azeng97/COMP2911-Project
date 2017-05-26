
public class Move {
	public Move(int direction)
	{
		this.direction = direction;
	}
	public static int keyToDirection(String move)
	{
		int dir = -1;
		switch (move)
		{
		case "w":
			System.out.println("up");
			dir = North;
			break;
		case "a":
			System.out.println("left");
			dir = West;
			break;
		case "s":
			System.out.println("down");
			dir = South;
			break;
		case "d":
			System.out.println("right");
			dir = East;
			break;
		default:
			System.out.println("Invalid Move!");
		}
	return dir;
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
	
	public static final int North = 0;
	public static final int East = 1;
	public static final int South = 2;
	public static final int West = 3;
}
