import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {
		//Creates a 2D array of doubles that is null
		char[][] circuitBoard = null;
					
		//creates the scanner for the file
		Scanner scan = new Scanner(new File(filename));
					
		// Scans the x and y values and creates a double array grid using them as the array sizes.
		if(scan.hasNextInt()) {
			int x = scan.nextInt();
			ROWS = x;
		} else {
			scan.close();
			throw new InvalidFileFormatException("X value input mismatched"); 
		}
		if(scan.hasNextInt()) {
			int y = scan.nextInt();
			COLS = y;	
		} else {
			scan.close();
			throw new InvalidFileFormatException("Y value input mismatched");
		}
		
		circuitBoard = new char[ROWS][COLS];
		boolean startDeclared = false;
		boolean endDeclared = false;
		// Scans in the values to the created double array.
		for(int i = 0; i < circuitBoard.length; i++) {
			for(int j = 0; j < circuitBoard[i].length; j++) {
				//Check immediately to see if the file has too little elements before trying to add a new element
				if(!scan.hasNext() && j != COLS && i != ROWS) {
					scan.close();
					throw new InvalidFileFormatException("Too little elements overall");
				}
				//Add the new character
				circuitBoard[i][j] = scan.next().charAt(0);
				
				//Check for the exceptions of invalid characters or if the start/ end has been declared multiple times
				if(ALLOWED_CHARS.indexOf(circuitBoard[i][j]) < 0) {
					scan.close();
					throw new InvalidFileFormatException("One of the characters in this file is a unapproved character.");
				} 
				
				//check to see if a start has been declared yet, if not and start is declared, set true
				if(circuitBoard[i][j] == START) {
					if(startDeclared == false) {
						startDeclared = true;
						startingPoint = new Point(i, j);
					} else {
						scan.close();
						throw new InvalidFileFormatException("Start has already been declared");
					}
					
					//check to see if an end has been declared yet, if not and end is declared, set true
				} else if(circuitBoard[i][j] == END) {
					if(endDeclared == false) {
						endDeclared = true;
						endingPoint = new Point(i, j);
					} else {
						scan.close();
						throw new InvalidFileFormatException("End has already been declared");
					}
				}
			}
		}
		//If either start or end are not declared by the end of the file being scanned, throw exception
		if(startDeclared == false || endDeclared == false) {
			scan.close();
			throw new InvalidFileFormatException("A start or end has not been declared");
		}
		
		//If the scanner has another value after grid has been filled, throw exception
		if(scan.hasNext()) {
			scan.close();
			throw new InvalidFileFormatException("Too many elements in file");
		}
		
		//Close the scanner
		scan.close();
		
		//Used to check after it has all been scanned through if the correct number of ROWS and COLS were input in file.
		Scanner lineScanner = new Scanner(new File(filename));
		String[] lines = new String[ROWS];
		lineScanner.nextLine();
		for (int i = 0; i < lines.length; i++) {
			lines[i] = lineScanner.nextLine();
		}
		if(lineScanner.hasNextLine()) {
			lineScanner.close();
			throw new InvalidFileFormatException("Wrong number of lines in grid.");
		} else {
			lineScanner.close();
		}
		board = circuitBoard;
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
