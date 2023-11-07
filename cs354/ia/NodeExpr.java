/**
 * This class represents an Expression Node for the Environment.
 * @authors James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class NodeExpr extends Node {

    private NodeTerm term;
    private NodeAddop addop;
    private NodeExpr expr;

    /**
     * Constructor for an expression node.
     * @param term - NodeTerm for the node
     * @param addop - NodeAddop for the node
     * @param expr - NodeExpr for the node
     */
    public NodeExpr(NodeTerm term, NodeAddop addop, NodeExpr expr) {
		this.term=term;
		this.addop=addop;
		this.expr=expr;
    }

    /**
     * Appends an expression node onto this node. If this node doesn't have an expression, just directly copy the other NodeExpr
     * @param expr - node being appended or copied.
     */
    public void append(NodeExpr expr) {
		if (this.expr==null) {
		    this.addop=expr.addop;
		    this.expr=expr;
		    expr.addop=null;
		} else
		    this.expr.append(expr);
    }
    
    /**
     * @return The multiplication of a expr and a term or simply a term's int value
     */
    public double eval(Environment env) throws EvalException {
		return (expr==null
		    ? term.eval(env)
		    : addop.op(expr.eval(env),term.eval(env)));
    }

}
