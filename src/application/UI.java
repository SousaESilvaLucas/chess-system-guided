package application;

import chess.ChessPiece;

public class UI {
	
	public static void printBoard(ChessPiece[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			System.out.print(8 - i + " ");
			for (int j = 0; j < mat[0].length; j++) {
				if (mat[i][j] != null) {
					System.out.print(mat[i][j] + " ");
				}
				else {
					System.out.print("- ");
				}
			}
			System.out.println();
		}

		System.out.print("  a b c d e f g h");
	}
}
