/**
 * This class represents an Program Node for the Environment.
 * @authors Steven Meyers
 * @date 10/26/2020
 */
public class NodeProg extends Node {
	private NodeBlock block;
	
	/**
     * Constructor for an Program node.
     * @param block - block in program node
     */
    public NodeProg(NodeBlock block) {
		this.block=block;
    }
    
    /**
     * @return the eval of a block stmt.
     */
    public double eval(Environment env) throws EvalException {
    	return block.eval(env);
    }
}
