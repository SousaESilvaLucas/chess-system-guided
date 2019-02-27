package boardgame;

public abstract class Piece {

	protected Position position;
	private Board board;
	
	public Piece (Board board) { 
		/* Why no position in constructor?
		 * When created, a piece was not yet placed on the board, thus it does
		 * not yet have a position. It gains a position only after it is placed
		 * on the board by the Board method placePiece*/
		this.board = board;
	}

	protected Board getBoard() {
		return board;
	}
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean [][] mat = possibleMoves();
		
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
