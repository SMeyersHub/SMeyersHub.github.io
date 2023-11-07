import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 */
public class CircuitTracer {

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			printUsage();
			System.exit(1);
		}
		try {
			new CircuitTracer(args); //create this with args
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private static void printUsage() {
		System.out.println("This program requires three unique arguments for operation. They are as follows: ");
		System.out.println("First argument: -s if a stack memory type or -q for a queue memory type.");
		System.out.println("Second argument: -c if you want console output and -g if you want GUI output (will return message if it is not supported).");
		System.out.println("Third argument: File name. Will return an error and a message if the file is not found.");
		System.out.println("Example program launch: $java CircuitTracer -s -c filename.txt ");
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main() 
	 */
	private CircuitTracer(String[] args) {
		//Set up the storage for the TraceStates and the ArrayList object for the bestPaths TraceStates
		Storage<TraceState> stateStore = null;
		List<TraceState> bestPaths = new ArrayList<TraceState>();
		
		//Set up a circuit board and a boolean for the console declaration
		boolean consoleOutput = false;
		CircuitBoard circuitBoard;
		
		//Scan the first argument input and create either the stack or queue instances. 
		if(args[0].equals("-s")) {
			stateStore = Storage.getStackInstance();
		} else if(args[0].equals("-q")) {
			stateStore = Storage.getQueueInstance();
		} else {
			System.out.println("Incorrectly formatted command line.");
			printUsage();
			System.exit(1);
		}
		
		//Reads in the CircuitBoard and catches the exception of a file not found.
		try {
			circuitBoard = new CircuitBoard(args[2]);
		} catch (FileNotFoundException e) {
			throw new InvalidFileFormatException("The file is currently unformatted to run in this program."); 
		}
		
		//Reads in the second argument of the command line and return a printed line if GUI not supported.
		if(args[1].equals("-g")) {
			System.out.println("At this time, GUI is not enabled for this program. Please use -c for console output.");
		} else if(args[1].equals("-c")) {
			consoleOutput = true;
		} else {
			System.out.println("Incorrectly formatted command line.");
			printUsage();
			System.exit(1);
		}
		
		//Scan around the starting point of the circuit board and store the trace states that can be formed.
		if(circuitBoard.isOpen(circuitBoard.getStartingPoint().x + 1, circuitBoard.getStartingPoint().y)) {
			stateStore.store(new TraceState(circuitBoard, circuitBoard.getStartingPoint().x + 1, circuitBoard.getStartingPoint().y));
		}
		if(circuitBoard.isOpen(circuitBoard.getStartingPoint().x - 1, circuitBoard.getStartingPoint().y)) {
			stateStore.store(new TraceState(circuitBoard, circuitBoard.getStartingPoint().x - 1, circuitBoard.getStartingPoint().y));
		}
		if(circuitBoard.isOpen(circuitBoard.getStartingPoint().x, circuitBoard.getStartingPoint().y + 1)) {
			stateStore.store(new TraceState(circuitBoard, circuitBoard.getStartingPoint().x, circuitBoard.getStartingPoint().y + 1));
		}
		if(circuitBoard.isOpen(circuitBoard.getStartingPoint().x, circuitBoard.getStartingPoint().y - 1)) {
			stateStore.store(new TraceState(circuitBoard, circuitBoard.getStartingPoint().x, circuitBoard.getStartingPoint().y - 1));
		}
		
		//When the stateStore has been populated with some trace states, start going through them.
		while(!stateStore.isEmpty()) {
			TraceState currentState = stateStore.retrieve();
			
			//If the current state is completed (trace state near the endpoint), compare and possibly add to bestPaths
			if(currentState.isComplete()) {
				if(bestPaths.isEmpty() || currentState.pathLength() == bestPaths.get(0).pathLength()) {
					bestPaths.add(currentState);
				} else if (currentState.pathLength() < bestPaths.get(0).pathLength()) {
					bestPaths.clear();
					bestPaths.add(currentState);
				}
				
				//If the currentState is not completed, scan and store all the trace states around it.
			} else {
				if (currentState.isOpen(currentState.getRow() + 1, currentState.getCol())) {
					stateStore.store(new TraceState(currentState, currentState.getRow() + 1, currentState.getCol()));
				}
				if (currentState.isOpen(currentState.getRow() - 1, currentState.getCol())) {
					stateStore.store(new TraceState(currentState, currentState.getRow() - 1, currentState.getCol()));
				}
				if (currentState.isOpen(currentState.getRow(), currentState.getCol() + 1)) {
					stateStore.store(new TraceState(currentState, currentState.getRow(), currentState.getCol() + 1));
				}
				if (currentState.isOpen(currentState.getRow(), currentState.getCol() - 1)) {
					stateStore.store(new TraceState(currentState, currentState.getRow(), currentState.getCol() - 1));
				}
			}
		}
		
		//Check to see if the console output is selected, if it is and then print out the results
		if(consoleOutput == true) {
			while(!bestPaths.isEmpty()) {
					System.out.println(bestPaths.remove(0));
			}
		}
	}
	
} // class CircuitTracer
