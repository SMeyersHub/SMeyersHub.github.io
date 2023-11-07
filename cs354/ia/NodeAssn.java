/**
 * This class scans through a provided program and tokenizes it.
 * @authors James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class NodeAssn extends Node {

    private String id; //id node of the assn
    private NodeExpr expr; //expr node of the assn

    /**
     * Constructor for the NodeAssn
     * @param id - The id of the node assn
     * @param expr - The expression of the node assn
     */
    public NodeAssn(String id, NodeExpr expr) {
		this.id=id;
		this.expr=expr;
    }

    /**
     * Evaluate the node and put it within the environment.
     */
    public double eval(Environment env) throws EvalException {
    	return env.put(id,expr.eval(env));
    }

}
