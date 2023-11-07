
public class NodeIf extends NodeStmt {
	private NodeBoolExpr boolexpr;
	private NodeStmt stmtOne;
	public NodeIf(NodeBoolExpr boolexpr, NodeStmt stmtOne) {
		this.boolexpr=boolexpr;
		this.stmtOne=stmtOne;
	}
	
	public double eval(Environment env) throws EvalException {
		if(boolexpr.eval(env) == 1.0) {
			return stmtOne.eval(env);
		} else {
			return 0.0;
		}
	}
}
