import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FormatChecker {

	public static void main(String[] args) {
		//Checks to see if there are any args in the line and prints out instructions.
		if (args.length == 0) {
			System.out.println("Usage: $ java FormatChecker file1 [file2 ... fileN]");
		} else {
			for(int i = 0; i < args.length; i++) {
				boolean invalid = false;
				try {
					checkFormat(args[i]);
					
					//Catches all exceptions produced and prints relevant info
				} catch (FileNotFoundException e) {
					System.out.println(args[i] + "\n" + e + "\n" + "INVALID" + "\n");
					invalid = true;
				} catch (NumberFormatException e) {
					System.out.println(args[i] + "\n" + e + "\n" + "INVALID" + "\n");
					invalid = true;
				} catch (DimensionMismatchException e) {
					System.out.println(args[i] + "\n" + e + "\n" + "INVALID" + "\n");
					invalid = true;
				}
				if (invalid == false) {
					System.out.println(args[i] + "\n" + "VALID" + "\n");	
				}
			}
		}

	}
	/**
	 * 
	 * @param filename Name of file to be format checked
	 * @throws FileNotFoundException If file can't be found
	 * @throws NumberFormatException If the number isn't an int or double in grid
	 * @throws DimensionMismatchException 
	 */
	public static void checkFormat(String filename) throws FileNotFoundException, NumberFormatException {
		//Creates a 2D array of doubles that is null
		double[][] scannedGrid = null;
		
		//Creates a file and scans it
		File file = new File(filename);
		Scanner scan = new Scanner(file);
		
		// Scans the x and y values and creates a double array grid using them as the array sizes.
		int x = Integer.parseInt(scan.next());
		int y = Integer.parseInt(scan.next());		
		int counter = 0;
		scannedGrid = new double[x][y];
		
		// Scans in the values to the created double array.
		for(int i =0; i < scannedGrid.length; i++) {
			for(int j = 0; j < scannedGrid[i].length; j++) {
				
				//Checks to see if there are enough values to fill array
				if(!scan.hasNext() && counter != x * y) {
					scan.close();
					throw new DimensionMismatchException("Too little values in grid.");
				}
				scannedGrid[i][j] = Double.parseDouble(scan.next());
				counter = counter + 1;
			}
		}
		
		//Checks to see if there are too many values in grid.
		if(scan.hasNext()) {
			scan.close();
			throw new DimensionMismatchException("Too many values in grid.");
		} 
		scan.close();
		
		//Checks to see if the lines in the grid are formatted incorrectly
		Scanner lineScanner = new Scanner(file);
		String[] lines = new String[x];
		lineScanner.nextLine();
		for (int i = 0; i < lines.length; i++) {
			lines[i] = lineScanner.nextLine();
		}
		if(lineScanner.hasNextLine()) {
			lineScanner.close();
			throw new DimensionMismatchException("Wrong number of lines in grid.");
		} else {
			lineScanner.close();
		}
		}
	}

