import java.util.HashMap;

// This class provides a stubbed-out environment.
// You are expected to implement the methods.
// Accessing an undefined variable should throw an exception.

// Hint!
// Use the Java API to implement your Environment.
// Browse:
//   https://docs.oracle.com/javase/tutorial/tutorialLearningPaths.html
// Read about Collections.
// Focus on the Map interface and HashMap implementation.
// Also:
//   https://www.tutorialspoint.com/java/java_map_interface.htm
//   http://www.javatpoint.com/java-map
// and elsewhere.

/**
 * This class scans through a provided program and tokenizes it.
 * @authors James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class Environment {
	HashMap<String, Double> env = new HashMap<String, Double>();
	
	/**
	 * Puts the given variable and its value into the HashMap environment
	 * @param var - name of variable being put into map
	 * @param d - value of variable being added or replaced
	 * @return value of variable being put in.
	 */
    public double put(String var, Double d) { 
    	env.put(var, d);
    	return d; 
    }
    
    /**
     * Throws an error if there is no variable with the key (name) in the map.
     * @param pos - position of where error occurs
     * @param var - name of variable that is being gotten
     * @return the double mapped to the key var
     * @throws EvalException - if the var does not exist in the map, throw this exception
     */
    public double get(int pos, String var) throws EvalException { 
    	if(!env.containsKey(var)) {
    		throw new EvalException(pos, var);
    	}
    	return env.get(var);
    }

}
