
public class MenuController {

	public void newGame () {
		System.out.println("Selected: New Game");
		WarehouseBoss game = new WarehouseBoss();
		game.play();
	}
	
	public void loadGame() {
		System.out.println("Selected: Load Game");
	}
	
	public void settings() {
		System.out.println("Selected: Settings");
	}
	
	public void exit() {
		System.exit(0);
	}
}
