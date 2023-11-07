/**
 * This class scans through a provided program and tokenizes it.
 * @authors James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class EvalException extends Exception {

    private int pos;
    private String msg;

    /**
     * Constructor for the EvalException
     * @param pos - position where the exception occurs in the original file.
     * @param msg - the expression at the evaluated spot.
     */
    public EvalException(int pos, String msg) {
	this.pos=pos;
	this.msg=msg;
    }

    /**
     * To string that prints out the position and message.
     */
    public String toString() {
	return "eval error"
	    +", pos="+pos
	    +", "+msg;
    }

}
