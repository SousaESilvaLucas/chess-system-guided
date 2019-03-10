package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	public Pawn(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "P";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position upNorth = new Position(position.getRow() -2, position.getColumn());
		Position north = new Position(position.getRow() -1, position.getColumn());
		Position northEast = new Position(position.getRow() -1, position.getColumn()+1);
		Position northWest = new Position(position.getRow() -1, position.getColumn()-1);
		Position downSouth = new Position(position.getRow() +2, position.getColumn());
		Position south = new Position(position.getRow() +1, position.getColumn());
		Position southEast = new Position(position.getRow() +1, position.getColumn()+1);
		Position southWest = new Position(position.getRow() +1, position.getColumn()-1);
		
		if (getColor() == Color.WHITE) {
			
			if (getBoard().positionExists(north) && !getBoard().isThereAPiece(north)) {
				mat[north.getRow()][north.getColumn()] = true;
			}
			
			if (getBoard().positionExists(northEast) && isThereOpponentPiece(northEast)) {
				mat[northEast.getRow()][northEast.getColumn()] = true;
			}
			
			if (getBoard().positionExists(northWest) && isThereOpponentPiece(northWest)) {
				mat[northWest.getRow()][northWest.getColumn()] = true;
			}
			
			if (getBoard().positionExists(upNorth)
					&& !getBoard().isThereAPiece(upNorth)
					&& !getBoard().isThereAPiece(north)
					&& getMoveCount() == 0) {
				mat[upNorth.getRow()][upNorth.getColumn()] = true;
			}
		}
		
		if (getColor() == Color.BLACK) {
			if (getBoard().positionExists(south) && !getBoard().isThereAPiece(south)) {
				mat[south.getRow()][south.getColumn()] = true;
			}
			
			if (getBoard().positionExists(southEast) && isThereOpponentPiece(southEast)) {
				mat[southEast.getRow()][southEast.getColumn()] = true;
			}
			
			if (getBoard().positionExists(southWest) && isThereOpponentPiece(southWest)) {
				mat[southWest.getRow()][southWest.getColumn()] = true;
			}
			
			if (getBoard().positionExists(downSouth)
					&& !getBoard().isThereAPiece(downSouth)
					&& !getBoard().isThereAPiece(south)
					&& getMoveCount() == 0) {
				mat[downSouth.getRow()][downSouth.getColumn()] = true;
			}
		}
		
		
		return mat;
	}

}
