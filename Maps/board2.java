public class board2 {
	public static void main(String[] args) {
		/* ' ' - Empty
		 * # - Storage
		 * $ - Box
		 * . - Target 
		 * @ - Human
		 */
		int level = 8;
		int boardLength = (12 + (int)(Math.random() * (level)))%19;
		int boardWidth = (12 + (int)(Math.random() * (level)))%19;
		int boardBoxes = boardLength*boardWidth-1;
		int difficulty = 0;
		if (level>10){difficulty++;} /* convert from easy to difficult */
		
		String[][] board = new String[boardLength][boardWidth];
		
		/* fill array with spaces */
		for (int temp=0; temp < boardWidth; temp++){
			for (int temp2=0; temp2 < boardLength; temp2++){
				board[temp2][temp]=" ";
			}
		}
		
		/* fill edges with boxes */
		for(int temp=0; temp<boardWidth; temp++){
			 board[0][temp]="#";
			 board[boardLength-1][temp]="#";
		}
		for(int temp=0; temp<(boardLength-1); temp++){
			 board[temp][0]="#";
			 board[temp][boardWidth-1]="#";
		}
		
		/* insert more boxes at random */
		/* move back one if not in first position of row (as it loops back at stays as 1 in that case) */
		for (int temp = 0; temp < level; temp++){
			int a = (int)(Math.random() * (boardLength));
			int b = (int)(Math.random() * (boardWidth));
			while (board[a][b] != "#"){
				board[a][b] = "#";
				int randNum = (int)(Math.random() * (3 + 1));
				if (randNum == 0){a++;}
				if (randNum == 1){a--;}
				if (randNum == 2){b++;}
				if (randNum == 3){b--;}
			}
		}
		
		/* insert boxes to move */
		int numBoxesToMove = 2;
		int added = 0;
		if (difficulty == 1) {numBoxesToMove=numBoxesToMove*2;};
		while(added < numBoxesToMove){
			int randNum = (int)(Math.random() * (boardBoxes + 1));
			int insertAtRow = randNum/boardWidth;
			int insertAtCol = randNum%boardWidth;
			if (board[insertAtRow][insertAtCol] == " "){
				int surrounding=0;
				if (board[insertAtRow][insertAtCol-1] != " ") {surrounding++;}
				if (board[insertAtRow][insertAtCol+1] != " ") {surrounding++;}
				if (board[insertAtRow-1][insertAtCol] != " ") {surrounding++;}
				if (board[insertAtRow+1][insertAtCol] != " ") {surrounding++;}
				if (surrounding == 0){
					board[insertAtRow][insertAtCol] = "$";
					added++;
				}
			}
		}
		
		/* insert target areas */
		int target = numBoxesToMove;
		added = 0;

		while(added < target){
			int randNum = (int)(Math.random() * (boardBoxes + 1));
			int insertAtRow = randNum/boardWidth;
			int insertAtCol = randNum%boardWidth;
			if (board[insertAtRow][insertAtCol] == " "){
				int surrounding=0;
				if (board[insertAtRow][insertAtCol-1] != " ") {surrounding++;}
				if (board[insertAtRow][insertAtCol+1] != " ") {surrounding++;}
				if (board[insertAtRow-1][insertAtCol] != " ") {surrounding++;}
				if (board[insertAtRow+1][insertAtCol] != " ") {surrounding++;}
				if (surrounding < 2){
					board[insertAtRow][insertAtCol] = ".";
					added++;
				}
			}
		}
		
		/* insert human */
		int living = 0;
		while (living == 0){
			int randNum = (int)(Math.random() * (boardBoxes + 1));
			int insertAtRow = randNum/boardWidth;
			int insertAtCol = randNum%boardWidth;
			if (board[insertAtRow][insertAtCol] == " "){
				int surrounding=0;
				if (board[insertAtRow][insertAtCol-1] != " ") {surrounding++;}
				if (board[insertAtRow][insertAtCol+1] != " ") {surrounding++;}
				if (board[insertAtRow-1][insertAtCol] != " ") {surrounding++;}
				if (board[insertAtRow+1][insertAtCol] != " ") {surrounding++;}
				if (surrounding < 2){
					board[insertAtRow][insertAtCol] = "@";
					living++;
				}
			}
		}
		
		
		System.out.println(boardLength);
		System.out.println(boardWidth);
		for (int temp=0; temp < boardWidth; temp++){
			for (int temp2=0; temp2 < boardLength; temp2++){
				System.out.print(board[temp2][temp]);
			}
			System.out.print("\n");
		}
	}
}
