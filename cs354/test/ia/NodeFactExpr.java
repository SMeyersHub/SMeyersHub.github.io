/**
 * This class is a node for an expression in the fact grammar.
 * @author James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class NodeFactExpr extends NodeFact {

    private NodeExpr expr; //the expression within the parenthesis

    /**
     * Constructor for the node fact expression node
     * @param expr - expression within the parenthesis
     */
    public NodeFactExpr(NodeExpr expr) {
    	this.expr=expr;
    }

    /**
     * Evaluates the environment. Throws an exception if the environment can't be evaluated.
     * @return the eval value of the expr
     */
    public double eval(Environment env) throws EvalException {
    	return expr.eval(env);
    }

}
