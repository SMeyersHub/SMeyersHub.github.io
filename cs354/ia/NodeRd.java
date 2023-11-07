
public class NodeRd extends NodeStmt {
	private String id;
	private double value;
	
	public NodeRd(String id, double value) {
		this.id = id;
		this.value = value;
	}
	
	public double eval(Environment env) throws EvalException {
		return env.put(id, value);
	}
}
