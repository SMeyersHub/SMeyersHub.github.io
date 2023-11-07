/**
 * This class is a node in the parse tree representing a multiplication operation.
 * @author James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class NodeMulop extends Node {

    private String mulop; //token that is the multop function
    
    /**
     * Constructor for a NodeMulop
     * @param pos - the position that the token is located in
     * @param mulop - operator that should be a mulop
     */
    public NodeMulop(int pos, String mulop) {
		this.pos=pos;
		this.mulop=mulop;
    }

    /**
     * This method performs operations on the two integer values put around it.
     * @param o1 - The number on the left side of the expression.
     * @param o2 - The number on the right side of the expression.
     * @return The result of the operation done on the two integer values passed in.
     * @throws EvalException - If the operator token doesn't equal any of the tokens in grammar.
     */
    public double op(double o1, double o2) throws EvalException {
		if (mulop.equals("*"))
		    return (o1*o2);
		if (mulop.equals("/"))
		    return (o1/o2);
		throw new EvalException(pos,"bogus mulop: "+mulop);
    }

}
