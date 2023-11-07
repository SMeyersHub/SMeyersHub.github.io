import java.util.Iterator;
import java.util.LinkedList;

/**
 * The cache class used by BTree
 * @authors Riley Schmid, Steven Meyers
 * @date 8/15/20
 */
public class BTreeCache implements Iterable<BTreeNode>
{

    private final int MAX_SIZE;
    private int numHits, numMisses;
    private LinkedList<BTreeNode> list;
    
    /**
     * Constructor for the BTree Cache
     * @param MAX - max size that the cache can obtain
     */
    public BTreeCache(int MAX)
    {
        MAX_SIZE = MAX;
        list = new LinkedList<BTreeNode>();
    }
    
    /**
     * Returns the node that was added to the BTree
     * @param nodeToAdd - Node being added to BTree
     * @param offset - offset of that node
     * @return nodeToReturn - the node being added to the BTree
     */
    public BTreeNode add(BTreeNode nodeToAdd, int offset)
    {
        BTreeNode nodeToReturn = null;
        if (isFull())
            nodeToReturn = list.removeLast();
        list.addFirst(nodeToAdd);
        return nodeToReturn;
    }

    /**
     * Clears the cache
     */
    public void clearCache()
    {
        list.clear();
    }
    
    /**
     * returns the node that was read in to the front
     * @param offset - offset of the searching for the node
     * @return n or null, N is the node that is being read, null if a node cant be found.
     */
    public BTreeNode readNode(int offset)
    {
        for (BTreeNode n : list)
        {
            if (n.getOffset() == offset)
            {
                list.remove(n);
                list.addFirst(n);
                increaseNumHits();
                return n;
            }
        }
        increaseNumMisses();
        return null;
    }
    
    /**
     * Returns the reference number
     * @return totalReferences - number of hits and misses added together
     */
    public int getNumReferences()
    {
        return numHits + numMisses;
    }

    /**
     * Increments number of hits
     */
    private void increaseNumHits()
    {
        numHits++;
    }
    
    /**
     * Increments the number of misses
     */
    private void increaseNumMisses()
    {
        numMisses++;
    }
    
    /**
     * Returns the number of hits
     * @return numHits - number of references that hit their node.
     */
    public int getNumHits()
    {
        return numHits;
    }
    
    /**
     * Returns the number of misses
     * @return numMisses - number of references that miss their node.
     */
    public int getNumMisses()
    {
        return numMisses;
    }
    
    /**
     * Returns the ratio of hits to misses
     * @return ratio - ratio of hits to misses.
     */
    public double getHitRatio()
    {
        double ratio = ((double) getNumHits()) / getNumReferences();
        return ratio;
    }
    
    /**
     * Returns the size of list
     * @return list.size - size of the list
     */
    public int getSize()
    {
        return list.size();
    }
    
    /**
     * Returns true if cache is currently full
     * @return - true if the getSize function == max size of cache
     */
    public boolean isFull()
    {
        return getSize() == MAX_SIZE;
    }
    
    /**
     * Returns the iterator
     */
    @Override
    public Iterator<BTreeNode> iterator()
    {
        return list.iterator();
    }
}     