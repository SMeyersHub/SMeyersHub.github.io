/**
 * This class defines the basic form of a process including a priority, execute time, and entry time
 * @author Steven Meyers
 * @date 7/15/20
 */
public class Process implements Comparable<Process>{
	int priorityLevel;
	int processTime;
	int arrivalTime;
	int timeNotProcessed;
	
	/**
	 * Constructor for a process
	 * @param entryTime - Current time, time when process was created or entered last into heap
	 * @param timeRemaining - Time units remaining till finish
	 * @param priority - priority of the process
	 */
	public Process(int arrivalTime, int processTime, int priority) {
		this.priorityLevel = priority;
		this.processTime = processTime;
		this.arrivalTime = arrivalTime;
		this.timeNotProcessed = 0;
	}
	
	/**
	 * Returns the time it will take to process
	 * @return processTime how long it will take to finish
	 */
	public int getTimeRemaining() {
		return processTime;
	}
	
	/**
	 * Sets the process time to new value
	 * @param newTime the new process time
	 */
	public void setProcessTime(int newTime) {
		processTime = newTime;
	}
	
	/**
	 *  Returns the priority level of the process
	 * @return priorityLevel the level of priority the process has
	 */
	public int getPriority() {
		return priorityLevel;
	}
	
	/**
	 * Sets the priority of process
	 * @param newPriority the new priority of that process
	 */
	public void setPriority(int newPriority) {
		priorityLevel = newPriority;
	}
	
	/**
	 * Adds one to priority
	 */
	public void incrementPriority() {
		++priorityLevel;
	}
	
	/**
	 * Returns the arrival time of an object
	 * @return arrivalTime the time something has arrived into the priority queue
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * Sets the arrival time of the Process
	 * @param newTime new time being set
	 */
	public void setArrivalTime(int newTime) {
		arrivalTime = newTime;
	}
	
	/**
	 * Reduces the time remaining for when it reaches the top of the queue
	 */
	public void reduceTimeRemaining() {
		processTime--;
		timeNotProcessed = 0;
	}
	
	/**
	 * Checks to see if the process is finished.
	 * @return true if the timeRemaining is 0, false if time still remains
	 */
	public boolean finish() {
		if(processTime <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Adds one to the time not processed
	 */
	public void incrementTimeNotProcessed() {
		++timeNotProcessed;
	}
	
	/**
	 * Reset the time not processed
	 */
	public void resetTimeNotProcessed() {
		timeNotProcessed = 0;
	}
	
	/**
	 * return the time not processed
	 * @return timeNotProcessed the time not processed since last processing
	 */
	public int getTimeNotProcessed() {
		return timeNotProcessed;
	}
	/**
	 * 
	 * @param process - process that is being compared too
	 * @return int - 1 if current process is higher priority, -1 if other process is higher priority
	 */
	public int compareTo(Process process) {
		if(this.arrivalTime != process.getArrivalTime()) {
			if(this.priorityLevel > process.getPriority()) {
				return 1;
			} else if(this.priorityLevel < process.getPriority()) {
				return -1;
			} else if(priorityLevel == process.getPriority() && this.arrivalTime < process.getArrivalTime()){
				return 1;
			} else if(priorityLevel == process.getPriority() && this.arrivalTime > process.getArrivalTime()) {
				return -1;
			}
			else return 2;
		}
		else {
			return 1;
		}
	}
	
	/**
	 * Returns a string containing details on all the contents of the process
	 */
	public String toString() {
		String output = "(Arrival: " + arrivalTime + " Remaining time: " + processTime + " Time not processed: " + timeNotProcessed + " Priority: " + priorityLevel + ")" + "\n";
		return output;
	}
}

