
public class NodeWhile extends NodeStmt{
	
	NodeBoolExpr boolexpr;
	NodeStmt stmt;
	
	public NodeWhile(NodeBoolExpr boolexpr, NodeStmt stmt) {
		this.boolexpr = boolexpr;
		this.stmt=stmt;
	}
	
	public double eval(Environment env) throws EvalException {
		while(boolexpr.eval(env) == 1.0) {
			return stmt.eval(env);
		} 
		return 0.0;
	}
}
