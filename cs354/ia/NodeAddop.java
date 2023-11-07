/**
 * This class represents a node that is a symbol within the add op grammar.
 * @author James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class NodeAddop extends Node {

    private String addop; //The token string that is an addop.

    /**
     * Constructor for a NodeAddop
     * @param pos - position in the program that the addop is
     * @param addop - the character or string that is the addop symbol in the language
     */
    public NodeAddop(int pos, String addop) {
	this.pos=pos;
	this.addop=addop;
    }

    /**
     * Performs the respective addop function on the two integers passed in.
     * @param o1 - The first number in the expression
     * @param o2 - The second number in the expression
     * @return The result of the operation.
     * @throws EvalException - thrown if the addop is not an expression in the addop syntax.
     */
    public double op(double o1, double o2) throws EvalException {
	if (addop.equals("+"))
	    return o1+o2;
	if (addop.equals("-"))
	    return o1-o2;
	if (addop.equals("--") || addop.equals("- -"))
	    return o1-(-o2);
	throw new EvalException(pos,"bogus addop: "+addop);
    }

}
