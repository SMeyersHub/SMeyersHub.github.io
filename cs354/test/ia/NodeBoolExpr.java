/**
 * This class represents an Boolean Expression Node for the Environment.
 * @authors Steven Meyers
 * @date 10/26/2020
 */
public class NodeBoolExpr extends Node {

    private NodeExpr expr1;
    private NodeRelop relop;
    private NodeExpr expr2;

    /**
     * Constructor for an expression node.
     * @param expr1 - NodeExpr number 1 for the node
     * @param addop - NodeAddop for the node
     * @param expr - NodeExpr number 2 for the node
     */
    public NodeBoolExpr(NodeExpr expr1, NodeRelop relop, NodeExpr expr) {
		this.expr1=expr1;
		this.relop=relop;
		this.expr2=expr;
    }

    /**
     * @return 1.0 if the node is true, 0 if it is false.
     */
    public double eval(Environment env) throws EvalException {
		return (relop.op(expr1.eval(env),expr2.eval(env)));
    }

}
