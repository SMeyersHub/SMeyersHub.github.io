import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GridMonitor implements GridMonitorInterface {
	//The filename is protected so that all the methods can access it but things outside can not.
	protected String filename;
	
	/**
	 * This constructor takes in a filename and calls the getBaseGrid() method to create a 2D arrays using the values
	 * in the file.
	 * @param filename The name of the file that is being scanned and modified.
	 */
	public GridMonitor(String filename) {
		this.filename = filename;
		getBaseGrid();
	}
	@Override
	/**
	 * This method loads in the a file specified in the constructor then scans it to make an array with the first two 
	 * numbers and fill the rest of the array with all the other numbers.
	 * @return baseGrid A 2D array double grid that has all the values from the file, if valid, in the correct cells.
	 */
	public double[][] getBaseGrid() {
		//Creates a 2D array of doubles that is null
		double[][] baseGrid = null;
		
		//Try-Catch surrounds the whole file creation process to catch all file exceptions if the filename isn't real.
		try {
			// Creates a new file
			File file = new File(filename);
			
			//creates the scanner for the file
			Scanner scan = new Scanner(file);
			
			// Scans the x and y values and creates a double array grid using them as the array sizes.
			int x = scan.nextInt();
			int y = scan.nextInt();
			baseGrid = new double[x][y];
			
			// Scans in the values to the created double array.
			for(int i =0; i < baseGrid.length; i++) {
				for(int j = 0; j < baseGrid[i].length; j++) {
					baseGrid[i][j] = scan.nextDouble();
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
		}
		return baseGrid;
	}

	@Override
	/**
	 * Takes the created baseGrid and modifies it by adding all the surrounding cell 
	 * values of a cell, then puts new num in the original cell spot. If the cell is 
	 * missing a side (ex: on the edge of grid), adds the cells value to the mix 
	 * instead of that surrounding cell's value.
	 * @return surroundingSumGrid A 2D array with calculated sum values from the base grid put into the cells.
	 */
	public double[][] getSurroundingSumGrid() {
		//Creates two 2D arrays, both loaded with the baseGrid (original grid in file).
		double[][] originalGrid = getBaseGrid();
		double[][] surroundingSumGrid = getBaseGrid();
		
		//Creates a basic for loop scanner for the arrays which checks the cell positions
		//then calculates the correct sum value.
		for(int i =0; i < originalGrid.length; i++) {
			for(int j = 0; j < originalGrid[i].length; j++) {
				// GRID IS 1 BY 1
				if (i - 1 < 0 && i + 1 >= originalGrid.length && j - 1 < 0 && j + 1 >= originalGrid[i].length ) {
					surroundingSumGrid[i][j] = originalGrid[i][j] + originalGrid[i][j] + originalGrid[i][j] + originalGrid[i][j];
				}
				//left
				else if(i - 1 < 0) {
					//top left corner
					if (j - 1 < 0) {
						surroundingSumGrid[i][j] = originalGrid[i][j] + originalGrid[i][j] + originalGrid[i+1][j] + originalGrid[i][j+1];
					}
					//bottom left corner
					else if (j + 1 >= originalGrid[i].length) {
						surroundingSumGrid[i][j] = originalGrid[i][j] + originalGrid[i][j-1] + originalGrid[i+1][j] + originalGrid[i][j];
					}
					//center left
					else {
						surroundingSumGrid[i][j] = originalGrid[i][j] + originalGrid[i][j-1] + originalGrid[i+1][j] + originalGrid[i][j+1];
					}
				} 
				//right
				else if(i + 1 >= originalGrid.length) {
					//top right corner
					if(j - 1 < 0) {
						surroundingSumGrid[i][j] = originalGrid[i-1][j] + originalGrid[i][j] + originalGrid[i][j] + originalGrid[i][j+1];
					}
					//bottom right corner
					else if (j + 1 >= originalGrid[i].length) {
						surroundingSumGrid[i][j] = originalGrid[i-1][j] + originalGrid[i][j-1] + originalGrid[i][j] + originalGrid[i][j];
					}
					//center right
					else {
						surroundingSumGrid[i][j] = originalGrid[i-1][j] + originalGrid[i][j-1] + originalGrid[i][j] + originalGrid[i][j+1];
					}
				}
				//top center
				else if(j - 1 < 0) {
					surroundingSumGrid[i][j] = originalGrid[i-1][j] + originalGrid[i][j] + originalGrid[i+1][j] + originalGrid[i][j+1];
				}
				//bottom center
				else if(j + 1 >= originalGrid[i].length) {
					surroundingSumGrid[i][j] = originalGrid[i-1][j] + originalGrid[i][j-1] + originalGrid[i+1][j] + originalGrid[i][j];
				}
				//true center
				else {
					surroundingSumGrid[i][j] = originalGrid[i-1][j] + originalGrid[i][j-1] + originalGrid[i+1][j] + originalGrid[i][j+1];
				}
			}
		}
		return surroundingSumGrid;
	}

	@Override
	/**
	 * This method takes the Surrounding Sum Grid from the method above and takes the average of all of them.
	 * The average taking method is simple, it divides all the cells values by 4.
	 * @return surroundingAvgGrid A double 2D array loaded with the average calculated values of the Sum grid.
	 */
	public double[][] getSurroundingAvgGrid() {
		//Creates a 2D array loaded with the cell values from the surrounding sum grid calculations.
		double[][] surroundingAvgGrid = getSurroundingSumGrid();
		
		//Scans through all the cells and divides them by 4 (takes average of the surrounding sum).
		for(int i =0; i < surroundingAvgGrid.length; i++) {
			for(int j = 0; j < surroundingAvgGrid[i].length; j++) {
				surroundingAvgGrid[i][j] = surroundingAvgGrid[i][j] / 4;
			}
		}
		return surroundingAvgGrid;
	}

	@Override
	/**
	 * This method takes the Avg Grid created in the method right above it and gives "Delta values". 
	 * The calculation of these Delta values is simply diving all the averaged cells by 2.
	 * @return deltaGrid A 2D array grid with cells loaded with the delta values of the base grid.
	 */
	public double[][] getDeltaGrid() {
		//Creates a 2D array loaded with the averages of the cells from the Surrounding Sum 2d array.
		double[][] deltaGrid = getSurroundingAvgGrid();
		
		//Scans through all of the cells and divides them by 2.
		for(int i =0; i < deltaGrid.length; i++) {
			for(int j = 0; j < deltaGrid[i].length; j++) {
				deltaGrid[i][j] = deltaGrid[i][j] / 2;
			}
		}
		return deltaGrid;
	}

	@Override
	/**
	 * This method takes the previous grids (surrounding avg, delta, and original) and makes a calculation
	 * verifying if they are within the calculated safe limit of the values. 
	 * @return A 2D array set of booleans. True values are dangerous values and false means the cell is safe.
	 */
	public boolean[][] getDangerGrid() {
		//Declare the 2D grids and fill them with values. dangerGrid is declared but uses a predefined array size
		double[][] surroundingAvgGrid = getSurroundingAvgGrid();
		double[][] deltaGrid = getDeltaGrid();
		double[][] originalGrid = getBaseGrid();
		boolean[][] dangerGrid = new boolean[1][1];
		
		//Simple for loops to sort through all the cells are set up here.
		for(int i =0; i < deltaGrid.length; i++) {
			for(int j = 0; j < deltaGrid[i].length; j++) {
				//Sets up the dangerGrid's correct size using the originalGrid and originalGrid[i] sizes.
				if(dangerGrid.length == 1) {
					dangerGrid = new boolean[originalGrid.length][originalGrid[i].length];
				}
				
				//Sets the values of all cells to absolute value for comparison calculations.
				surroundingAvgGrid[i][j] = Math.abs(surroundingAvgGrid[i][j]);
				originalGrid[i][j] = Math.abs(originalGrid[i][j]);
				deltaGrid[i][j] = Math.abs(deltaGrid[i][j]);
				
				//Compares the original value to the safe range (which is between delta+avg and avg-delta).
				if((originalGrid[i][j] < (surroundingAvgGrid[i][j] - deltaGrid[i][j]) || (originalGrid[i][j] > (deltaGrid[i][j] + surroundingAvgGrid[i][j])))) {
						dangerGrid[i][j] = true;
				} else {
					dangerGrid[i][j] = false;
				}
			}
		}
		return dangerGrid;
	}
	
}
