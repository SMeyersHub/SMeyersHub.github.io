package fa.dfa;

import fa.State;

/**
 * This file simulates a working DFA state, which can be marked as final and initial.
 * @author Steven Meyers
 * @date 9/27/020
 */
public class DFAState extends State{
	/**
	 * initialState - true if the state is initial, false if not
	 * finalState - true if the state is final, false if not
	 */
	private boolean initialState = false;
	private boolean finalState = false;
	
	/**
	 * Sets the state to initial or not initial
	 * @param bool - sets whether the state is initial or not
	 */
	public void setInitial(boolean bool) {
		initialState = bool;
	}
	
	/**
	 * Sees if the state is marked initial or not.
	 * @return initialState - true if state is initial
	 */
	public boolean isInitial() {
		return initialState;
	}
	
	/**
	 * Sets the state to final or not final
	 * @param bool - sets whether the state is final or not
	 */
	public void setFinal(boolean bool) {
		finalState = bool;
	}
	
	/**
	 * Sees if the state is marked final or not.
	 * @return finalState - true if state is final
	 */
	public boolean isFinal() {
		return finalState;
	}
	
	/**
	 * Changes the name to name.
	 * @param name - new name for set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
