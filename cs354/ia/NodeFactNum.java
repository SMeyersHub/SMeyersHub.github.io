/**
 * This class is a node for a number in the fact grammar.
 * @author James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class NodeFactNum extends NodeFact {

    private String num;
    
    /**
     * Constructor for a NodeFactNum
     * @param num - the number that was read in by the Scanner
     */
    public NodeFactNum(String num) {
    	this.num=num;
    }
    
    /**
     * Evaluates the environment. Throws an exception if the environment can't be evaluated.
     */
    public double eval(Environment env) throws EvalException {
    	return Double.parseDouble(num);
    }

}
