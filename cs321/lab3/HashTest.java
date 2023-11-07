import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

/**
 * This is the driver program for running a hashtable
 * @author Steven Meyers
 * @date 8/5/2020
 */
public class HashTest {
	
	private static int twinPrime(int lowerBound, int upperBound) {
    	int index = lowerBound;
    	int twinIndex = lowerBound - 2;
        while(!isPrime(index) || !isPrime(twinIndex)) {
        	index++;
        	twinIndex++;
        }
        if(index <= upperBound) {
        	return index;
        }
        return 0;
    }
	
	private static boolean isPrime(int n) { 
        if (n <= 1) 
            return false; 
        if (n <= 3) 
            return true; 
        
        if (n % 2 == 0 || n % 3 == 0) 
            return false; 
  
        for (int i = 5; i * i <= n; i = i + 6) 
            if (n % i == 0 || n % (i + 2) == 0) 
                return false; 
  
        return true; 
    }
	
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Usage: java HashTest <input type> <load factor> [<debug level>]");
			System.out.println("<input type>: 1 for random number generator, 2 for current time, 3 for word list file.");
			System.out.println("<load factor>: Must be between 0 and 1. The designated load factor that the program will run till it hits.");
			System.out.println("[<debug level>]: 0 (or none) for standard print to console, 1 for print to console and print hash tables with duplicates # and probes # in files"); 
			System.exit(1);
		}
		
		//Find the smallest twin prime between the two numbers and print out size of table;
		int hashTableSize = twinPrime(95500, 96000);
		System.out.println("A good table size is found: " + hashTableSize);
		
		//Set the input type, wanted load, and debug count
		int inputType = Integer.parseInt(args[0]);
		double wantedLoad = Double.parseDouble(args[1]);
		if(Double.compare(wantedLoad, 1) > 0 || Double.compare(wantedLoad, 0) < 0) {
			throw new IllegalArgumentException("Load must be between 0 and 1");
		}
		int debug = 0;
		if(args.length == 3) {
			debug = Integer.parseInt(args[2]);
		}
		if(inputType <= 0 || inputType > 3) {
			throw new IllegalArgumentException("Input type must be: 1 (random), 2 (system time), 3, (file input)");
			
		//Use a random number generator to fill keys until load factor is hit
		} else if(inputType == 1) {
			Random rand = new Random();
			System.out.println("Data source type: random number generator");
			HashTable linearHashTable = new HashTable(hashTableSize, inputType, 1, debug);
			HashTable doubleHashTable = new HashTable(hashTableSize, inputType, 2, debug);
			//While the load factor is smaller or equal to wanted load, insert into objects into the hashtable
			
			while((Double.compare(linearHashTable.getLoad(), wantedLoad) < 0) || (Double.compare(doubleHashTable.getLoad(), wantedLoad) < 0)) {
				Integer intObj = rand.nextInt(hashTableSize);
				linearHashTable.hashInsert(intObj);
				doubleHashTable.hashInsert(intObj);
			}
			try {
				if(debug == 1) {
					PrintStream linearOut = new PrintStream(new FileOutputStream("linear-dump"));
					System.setOut(linearOut);
				}
				
				System.out.println(linearHashTable.toString());
				
				if(debug == 1) {
					PrintStream doubleOut = new PrintStream(new FileOutputStream("double-dump"));
					System.setOut(doubleOut);
				}
				System.out.println(doubleHashTable.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		//Use long data from Currentsystemtimeinmillis
		} else if(inputType == 2) {
			
			//Define what is being used as input
			System.out.println("Data source type: current time");
			HashTable linearHashTable = new HashTable(hashTableSize, inputType, 1, debug);
			HashTable doubleHashTable = new HashTable(hashTableSize, inputType, 2, debug);
			
			//While the load factor is smaller or equal to wanted load, insert into bjects into the hashtable
			while((Double.compare(linearHashTable.getLoad(), wantedLoad) < 0) || (Double.compare(doubleHashTable.getLoad(), wantedLoad) < 0)) {
				Long longObj = System.currentTimeMillis();
				linearHashTable.hashInsert(longObj);
				doubleHashTable.hashInsert(longObj);
			}
			try {
				if(debug == 1) {
					PrintStream linearOut = new PrintStream(new FileOutputStream("linear-dump"));
					System.setOut(linearOut);
				}
				System.out.println(linearHashTable.toString());
				
				if(debug == 1) {
					PrintStream doubleOut = new PrintStream(new FileOutputStream("double-dump"));
					System.setOut(doubleOut);
				}
				System.out.println(doubleHashTable.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			
		//Use file input data from word-list
		} else if(inputType == 3) {
			File file = new File("word-list");
			System.out.println("Data source type: word-list");
			HashTable linearHashTable = new HashTable(hashTableSize, inputType, 1, debug);
			HashTable doubleHashTable = new HashTable(hashTableSize, inputType, 2, debug);
			try {
				Scanner scan = new Scanner(file);
				//While the load factor is smaller or equal to wanted load, insert into objects into the hashtable
				while((Double.compare(linearHashTable.getLoad(), wantedLoad) < 0) || (Double.compare(doubleHashTable.getLoad(), wantedLoad) < 0)) {
					String stringObj = scan.nextLine();
					doubleHashTable.hashInsert(stringObj);
					linearHashTable.hashInsert(stringObj);
				}
				scan.close();
				if(debug == 1) {
					PrintStream linearOut = new PrintStream(new FileOutputStream("linear-dump"));
					System.setOut(linearOut);
				}
				System.out.println(linearHashTable.toString());
				
				if(debug == 1) {
					PrintStream doubleOut = new PrintStream(new FileOutputStream("double-dump"));
					System.setOut(doubleOut);
				}
				System.out.println(doubleHashTable.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
