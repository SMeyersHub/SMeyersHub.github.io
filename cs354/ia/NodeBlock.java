
public class NodeBlock extends Node{
	NodeBlock block;
	NodeStmt stmt;
	
	/**
     * Constructor of NodeStmt
     * @param block - the possible block node that this block contains
     * @param stmt - the stmt that this block contains.
     */
    public NodeBlock(NodeBlock block, NodeStmt stmt) {
    	this.block=block;
    	this.stmt=stmt;
    }
    
    public NodeBlock(NodeStmt stmt) {
    	this.stmt=stmt;
    }
    
	public double eval(Environment env) throws EvalException {
		return stmt.eval(env);
	}

}
