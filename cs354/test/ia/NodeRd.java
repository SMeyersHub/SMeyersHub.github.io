/**
 * This node is for a rd statement, which reads a double from stdin.
 * @author Steven Meyers
 * @date 10/30/20
 */
public class NodeRd extends NodeStmt {
	private String id;
	private double value;
	
	/**
	 * Constructor for the NodeRd
	 * @param id - Id the value is being assigned to
	 * @param value - double value being assigned to an id
	 */
	public NodeRd(String id, double value) {
		this.id = id;
		this.value = value;
	}
	
	/**
	 * Puts the id and its assigned value into the environment.
	 */
	public double eval(Environment env) throws EvalException {
		return env.put(id, value);
	}
}
