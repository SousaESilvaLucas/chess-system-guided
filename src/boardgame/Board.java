package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error while creating board: there must be at "
					+ "least 1 row and 1 column");
		}
		else {
			this.rows = rows;
			this.columns = columns;
			this.pieces = new Piece[rows][columns];
		}
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if (positionExists(row,column)) {
			return pieces[row][column];
			}
		else {
			throw new BoardException("Error:"
					+ "This board position doesn't exist!");
		}
	}
	
	public Piece piece(Position position) {
		if (positionExists(position)) {
			return pieces[position.getRow()][position.getColumn()];
		}
		else {
			throw new BoardException("Error:"
					+ "This board position doesn't exist!");
		}
		
	}
	
	public void placePiece(Piece piece, Position position) {	
		if (isThereAPiece(position)) {
			throw new BoardException("Error:"
					+ "This board position "+ position +" is already occupied!");
		}
		else {
			pieces[position.getRow()][position.getColumn()] = piece;
			piece.position = position;
		}
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean isThereAPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Error:"
					+ "This board position doesn't exist!");
		}
		else {
			return piece(position) != null;  
		}
		
	}
	
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Error:"
					+ "This board position doesn't exist!");
		}
		
		else if (!isThereAPiece(position)) {
			return null;
		}
		else {
			Piece aux =  pieces[position.getRow()][position.getColumn()];
			pieces[position.getRow()][position.getColumn()] = null;
			return aux;
		}

	}
}
