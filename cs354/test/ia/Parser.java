// This class is a recursive-descent parser,
// modeled after the programming language's grammar.
// It constructs and has-a Scanner for the program
// being parsed.

import java.util.*;

/**
 * This class is a recursive-dependent parser.
 * @author James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class Parser {

    private Scanner scanner;
    
    /**
     * This method checks to see that string s is a correct type for its spot.
     * @param s - token being type checked
     * @throws SyntaxException - If some token is not in the syntax of the program.
     */
    private void match(String s) throws SyntaxException {
    	scanner.match(new Token(s));
    }

    /**
     * This program returns the token it is currently on (last processed)
     * @return scanner.curr() - token that was last processed by the scanner
     * @throws SyntaxException - if no token has been processed so far
     */
    private Token curr() throws SyntaxException {
    	return scanner.curr();
    }

    /**
     * Return the position of the scanner in the file
     * @return scanner.pos() - the position of the scanner in the file
     */
    private int pos() {
    	return scanner.pos();
    }

    /**
     * Checks to see if the current token fits the grammar classifications for a Mulop.
     * @return NodeMulop - returns a Mulop object containing the token and position 
     * or null if the current token is not a mul operator.
     * @throws SyntaxException - If the token formatting is not correct
     */
    private NodeMulop parseMulop() throws SyntaxException {
		if (curr().equals(new Token("*"))) {
		    match("*");
		    return new NodeMulop(pos(),"*");
		}
		if (curr().equals(new Token("/"))) {
		    match("/");
		    return new NodeMulop(pos(),"/");
		}
		return null;
    }

    /**
     * Checks to see if the current token fits the grammar classifications for an addop.
     * @return NodeAddop - returns a Nodeaddop object containing the token and position 
     * or null if the current token is not a add operator.
     * @throws SyntaxException - If the token formatting is not correct
     */
    private NodeAddop parseAddop() throws SyntaxException {
		if (curr().equals(new Token("+"))) {
		    match("+");
		    return new NodeAddop(pos(),"+");
		}
		if (curr().equals(new Token("-"))) {
		    match("-");
		    return new NodeAddop(pos(),"-");
		}
		
		if(curr().equals(new Token("--"))) {
			match("--");
			return new NodeAddop(pos(), "--");
		}
		return null;
    }
    
    /**
     * Checks to see if the current token fits the grammar classifications for a fact.
     * @return NodeFact... - depending on what the fact is parsed to be, returns different NodeFact
     * @throws SyntaxException - if the token is not in the program
     */
    private NodeFact parseFact() throws SyntaxException {
		if (curr().equals(new Token("("))) {
		    match("(");
		    NodeExpr expr=parseExpr();
		    match(")");
		    return new NodeFactExpr(expr);
		}
		if (curr().equals(new Token("id"))) {
		    Token id=curr();
		    match("id");
		    return new NodeFactId(pos(),id.lex());
		}
		Token num=curr();
		match("num");
		return new NodeFactNum(num.lex());
    }
    
    /**
     * Checks to see if the current token fits the grammar classifications for a term.
     * @return NodeTerm - can vary depending on what the current token is parsed as.
     * @throws SyntaxException - token is null or not in the program's language
     */
    private NodeTerm parseTerm() throws SyntaxException {
		NodeFact fact=parseFact();
		NodeMulop mulop=parseMulop();
		if (mulop==null)
		    return new NodeTerm(fact,null,null);
		NodeTerm term=parseTerm();
		term.append(new NodeTerm(fact,mulop,null));
		return term;
    }
    
    /**
     * Checks to see if the current token fits the grammar classifications for an expression.
     * @return NodeExpr - some form of a NodeExpr varying depending on what the current token is parsed as.
     * @throws SyntaxException - if the token is null or not in the program's language.
     */
    private NodeExpr parseExpr() throws SyntaxException {
		NodeTerm term=parseTerm();
		NodeAddop addop=parseAddop();
		if (addop==null)
		    return new NodeExpr(term,null,null);
		NodeExpr expr=parseExpr();
		expr.append(new NodeExpr(term,addop,null));
		return expr;
    }

    /**
     * Checks to see if the current token fits the grammar classifications for an assignment.
     * @return NodeAssn - a form of NodeAssn that represents an assignment
     * @throws SyntaxException - If some token is not in the syntax of the program.
     */
    private NodeAssn parseAssn() throws SyntaxException {
		Token id=curr();
		match("id");
		match("=");
		NodeExpr expr=parseExpr();
		NodeAssn assn=new NodeAssn(id.lex(),expr);
		return assn;
    }

    /**
     * Checks to see if the current token fits the grammar classifications for a stmt.
     * @return NodeStmt - a NodeStmt with of some form.
     * @throws SyntaxException - If some token is not in the syntax of the program.
     */
    private NodeStmt parseStmt() throws SyntaxException {
    	if(curr().equals(new Token("rd"))) {
    		match("rd");
    		if(curr().equals(new Token("id"))) {
    			Token id=curr();
    		    match("id");
    		    java.util.Scanner scan = new java.util.Scanner(System.in);
        		NodeRd rd = new NodeRd(id.lex(), scan.nextDouble());
        		scan.close();
        		return rd;
    		}
    	} 
    	if(curr().equals(new Token("wr"))) {
    		match("wr");
    		NodeExpr expr = parseExpr();
    		return(new NodeWr(expr));
    	} 
    	if(curr().equals(new Token("if"))) {
    		match("if");
    		NodeBoolExpr boolexpr=parseBoolExpr();
    		match("then");
    		NodeStmt stmt = parseStmt();
    		if(curr().equals(new Token("else"))) {
    			match("else");
    			NodeStmt elseStmt = parseStmt();
    			return(new NodeIfElse(boolexpr, stmt, elseStmt));
    		}
    		return(new NodeIf(boolexpr, stmt));
    	} 
    	if(curr().equals(new Token("while"))) {
    		match("while");
    		NodeBoolExpr boolexpr=parseBoolExpr();
    		match("do");
    		NodeStmt stmt=parseStmt();
    		NodeWhile whileNode = new NodeWhile(boolexpr, stmt);
    		return whileNode;
    	} 
    	if(curr().equals(new Token("begin"))) {
    		match("begin");
		    NodeBlock block=parseBlock();
		    match("end");
		    NodeStmt stmt=new NodeStmtBlock(block);
		    return stmt;
    	} 
		NodeAssn assn=parseAssn();
		NodeStmt stmt=new NodeStmtAssn(assn);
		return stmt;
    }

    /**
     * Parses a boolean expression from the program and returns the results
     * @return Results of the boolean expression (0.0 if false)
     * @throws SyntaxException - If some token is not in the syntax of the program.
     */
    private NodeBoolExpr parseBoolExpr() throws SyntaxException {
    	NodeExpr expr1=parseExpr();
    	NodeRelop relop=parseRelop();
    	NodeExpr expr=parseExpr();
		return (new NodeBoolExpr(expr1,relop,expr));
	}

    /**
     * Parses a relative operator from the program for use in a boolean expression
     * @return Returns a relop with pos.
     * @throws SyntaxException - If some token is not in the syntax of the program.
     */
	private NodeRelop parseRelop() throws SyntaxException{
		if (curr().equals(new Token("<"))) {
		    match("<");
		    return new NodeRelop(pos(),"<");
		}
		if (curr().equals(new Token("<="))) {
		    match("<=");
		    return new NodeRelop(pos(),"<=");
		}
		if(curr().equals(new Token(">"))) {
			match(">");
			return new NodeRelop(pos(), ">");
		}
		if(curr().equals(new Token(">="))) {
			match(">=");
			return new NodeRelop(pos(), ">=");
		}
		if(curr().equals(new Token("<>"))) {
			match("<>");
			return new NodeRelop(pos(), "<>");
		}
		if(curr().equals(new Token("=="))) {
			match("==");
			return new NodeRelop(pos(), "==");
		}
		return null;
	}

	/**
	 * Parses a block in the program.
	 * @return The results of the block
	 * @throws SyntaxException - If some token is not in the syntax of the program.
	 */
	private NodeBlock parseBlock() throws SyntaxException {
		NodeStmt stmt = parseStmt();
		if(curr().equals(new Token(";"))) {
			match(";");
			NodeBlock block = new NodeBlock(parseBlock(), stmt);
			return block;
		} else {
			NodeBlock block=new NodeBlock(stmt);
			return block;	
		}
	}

	/**
	 * Parses the whole program
	 * @return The program node.
	 * @throws SyntaxException - If some token is not in the syntax of the program.
	 */
	private NodeProg parseProg() throws SyntaxException {
		NodeBlock block=parseBlock();
		NodeProg prog = new NodeProg(block);
		return prog;
	}
	
	/**
     * Parses a program and returns the statement of it.
     * @param program - Text from program being entered in.
     * @return stmt - a parsed version of the statement originally put in.
     * @throws SyntaxException - If some token is not in the syntax of the program.
     */
    public Node parse(String program) throws SyntaxException {
		scanner=new Scanner(program);
		scanner.next();
		NodeProg stmt=parseProg();
		match("EOF");
		return stmt;
    }
}
