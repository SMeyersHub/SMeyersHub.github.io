
public class NodeRelop extends Node{

    private String relop; //token that is the relop function
    
    /**
     * Constructor for a NodeRelop
     * @param pos - the position that the token is located in
     * @param relop - operator that should be a relop
     */
    public NodeRelop(int pos, String relop) {
		this.pos=pos;
		this.relop=relop;
    }

    /**
     * This method performs operations on the two integer values put around it.
     * @param o1 - The number on the left side of the expression.
     * @param o2 - The number on the right side of the expression.
     * @return 1.0 if boolean expression is true, 0.0 if false.
     * @throws EvalException - If the operator token doesn't equal any of the tokens in grammar.
     */
    public double op(double o1, double o2) throws EvalException {
		if (relop.equals("<")) {
			if(o1 < o2) {
				return 1.0;
			} else {
				return 0.0;
			}
		}
		if (relop.equals("<=")) {
			if(o1 <= o2) {
				return 1.0;
			} else {
				return 0.0;
			}
		}
		if (relop.equals(">")) {
			if(o1 > o2) {
				return 1.0;
			} else {
				return 0.0;
			}
		}
		if (relop.equals(">=")) {
			if(o1 >= o2) {
				return 1.0;
			} else {
				return 0.0;
			}
		}
		//TODO: WTF IS THIS
		if (relop.equals("<>")) {
			if(o1 != o2) {
				return 1.0;
			} else {
				return 0.0;
			}
		}
		
		if (relop.equals("==")) {
			if(o1 == o2) {
				return 1.0;
			} else {
				return 0.0;
			}
		}
		
		throw new EvalException(pos,"bogus mulop: "+relop);
    }
}
