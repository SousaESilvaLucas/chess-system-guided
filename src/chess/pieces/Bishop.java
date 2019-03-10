package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece{

	public Bishop(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position NEdirection = new Position(position.getRow() - 1, position.getColumn() + 1);
		Position NWdirection = new Position(position.getRow() - 1, position.getColumn() - 1);
		Position SEdirection = new Position(position.getRow() + 1, position.getColumn() + 1);
		Position SWdirection = new Position(position.getRow() + 1, position.getColumn() - 1);
		
		/* Northeast direction*/
		while(getBoard().positionExists(NEdirection) && !getBoard().isThereAPiece(NEdirection)) {
			mat[NEdirection.getRow()][NEdirection.getColumn()] = true;
			NEdirection.setValues(NEdirection.getRow() - 1, NEdirection.getColumn() + 1);
		}
		
		if (getBoard().positionExists(NEdirection) && isThereOpponentPiece(NEdirection)) {
			mat[NEdirection.getRow()][NEdirection.getColumn()] = true;
		}
		
		/* Northwest direction*/
		while(getBoard().positionExists(NWdirection) && !getBoard().isThereAPiece(NWdirection)) {
			mat[NWdirection.getRow()][NWdirection.getColumn()] = true;
			NWdirection.setValues(NWdirection.getRow() - 1, NWdirection.getColumn() - 1);
		}
		
		if (getBoard().positionExists(NWdirection) && isThereOpponentPiece(NWdirection)) {
			mat[NWdirection.getRow()][NWdirection.getColumn()] = true;
		}
		
		/* Southeast direction*/
		while(getBoard().positionExists(SEdirection) && !getBoard().isThereAPiece(SEdirection)) {
			mat[SEdirection.getRow()][SEdirection.getColumn()] = true;
			SEdirection.setValues(SEdirection.getRow() + 1, SEdirection.getColumn() + 1);
		}
		
		if (getBoard().positionExists(SEdirection) && isThereOpponentPiece(SEdirection)) {
			mat[SEdirection.getRow()][SEdirection.getColumn()] = true;
		}
		
		/* Southwest direction*/
		while(getBoard().positionExists(SWdirection) && !getBoard().isThereAPiece(SWdirection)) {
			mat[SWdirection.getRow()][SWdirection.getColumn()] = true;
			SWdirection.setValues(SWdirection.getRow() + 1, SWdirection.getColumn() - 1);
		}
		
		if (getBoard().positionExists(SWdirection) && isThereOpponentPiece(SWdirection)) {
			mat[SWdirection.getRow()][SWdirection.getColumn()] = true;
		}
		
		return mat;
	}

}
