package chess.pieces;

import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	private ChessMatch chessMatch;
	
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
		boolean [][] mat = possibleCaptures();
		
		/* Special movement: castling  */
		if (getColor() == Color.WHITE) {
			if (CanCastleKingsideWhite()) {
				mat[position.getRow()][position.getColumn() + 2] = true;
			}
			
			if (CanCastleQueensideWhite()) {
				mat[position.getRow()][position.getColumn() - 2] = true;
			}
		}
		
		if (getColor() == Color.BLACK) {
			if (CanCastleKingsideBlack()) {
				mat[position.getRow()][position.getColumn() + 2] = true;
			}
			
			if (CanCastleQueensideBlack()) {
				mat[position.getRow()][position.getColumn() - 2] = true;
			}
		}
		
		return mat;
	}
	
	public boolean possibleCapture(Position position) {
		return possibleCaptures()[position.getRow()][position.getColumn()];
	}
	
	public boolean[][] possibleCaptures() {
		
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		/* The king can move to the 8 tiles that surround him.
		* If his source position is (x0,y0), his possible capture (x,y) is every combination
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
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	public boolean CanCastleKingsideWhite() {
		/* https://en.wikipedia.org/wiki/Castling */	
		/* Condition 1: The king and the chosen rook are on the player's first rank. 
		 * Condition 2: Neither the king nor the chosen rook has previously moved. */
		
		Position f1 = new Position(7,5);
		Position g1 = new Position(7,6);
		ChessPiece whiteKingRook = chessMatch.getPieces()[7][7];
				
		if (whiteKingRook instanceof Rook) {
			whiteKingRook = (Rook) whiteKingRook;	
		}
		else {
			return false; // if the piece is not a rook, then the rook has moved
		}
		
		boolean condition1and2 = this.getMoveCount() == 0 
				&& whiteKingRook.getMoveCount() == 0
				&& whiteKingRook instanceof Rook;
		
		/* Condition 3: There are no pieces between the king and the chosen rook. */
		boolean condition3 = !getBoard().isThereAPiece(f1)
				&& !getBoard().isThereAPiece(g1);
		
		/* Condition 4: The king is not currently in check. */
		boolean condition4 = !chessMatch.getCheck();
		
		/* Condition 5: The king does not pass through a square that is attacked 
		 * by an enemy piece. */
		boolean condition5 = true;
		List<ChessPiece> opponentsPieces = chessMatch.getPiecesOnTheBoard().stream()
				.filter(x -> x.getColor() == opponent(this.getColor()))
				.collect(Collectors.toList());
		for (ChessPiece p : opponentsPieces) {
			if (p instanceof King) {
				if (((King)p).possibleCaptures()[f1.getRow()][f1.getColumn()]) {
					condition5 = false;
				}
			}
			else {
				if (p.possibleMove(f1)) {
					condition5 = false;
				}
			}
		}
		/* Condition 6: The king does not end up in check. 
		 * Since this should be true of any legal move, this is already done. */
		
		return condition1and2
				&& condition3
				&& condition4
				&& condition5;
	}
	
	public boolean CanCastleQueensideWhite() {
		/* https://en.wikipedia.org/wiki/Castling */	
		/* Condition 1: The king and the chosen rook are on the player's first rank. 
		 * Condition 2: Neither the king nor the chosen rook has previously moved. */
		
		Position b1 = new Position(7,1);
		Position c1 = new Position(7,2);
		Position d1 = new Position(7,3);
		ChessPiece whiteQueenRook = chessMatch.getPieces()[7][0];
				
		if (whiteQueenRook instanceof Rook) {
			whiteQueenRook = (Rook) whiteQueenRook;	
		}
		else {
			return false; // if the piece is not a rook, then the rook has moved
		}
		
		boolean condition1and2 = this.getMoveCount() == 0 
				&& whiteQueenRook.getMoveCount() == 0
				&& whiteQueenRook instanceof Rook;
		
		/* Condition 3: There are no pieces between the king and the chosen rook. */
		boolean condition3 = !getBoard().isThereAPiece(b1) 
				&& !getBoard().isThereAPiece(c1)
				&& !getBoard().isThereAPiece(d1);
		
		/* Condition 4: The king is not currently in check. */
		boolean condition4 = !chessMatch.getCheck();
		
		/* Condition 5: The king does not pass through a square that is attacked 
		 * by an enemy piece. */
		boolean condition5 = true;
		List<ChessPiece> opponentsPieces = chessMatch.getPiecesOnTheBoard().stream()
				.filter(x -> x.getColor() == opponent(this.getColor()))
				.collect(Collectors.toList());
		for (ChessPiece p : opponentsPieces) {
			if (p instanceof King) {
				if (((King)p).possibleCaptures()[d1.getRow()][d1.getColumn()]) {
					condition5 = false;
				}
			}
			else {
				if (p.possibleMove(d1)) {
					condition5 = false;
				}
			}
		}
		/* Condition 6: The king does not end up in check. 
		 * Since this should be true of any legal move, this is already done. */
		
		return condition1and2
				&& condition3
				&& condition4
				&& condition5;
	}
	
	public boolean CanCastleKingsideBlack() {
		/* https://en.wikipedia.org/wiki/Castling */	
		/* Condition 1: The king and the chosen rook are on the player's first rank. 
		 * Condition 2: Neither the king nor the chosen rook has previously moved. */
		
		Position f8 = new Position(0,5);
		Position g8 = new Position(0,6);
		ChessPiece blackKingRook = chessMatch.getPieces()[0][7];
				
		if (blackKingRook instanceof Rook) {
			blackKingRook = (Rook) blackKingRook;	
		}
		else {
			return false; // if the piece is not a rook, then the rook has moved
		}
		
		boolean condition1and2 = this.getMoveCount() == 0 
				&& blackKingRook.getMoveCount() == 0
				&& blackKingRook instanceof Rook;
		
		/* Condition 3: There are no pieces between the king and the chosen rook. */
		boolean condition3 = !getBoard().isThereAPiece(f8)
				&& !getBoard().isThereAPiece(g8);
		
		/* Condition 4: The king is not currently in check. */
		boolean condition4 = !chessMatch.getCheck();
		
		/* Condition 5: The king does not pass through a square that is attacked 
		 * by an enemy piece. */
		boolean condition5 = true;
		List<ChessPiece> opponentsPieces = chessMatch.getPiecesOnTheBoard().stream()
				.filter(x -> x.getColor() == opponent(this.getColor()))
				.collect(Collectors.toList());
		for (ChessPiece p : opponentsPieces) {
			if (p instanceof King) {
				if (((King)p).possibleCaptures()[f8.getRow()][f8.getColumn()]) {
					condition5 = false;
				}
			}
			else {
				if (p.possibleMove(f8)) {
					condition5 = false;
				}
			}
		}
		/* Condition 6: The king does not end up in check. 
		 * Since this should be true of any legal move, this is already done. */
		
		return condition1and2
				&& condition3
				&& condition4
				&& condition5;
	}
	
	public boolean CanCastleQueensideBlack() {
		/* https://en.wikipedia.org/wiki/Castling */	
		/* Condition 1: The king and the chosen rook are on the player's first rank. 
		 * Condition 2: Neither the king nor the chosen rook has previously moved. */
		
		Position b8 = new Position(0,1);
		Position c8 = new Position(0,2);
		Position d8 = new Position(0,3);
		ChessPiece blackQueenRook = chessMatch.getPieces()[0][0];
				
		if (blackQueenRook instanceof Rook) {
			blackQueenRook = (Rook) blackQueenRook;	
		}
		else {
			return false; // if the piece is not a rook, then the rook has moved
		}
		
		boolean condition1and2 = this.getMoveCount() == 0 
				&& blackQueenRook.getMoveCount() == 0
				&& blackQueenRook instanceof Rook;
		
		/* Condition 3: There are no pieces between the king and the chosen rook. */
		boolean condition3 = !getBoard().isThereAPiece(b8)
				&& !getBoard().isThereAPiece(c8)
				&& !getBoard().isThereAPiece(d8);
		
		/* Condition 4: The king is not currently in check. */
		boolean condition4 = !chessMatch.getCheck();
		
		/* Condition 5: The king does not pass through a square that is attacked 
		 * by an enemy piece. */
		boolean condition5 = true;
		List<ChessPiece> opponentsPieces = chessMatch.getPiecesOnTheBoard().stream()
				.filter(x -> x.getColor() == opponent(this.getColor()))
				.collect(Collectors.toList());
		for (ChessPiece p : opponentsPieces) {
			if (p instanceof King) {
				if (((King)p).possibleCaptures()[d8.getRow()][d8.getColumn()]) {
					condition5 = false;
				}
			}
			else {
				if (p.possibleMove(d8)) {
					condition5 = false;
				}
			}
		}
		/* Condition 6: The king does not end up in check. 
		 * Since this should be true of any legal move, this is already done. */
		
		return condition1and2
				&& condition3
				&& condition4
				&& condition5;
	}
}
