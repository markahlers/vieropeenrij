/**
 * 
 */
package net.markahlers.connect4;

/**
 * @author mark
 *
 */
public class Board {

	/* Define the size of the board */
	static final int ROWS = 6;
	static final int COLUMNS = 7;
	protected static final Cell[][] PLAYINGBOARD = new Cell[ROWS][COLUMNS];
	private int currentRow;
	private int currentColumn;

	/**
	 * Constructor
	 */
	public Board() {
		createBoard();
	}

	/**
	 * Creates the board
	 */
	private static void createBoard() {
		/* Loop through the rows */
		for (int row = 0; row < ROWS; ++row) {
			/* Loop through the columns in the row */
			for (int column = 0; column < COLUMNS; ++column) {
				PLAYINGBOARD[row][column] = new CellImpl(row, column);
			}
		}
	}

	/**
	 * Resets the board so all cells are empty
	 */
	public void clearBoard() {

		/* Loop through the rows */
		for (int row = 0; row < ROWS; ++row) {
			/* Loop through the columns in the row */
			for (int column = 0; column < COLUMNS; ++column) {
				/* clear the cell/ Set the cell to empty */
				PLAYINGBOARD[row][column].clear();
			}
		}

	}

	/**
	 * Inserts a new token and makes sure it 'falls down to the last empty cell'.
	 * @param column The column the user has chosen to put the token into.
	 */
	public void insertNewToken(int column, CellValue currentPlayer) throws FullColumnException {
		/*
		 * Check which row the chip would be in for the selected column We remove 1 from
		 * the column, as the columns start at 0. Same for the row.
		 */
		int row = ROWS - 1;
		while (row >= 0) {
			if (PLAYINGBOARD[row][column - 1].isEmpty()) {
				PLAYINGBOARD[row][column - 1].setCellValue(currentPlayer);
				currentRow = row;
				currentColumn = column - 1;
				/* since we found one and claimed it, exit the loop */
				break;
			}
			/* remove one of the row */
			--row;
			if (row < 0)
				throw new FullColumnException("Row already full");
		}

	}

	/** Print the game board */
	public void showBoard() {		
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLUMNS; ++col) {
				printCell(PLAYINGBOARD[row][col].getCellValue()); // print each of the cells
				if (col != COLUMNS - 1) {
					System.out.print("|"); // print vertical partition
				}
			}
			System.out.println();
			if (row != ROWS - 1) {
				System.out.println("---------------------------"); // print horizontal partition				
			}
		}
		System.out.println(" 1   2   3   4   5   6   7 "); // print column number partition
		System.out.println();
	}

	/** Print a cell with the specified "content" */
	public static void printCell(CellValue value) {
		switch (value) {
		case EMPTY:
			System.out.print("   ");
			break;
		case PLAYERRED:
			System.out.print(" R ");
			break;
		case PLAYERBLUE:
			System.out.print(" B ");
			break;
		}
	}

	/**
	 * Checks if there is a winner.
	 * @param currentPlayer The current player
	 * @return boolean won(true) or no winner yet (false)
	 */
	public boolean hasWon(CellValue currentPlayer) {
		return (checkHorizontal(currentPlayer) || checkVertical(currentPlayer) || checkDiagonal(currentPlayer)) ;
	}

	/**
	 * Checks if there are four of the same kind in the row
	 * 
	 * @return boolean true if there is a win, otherwise false
	 */
	private boolean checkHorizontal(CellValue currentPlayer) {
		int totalCount = 0;
		for (int column = 0; column < COLUMNS; ++column) {
			if (PLAYINGBOARD[currentRow][column].getCellValue() == currentPlayer)
				++totalCount;
			else
				totalCount = 0;

			/* If we get 4 in a row, we have a winner */
			if (totalCount == 4)
				return true;
		}
		return false;
	}

	/**
	 * Checks if there are four of the same kind in the column
	 * 
	 * @return boolean true if there is a win, otherwise false
	 */
	private boolean checkVertical(CellValue currentPlayer) {

		int totalCount = 0;
		/*
		 * loop through the rows to see if there are 4 in a row (or column in this case)
		 */
		for (int row = 0; row < ROWS; ++row) {
			if (PLAYINGBOARD[row][currentColumn].getCellValue() == currentPlayer)
				++totalCount;
			else
				totalCount = 0;

			/* If we get 4 in a row, we have a winner */
			if (totalCount == 4)
				return true;
		}

		return false;
	}

	/**
	 * Checks if there is a winner diagonally.
	 * @param currentPlayer the current player
	 * @return boolean true if there is a winner, false if there is none yet.
	 */
	private boolean checkDiagonal(CellValue currentPlayer) {		

		/* Check diagonal from left top to right bottom and from right top to left bottom */
		return ( checkLeftTopToBottom(currentPlayer) || checkRightTopToBottom(currentPlayer) );
	}

	/**
	 * Checks for a winner diagonally from right top to the left bottom
	 * 
	 * @return boolean if there is a winner found, returns true, otherwise false
	 */
	private boolean checkRightTopToBottom(CellValue currentPlayer) {
		int startingRow = 0;
		int totalCount = 0;
		int startingColumn = currentColumn + currentRow;
		
		if (startingColumn >= COLUMNS) {
			startingRow = startingColumn - (COLUMNS -1);
			startingColumn = COLUMNS - 1;
		}

		for (int row = startingRow; row < ROWS; ++row) {
			
			if (startingColumn < 0)
				continue;
			if (PLAYINGBOARD[row][startingColumn].getCellValue() == currentPlayer) {
				++totalCount;
			} else {
				totalCount = 0;
			}
			
			if (totalCount == 4)
				return true;

			--startingColumn;
		}
		return false;
	}

	/**
	 * Checks for a winner diagonally from left top to the right bottom
	 * 
	 * @return boolean if there is a winner found, returns true, otherwise false
	 */
	private boolean checkLeftTopToBottom(CellValue currentPlayer) {

		int startingRow = 0;
		int totalCount = 0;
		int startingColumn = currentColumn - currentRow;
		if (startingColumn < 0) {
			startingRow = startingColumn * -1;
			startingColumn = 0;
		}

		for (int row = startingRow; row < ROWS; ++row) {
			
			if (startingColumn > (COLUMNS - 1))
				continue;
			if (PLAYINGBOARD[row][startingColumn].getCellValue() == currentPlayer) {
				++totalCount;
			} else {
				totalCount = 0;
			}
			
			if (totalCount == 4)
				return true;

			++startingColumn;
		}
		return false;
	}

	/**
	 * Checks if there are any empty cells. If not, it assumed it is a draw. Make
	 * sure to call method hasWon before this, so it isn't actually a win.
	 * 
	 * @return boolean if it is a draw (true) or not (false).
	 */
	public boolean isDraw() {
		for (int row = 0; row < ROWS; ++row) {
			for (int column = 0; column < COLUMNS; ++column) {
				if (PLAYINGBOARD[row][column].getCellValue() == CellValue.EMPTY)
					return false;
			}
		}
		return true;
	}

}
