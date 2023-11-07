/**
 * This class is a node that simulates a while do.
 * @author Steven Meyers
 * @date 10/30/20
 */

public class NodeWhile extends NodeStmt{
	
	NodeBoolExpr boolexpr;
	NodeStmt stmt;
	
	/**
	 * Constructor for Node While
	 * @param boolexpr - boolean expression that the while uses to evaluate
	 * @param stmt - the stmt executed while the boolexpr is true.
	 */
	public NodeWhile(NodeBoolExpr boolexpr, NodeStmt stmt) {
		this.boolexpr = boolexpr;
		this.stmt=stmt;
	}
	
	/**
	 * @return The eval of the stmt if true, 0.0 if false.
	 */
	public double eval(Environment env) throws EvalException {
		while(boolexpr.eval(env) == 1.0) {
			return stmt.eval(env);
		} 
		return 0.0;
	}
}
