package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.Knight;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Queen;
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
			if (p instanceof King) {
				/* I think this is impossible, a king cannot put another king in check */
				if (((King)p).possibleCapture(kingPosition.toPosition())) {
					return true;
				}
					
			}
			else {
				if (p.possibleMove(kingPosition.toPosition())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean testCheckMate (Color color) {
		List<ChessPiece> PlayerPieces = piecesOnTheBoard.stream()
		.filter(x -> x.getColor() == color)
		.collect(Collectors.toList());
		//Position testPosition = new Position(0,0);
		
		if (testCheck(color)) {
			for (ChessPiece p : PlayerPieces) {
				boolean [][] mat;
				if (p instanceof King) {
					mat = ((King) p).possibleCaptures();
				}
				else {
					mat = p.possibleMoves();
				}
				for (int i = 0; i < mat.length; i++) {
					for (int j = 0; j < mat[0].length; j++) {
						if (mat[i][j]) {
							Position testPosition = new Position(i, j);
							Position sourcePosition = p.getChessPosition().toPosition();
							Piece capturedPiece = makeMove(sourcePosition, testPosition);
							if (!testCheck(color)) {
								undoMove(sourcePosition, testPosition, capturedPiece);
								return false;
							}
							undoMove(sourcePosition, testPosition, capturedPiece);		
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
		
		Position a1 = new Position(7,0);	// Rook starts here before queenside castle
		Position c1 = new Position(7,2);	// King ends here after queenside castle
		Position d1 = new Position(7,3);	// Rook ends here after queenside castle
		Position e1 = new Position(7,4);	// King starts here before any castle
		Position f1 = new Position(7,5);	// Rook ends here after kingside castle
		Position g1 = new Position(7,6);	// King ends here after kingside castle
		Position h1 = new Position(7,7);	// Rook starts here before kingside castle
		
		Position a8 = new Position(0,0);	// Rook starts here before queenside castle
		Position c8 = new Position(0,2);	// King ends here after queenside castle
		Position d8 = new Position(0,3);	// Rook ends here after queenside castle
		Position e8 = new Position(0,4);	// King starts here before any castle
		Position f8 = new Position(0,5);	// Rook ends here after kingside castle
		Position g8 = new Position(0,6);	// King ends here after kingside castle
		Position h8 = new Position(0,7);	// Rook starts here before kingside castle
		
		ChessPiece q = (ChessPiece) board.piece(sourcePosition);
		Piece capturedPiece = null;
		
			/* Special move: castle king side (white)*/
			if (q instanceof King 
					&& q.getColor() == Color.WHITE
					&& sourcePosition.getRow() == e1.getRow()
					&& sourcePosition.getColumn() == e1.getColumn()
					&& targetPosition.getRow() ==  g1.getRow()
					&& targetPosition.getColumn() == g1.getColumn()
					&& q.possibleMove(targetPosition)) {
				ChessPiece king = (ChessPiece) board.removePiece(sourcePosition);
				ChessPiece rook = (ChessPiece) board.removePiece(h1);
				board.placePiece(king, targetPosition);
				board.placePiece(rook, f1);
				king.increaseMoveCount();
				rook.increaseMoveCount();
			}
			/* Special move: castle queen side (white)*/
			else if (q instanceof King 
					&& q.getColor() == Color.WHITE
					&& sourcePosition.getRow() == e1.getRow()
					&& sourcePosition.getColumn() == e1.getColumn()
					&& targetPosition.getRow() ==  c1.getRow()
					&& targetPosition.getColumn() == c1.getColumn() 
					&& q.possibleMove(targetPosition)) {
				ChessPiece king = (ChessPiece) board.removePiece(sourcePosition);
				ChessPiece rook = (ChessPiece) board.removePiece(a1);
				board.placePiece(king, targetPosition);
				board.placePiece(rook, d1);
				king.increaseMoveCount();
				rook.increaseMoveCount();
			}
			
			/* Special move: castle king side (black)*/
			else if (q instanceof King 
					&& q.getColor() == Color.BLACK
					&& sourcePosition.getRow() == e8.getRow()
					&& sourcePosition.getColumn() == e8.getColumn()
					&& targetPosition.getRow() ==  g8.getRow()
					&& targetPosition.getColumn() == g8.getColumn()
					&& q.possibleMove(targetPosition)) {
				ChessPiece king = (ChessPiece) board.removePiece(sourcePosition);
				ChessPiece rook = (ChessPiece) board.removePiece(h8);
				board.placePiece(king, targetPosition);
				board.placePiece(rook, f8);
				king.increaseMoveCount();
				rook.increaseMoveCount();
			}
			
			/* Special move: castle queen side (black)*/
			else if (q instanceof King 
					&& q.getColor() == Color.BLACK
					&& sourcePosition.getRow() == e8.getRow()
					&& sourcePosition.getColumn() == e8.getColumn()
					&& targetPosition.getRow() ==  c8.getRow()
					&& targetPosition.getColumn() == c8.getColumn() 
					&& q.possibleMove(targetPosition)) {
				ChessPiece king = (ChessPiece) board.removePiece(sourcePosition);
				ChessPiece rook = (ChessPiece) board.removePiece(a8);
				board.placePiece(king, targetPosition);
				board.placePiece(rook, d8);
				king.increaseMoveCount();
				rook.increaseMoveCount();
			}
			
			else {
				ChessPiece p = (ChessPiece) board.removePiece(sourcePosition);
				capturedPiece = board.removePiece(targetPosition);
				board.placePiece(p, targetPosition);
				p.increaseMoveCount();
				if (capturedPiece != null) {
					piecesOnTheBoard.remove(capturedPiece);
					capturedPieces.add((ChessPiece) capturedPiece);
				}
			}
		return capturedPiece;
	}
	
	private void undoMove(Position sourcePosition, Position targetPosition, Piece capturedPiece) {
		
		Position a1 = new Position(7,0);	// Rook starts here before queenside castle
		Position c1 = new Position(7,2);	// King ends here after queenside castle
		Position d1 = new Position(7,3);	// Rook ends here after queenside castle
		Position e1 = new Position(7,4);	// King starts here before any castle
		Position f1 = new Position(7,5);	// Rook ends here after kingside castle
		Position g1 = new Position(7,6);	// King ends here after kingside castle
		Position h1 = new Position(7,7);	// Rook starts here before kingside castle
		
		Position a8 = new Position(0,0);	// Rook starts here before queenside castle
		Position c8 = new Position(0,2);	// King ends here after queenside castle
		Position d8 = new Position(0,3);	// Rook ends here after queenside castle
		Position e8 = new Position(0,4);	// King starts here before any castle
		Position f8 = new Position(0,5);	// Rook ends here after kingside castle
		Position g8 = new Position(0,6);	// King ends here after kingside castle
		Position h8 = new Position(0,7);	// Rook starts here before kingside castle
		
		ChessPiece q = (ChessPiece) board.piece(targetPosition);
		
			/* Special move: castle king side (white)*/
			if (q instanceof King 
					&& q.getColor() == Color.WHITE
					&& sourcePosition.getRow() == e1.getRow()
					&& sourcePosition.getColumn() == e1.getColumn()
					&& targetPosition.getRow() ==  g1.getRow()
					&& targetPosition.getColumn() == g1.getColumn()) {
				ChessPiece king = (ChessPiece) board.removePiece(targetPosition);
				ChessPiece rook = (ChessPiece) board.removePiece(f1);
				board.placePiece(king, sourcePosition);
				board.placePiece(rook, h1);
				king.decreaseMoveCount();
				rook.decreaseMoveCount();
			}
			/* Special move: castle queen side (white)*/
			else if (q instanceof King 
					&& q.getColor() == Color.WHITE
					&& sourcePosition.getRow() == e1.getRow()
					&& sourcePosition.getColumn() == e1.getColumn()
					&& targetPosition.getRow() ==  c1.getRow()
					&& targetPosition.getColumn() == c1.getColumn()) {
				ChessPiece king = (ChessPiece) board.removePiece(targetPosition);
				ChessPiece rook = (ChessPiece) board.removePiece(d1);
				board.placePiece(king, sourcePosition);
				board.placePiece(rook, a1);
				king.decreaseMoveCount();
				rook.decreaseMoveCount();
			}
			
			/* Special move: castle king side (black)*/
			else if (q instanceof King 
					&& q.getColor() == Color.BLACK
					&& sourcePosition.getRow() == e8.getRow()
					&& sourcePosition.getColumn() == e8.getColumn()
					&& targetPosition.getRow() ==  g8.getRow()
					&& targetPosition.getColumn() == g8.getColumn()) {
				ChessPiece king = (ChessPiece) board.removePiece(targetPosition);
				ChessPiece rook = (ChessPiece) board.removePiece(f8);
				board.placePiece(king, sourcePosition);
				board.placePiece(rook, h8);
				king.decreaseMoveCount();
				rook.decreaseMoveCount();
			}
			
			/* Special move: castle queen side (black)*/
			else if (q instanceof King 
					&& q.getColor() == Color.BLACK
					&& sourcePosition.getRow() == e8.getRow()
					&& sourcePosition.getColumn() == e8.getColumn()
					&& targetPosition.getRow() ==  c8.getRow()
					&& targetPosition.getColumn() == c8.getColumn()) {
				ChessPiece king = (ChessPiece) board.removePiece(targetPosition);
				ChessPiece rook = (ChessPiece) board.removePiece(d8);
				board.placePiece(king, sourcePosition);
				board.placePiece(rook, a8);
				king.decreaseMoveCount();
				rook.decreaseMoveCount();
			}
			
			else {
				ChessPiece p = (ChessPiece) board.removePiece(targetPosition);
				board.placePiece(p, sourcePosition);
				p.decreaseMoveCount();
				if (capturedPiece != null) {
					board.placePiece(capturedPiece, targetPosition);
					piecesOnTheBoard.add((ChessPiece) capturedPiece);
					capturedPieces.remove(capturedPiece);
				}
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
		
		placeNewPiece(new Rook(board, Color.WHITE), 'a', 1);
		placeNewPiece(new Knight(board, Color.WHITE), 'b', 1);
		placeNewPiece(new Bishop(board, Color.WHITE), 'c', 1);
		placeNewPiece(new Queen(board, Color.WHITE), 'd', 1);
		placeNewPiece(new King(board, Color.WHITE, this), 'e', 1);
		placeNewPiece(new Bishop(board, Color.WHITE), 'f', 1);
		placeNewPiece(new Knight(board, Color.WHITE), 'g', 1);
		placeNewPiece(new Rook(board, Color.WHITE), 'h', 1);
		
		placeNewPiece(new Rook(board, Color.BLACK), 'a', 8);
		placeNewPiece(new Knight(board, Color.BLACK), 'b', 8);
		placeNewPiece(new Bishop(board, Color.BLACK), 'c', 8);
		placeNewPiece(new Queen(board, Color.BLACK), 'd', 8);
		placeNewPiece(new King(board, Color.BLACK, this), 'e', 8);
		placeNewPiece(new Bishop(board, Color.BLACK), 'f', 8);
		placeNewPiece(new Knight(board, Color.BLACK), 'g', 8);
		placeNewPiece(new Rook(board, Color.BLACK), 'h', 8);
		
		for (char x = 'a'; x <= 'h'; x++) {
			placeNewPiece(new Pawn(board, Color.WHITE), x, 2);
			placeNewPiece(new Pawn(board, Color.BLACK), x, 7);
		}
		
		
		
		
		
		
	}
}
