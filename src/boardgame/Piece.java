package boardgame;

public class Piece {

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
	
	
}
