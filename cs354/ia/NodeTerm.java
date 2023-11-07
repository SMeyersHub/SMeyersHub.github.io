/**
 * This class scans through a provided program and tokenizes it.
 * @authors James Buffenbarger, Steven Meyers
 * @date 10/5/2020
 */
public class NodeTerm extends Node {

    private NodeFact fact; 
    private NodeMulop mulop;
    private NodeTerm term;

    /**
     * Constructor for a NodeTerm
     * @param fact - fact for if the term is a fact
     * @param mulop - mulop for if the term is a mulop
     * @param term - term for if the term is another term for tree purposes
     */
    public NodeTerm(NodeFact fact, NodeMulop mulop, NodeTerm term) {
	this.fact=fact;
	this.mulop=mulop;
	this.term=term;
    }

    /**
     * Appends a term onto the current term.
     * @param term - the term that is being appended onto this term object, basically copies a term over.
     */
    public void append(NodeTerm term) {
		if (this.term==null) {
		    this.mulop=term.mulop;
		    this.term=term;
		    term.mulop=null;
		} else
		    this.term.append(term);
    }

    /**
     * @return The multiplication of a term and a fact or simply a fact's int value
     */
    public double eval(Environment env) throws EvalException {
		return (term==null
		    ? fact.eval(env)
		    : mulop.op(term.eval(env),fact.eval(env)));
    }

}
