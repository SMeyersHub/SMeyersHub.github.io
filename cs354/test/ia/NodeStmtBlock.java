/**
 * This class is a node stmt with a block for the begin end block thingy.
 * @author Steven Meyers
 * @date 10/27/2020
 */
public class NodeStmtBlock extends NodeStmt {
	NodeBlock block;
	
	/**
	 * Constructor for a block that is a statement.
	 * @param block - Block being passed into the Block Statement
	 */
	public NodeStmtBlock(NodeBlock block) {
		this.block = block;
	}

	/**
	 * @return The eval of the block within the NodeStmtBlock
	 */
	public double eval(Environment env) throws EvalException {
	    	return block.eval(env);
	}
}
