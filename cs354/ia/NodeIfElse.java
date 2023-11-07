
public class NodeIfElse extends NodeStmt {
	
	private NodeBoolExpr boolexpr;
	private NodeStmt stmt;
	private NodeStmt stmtElse;

	public NodeIfElse(NodeBoolExpr boolexpr, NodeStmt stmt, NodeStmt stmtElse) {
		this.boolexpr=boolexpr;
		this.stmt=stmt;
		this.stmtElse=stmtElse;
	}
	
	public double eval(Environment env) throws EvalException {
		if(boolexpr.eval(env) == 1.0) {
			return stmt.eval(env);
		} else {
			return stmtElse.eval(env);
		}
	}
}
