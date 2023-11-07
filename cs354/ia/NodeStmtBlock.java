/**
 * This class is a node stmt with a block for the begin end block thingy.
 * @author Steven Meyers
 * @date 10/27/2020
 */
public class NodeStmtBlock extends NodeStmt {
	NodeBlock block;
	public NodeStmtBlock(NodeBlock block) {
		this.block = block;
	}

	public double eval(Environment env) throws EvalException {
	    	return block.eval(env);
	}
}
