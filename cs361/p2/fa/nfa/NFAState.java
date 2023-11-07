package fa.nfa;

import fa.State;

/**
 * This class simulates an NFA state
 * @author Steven Meyers
 * @date 11/8/2020
 */
public class NFAState extends State {
	/**
	 * initialState - true if the state is initial, false if not
	 * finalState - true if the state is final, false if not
	 * name - name of the state in the NFA
	 */
	private boolean initialState = false;
	private boolean finalState = false;
	protected String name;
	
	/**
	 * Constructor for an NFA state
	 * @param name -  name of the state
	 */
	public NFAState(String name) {
		this.name=name;
	}
	
	/**
	 * getter for the string label
	 * @return returns the state label.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Set the state as initial
	 * @param b - true or false, for whether its an initial state or not
	 */
	public void setInitial(boolean b) {
		initialState = b;
	}
	
	/**
	 * Set the state as final or nonfinal
	 * @param b - true or false, for whether it is a final state or not
	 */
	public void setFinal(boolean b) {
		finalState = b;
	}
	
	/**
	 * Check to see if the state is initial
	 * @return true if the state is initial
	 */
	public boolean isInitial() {
		return initialState;
	}
	
	/**
	 * Check to see if the state is final
	 * @return true if the state is final
	 */
	public boolean isFinal() {
		return finalState;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
