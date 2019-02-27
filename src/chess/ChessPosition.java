package chess;

import boardgame.Position;

public class ChessPosition {

	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error on intanciation of the"
					+ "ChessPosition object. Valid values are from a1 to h8.");
		}
		else {
			this.column = column;
			this.row = row;
		}
		
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	@Override
	public String toString() {
		return  "("+ column + row +")";
	}
	
	protected Position toPosition() {
		// Parses ChessPosition to Position
		return new Position(8 - row, column - 'a');
		
	}
	
	protected static ChessPosition fromPosition(Position position) {
		// Parses Position to Chess Position
		return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
	}
}
