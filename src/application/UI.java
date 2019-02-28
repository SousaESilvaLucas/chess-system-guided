package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String chessString = sc.next();
			char chessColumn = chessString.charAt(0); 
			int chessRow = Integer.parseInt(chessString.substring(1));
			return new ChessPosition(chessColumn, chessRow);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Invalid Input. The input must "
					+ "be a value from a1 to h8");
		}
	}
	
	public static void printMatch(ChessMatch chessMatch) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());
		System.out.println("Waiting player " + chessMatch.getCurrentPlayer());
	}
	
	public static void printBoard(ChessPiece[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			System.out.print(8 - i + " ");
			for (int j = 0; j < mat[0].length; j++) {
				printPiece(mat[i][j]);
			}
			System.out.println();
		}

		System.out.println("  a b c d e f g h");
	}
	
	public static void printPiece(ChessPiece chessPiece) {
		if (chessPiece != null) {
			if (chessPiece.getColor() == Color.BLACK) {
				System.out.print(ANSI_YELLOW + chessPiece + ANSI_RESET);
			}
			else {
				System.out.print(ANSI_WHITE + chessPiece + ANSI_RESET);
			}
			
		} 
		else {
			System.out.print("-" + ANSI_RESET);
		}
		
		System.out.print(" ");
	}
	
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(8 - i + " ");
			for (int j = 0; j < pieces[0].length; j++) {
				if (possibleMoves[i][j]) {
					System.out.print(ANSI_PURPLE_BACKGROUND);
					printPiece(pieces[i][j]);
				}
				else {
					printPiece(pieces[i][j]);
				}
				
			}
			System.out.println();
		}

		System.out.println("  a b c d e f g h");
		
	}
}
