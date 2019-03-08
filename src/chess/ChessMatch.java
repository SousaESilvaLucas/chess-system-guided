package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	private int turn;
	private Color currentPlayer;
	private List <ChessPiece> piecesOnTheBoard;
	private List <ChessPiece> capturedPieces;
	private boolean check;
	private boolean checkMate;

	public ChessMatch(){
		
		board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		capturedPieces = new ArrayList<>();
		piecesOnTheBoard = new ArrayList<>();
		initialSetup();
	}

	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public List<ChessPiece> getCapturedPieces(){
		return capturedPieces;
	}
	
	public List<ChessPiece> getPiecesOnTheBoard(){
		return piecesOnTheBoard;
	}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		return piecesOnTheBoard.stream()
		.filter(x -> x.toString() == "K" && x.getColor() == color)
		.collect(Collectors.toList()).get(0);
	}
	
	private boolean testCheck(Color color) {
		ChessPiece PlayerKing = king(color);
		ChessPosition kingPosition = PlayerKing.getChessPosition();
		List<ChessPiece> opponentsPieces = piecesOnTheBoard.stream()
		.filter(x -> x.getColor() == opponent(color))
		.collect(Collectors.toList());
		
		for (ChessPiece p : opponentsPieces) {
			if (p.possibleMove(kingPosition.toPosition())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate (Color color) {
		List<ChessPiece> PlayerPieces = piecesOnTheBoard.stream()
		.filter(x -> x.getColor() == color)
		.collect(Collectors.toList());
		Position targetPosition = new Position(0,0);
		
		if (testCheck(color)) {
			for (ChessPiece p : PlayerPieces) {
				boolean [][] mat = p.possibleMoves();
				for (int i = 0; i < mat.length; i++) {
					for (int j = 0; j < mat[0].length; j++) {
						if (mat[i][j]) {
							targetPosition.setValues(i, j);
							Position sourcePosition = p.getChessPosition().toPosition();
							Piece capturedPiece = makeMove(sourcePosition, targetPosition);
							if (!testCheck(color)) {
								undoMove(sourcePosition, targetPosition, capturedPiece);
								return false;
							}
							undoMove(sourcePosition, targetPosition, capturedPiece);		
						}
					}
				}
			}
			return true;
		}
		else {
			return false;
		}
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
		piecesOnTheBoard.add(chessPiece);
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		Boolean isPlayerInCheck = check;
		/* First step: Check if sourcePosition and targetPosition exists */
		validateSourcePosition(source);
		
		/* Second step: Check if chessPiece is in sourcePosition */
		
		/* Third step: Check if the target position can be occupied (i.e.
		 * Checks if it is not occupied or if it is, if chessPiece can capture the piece */
		validateTargetPosition (target, board.piece(source));
		
		/* Fourth step: remove sourcePiece from sourcePosition, 
		 * remove targetPiece from targetPosition, add chessPiece to targetPosition 
		 * Test if the player did not put themselves in check */
		Piece capturedPiece = makeMove(source, target);
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			if (isPlayerInCheck) {
				throw new ChessException("You are still in check!");
			}
			else {
				throw new ChessException("You cannot put yourself in check!");
			}
			
		}
		
		check = testCheck(opponent(currentPlayer)) ? true : false;
		
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
		/* Fifth step: Update chessPiece position */
		
		/* Sixth step: Return targetPiece (if there was a captured piece)*/
		return (ChessPiece) capturedPiece;
		
		
	}
	
	private Piece makeMove(Position sourcePosition, Position targetPosition) {
		ChessPiece p = (ChessPiece) board.removePiece(sourcePosition);
		Piece capturedPiece = board.removePiece(targetPosition);
		board.placePiece(p, targetPosition);
		p.increaseMoveCount();
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add((ChessPiece) capturedPiece);
		}
		return capturedPiece;
	}
	
	private void undoMove(Position sourcePosition, Position targetPosition, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(targetPosition);
		board.placePiece(p, sourcePosition);
		p.decreaseMoveCount();
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, targetPosition);
			piecesOnTheBoard.add((ChessPiece) capturedPiece);
			capturedPieces.remove(capturedPiece);
		}
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
