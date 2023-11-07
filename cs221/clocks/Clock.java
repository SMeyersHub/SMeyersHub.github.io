/**
 * This file contains both an enum of ClockTypes that we can select out of and a Clock class that allows us to 
 * simulate a clock and how it would work in real life. The methods in this class are reset, tick, display (abstract), and 
 * getClockType. Following the Clock class are several subclasses of Clock that define the display method in their own ways.
 * @author Steven Meyers
 *
 */
enum ClockType {
	natural, mechanical, digital, quantum
}
public abstract class Clock extends Time implements TimePiece{
	//Variables declared that allow us to use the ClockType enum and Time class in all methods.
	ClockType clockType;
	Time time;
	
	/**
	 * The basic constructor for this class that creates a Clock with the input ClockType and drift for that clock.
	 * @param clockType The type of clock you want from the enum declared above.
	 * @param drift The amount of drift that is added for each second the clock ticks forwards.
	 */
	public Clock(ClockType clockType,  double drift) {
		setClockType(clockType);
		time = new Time(0,0,0,drift);
	}
	
	/**
	 * This method resets the time on the Clock.
	 */
	public void reset() {
		time.resetToStartTime();
	}

	/**
	 * This method adds a single second to the Clock.
	 */
	public void tick() {
		time.incrementTime();
	}

	/**
	 * This method displays the ClockType, clock name, time of the Clock, and drift of the Clock.
	 */
	public abstract void display();
	
	/**
	 * Simply returns the type of clock that the Clock it is called on is.
	 * @return clockType. This is the ClockType from the enum ClockType that the Clock is.
	 */
	public ClockType getClockType() {
		return clockType;
	}
	
	/**
	 * Sets the type of a Clock to the input ClockType.
	 * @param clockType. The clock type you want the Clock to be.
	 */
	public void setClockType(ClockType clockType) {
		this.clockType = clockType;
	}
}
