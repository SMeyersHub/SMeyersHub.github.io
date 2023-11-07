/**
 * This class represents the stmt node in the parse tree.
 * @authors James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class NodeStmtAssn extends NodeStmt {

    private NodeAssn assn;
    
    /**
     * Constructor of NodeStmt
     * @param assn - the Node assn that the stmt contains
     */
    public NodeStmtAssn(NodeAssn assn) {
    	this.assn=assn;
    }

    /*
     * Evaluates the environment. Basically just calls the assn nodes eval
     */
    public double eval(Environment env) throws EvalException {
    	return assn.eval(env);
    }

}
