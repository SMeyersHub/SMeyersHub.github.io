import java.util.Random;

/**
 * Generates a process object from a percentage chance
 * @author Steven Meyers
 * @date 7/17/20
 */
public class ProcessGenerator {
	//Declare variables
	Random ran = new Random();
	double probability;
	
	/**
	 * Defines a process generator
	 * @param probability chance that a process will be created every second
	 */
	public ProcessGenerator(double probability) {
		this.probability = probability;
	}
	
	/**
	 * Sets random variables for the process time and priority level and assigns that current time
	 * to a Process object
	 * @param currentTime The time it currently is
	 * @param maxProcessTime The time it will take to process the object
	 * @param maxLevel The highest priority allowed
	 * @return process Process object with the defined variables
	 */
	public Process getNewProcess(int currentTime, int maxProcessTime, int maxLevel) {
		int randomTime = ran.nextInt(maxProcessTime - 1) + 1;
		int randomLevel = ran.nextInt(maxLevel - 1) + 1;
		Process process = new Process(currentTime, randomTime, randomLevel);
		return process;
	}
	
	/**
	 * Runs a number between 0 and 1 and returns true if its less than or equal to the defined probability
	 * @return true if an process has arrived
	 */
	public boolean query() {
		double ranDouble = ran.nextDouble();
		
		if(ranDouble <= probability) {
			return true;
		} else {
			return false;
		}
	}
}
