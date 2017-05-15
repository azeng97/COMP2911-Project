
public class Output {
	public Output(WarehouseBoss g, Board b)
	{
		this.game = g;
		this.b = b;
	}
	public void printBoard()
	{
		b.printBoard();
	}
	private WarehouseBoss game;
	private Board b;
}
