
public class Output {
	public Output(WarehouseBoss g, Board b)
	{
		this.game = g;
		this.b = b;
	}
	public void printBoard()
	{
		b.printBoard();
		System.out.println("Number of undos: " + game.nUndos);
		System.out.println("Number of moves: " + game.totalMoves);
		System.out.println("Size of Move History: " + game.moveHistory.size());
	}
	private WarehouseBoss game;
	private Board b;
}
