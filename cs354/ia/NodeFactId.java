/**
 * This class is a node for an id in the fact grammar.
 * @author James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class NodeFactId extends NodeFact {

    private String id;

    /**
     * Constructor for a NodeFactId
     * @param pos - position of the id in the program
     * @param id - token of the id
     */
    public NodeFactId(int pos, String id) {
    	this.pos=pos;
    	this.id=id;
    }

    /**
     * Gets the variable from the environment.
     */
    public double eval(Environment env) throws EvalException {
    	return env.get(pos,id);
    }

}
