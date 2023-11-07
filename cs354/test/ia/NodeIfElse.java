/**
 * This class represents a node for an if else statement.
 * @author Steven Meyers
 * @date 10/30/20
 */

public class NodeIfElse extends NodeStmt {
	
	private NodeBoolExpr boolexpr;
	private NodeStmt stmt;
	private NodeStmt stmtElse;

	/**
	 * Constructor for a IfElse statement
	 * @param boolexpr - expression being put into the boolean expression
	 * @param stmt - stmt being executed if the bool expr is true
	 * @param stmtElse - stmt being executed if the bool expr is false
	 */
	public NodeIfElse(NodeBoolExpr boolexpr, NodeStmt stmt, NodeStmt stmtElse) {
		this.boolexpr=boolexpr;
		this.stmt=stmt;
		this.stmtElse=stmtElse;
	}
	
	/**
	 * @return One stmt if it is true, different stmt if false
	 */
	public double eval(Environment env) throws EvalException {
		if(boolexpr.eval(env) == 1.0) {
			return stmt.eval(env);
		} else {
			return stmtElse.eval(env);
		}
	}
}
