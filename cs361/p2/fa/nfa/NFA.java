package fa.nfa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

/**
 * This class represents an NFA. 
 * @author Steven Meyers
 * @date 11/8/20
 */
public class NFA {
	LinkedHashSet<NFAState> states = new LinkedHashSet<NFAState>();
	LinkedHashSet<Character> alphabet = new LinkedHashSet<Character>();
	LinkedHashMap<String, LinkedHashMap<Character, ArrayList<String>>> transitions = new LinkedHashMap<String, LinkedHashMap<Character, ArrayList<String>>>();
	
	/**
	 * Add a start state to your NFA
	 * @param startStateName - name of start state being added
	 */
	public void addStartState(String startStateName) {
		//Create new state, set its name to name variable, add to states LinkedHashSet
		NFAState startState = new NFAState(startStateName);
		startState.setInitial(true); //Set this state's initial value to true.
		states.add(startState);
	}
	
	/**
	 * Retrieve the start state from your NFA.
	 * @return state - the start state to your NFA
	 */
	private NFAState getStartState() {
		for(NFAState state : states) {
			if(state.isInitial()) {
				return state;
			}
		}
		return null;
	}
	
	/**
	 * Add a normal state to your NFA
	 * @param nextToken - name of state being added
	 */
	public void addState(String nextToken) {
		//Create new state, set its name to name variable, add to states LinkedHashSet
		NFAState newState = new NFAState(nextToken);
		states.add(newState);
	}

	/**
	 * Add a final state to your NFA
	 * @param nextToken - name of the state being added
	 */
	public void addFinalState(String nextToken) {
		//Create new state, set its name to name variable, add to states LinkedHashSet
		NFAState newState = new NFAState(nextToken);
		newState.setFinal(true); //Set this state's final variable to true
		states.add(newState);
		
	}
	
	/**
	 * Add a transition to the NFA
	 * @param fromState - state that the transition goes from
	 * @param onSymb - symbol of transition
	 * @param toState - state that the transition goes to
	 */
	public void addTransition(String fromState, char onSymb, String toState) {
		//Add the transition to the alphabet if its not already
		if(!alphabet.contains(onSymb)) {
			alphabet.add(onSymb);
		}
	
		if(!transitions.containsKey(fromState)) {
			//Create a new LinkedHashMap named value to put into the transitions LinkedHashMap
			ArrayList<String> toList = new ArrayList<String>();
			LinkedHashMap<Character, ArrayList<String>> value = new LinkedHashMap<Character, ArrayList<String>>();
			//Add the value to the transitions map
			transitions.put(fromState, value);
			//Add the K, V to the value map.
			value.put(onSymb, toList);
			toList.add(toState);
				
		} else {
			ArrayList<String> toList;
			//If the transitions map already contains the key, simply add the new value to the transitions map.
			LinkedHashMap<Character, ArrayList<String>> value = transitions.get(fromState);
			if(transitions.get(fromState).get(onSymb) != null) {
				toList = transitions.get(fromState).get(onSymb);
			} else {
				toList = new ArrayList<String>();
			}
			toList.add(toState);
			value.put(onSymb, toList);
		}
	}
	
	/**
	 * Check to see if a transition is existent or not
	 * @param fromState - state that the transition goes from
	 * @param onSymb - symbol of transition
	 * @param toState - state that the transition goes to
	 * @return true if the transition exists
	 */
	private boolean checkTransition(String fromState, char onSymb, String toState) {
		try {
			//LinkedHashMap<Character, ArrayList<String>> value = new LinkedHashMap<Character, ArrayList<String>>();
			//value.put(onSymb, toState);
			if(transitions.get(fromState).get(onSymb).contains(toState)) {
				return true;
			} 
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	/**
	 * Get all the states that a certain state goes to on a character
	 * @param from - state that is originating the transitions
	 * @param onSymb - symbol of transition
	 * @return toStateArray - an array list of the strings of the states that that symbol goes to from the from state
	 */
	private ArrayList<String> getToStates(NFAState from, char onSymb) {
		ArrayList<String> toStateArray = new ArrayList<String>();
		//Get the name of the state that onSymb leads to from the current state ("from")
		try {
			int size = transitions.get(from.getName()).get(onSymb).size();
			if(size != 0) {
				toStateArray = transitions.get(from.getName()).get(onSymb);
			}
			return toStateArray;
		 } catch (NullPointerException e) {
			return toStateArray;
		} 
	}
	
	/**
	 * Gets a state from a provided string given the state exists
	 * @param s - string of state you want to get
	 * @return next - state being returned if the tostate exists
	 */
	private State stringToState(String s) {
		//Create an iterator that goes through states
		Iterator<NFAState> stateIterator = states.iterator();
		while(stateIterator.hasNext()) {
			//Check to see if there is a corresponding state with the name of "s".
			NFAState next = stateIterator.next();
			if(next.getName().equals(s)) {
				return next;
			}
		}
		//Return null if there is no corresponding state.
		return null;
	}
	
	/**
	 * Creates a dfa out of the NFA.
	 * Current does not work completely properly and probably never will.
	 * @return dfa - a dfa of the nfa
	 */
	public DFA getDFA() {
		DFA dfa = new DFA();
		//Create a new DFA, a new set of start states, and a new set of eClosure states from start.
		NFAState startState = getStartState();
		Set<Set<NFAState>> completeStates = new LinkedHashSet<Set<NFAState>>();
		int counter = 1;
		//Get the start state and add it as its own little cute set to the DFA
		
		//Add the start state IN STATE FORM to the completeStates set
		dfa.addStartState("[" + startState.toString() + "]");
		
		//Create a set of NFAStates using the epsilon closure method from the start state
		Set<NFAState> startClosure = eClosure(startState);
		completeStates.add(startClosure);
		
		//Check to see if the new state contains a final inside it, if it does then add to the dfa as a final state
		//If its not final, add to the dfa as a normal state
		if(checkFinal(dfa, startClosure)) {
			dfa.addFinalState(startClosure.toString());
		}
		
		while(counter <= completeStates.size()) {
			Set<Set<NFAState>> newCompleteStates = new LinkedHashSet<Set<NFAState>>();
			for(Set<NFAState> currentStateSet : completeStates) {
				newCompleteStates.addAll(completeStates);
				//Loop through all the states in the current state set
				for(NFAState currentState : currentStateSet) {
					Set<NFAState> resultingStatesSet = new LinkedHashSet<NFAState>();
					for(char c : alphabet) {
						//Create an ArrayList of the strings that are the results of breadth search
						ArrayList<String> resultStates = getToStates(currentState, c);
						
						//Create an iterator to go over the returned results arraylist
						Iterator<String> setIterator = resultStates.iterator();
						
						//Go through the whole list, adding the strings to a set, then check to see if the resulting string
						//is in the dfa.
						while(setIterator.hasNext()) {
							String s = setIterator.next();
							NFAState newState = (NFAState) stringToState(s);
							Set<NFAState> eStates = eClosure(newState);
							for(NFAState currStateTwo : eStates) {
								resultingStatesSet.add(currStateTwo);
							}
							
						//If the transfer thing is not an e, which is reserved for epsilon states, log it in the dfa as a proper state.
						if(c != 'e') {
							if(!ifIn(dfa, resultingStatesSet.toString())) {
								if(checkFinal(dfa, resultingStatesSet)) {
									dfa.addFinalState(resultingStatesSet.toString());
									newCompleteStates.add(resultingStatesSet);
								} else {
									dfa.addState(resultingStatesSet.toString());
									newCompleteStates.add(resultingStatesSet);
								}
								dfa.addTransition(currentStateSet.toString(), c, resultingStatesSet.toString());
								newCompleteStates.add(resultingStatesSet);
							} else if(!resultingStatesSet.isEmpty()){
								dfa.addTransition(currentStateSet.toString(), c, resultingStatesSet.toString());
								newCompleteStates.add(resultingStatesSet);
							} else if(resultStates.isEmpty()) {
								dfa.addState("[]");
								dfa.addTransition(currentStateSet.toString(), c, "[]");
							}
						}
					}
				}
			}
		}
		completeStates.addAll(newCompleteStates);
		counter++;
		}
		return dfa;
	}
	
	/**
	 * Checks to see if the string state is in the dfa
	 * @param dfa - dfa being checked
	 * @param s - name of state being checked
	 * @return true if the state s is in the dfa
	 */
	private boolean ifIn(DFA dfa, String s) {
		Set<DFAState> dfaStates = dfa.getStates();
		for(DFAState state : dfaStates) {
			String checkString = state.toString();
			if(checkString.contentEquals(s)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks to see if a set contains a final state
	 * @param dfa - dfa being checked for the set
	 * @param set - set being check to see if it contains a final state
	 * @return true if there is a final state within it
	 */
	private boolean checkFinal(DFA dfa, Set<NFAState> set) {
		for(NFAState state : set) {
			if(state.isFinal() && set.contains(state)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a set of NFAStates that are all epsilon transitions and the state itself
	 * @param s -  NFAState that you are basing the epsilon transitions
	 * @return eStates - a set of NFAStates that are all the states epsilon connected to 
	 */
	public Set<NFAState> eClosure(NFAState s) {
		LinkedHashSet<NFAState> eStates = new LinkedHashSet<NFAState>();
		eStates.add(s);
		for(NFAState toState : states) {
			if(checkTransition(s.getName(), 'e', toState.name) && checkTransition(toState.name, 'e', s.getName())) {
				eStates.add(toState);
			} else if(checkTransition(s.getName(), 'e', toState.name)) {
				Set<NFAState> returnEStates = eClosure(toState);
				eStates.addAll(returnEStates);
			}
		}
		return eStates;
	}
}
