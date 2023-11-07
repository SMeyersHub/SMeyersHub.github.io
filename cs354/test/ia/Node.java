// This class, and its subclasses,
// collectively model parse-tree nodes.
// Each kind of node can be eval()-uated.

/**
 * This class, and its subclasses, collectively model parse-tree nodes. 
 * Each kind of node can be eval()-uated.
 * @author James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public abstract class Node {

    protected int pos=0; //the position of the node in the tree

    /**
     * This method evaluates an enviroment and returns something.
     * @param env - Enviroment object you are trying to evaluate.
     * @return double - the value stored at the given node.
     * @throws EvalException - Thrown if a node that isnt a subclass or Node class object is trying to be Evaluated.
     */
    public double eval(Environment env) throws EvalException {
    	throw new EvalException(pos,"cannot eval() node!");
    }

}
