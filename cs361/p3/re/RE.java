package re;

import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;
import fa.nfa.NFA;
import fa.nfa.NFAState;

/**
 * This class stores a Regular Expression and is able to parse and turn it into an NFA.
 * @author Steven Meyers
 */
public class RE implements REInterface {
	private String input = "";
	private int nameCounter = 1;
	/**
	 * Constructor for regex
	 * @param regEx - String of regex being passed in.
	 */
	public RE(String regEx) {
		this.input = regEx;
	}

	/**
	 * Builds an NFA from the regex.
	 * @return The NFA built from the regular expression
	 */
	@Override
	public NFA getNFA() {
		return regex();
	}

	/**
	 * Creates an NFA representing a regular expression
	 * @return NFA representing the input regular expression.
	 */
	private NFA regex() {
		//Create the term NFA
		NFA termNFA = term();
		
		//If there are more characters and the next symbol is a "or" symbol.
		if(more() && peek() == '|') {
			eat('|');
			//Create an NFA representing this part of the union
			NFA regex = regex();
			
			//Create a new NFA to make a union out of the term NFA and this NFA
			NFA unionNFA = new NFA();
			
			//Add a start state named the name counter.
			String startName = Integer.toString(nameCounter);
			nameCounter++;
			unionNFA.addStartState(startName);
			
			//Add the states and alphabets from the base term NFA and the regex NFA
			unionNFA.addNFAStates(termNFA.getStates());
			unionNFA.addNFAStates(regex.getStates());
			unionNFA.addAbc(termNFA.getABC());
			unionNFA.addAbc(regex.getABC());
			
			//Make the epsilon transitions from the new start state to the existing NFA's start states.
			unionNFA.addTransition(startName, 'e', termNFA.getStartState().getName());
			unionNFA.addTransition(startName, 'e', regex.getStartState().getName());
			
			return unionNFA;
			
		//If there is no union symbol, simply return the normal term NFA
		} else {
			return termNFA;
		}
	}
	
	/**
	 * Checks to see if it has hit the end of the (), an if statement, or the end of the regex.
	 * @return factor - NFA that is empty if the term is empty, or represents the term combined together.
	 */
	private NFA term() {
		NFA factor = new NFA();
	
		//While the next character is not a character to end the ( or start another or or end of regex
		while(more() && peek() != ')' && peek() != '|') {
			//Create a new NFA from factor
			NFA nextFactor = factor();
			
			//If factor is an empty NFA, set factor = to existing factor NFA
			if(factor.getStates().isEmpty()) {
				factor = nextFactor;
				
			//If not, essentially concatenate the two NFA's together.
			} else {
				
				//Create a state of the current factors final states
				 Set<State> factorFinals = factor.getFinalStates();
				
				 //Add the NFA states and alphabets of the nextFactor NFA to this NFA
				factor.addNFAStates(nextFactor.getStates());
				factor.addAbc(nextFactor.getABC()); 
				
				//For each final state in the current finals, set them to nonfinal, leaving only the nextFactor finals
				for(State state: factorFinals) {
					((NFAState) state).setNonFinal();
					factor.addTransition(state.getName(), 'e', nextFactor.getStartState().getName());
				}
			}
		}
		return factor;
	} 
	
	/**
	 * Check and see if the char/statement has a kleene star after
	 * @return NFA that has epsilon transitions to loop it an infinite amount of times.
	 */
	private NFA factor() {
		NFA baseNFA = base();
		
		//While there are more to the statement and the next character is a kleene star
		while(more() && peek() == '*') {
			//Get rid of the star and create a new NFA
			eat('*');
			NFA newNFA = new NFA();
			
			//Make a new start state and final state
			String newStart =  Integer.toString(nameCounter);
			nameCounter++;
			String newFinal = Integer.toString(nameCounter);
			nameCounter++;
			
			//Add new start and final state to the new NFA
			newNFA.addStartState(newStart);
			newNFA.addFinalState(newFinal);
			
			//Add NFA states and alphabet from the baseNFA 
			newNFA.addNFAStates(baseNFA.getStates());
			newNFA.addAbc(baseNFA.getABC());
			
			//Add epsilon transitions from the new start state to the new final state
			newNFA.addTransition(newStart, 'e', newFinal);
			
			//Add epsilon transitions from the new final (which has epsilon from new start) to base start
			newNFA.addTransition(newFinal, 'e', baseNFA.getStartState().getName());
			
			//Ensure that the new final is tied to all existing finals and the only final in new NFA
			//Go through all baseNFA finalStates, epsilon transition to new final
			for(State state: baseNFA.getFinalStates()) {
				newNFA.addTransition(state.getName(), 'e', newFinal);
				
				//For each final state in new FinalStates, if its in the base NFA, turn nonfinal
				for(State s2: newNFA.getFinalStates()){
					if(s2.getName().equals(state.getName())) {
						((NFAState)s2).setNonFinal();
					}
				}
			}
			//Set the baseNFA as the new NFA for returning purposes.
			baseNFA = newNFA;
		}
		
		return baseNFA;
	}
	
	/**
	 * Checks to see what possible regex it read and do the correct actions
	 * @return NFA that is correctly built for () or just a normal NFA format
	 */
	private NFA base() {
		//Switch case for the next symbol
		switch(peek()) {
		//If a parenthesis is detected, use regex to build NFA
		case '(':
			eat('(');
			NFA nfa = regex();
			eat(')');
			return nfa;
		//If no parenthesis is detected, build NFA
		default:
			//Create a new NFA
			NFA nfaTwo = new NFA();
			
			//Create a new start and final state
			String startName = Integer.toString(nameCounter);
			nameCounter++;
			String finalName = Integer.toString(nameCounter);
			nameCounter++;
			nfaTwo.addStartState(startName);
			nfaTwo.addFinalState(finalName);
			
			//Grab the next character and make it a transition character
			char transitionChar = next();
			nfaTwo.addTransition(startName, transitionChar, finalName);
			
			//Create an alphabet, add transition character, and add to the NFA's alphabet
			Set<Character> newAlphabet = new LinkedHashSet<Character>();
			newAlphabet.add(transitionChar);
			
			nfaTwo.addAbc(newAlphabet);
			
			return nfaTwo;
		}
	}
	
	/**
	 * Peeks at the next char
	 * @return next char in regex
	 */
	private char peek() {
		return input.charAt(0);
	}
	
	/**
	 * Delete the char if its next
	 * @param c - char being eaten
	 */
	private void eat(char c) {
		if(peek() == c) {
			this.input = this.input.substring(1);
		} else {
			throw new RuntimeException("Expected: " + c + " but got: " + peek());
		}
	}
	
	/**
	 * Return the next char, delete it
	 * @return c - next char in regex
	 */
	private char next() {
		char c = peek();
		eat(c);
		return c;
	}
	
	/**
	 * @return true if more chars are in regex
	 */
	private boolean more() {
		return input.length() > 0;
	}
}
