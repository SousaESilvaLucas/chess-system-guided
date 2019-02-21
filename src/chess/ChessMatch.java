package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	
	public ChessMatch(){
		
		board = new Board(8,8);
		initialSetup();
	}

	public ChessPiece[][] getPieces() {
		ChessPiece [][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	private void placeNewPiece(ChessPiece chessPiece, char column, int row) {
		ChessPosition chessPosition = new ChessPosition(column, row);
		board.placePiece(chessPiece, chessPosition.toPosition());
	}
	
	private void initialSetup() {
		placeNewPiece(new King(board, Color.WHITE), 'e', 1);
		placeNewPiece(new Rook(board, Color.WHITE), 'a', 1);
		placeNewPiece(new Rook(board, Color.WHITE), 'h', 1);
		
		placeNewPiece(new King(board, Color.BLACK), 'e', 8);
		placeNewPiece(new Rook(board, Color.BLACK), 'a', 8);
		placeNewPiece(new Rook(board, Color.BLACK), 'h', 8);
		
		
	}
}
