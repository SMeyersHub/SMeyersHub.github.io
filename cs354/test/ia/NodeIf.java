/**
 * This class represents a node that is an if statement (with no else statement)
 * @author Steven Meyers
 * @date 10/30/20
 */
public class NodeIf extends NodeStmt {
	private NodeBoolExpr boolexpr;
	private NodeStmt stmtOne;
	public NodeIf(NodeBoolExpr boolexpr, NodeStmt stmtOne) {
		this.boolexpr=boolexpr;
		this.stmtOne=stmtOne;
	}
	
	/**
	 * @return the evaluated statement or 0.0 if false
	 */
	public double eval(Environment env) throws EvalException {
		if(boolexpr.eval(env) == 1.0) {
			return stmtOne.eval(env);
		} else {
			return 0.0;
		}
	}
}
