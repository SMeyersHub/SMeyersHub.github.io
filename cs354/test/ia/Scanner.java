// This class is a scanner for the program
// and programming language being interpreted.

import java.util.*;

/**
 * This class scans through a provided program and tokenizes it.
 * @authors James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class Scanner {

    private String program;	// source program being interpreted
    private int pos;		// index of next char in program
    private Token token;	// last/current scanned token

    private boolean comment; // identifier for if a line is a comment or not.
    // sets of various characters and lexemes
    private Set<String> whitespace=new HashSet<String>();
    private Set<String> digits=new HashSet<String>();
    private Set<String> letters=new HashSet<String>();
    private Set<String> legits=new HashSet<String>();
    private Set<String> keywords=new HashSet<String>();
    private Set<String> operators=new HashSet<String>();

    // initializers for previous sets

    /**
     * Fills the provided set "s" with all chars between the ASCII value lo to hi.
     * @param s - set that is being "filled" with characters
     * @param lo - the char at the low end of the ASCII filler
     * @param hi - the character at the end of the ASCII filler.
     */
    private void fill(Set<String> s, char lo, char hi) {
		for (char c=lo; c<=hi; c++)
		    s.add(c+"");
    }    

    /**
     * Fills a set "s" with characters the scanner will treat as whitespace
     * @param s - Set that is being filled with the whitespace syntax
     */
    private void initWhitespace(Set<String> s) {
		s.add(" ");
		s.add("\n");
		s.add("\t");
    }

    /**
     * Fills a set with digits allowed in the program.
     * @param s - Set that is being filled with the digit syntax.
     */
    private void initDigits(Set<String> s) {
    	fill(s,'0','9');
    	s.add(".");
    }

    /**
     * Fills a set with all the letters allowed in the program.
     * @param s - Set being filled with the letters.
     */
    private void initLetters(Set<String> s) {
		fill(s,'A','Z');
		fill(s,'a','z');
    }

    /**
     * Fills a set with all legit characters allowed in the program
     * @param s - Set being filled with the legit characters 
     */
    private void initLegits(Set<String> s) {
		s.addAll(letters);
		s.addAll(digits);
    }

    /**
     * Fills a set with all operators allowed in the program
     * @param s - Set being filled with the operators
     */
    private void initOperators(Set<String> s) {
		s.add("=");
		s.add("+");
		s.add("-");
		s.add("--");
		s.add("*");
		s.add("/");
		s.add("(");
		s.add(")");
		s.add(";");
		s.add("<");
		s.add("<=");
		s.add(">");
		s.add(">=");
		s.add("<>");
		s.add("==");
    }

    /**
     * Set being filled with the keywords in the program.
     * @param s - Set being filled with the keywords.
     */
    private void initKeywords(Set<String> s) {
    	s.add("rd");
    	s.add("wr");
    	s.add("if");
    	s.add("then");
    	s.add("else");
    	s.add("while");
    	s.add("do");
    	s.add("begin");
    	s.add("end");
    }
	
	    // constructor:
	    //   - squirrel-away source program
	    //   - initialize sets
	    public Scanner(String program) {
		this.program=program;
		pos=0;
		token=null;
		initWhitespace(whitespace);
		initDigits(digits);
		initLetters(letters);
		initLegits(legits);
		initKeywords(keywords);
		initOperators(operators);
    }

    // handy string-processing methods
    
    /**
     * This method checks to see if the next char position is greater than the length of the whole program.
     * @return True if the program is done scanning.
     */
    public boolean done() {
    	return pos>=program.length();
    }

    /**
     * Checks to see if the next character is from the same set as the current character.
     * @param s - Set of character type you are checking for.
     */
    private void many(Set<String> s) {
    	while (!done() && s.contains(program.charAt(pos)+""))
    		pos++;
    }
    
    /**
     * This method goes through the program and moves the position pointer past the char c
     * @param c - character you are scanning for and putting the pointer past
     */
    private void past(char c) {
		while (!done() && c!=program.charAt(pos))
		    pos++;
		if (!done() && c==program.charAt(pos))
		    pos++;
    }

    // scan various kinds of lexeme

    /**
     * Scans from the current position until their is no more numbers and create a token with that whole number
     */
    private void nextNumber() {
		int old=pos;
		many(digits);
		token=new Token("num",program.substring(old,pos));
    }

    /**
     * Scans from the current position to the non-keyword word
     */
    private void nextKwId() {
		int old=pos;
		many(letters);
		many(legits);
		String lexeme=program.substring(old,pos);
		token=new Token((keywords.contains(lexeme) ? lexeme : "id"),lexeme);
    }

    /**
     * Scans and checks two positions for a two char operator and one character for a one char operator.
     */
    private void nextOp() {
		int old=pos;
		pos=old+2;
		if (!done()) {
		    String lexeme=program.substring(old,pos);
		    if (operators.contains(lexeme)) {
			token=new Token(lexeme); // two-char operator
			return;
		    }
		}
		pos=old+1;
		String lexeme=program.substring(old,pos);
		token=new Token(lexeme); // one-char operator
    }

    // This method determines the kind of the next token (e.g., "id"),
    // and calls a method to scan that token's lexeme (e.g., "foo").
    public boolean next() {
		many(whitespace);
		if (done()) {
		    token=new Token("EOF");
		    return false;
		}
		String c=program.charAt(pos)+"";
		String cNext = null;
		
		if(pos + 1 < program.length()) {
			cNext = program.charAt(pos +1) + "";
		}
		
		//Built in java line comments "// comment"
		if(c.contentEquals("/")) {
			if(cNext.contentEquals("/")) {
				comment = true;
			}
		}
	
		//Added form of special commenting "#! comment"
		if(c.contentEquals("#")) {
			if(cNext.contentEquals("!")) {
				comment = true;
			}
		}
		
		if(comment == false) {
			if (digits.contains(c))
			    nextNumber();
			else if (letters.contains(c))
			    nextKwId();
			else if (operators.contains(c))
			    nextOp();
			else {
			    System.err.println("illegal character at position "+pos);
			    pos++;
			    return next();
			}
			return true;
		}
		
		if(c.contentEquals("\\")) {
			if(cNext.contentEquals("n")) {
				comment = false;
				pos++;
			}
		}
	
		pos++;
	    return next();
    }

    // This method scans the next lexeme,
    // if the current token is the expected token.
    public void match(Token t) throws SyntaxException {
		if (!t.equals(curr()))
		    throw new SyntaxException(pos,t,curr());
		next();
    }
    
    /**
     * Returns the token that it last processed
     * @return token - token last processed
     * @throws SyntaxException - if no token has been processed so far
     */
    public Token curr() throws SyntaxException {
		if (token==null)
		    throw new SyntaxException(pos,new Token("ANY"),new Token("EMPTY"));
		return token;
    }

    public int pos() { return pos; }

    // for unit testing
    public static void main(String[] args) {
		try {
		    Scanner scanner=new Scanner(args[0]);
		    while (scanner.next())
			System.out.println(scanner.curr());
		} catch (SyntaxException e) {
		    System.err.println(e);
		}
    }

}
