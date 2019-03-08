package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(this.position);
	}
	
	protected boolean isThereOpponentPiece(Position position) {
		if (!getBoard().isThereAPiece(position)) {
			return false;
		}
		else {
			ChessPiece other = (ChessPiece) getBoard().piece(position);
			return (this.color != other.getColor());
		}
	}
	
}
