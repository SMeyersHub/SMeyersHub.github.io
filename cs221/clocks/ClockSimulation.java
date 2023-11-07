import java.util.Iterator;
/**
 *  This class is used to test and print results of the Clock subclasses. These classes include Sundial, CuckooClock,
 *  GrandfatherClock, AtomicClock, and Wristwatch. It contains 2 methods for simply going through all the clock types
 *  and performing actions on them.
 * @author Steven Meyers
 *
 */
public class ClockSimulation {
	//Final variables that contain a set amount of time in seconds
	final static long DAY_SECONDS = 86400;
	final static long WEEK_SECONDS = 604800;
	final static long MONTH_SECONDS = 2592000;
	final static long YEAR_SECONDS = 31536000;
	
	public static void main(String[] args) {
		//A Bag is created that carries Clock objects and is loaded with clock objects representing the subclasses in Clock
		Bag<Clock> clockBag = new Bag<Clock>();
		clockBag.add(new Sundial(ClockType.natural, 0));
		clockBag.add(new CuckooClock(ClockType.mechanical, 0.000694444));
		clockBag.add(new GrandfatherClock(ClockType.mechanical, 0.000347222));
		clockBag.add(new AtomicClock(ClockType.quantum, 0.0));
		clockBag.add(new WristWatch(ClockType.digital, 0.000034722));
		
		//The clocks are ticked and printed for all of the variable time periods.
		System.out.println("Times before start: ");
		timeDisplay(clockBag);
		System.out.println();
		
		System.out.println("After one day: ");
		timeTicker(clockBag, DAY_SECONDS);
		timeDisplay(clockBag);
		System.out.println();
		
		System.out.println("After one week: ");
		timeTicker(clockBag, WEEK_SECONDS);
		timeDisplay(clockBag);
		System.out.println();
		
		System.out.println("After one month: ");
		timeTicker(clockBag, MONTH_SECONDS);
		timeDisplay(clockBag);
		System.out.println();
		
		System.out.println("After one year: ");
		timeTicker(clockBag, YEAR_SECONDS);
		timeDisplay(clockBag);
		System.out.println();
	}
	
	/**
	 * This class takes a Bag of Clock items and ticks the clocks inside it however many seconds are passed into it.
	 * @param passedBag The Bag of Clock objects that is going to be ticked however many times.
	 * @param tickTimes The amount of times every Clock in the Bag is going to get ticked.
	 */
	public static void timeTicker(Bag<Clock> passedBag, long tickTimes) {
		Iterator<Clock> iterator = passedBag.iterator();
		while(iterator.hasNext()) {
			Clock currentClock = iterator.next();
			for(int i = 0; i < tickTimes; i++) {
				currentClock.tick();
			}
		}
	}
	
	/**
	 * This class goes through a Bag of Clock objects passed into it and prints out their display methods.
	 * After all the Clocks in the Bag have printed their display methods, it resets them.
	 * @param passedBag The Bag of Clock objects that you want to print out the times of.
	 */
	public static void timeDisplay(Bag<Clock> passedBag) {
		Iterator<Clock> iterator = passedBag.iterator();
			while(iterator.hasNext()) {
				Clock currentClock = iterator.next();
				currentClock.display();
				currentClock.reset();
			}
	}

}
