package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		return getBoard().positionExists(position)
				&& (!getBoard().isThereAPiece(position)|| isThereOpponentPiece(position));
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		/* The king can move to the 8 tiles that surround him.
		* If his source position is (x0,y0), his possible movement (x,y) is every combination
		of (x0 +- 1, y0 +- 1)*/
		
		int sourceRow = this.position.getRow();
		int sourceColumn = this.position.getColumn();
		Position nextPosition = new Position(0,0);
		
		/* South Movement */
		nextPosition.setValues(sourceRow + 1, sourceColumn);
		if (canMove(nextPosition)) {
			mat [nextPosition.getRow()][nextPosition.getColumn()] = true;
		}
		
		/* North Movement */
		nextPosition.setValues(sourceRow - 1, sourceColumn);
		if (canMove(nextPosition)) {
			mat [nextPosition.getRow()][nextPosition.getColumn()] = true;
		}
		
		/* West Movement */
		nextPosition.setValues(sourceRow, sourceColumn -1);
		if (canMove(nextPosition)) {
			mat [nextPosition.getRow()][nextPosition.getColumn()] = true;
		}
		
		/* East Movement*/
		nextPosition.setValues(sourceRow, sourceColumn +1);
		if (canMove(nextPosition)) {
			mat [nextPosition.getRow()][nextPosition.getColumn()] = true;
		}
		
		/* Northeast Movement*/
		nextPosition.setValues(sourceRow -1, sourceColumn +1);
		if (canMove(nextPosition)) {
			mat [nextPosition.getRow()][nextPosition.getColumn()] = true;
		}
		
		/* Northwest Movement*/
		nextPosition.setValues(sourceRow -1, sourceColumn -1);
		if (canMove(nextPosition)) {
			mat [nextPosition.getRow()][nextPosition.getColumn()] = true;
		}
		
		/* Southeast Movement*/
		nextPosition.setValues(sourceRow + 1, sourceColumn +1);
		if (canMove(nextPosition)) {
			mat [nextPosition.getRow()][nextPosition.getColumn()] = true;
		}
		
		/* Southwest Movement*/
		nextPosition.setValues(sourceRow + 1, sourceColumn -1);
		if (canMove(nextPosition)) {
			mat [nextPosition.getRow()][nextPosition.getColumn()] = true;
		}
		
		return mat;
	}
}
