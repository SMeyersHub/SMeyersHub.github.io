import java.io.*;
import java.util.*;

/**
* This class represents the BTree Node
* @authors Riley Schmid, Steven Meyers
* @date 8/15/20
*/
public class BTreeNode{

    LinkedList<TreeObject> keys;
    LinkedList<Integer> child;
    private int parent;
    private int offset;
    private boolean isLeaf;
    public int numKeys;

    /**
     * Constructor for a node in the BTree
     * @param height - height of the node being created
     * @param isRoot - true if the node is a root
     * @param isLeaf - true if the node is a leaf
     * @param nodeCount - number of nodes in the tree
     */
    public BTreeNode() {
        keys = new LinkedList<TreeObject>();
        child = new LinkedList<Integer>();

        numKeys = 0;
        parent = -1;

    }

    /**
     * Returns the object given the key from the BTree
     *
     * @param key - the key in the object you want
     * @return object - object with the key assigned to it
     */
    public TreeObject getKey(int key) {
        TreeObject object = keys.get(key);
        return object;
    }

    /**
     * Adds a key using our TreeObject
     *
     * @param object - Adds the object to our key list
     */
    public void addKey(TreeObject object) {
        keys.add(object);
    }

    /**
     * Adds a key using our TreeObject and a integer
     * @param object - object being added into key list
     * @param key - int spot of object being added
     */
    public void addKey2(TreeObject object, int key) {
        keys.add(key, object);
    }

    /**
     * Removes a key from out TreeObject using a key
     * @param key - key being removed from the key list
     * @return keys.remove(key) - removed key
     */
    public TreeObject removeKey(int key) {
        return keys.remove(key);
    }

    /**
     * Returns the amount of keys in the BTree
     * @return keys - the size of the list
     */
    public LinkedList<TreeObject> getKeys() {
        return keys;
    }

    /**
     * Returns the child specified in the BTree
     * @param key - the key spot of the value getting the child
     * @return child.get(key).intValue() - the key value in an int of the child node
     */
    public int getChild(int key) {
        return child.get(key).intValue();
    }

    /**
     * Adds a child into our BTree
     * @param key - spot where child is being added to key list.
     */
    public void addChild(int key) {
        child.add(key);
    }

    /**
     * Adds a child into our BTree using two parameters
     * @param c - child int being added into the list.
     * @param key - key spot of child being added
     */
    public void addChild2(Integer c, int key) {
        child.add(key, c);
    }

    /**
     * Returns the removed child from the BTree
     * @param key - spot where child was
     * @return child.remove(key) - removed child object
     */
    public int removeChild(int key) {
        return child.remove(key);
    }

    /**
     * Returns the amount of children in the BTree
     * @return child - The amount of child objects in the list
     */
    public LinkedList<Integer> getChild() {
        return child;
    }

    /**
     * Sets the offset of the given node.
     * @param index - the index to set the node to.
     */
    public void setOffset(int index) {
        offset = index;
    }

    /**
     * Returns if the node is a leaf or not.
     * @return isLeaf - true if the node is a leaf
     */
    public boolean isLeaf() {
        return isLeaf;
    }

    /**
     * Returns the offset of the BTree.
     * @return offset - the int offset of the BTree
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Returns the number of key value for the BTree
     * @return numKeys - the number of keys in the BTre
     */
    public int getNumKeys() {
        return numKeys;
    }

    /**
     * Sets the number of keys in the BTree
     * @param numKeys - the new number of keys being set for the BTree
     */
    public void setNumKeys(int numKeys) {
        this.numKeys = numKeys;
    }

    /**
     * Sets the given node as a leaf node
     * @param isLeaf - sets the isLeaf value of the node to true or false
     */
    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * Sets the amount of parents a node has.
     * @param parent - number of parents
     */
    public void setParent(int parent) {
        this.parent = parent;
    }

    /**
     * Returns the amount of parents a node has.
     * @return parent - how many parents that node has
     */
    public int getParent() {
        return parent;
    }

      /**
     * Returns the different of the offset and BTreeNode offset
     * @return offset - difference between the BTreeNode offset and this BTreeNode
     */
    public int compareTo(BTreeNode o) {
        return offset - o.offset;
    }
 
    
    /** 
    * another add method for addChild
    * when added, it shifts all the children to the right
    * newNode is the index of the new node of the child
    * node is where it is going to be added to
    */
    public void addChild(Integer newNode, int node) {
        child.add(node, newNode);
    }
 
    /** sets the key by overwriting the current key within the array list
    * k is the key that will be overwritten
    * key is what will replace k 
    */
    public void setKey(int k, TreeObject key) {
        keys.set(k, key);
    }
    
}
