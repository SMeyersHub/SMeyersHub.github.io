/**
 * This class represents an exception for when the syntax is incorrect.
 * @authors James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class SyntaxException extends Exception {

    private int pos;           //position that syntax error occurs
    private Token expected;    //Token that was expected at the position
    private Token found;       //Token that was found at the position

    /**
     * Constructor method that creates the exception
     * @param pos - position that syntax error occurs
     * @param expected - Token that was expected at the position
     * @param found - Token that was found at the position
     */
    public SyntaxException(int pos, Token expected, Token found) {
    	this.pos=pos;
    	this.expected=expected;
    	this.found=found;
    }

    /**
     * ToString method for the exception that prints out the position, expected token,
     * and token that it found at the position.
     */
    public String toString() {
		return "syntax error"
		    +", pos="+pos
		    +", expected="+expected
		    +", found="+found;
    }

}
