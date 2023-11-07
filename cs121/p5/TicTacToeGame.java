import java.awt.Point;
/**
 * 
 * @author Steven Meyers
 * @Date Srping 2019
 * Sets up a playable Tic Tac Toe game that is accessible through the TicTacToeGUI. 
 */
public class TicTacToeGame implements TicTacToe{
	private Player[][] gameBoard;
	private Player currentPlayer;
	private Winner gameWinner;
	private Point[] moves;
	private int movesNextIndex;
	
	/*
	 * Calls the new game method which creates a new game.
	 */
	public TicTacToeGame() {
		newGame();
	}

	@Override
	public void newGame() {
		//Create a new game.
		gameBoard = new Player[3][3];
		for(int i = 0; i < 3; i ++) {
			for(int j = 0; j < 3; j ++) {
				gameBoard[i][j] = Player.OPEN;
			}
		}
		currentPlayer = Player.X;
		gameWinner = Winner.IN_PROGRESS;
		moves = new Point[9];
		movesNextIndex = 0;
	}

	@Override
	public boolean gameOver() {
		// X Winner Tests Horizontal Lines
		if(gameBoard[0][0] == Player.X && gameBoard[0][1] == Player.X && gameBoard[0][2] == Player.X) {
			gameWinner = Winner.X;
			return true;
		} else if(gameBoard[1][0] == Player.X && gameBoard[1][1] == Player.X && gameBoard[1][2] == Player.X) {
			gameWinner = Winner.X;
			return true;
		} else if(gameBoard[2][0] == Player.X && gameBoard[2][1] == Player.X && gameBoard[2][2] == Player.X) {
			gameWinner = Winner.X;
			return true;

			// X Winner Tests Vertical Lines
		} else if(gameBoard[0][0] == Player.X && gameBoard[1][0] == Player.X && gameBoard[2][0] == Player.X) {
			gameWinner = Winner.X;
			return true;
		} else if(gameBoard[0][1] == Player.X && gameBoard[1][1] == Player.X && gameBoard[2][1] == Player.X) {
			gameWinner = Winner.X;
			return true;
		} else if(gameBoard[0][2] == Player.X && gameBoard[1][2] == Player.X && gameBoard[2][2] == Player.X) {
			gameWinner = Winner.X;
			return true;

			// X Winner Tests Diagonal
		} else if(gameBoard[0][0] == Player.X && gameBoard[1][1] == Player.X && gameBoard[2][2] == Player.X) {
			gameWinner = Winner.X;
			return true;
		} else if(gameBoard[0][2] == Player.X && gameBoard[1][1] == Player.X && gameBoard[2][0] == Player.X) {
			gameWinner = Winner.X;
			return true;

			// O Winner Tests Vertical
		} else if(gameBoard[0][0] == Player.O && gameBoard[0][1] == Player.O && gameBoard[0][2] == Player.O) {
			gameWinner = Winner.O;
			return true;
		} else if(gameBoard[1][0] == Player.O && gameBoard[1][1] == Player.O && gameBoard[1][2] == Player.O) {
			gameWinner = Winner.O;
			return true;
		} else if(gameBoard[2][0] == Player.O && gameBoard[2][1] == Player.O && gameBoard[2][2] == Player.O) {
			gameWinner = Winner.O;
			return true;

			// O Winner Tests Horizontal
		} else if(gameBoard[0][0] == Player.O && gameBoard[1][0] == Player.O && gameBoard[2][0] == Player.O) {
			gameWinner = Winner.O;
			return true;
		} else if(gameBoard[0][1] == Player.O && gameBoard[1][1] == Player.O && gameBoard[2][1] == Player.O) {
			gameWinner = Winner.O;
			return true;
		} else if(gameBoard[0][2] == Player.O && gameBoard[1][2] == Player.O && gameBoard[2][2] == Player.O) {
			gameWinner = Winner.O;
			return true;

			// O Winner Tests Diagonal
		} else if(gameBoard[0][0] == Player.O && gameBoard[1][1] == Player.O && gameBoard[2][2] == Player.O) {
			gameWinner = Winner.O;
			return true;
		} else if(gameBoard[0][2] == Player.O && gameBoard[1][1] == Player.O && gameBoard[2][0] == Player.O) {
			gameWinner = Winner.O;
			return true;
			
			//Winner is a tie
		} else if(gameBoard[0][0] != Player.OPEN && gameBoard[0][1] != Player.OPEN && gameBoard[0][2] != Player.OPEN && 
				gameBoard[1][0] != Player.OPEN && gameBoard[1][1] != Player.OPEN && gameBoard[1][2] != Player.OPEN && 
				gameBoard[2][0] != Player.OPEN && gameBoard[2][1] != Player.OPEN && gameBoard[2][2] != Player.OPEN) {
			gameWinner = Winner.TIE;
			return true;
		}else {
			gameWinner = Winner.IN_PROGRESS;
			return false;
		}
	}

	@Override
	public Winner winner() {
		gameOver();
		return gameWinner;
	}

	@Override
	public Player[][] getGameGrid() {
		//Create a game grid copy
		Player[][] gameGridClone = new Player[3][3];
		
		//Fill copy of game grid.
		for(int i = 0; i < 3; i ++) {
			for(int j = 0; j < 3; j ++) {
				gameGridClone[i][j] = gameBoard[i][j];
			}
		}
		return gameGridClone;
	}

	@Override
	public Point[] getMoves() {
		//Create a moves copy.
		Point[] copyMoves = new Point[movesNextIndex];
		
		//Fill the moves copy with points.
		for (int i = 0; i < copyMoves.length; i++) {
			copyMoves[i] = moves[i];
		}
		return copyMoves;
	}

	@Override
	public boolean choose(Player player, int row, int col) {
		boolean validMove = false;
		
		//Makes sure that the row and column are in limits.
		if(row < 3 && col < 3 && row > -1 && col > -1) {
			if(gameBoard[row][col] == Player.OPEN && !gameOver()) {
				gameBoard[row][col] = player;
				moves[movesNextIndex] = new Point(row, col);
				validMove = true;
				movesNextIndex++;
			}
		}

		return validMove;
	}

}
