
public class NodeWr extends NodeStmt {
	private NodeExpr expr;

	public NodeWr(NodeExpr expr) {
		this.expr=expr;
	}
	
	public double eval(Environment env) throws EvalException {
		System.out.println(expr.eval(env));
		return expr.eval(env);
	}
}
