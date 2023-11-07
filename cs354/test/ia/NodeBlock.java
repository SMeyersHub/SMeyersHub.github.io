/**
 * This class is a node which represents a block.
 * @author Steven Meyers
 * @date 10/30/20
 *
 */
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
    
    /**
     * Constructor for a node block if it has no block after
     * @param stmt - the stmt that is the end of the block
     */
    public NodeBlock(NodeStmt stmt) {
    	this.block=null;
    	this.stmt=stmt;
    }
    
    /**
     * Eval method for the block
     * @return Returns the result of the stmt.
     */
	public double eval(Environment env) throws EvalException {
		Double stmtResult = stmt.eval(env);
		if(block==null) {
			return stmtResult;
		}
		return block.eval(env);
	}

}
