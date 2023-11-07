// This class models a token, which has two parts:
// 1) the token itself (e.g., "id" or "+")
// 2) the token's lexeme (e.g., "foo")

/**
 * This class models a token.
 * @author James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class Token {

    private String token; //defines the token itself (what it literally is)
    private String lexeme; //defines the token's lexeme (what the lexical analyzer uses to identify the token)

    /**
     * Constructor for the token.
     * @param token - actual token
     * @param lexeme - lexemme of the token
     */
    public Token(String token, String lexeme) {
    	this.token=token;
    	this.lexeme=lexeme;
    }
    
    /**
     * Constructor for the token.
     * @param token - actual token set for both lexeme and token.
     */
    public Token(String token) {
    	this(token,token);
    }

    public String tok() { return token; } //Simply return the token
    public String lex() { return lexeme; } //simple return the lexeme

    /**
     * See if the token is the same as the token put in
     * @param t - token being compared to
     * @return true if the token is equal to the token being put in
     */
    public boolean equals(Token t) {
    	return token.equals(t.token);
    }

    /**
     * To string for the token that prints out the token and its lexeme
     */
    public String toString() {
    	return "<"+tok()+","+lex()+">";
    }

}
