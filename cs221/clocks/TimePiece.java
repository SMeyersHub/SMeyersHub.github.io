/**
 * This interface is used to implement how a clock of a certain type would
 * work and its basic functions. It includes the methods of reset, tick, 
 * and display.
 * @author Steven Meyers
 *
 */
public interface TimePiece {
	
	/**
	 * This method resets the TimePiece to its default time at midnight.
	 * Sets the time to (00:00:00).
	 */
	public void reset();
	
	/**
	 * This method adds 1 second to the current TimePiece count.
	 * Simulates that one second has passed.
	 */
	public void tick();
	
	/**
	 * This method displays the time of the clock.
	 */
	public void display();
}
