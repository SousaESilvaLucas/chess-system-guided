package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "N";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position upLeftDirection = new Position(position.getRow() - 2, position.getColumn() - 1);
		Position leftUpDirection = new Position(position.getRow() - 1, position.getColumn() - 2);
		Position upRightDirection = new Position(position.getRow() - 2, position.getColumn() + 1);
		Position rightUpDirection = new Position(position.getRow() - 1, position.getColumn() + 2);
		
		Position downLeftDirection = new Position(position.getRow() + 2, position.getColumn() - 1);
		Position leftDownDirection = new Position(position.getRow() + 1, position.getColumn() - 2);
		Position downRightDirection = new Position(position.getRow() + 2, position.getColumn() + 1);
		Position rightDownDirection = new Position(position.getRow() + 1, position.getColumn() + 2);

		if (getBoard().positionExists(upLeftDirection)
				&& (!getBoard().isThereAPiece(upRightDirection)
						|| isThereOpponentPiece(upLeftDirection))) {
			mat[upLeftDirection.getRow()][upLeftDirection.getColumn()] = true;
		}
		
		if (getBoard().positionExists(leftUpDirection)
				&& (!getBoard().isThereAPiece(leftUpDirection)
						|| isThereOpponentPiece(leftUpDirection))) {
			mat[leftUpDirection.getRow()][leftUpDirection.getColumn()] = true;
		}

		if (getBoard().positionExists(upRightDirection)
				&& (!getBoard().isThereAPiece(upRightDirection)
						|| isThereOpponentPiece(upRightDirection))) {
			mat[upRightDirection.getRow()][upRightDirection.getColumn()] = true;
		}
		
		if (getBoard().positionExists(rightUpDirection)
				&& (!getBoard().isThereAPiece(rightUpDirection)
						|| isThereOpponentPiece(rightUpDirection))) {
			mat[rightUpDirection.getRow()][rightUpDirection.getColumn()] = true;
		}
		
		if (getBoard().positionExists(downLeftDirection)
				&& (!getBoard().isThereAPiece(downLeftDirection)
						|| isThereOpponentPiece(downLeftDirection))) {
			mat[downLeftDirection.getRow()][downLeftDirection.getColumn()] = true;
		}
		
		if (getBoard().positionExists(leftDownDirection)
				&& (!getBoard().isThereAPiece(leftDownDirection)
						|| isThereOpponentPiece(leftDownDirection))) {
			mat[leftDownDirection.getRow()][leftDownDirection.getColumn()] = true;
		}

		if (getBoard().positionExists(downRightDirection)
				&& (!getBoard().isThereAPiece(downRightDirection)
						|| isThereOpponentPiece(downRightDirection))) {
			mat[downRightDirection.getRow()][downRightDirection.getColumn()] = true;
		}
		
		if (getBoard().positionExists(rightDownDirection)
				&& (!getBoard().isThereAPiece(rightDownDirection)
						|| isThereOpponentPiece(rightDownDirection))) {
			mat[rightDownDirection.getRow()][rightDownDirection.getColumn()] = true;
		}
		
		return mat;
	}
	

}
