package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	private int turn;
	private Color currentPlayer;

	public ChessMatch(){
		
		board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	private void nextTurn() {
		turn ++;
		if (currentPlayer == Color.WHITE) {
			currentPlayer = Color.BLACK;
		}
		else {
			currentPlayer = Color.WHITE;
		}
			
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
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		
		/* First step: Check if sourcePosition and targetPosition exists */
		validateSourcePosition(source);
		
		/* Second step: Check if chessPiece is in sourcePosition */
		
		/* Third step: Check if the target position can be occupied (i.e.
		 * Checks if it is not occupied or if it is, if chessPiece can capture the piece */
		validateTargetPosition (target, board.piece(source));
		
		/* Fourth step: remove sourcePiece from sourcePosition, 
		 * remove targetPiece from targetPosition, add chessPiece to targetPosition */
		Piece capturedPiece = makeMove(source, target);
		nextTurn();
		/* Fifth step: Update chessPiece position */
		
		/* Sixth step: Return targetPiece (if there was a captured piece)*/
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position sourcePosition, Position targetPosition) {
		Piece p = board.removePiece(sourcePosition);
		Piece capturedPiece = board.removePiece(targetPosition);
		board.placePiece(p, targetPosition);
		return capturedPiece;
	}
	private void validateSourcePosition(Position position) {
		if (!board.isThereAPiece(position)) {
			throw new ChessException("There's no chess piece at source position.");
		}
		
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There are no possible moves for the chosen piece.");
		}
		if (((ChessPiece) (board.piece(position))).getColor() != currentPlayer) {
			throw new ChessException("That's not your piece!");
		}
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position source = sourcePosition.toPosition();
		validateSourcePosition(source);
		return board.piece(source).possibleMoves();
	}
	
	private void validateTargetPosition (Position position, Piece piece) {
		boolean [][] mat = piece.possibleMoves();
		if (!mat[position.getRow()][position.getColumn()]) {
			throw new ChessException("Illegal move");
		}
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
