/**
 * 
 */
package net.markahlers.connect4;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Vier op een Rij is a connect4 game the Dutch way. It's console based.
 * 
 * @author mark
 * 
 */
public class Connect4 {

	private CellValue currentPlayer; // Active player, PLAYERRED or PLAYERYELLOW
	private GameState currentGameState; // current state the game is in. @see net.markahlers.vieropeenrij.GameState

	private Board playingBoard; // The playing board
	private Scanner inputScanner = new Scanner(System.in);
	private static final String RED = "Red";
	private static final String BLUE = "Blue";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		showLogo();
		Connect4 vierOpEenRij = new Connect4();
		vierOpEenRij.play();
		System.out.println("Thank you for playing.");
	}

	public static void showLogo() {
		clearConsole();
		System.out.println(" ____________________________ ");
		System.out.println("/                            \\");
		System.out.println("| V I E R  O P  E E N  R I J |");
		System.out.println("\\____________________________/");
		System.out.println("");
		System.out.println("(c)2018");
		System.out.println("");
	}

	private static void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	/**
	 * Refreshes the console so it keeps the board in the same place.
	 */
	private void refreshConsole() {
		clearConsole();
		showLogo();
		playingBoard.showBoard();
	}

	/**
	 * Constructor
	 */
	Connect4() {
		/* Create the board */
		playingBoard = new Board();
		/* Set the initial values */
		currentPlayer = CellValue.EMPTY; // Player is not yet chosen, so will be empty
		currentGameState = GameState.NOTSTARTED; // Game is not yet started, so will have the state not started.
	}

	/**
	 * Start the game Set the game state to playing and set the current player to
	 * red, as red begins the game.
	 */
	private void play() {
		initializePlaySequence();
		/* Keep doing the play sequence until there is a draw or a win */
		while (currentGameState == GameState.PLAYING) {
			try {
				doPlaySequence();
			} catch (FullColumnException e) {
				/* the column is full, so the sequence has to be restarted. */
				System.out.println(e.getMessage());
			}
		}
		playAgain();
	}

	/**
	 * Checks if the user wants to play again and calls the play method to restart
	 * the game.
	 */
	private void playAgain() {
		System.out.println("");
		System.out.println("Play again? (Y/N)");
		String input = inputScanner.next();
		if (input.equalsIgnoreCase("Y"))
			play();
		else if (!input.equalsIgnoreCase("N")) {
			System.out.println("No valid input received");
			playAgain();
		}
	}

	/**
	 * Sets the gamestate to playing, sets the initial player to Red and clears the
	 * board.
	 */
	private void initializePlaySequence() {
		currentGameState = GameState.PLAYING;
		currentPlayer = CellValue.PLAYERRED;
		playingBoard.clearBoard();
	}

	/**
	 * Handles the actual turn.
	 * 
	 * @throws Exception
	 */
	private void doPlaySequence() throws FullColumnException {
		int input = 0;
		int column = 0;
		refreshConsole();
		while (column == 0) {
			column = 0;
			try {
				/* Ask for input in the console */
				System.out.println(getPlayerName(currentPlayer) + "'s turn. Choose a column (1-7)");
				input = inputScanner.nextInt();
				if (input > 0 && input <= Board.COLUMNS)
					column = input;
				else
					System.out.println("incorrect column");
			} catch (InputMismatchException ime) {
				System.out.println("That's not a number.");
				/* skip the current character and go to the next */
				inputScanner.next();
			}
		}

		/* Do the move */
		playingBoard.insertNewToken(column, currentPlayer);

		/* Check for win */
		checkResult();
		/* Change to the other player */
		changePlayer();
	}

	/**
	 * Checks the result of the move to see if there is a win or a draw.
	 */
	private void checkResult() {
		if (playingBoard.hasWon(currentPlayer)) {
			refreshConsole();
			System.out.println(".----------------------.");
			switch (currentPlayer) {
			case PLAYERRED:
				currentGameState = GameState.REDWON;
				System.out.println("|      " + RED + " WON!        |");
				break;
			case PLAYERBLUE:
				currentGameState = GameState.BLUEWON;
				System.out.println("|      " + BLUE + " WON!       |");
				break;
			default:
				break;
			}
			System.out.println("'----------------------'");
		} else if (playingBoard.isDraw()) {
			refreshConsole();
			currentGameState = GameState.DRAW;
			System.out.println(".----------------------.");
			System.out.println("|    It's a DRAW!      |");
			System.out.println("'----------------------'");
		}
	}

	/**
	 * Changes to the other player, or to RED if not set.
	 */
	private void changePlayer() {
		switch (currentPlayer) {
		case PLAYERRED:
			currentPlayer = CellValue.PLAYERBLUE;
			break;
		case PLAYERBLUE:
			currentPlayer = CellValue.PLAYERRED;
			break;
		default:
			currentPlayer = CellValue.PLAYERRED;
		}
	}

	/**
	 * Returns the colour of the player.
	 * 
	 * @param currentPlayer
	 * @return String for the player name.
	 */
	private String getPlayerName(CellValue currentPlayer) {
		switch (currentPlayer) {
		case PLAYERRED:
			return RED;

		case PLAYERBLUE:
			return BLUE;

		default:
			return RED;
		}
	}

}
