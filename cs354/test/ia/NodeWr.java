/**
 * This class is a node that is a wr statement.
 * @author Steven Meyers
 * @date 10/30/20
 */

public class NodeWr extends NodeStmt {
	private NodeExpr expr;

	/**
	 * Constructor for the NodeWr
	 * @param expr - expression that the wr is printing out.
	 */
	public NodeWr(NodeExpr expr) {
		this.expr=expr;
	}
	
	/**
	 * Print out and returns the eval of the expr.
	 * @return Eval of the expr
	 */
	public double eval(Environment env) throws EvalException {
		System.out.println(expr.eval(env));
		return expr.eval(env);
	}
}
