import java.text.DecimalFormat;

/**
 *  This class is a subclass of Clock. It has all the methods of Clock but has a special display method.
 * @author Steven Meyers
 *
 */
class GrandfatherClock extends Clock {
	/**
	 * The basic constructor for this class that creates a GrandfatherClock with the input ClockType and drift for that clock.
	 * @param clockType The type of clock you want from the enum declared above.
	 * @param drift The amount of drift that is added for each second the clock ticks forwards.
	 */
	public GrandfatherClock(ClockType clockType, double drift) {
		super(clockType, drift);
	}
	
	/**
	 * This method displays the information such as time and clocktype along with the name of the clock in a string.
	 */
	public void display() {
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.println(clockType.toString() + ", grandfather clock, " + "time [" + time.formattedTime() + "], total drift = " + df.format(time.getTotalDrift()));
	}

}