package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position aux = new Position(0,0);
		int sourceRow = this.position.getRow();
		int sourceColumn = this.position.getColumn();
		
		
		/*Down movement*/
		boolean foundPiece = false;
		int i = sourceRow + 1; // The piece can't move in place
		
		while (i < mat.length && foundPiece == false) {	
			aux.setValues(i, sourceColumn);
			if (!getBoard().isThereAPiece(aux)) {
				mat[i][sourceColumn] = true;
			}
			else if (isThereOpponentPiece(aux)) {
				mat[i][sourceColumn] = true;
				foundPiece = true;
			}
			else {
				foundPiece = true;
			}
			i++;
		}
		
		/*Up movement*/
		foundPiece = false;
		i = sourceRow - 1; // The piece can't move in place
		
		while (i >= 0 && foundPiece == false) {	
			aux.setValues(i, sourceColumn);
			if (!getBoard().isThereAPiece(aux)) {
				mat[i][sourceColumn] = true;
			}
			else if (isThereOpponentPiece(aux)) {
				mat[i][sourceColumn] = true;
				foundPiece = true;
			}
			else {
				foundPiece = true;
			}
			i--;
		}
		
		/*Right movement*/
		foundPiece = false;
		int j = sourceColumn + 1; // The piece can't move in place
		
		while (j < mat[0].length && foundPiece == false) {	
			aux.setValues(sourceRow, j);
			if (!getBoard().isThereAPiece(aux)) {
				mat[sourceRow][j] = true;
			}
			else if (isThereOpponentPiece(aux)) {
				mat[sourceRow][j] = true;
				foundPiece = true;
			}
			else {
				foundPiece = true;
			}
			j++;
		}
		
		/*Left movement*/
		foundPiece = false;
		j = sourceColumn -1; // The piece can't move in place
		
		while (j >= 0 && foundPiece == false) {	
			aux.setValues(sourceRow, j);
			if (!getBoard().isThereAPiece(aux)) {
				mat[sourceRow][j] = true;
			}
			else if (isThereOpponentPiece(aux)) {
				mat[sourceRow][j] = true;
				foundPiece = true;
			}
			else {
				foundPiece = true;
			}
			j--;
		}
		
		return mat;
	}
	
}
