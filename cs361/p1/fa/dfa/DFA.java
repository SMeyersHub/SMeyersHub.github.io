package fa.dfa;

import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import fa.State;

/**
 * This program simulates a DFA. it includes states, transitions, and a way to navigate from state to state on transitions created.
 * @author Steven Meyers
 * @date 9/27/2020
 */
public class DFA implements DFAInterface {
	LinkedHashSet<DFAState> states = new LinkedHashSet<DFAState>();
	LinkedHashSet<Character> alphabet = new LinkedHashSet<Character>();
	LinkedHashMap<String, LinkedHashMap<Character, String>> transitions = new LinkedHashMap<String, LinkedHashMap<Character, String>>();
	
	@Override
	public void addStartState(String name) {
		//Create new state, set its name to name variable, add to states LinkedHashSet
		DFAState startState = new DFAState();
		startState.setName(name);
		startState.setInitial(true); //Set this state's initial value to true.
		states.add(startState);
	}

	@Override
	public void addState(String name) {
		//Create new state, set its name to name variable, add to states LinkedHashSet
		DFAState newState = new DFAState();
		newState.setName(name);
		states.add(newState);
	}

	@Override
	public void addFinalState(String name) {
		//Create new state, set its name to name variable, add to states LinkedHashSet
		DFAState newState = new DFAState();
		newState.setName(name);
		newState.setFinal(true); //Set this state's final variable to true
		states.add(newState);
	}
	
	/**
	 * This method creates an initial and final state. Used for complement states that are both initial and turned into final.
	 * @param name is the label of the state
	 */
	private void addInitialFinalState(String name) {
		//Create new state, set its name to name variable, add to states LinkedHashSet
		DFAState newState = new DFAState();
		newState.setName(name);
		newState.setInitial(true); //Set this state's initial variable to true
		newState.setFinal(true); //Set this state's final variable to true
		states.add(newState);
	}

	@Override
	public void addTransition(String fromState, char onSymb, String toState) {
		//Add the transition to the alphabet if its not already
		if(!alphabet.contains(onSymb)) {
			alphabet.add(onSymb);
		}
		
		if(!transitions.containsKey(fromState)) {
			//Create a new LinkedHashMap named value to put into the transitions LinkedHashMap
			LinkedHashMap<Character, String> value = new LinkedHashMap<Character, String>();
			//Add the value to the transitions map
			transitions.put(fromState, value);
			//Add the K, V to the value map.
			value.put(onSymb, toState);
			
		} else {
			//If the transitions map already contains the key, simply add the new value to the transitions map.
			LinkedHashMap<Character, String> value = transitions.get(fromState);
			value.put(onSymb, toState);
		}
	}

	@Override
	public Set<? extends State> getStates() {
		//Create an iterator through the LinkedHashSet states. Also create a set to add the returned values to.
		Iterator<DFAState> stateIterator = states.iterator();
		//Set to be returned full of states
		Set<DFAState> statesReturn = new LinkedHashSet<DFAState>();
		//While the states linked hash set has more values, keep going through and adding states to the return set.
		while(stateIterator.hasNext()) {
			statesReturn.add(stateIterator.next());
		}
		return statesReturn;
	}

	@Override
	public Set<? extends State> getFinalStates() {
		//Create an iterator through the LinkedHashSet states. Also create a set to add the returned values to.
		Iterator<DFAState> stateIterator = states.iterator();
		//Set to be returned full of final states
		Set<DFAState> statesReturn = new LinkedHashSet<DFAState>();
		//While the states linked hash set has more values, keep going through them.
		while(stateIterator.hasNext()) {
			DFAState next = stateIterator.next();
			//if the state is final, add it to the return set.
			if(next.isFinal()) {
				statesReturn.add(next);
			}
		}
		return statesReturn;
	}

	@Override
	public State getStartState() {
		//Create an iterator through the LinkedHashSet states.
		Iterator<DFAState> stateIterator = states.iterator();
		//While the states linked hash set has more values, keep going through them.
		while(stateIterator.hasNext()) {
			DFAState next = stateIterator.next();
			//If the state is initial, add it to the return set.
			if(next.isInitial()) {
				return next;
			}
		}
		//If no start state, return null.
		return null;
	}

	@Override
	public Set<Character> getABC() {
		//Create an iterator through the alphabet LinkedHashSet. Also create a set of states to return.
		Iterator<Character> alphabetIterator = alphabet.iterator();
		//set to be returned full of alphabet characters
		Set<Character> statesReturn = new LinkedHashSet<Character>();
		//Go through the whole alphabet LinkedHashSet and add each character to the set.
		while(alphabetIterator.hasNext()) {
			statesReturn.add(alphabetIterator.next());
		}
		return statesReturn;
	}

	@Override
	public DFA complement() {
		//Create a new DFA to put the states in.
		DFA complementDFA = new DFA();
		//Iterator to iterate through state LinkedHashMap
		Iterator<DFAState> stateIterator = states.iterator();
		
		//Go through all the states.
		while(stateIterator.hasNext()) {
			//temporary variable to hold that state without moving next from calls.
			DFAState nextState = stateIterator.next();
			//If the state is final, add a non-final state to the complementDFA
			if(nextState.isFinal()) {
				complementDFA.addState(nextState.getName());
			//If the state is not final, but is initial, add an initial final state to the complementDFA
			} else if(!nextState.isFinal() && nextState.isInitial()) {
				complementDFA.addInitialFinalState(nextState.getName());
			//If the state is not final and not initial, add a final state to the complementDFA
			}else if(!nextState.isFinal()) {
				complementDFA.addFinalState(nextState.getName());
			}
		}
		//Add the alphabet and transitions (string based, so no need to change anything) to the complementDFA
		complementDFA.alphabet.addAll(alphabet);
		complementDFA.transitions.putAll(transitions);
		return complementDFA;
	}
	
	@Override
	public boolean accepts(String s) {
		//Start off on the startState
		DFAState currentState = (DFAState) getStartState();
		//Walk through the string one character at a time, moving currentState to the toState at that character
		for(int i = 0; i < s.length(); i++) {
			//Check to see if you are not moving to nothing, then move current state to post transition state
			if((DFAState) getToState(currentState, s.charAt(i)) != null) {
				currentState = (DFAState) getToState(currentState, s.charAt(i));
			}
		}
		//Check to see if the current state is final or not.
		if(currentState.isFinal()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public State getToState(DFAState from, char onSymb) {
		//Get the name of the state that onSymb leads to from the current state ("from")
		String toStateName = transitions.get(from.getName()).get(onSymb);
		//Turn that state name into its corresponding state and return the state.
		DFAState toState = (DFAState) stringToState(toStateName);
		return toState;
	}
	
	/**
	 * This function checks to see if a provided string corresponds to a name of a state in the state hashset, and returns the state.
	 * @param s - string of the state you wanna get
	 * @return next - the state with the name being "s". Returns null if no state has name "s".
	 */
	private State stringToState(String s) {
		//Create an iterator that goes through states
		Iterator<DFAState> stateIterator = states.iterator();
		while(stateIterator.hasNext()) {
			//Check to see if there is a corresponding state with the name of "s".
			DFAState next = stateIterator.next();
			if(next.getName().equals(s)) {
				return next;
			}
		}
		//Return null if there is no corresponding state.
		return null;
	}
	
	/**
	 * A toString function for printing out a formatted state hashset.
	 * @return output - formatted state output
	 */
	private String stateToString() {
		//iterator that goes through states
		Iterator<DFAState> stateIterator = states.iterator();
		//output string of method
		String output = "";
		output += "Q = { ";
		while(stateIterator.hasNext()) {
			output += stateIterator.next().getName() + " ";
		}
		output += "} \n";
		return output;
	}
	
	/**
	 * A toString function for printing out a formatted alphabet hashset.
	 * @return output - formatted alphabet output
	 */
	private String alphabetToString() {
		//Iterator that goes through the alphabet and returns all characters
		Iterator<Character> alphabetIterator = alphabet.iterator();
		//output string of method
		String output = "";
		output += "Sigma = { ";
		while(alphabetIterator.hasNext()) {
			output += alphabetIterator.next() + " ";
		}
		output += "} \n";
		return output;
	}
	
	/**
	 * A toString function for printing out a formatted transition hashmap.
	 * @return output - formatted transition output
	 */
	private String deltaFormation() {
		//Iterator that goes through states
		Iterator<DFAState> stateIterator = states.iterator();
		//Iterator that goes through alphabet
		Iterator<Character> alphabetIterator = alphabet.iterator();
		//Output string of method
		String output = "";
		output += "delta = \n         ";
		while(alphabetIterator.hasNext()) {
			output += "         " + alphabetIterator.next();
		}
		output += "\n         ";
		while(stateIterator.hasNext()) {
			output += "\n         ";
			DFAState next = stateIterator.next();
			//Iterator that goes through alphabet
			Iterator<Character> alphabetTwoIterator = alphabet.iterator();
			output += next;
			while(alphabetTwoIterator.hasNext()) {
				output += "        " + getToState(next, alphabetTwoIterator.next());
			}
		}
		output += "\n";
		return output;
	}
	
	/**
	 * A toString function for printing out a formatted startState.
	 * @return output - formatted startState output
	 */
	private String startStateToString() {
		//iterator that goes through states
		Iterator<DFAState> stateIterator = states.iterator();
		//Output string of start state
		String output = "";
		output += "q0 = ";
		while(stateIterator.hasNext()) {
			DFAState next = stateIterator.next();
			if(next.isInitial()) {
				output += next.getName();
			}
		}
		output += "\n";
		return output;
	}
	
	/**
	 * A toString function for printing out a formatted finalState (solo or multiple).
	 * @return output - formatted finalStates output
	 */
	private String finalStateToString() {
		//iterator that goes through states
		Iterator<DFAState> stateIterator = states.iterator();
		//Output string of start state
		String output = "";
		output += "F = { ";
		while(stateIterator.hasNext()) {
			DFAState next = stateIterator.next();
			if(next.isFinal()) {
				output += next.getName() + " ";
			}
		}
		output += "} \n";
		return output;
	}
	
	@Override
	public String toString() {
		//output string of whole program basically
		String output = "";
		output += stateToString();
		output += alphabetToString();
		output += deltaFormation();
		output += startStateToString();
		output += finalStateToString();
		return output;
	}

}
