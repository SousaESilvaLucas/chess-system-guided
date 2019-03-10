package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece{

	public Queen(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Q";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position NorthDirection = new Position(position.getRow() - 1, position.getColumn());
		Position SouthDirection = new Position(position.getRow() + 1, position.getColumn());
		Position EastDirection = new Position(position.getRow(), position.getColumn() + 1);
		Position WestDirection = new Position(position.getRow(), position.getColumn() - 1);

		
		Position NEdirection = new Position(position.getRow() - 1, position.getColumn() + 1);
		Position NWdirection = new Position(position.getRow() - 1, position.getColumn() - 1);
		Position SEdirection = new Position(position.getRow() + 1, position.getColumn() + 1);
		Position SWdirection = new Position(position.getRow() + 1, position.getColumn() - 1);
		
		/* North direction */
		while(getBoard().positionExists(NorthDirection) && !getBoard().isThereAPiece(NorthDirection)) {
			mat[NorthDirection.getRow()][NorthDirection.getColumn()] = true;
			NorthDirection.setValues(NorthDirection.getRow() - 1, NorthDirection.getColumn());
		}
		
		if (getBoard().positionExists(NorthDirection) && isThereOpponentPiece(NorthDirection)) {
			mat[NorthDirection.getRow()][NorthDirection.getColumn()] = true;
		}
		
		/* South direction */
		while(getBoard().positionExists(SouthDirection) && !getBoard().isThereAPiece(SouthDirection)) {
			mat[SouthDirection.getRow()][SouthDirection.getColumn()] = true;
			SouthDirection.setValues(SouthDirection.getRow() + 1, SouthDirection.getColumn());
		}
		
		if (getBoard().positionExists(SouthDirection) && isThereOpponentPiece(SouthDirection)) {
			mat[SouthDirection.getRow()][SouthDirection.getColumn()] = true;
		}
		
		/* East direction */
		while(getBoard().positionExists(EastDirection) && !getBoard().isThereAPiece(EastDirection)) {
			mat[EastDirection.getRow()][EastDirection.getColumn()] = true;
			EastDirection.setValues(EastDirection.getRow(), EastDirection.getColumn() + 1);
		}
		
		if (getBoard().positionExists(EastDirection) && isThereOpponentPiece(EastDirection)) {
			mat[EastDirection.getRow()][EastDirection.getColumn()] = true;
		}
		
		/* West direction */
		while(getBoard().positionExists(WestDirection) && !getBoard().isThereAPiece(WestDirection)) {
			mat[WestDirection.getRow()][WestDirection.getColumn()] = true;
			WestDirection.setValues(WestDirection.getRow(), WestDirection.getColumn() - 1);
		}
		
		if (getBoard().positionExists(WestDirection) && isThereOpponentPiece(WestDirection)) {
			mat[WestDirection.getRow()][WestDirection.getColumn()] = true;
		}
		
		/* Northeast direction */
		while(getBoard().positionExists(NEdirection) && !getBoard().isThereAPiece(NEdirection)) {
			mat[NEdirection.getRow()][NEdirection.getColumn()] = true;
			NEdirection.setValues(NEdirection.getRow() - 1, NEdirection.getColumn() + 1);
		}
		
		if (getBoard().positionExists(NEdirection) && isThereOpponentPiece(NEdirection)) {
			mat[NEdirection.getRow()][NEdirection.getColumn()] = true;
		}
		
		/* Northwest direction */
		while(getBoard().positionExists(NWdirection) && !getBoard().isThereAPiece(NWdirection)) {
			mat[NWdirection.getRow()][NWdirection.getColumn()] = true;
			NWdirection.setValues(NWdirection.getRow() - 1, NWdirection.getColumn() - 1);
		}
		
		if (getBoard().positionExists(NWdirection) && isThereOpponentPiece(NWdirection)) {
			mat[NWdirection.getRow()][NWdirection.getColumn()] = true;
		}
		
		/* Southeast direction */
		while(getBoard().positionExists(SEdirection) && !getBoard().isThereAPiece(SEdirection)) {
			mat[SEdirection.getRow()][SEdirection.getColumn()] = true;
			SEdirection.setValues(SEdirection.getRow() + 1, SEdirection.getColumn() + 1);
		}
		
		if (getBoard().positionExists(SEdirection) && isThereOpponentPiece(SEdirection)) {
			mat[SEdirection.getRow()][SEdirection.getColumn()] = true;
		}
		
		/* Southwest direction */
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
