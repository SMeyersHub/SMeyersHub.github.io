import java.io.*;
import java.util.*;

/**
 * This program simulates a BTree and manages it.
 * @authors Riley Schmid, Steven Meyers
 * @date 8/15/20
 */
public class BTree implements Serializable {

    private int degree;
    private BTreeNode root;
    private int currentOffset;
    private int nodeSize;
    private int insertPoint;
    private int seqLength;
    private File binFile;
    private RandomAccessFile disk;
    private static BTreeCache cache;
    private boolean isCache = false;

    /**
     * Constructor of the BTree
     * @param degree - degree of the BTree
     * @param file - the file to be written to.
     * @param seqLength - the length of the DNA sequence
     */

    public BTree(int degree, String file, int seqLength) {
        this.degree = degree;
        this.seqLength = seqLength;
        nodeSize = (32 * degree - 3);
        currentOffset = 12;
        insertPoint = (currentOffset + nodeSize);
        BTreeNode temp = new BTreeNode();
        root = temp;
        root.setOffset(currentOffset);
        temp.setLeaf(true);
        temp.setNumKeys(0);

        try {
            binFile = new File(file);
            binFile.delete();
            binFile.createNewFile();
            disk = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException ee) {
            System.err.println("file is missing!");
            System.exit(-1);
        } catch (IOException ioe) {
            System.err.println("IO Exception occurred!");
            System.exit(-1);
        }

    }
    
    /**
     * This constructor is used for cache implementation
     * @param degree - degree of tree 
     * @param gbkfile - gbkfile being passed in
     * @param cacheSize - size of cache if cache is being used
     * @param seqlength - length of sequence 
     */
    public BTree(int degree, String gbkfile, int cacheSize, int seqlength)
    {
        nodeSize = 32 * degree - 3;
        currentOffset = 12;
        insertPoint = currentOffset + nodeSize;
        this.seqLength = seqlength;
        this.degree = degree;
        int what = cacheSize;
        	 cache = new BTreeCache(what);
        	 isCache = true;
        
        BTreeNode x = new BTreeNode();
        root = x;
        root.setOffset(currentOffset);
        x.setLeaf(true);
        x.setNumKeys(0);

        try
        {
            binFile = new File(gbkfile);
            binFile.delete();
            binFile.createNewFile();
            disk = new RandomAccessFile(gbkfile, "rw");
        }
        catch (FileNotFoundException fnfe)
        {
            System.err.println("Error: File is corrupt or missing.");
            System.exit(-1);
        }
        catch (IOException ioe)
        {
            System.err.println("Error: IO Exception occurred.");
            System.exit(-1);
        }

        writeTreeData();
    }
    
    /**
     * This constructor is used for searching cache implementation
     * @param degree - degree of tree 
     * @param gbkfile - gbkfile being passed in
     * @param useCache - true if cache is being used, false if else
     * @param cacheSize - size of cache if cache is being used
     * @param seqlength - length of sequence 
     */
    public BTree(int degree, File file, boolean useCache, int cacheSize, int seqlength)
    {
    	
    	try {
    		disk = new RandomAccessFile(file, "rw");
    	}catch(FileNotFoundException e) {
    		System.err.println("Error: File is corrupt or missing.");
            System.exit(-1);
    	}
    	 readTreeData();
    	 BTreeNode x = new BTreeNode();
         root = x;
         root.setOffset(currentOffset);
    }

    /**
     * Returns the root of the BTree
     * @return BTreeNode root - the root node of the BTree
     */
    public BTreeNode getNode() {
        return root;
    }

    /**
     * Returns the degree of the BTree.
     * @return degree - the degree of the BTree
     */
    public int getDegree() {
        return this.degree;
    }

    /**
     * Returns the value of the node associated with the specified key.
     *
     * @param key - the key of the node you are wanting to obtain
     * @return key - the value stored at that key location
     */
    public long get(long key) {
        return key; // should return the value at the key location
    }

    /**
     * Inserts a value into a specified location.
     * @param key - Key of node being inserted
     */
    public void insert(long key) {
        BTreeNode start = root;
        int i = start.getNumKeys();
        if (i == (2 * degree) - 1) {
            TreeObject object = new TreeObject(key);
            while (i > 0 && object.compareTo(start.getKey(i - 1)) == 0) {
                i--;
            }
            if (i < start.getNumKeys()) {
            }
            if (i > 0 && object.compareTo(start.getKey(i - 1)) == 0) {
                start.getKey(i - 1).increaseFreq();
                writeNode(start,start.getOffset());
            } else {
                BTreeNode n = new BTreeNode();
                n.setOffset(start.getOffset());
                root = n;
                start.setOffset(insertPoint);
                start.setParent(n.getOffset());
                n.setLeaf(false);
                n.addChild(start.getOffset());
                splitChild(n, 0, start);
                BTreeInsertNotFull(n, key);
            }
        } else {
            BTreeInsertNotFull(start, key);
        }
    }

    /**
     * Inserts a value when the node is not full.
     * @param start - node to start the insert at
     * @param key - key being inserted into the tree in a node
     */
    public void BTreeInsertNotFull(BTreeNode start, long key) {
        int i = start.getNumKeys();

        TreeObject object = new TreeObject(key);
        if (start.isLeaf()) {
            if (start.getNumKeys() != 0) {
                while (i > 0 && object.compareTo(start.getKey(i - 1)) < 0) {
                    i--;
                }
            }
            if (i > 0 && object.compareTo(start.getKey(i - 1)) == 0) {
                start.getKey(i - 1).increaseFreq();
            } else {
                start.addKey2(object, i);
                start.setNumKeys(start.getNumKeys() + 1);
            }
            writeNode(start, start.getOffset());
        } else {
            while (i > 0 && object.compareTo(start.getKey(i - 1)) < 0) {
                i--;
            }
            if (i > 0 && object.compareTo(start.getKey(i - 1)) == 0) {
                start.getKey(i - 1).increaseFreq();
                writeNode(start, start.getOffset());
                return;
            }
            int off = start.getChild(i);
            BTreeNode c = readNode(off);
            if (c.getNumKeys() == (2 * degree) - 1) {
                int j = c.getNumKeys();
                while (j > 0 && object.compareTo(c.getKey(j - 1)) < 0) {
                    j--;
                }
                if (j > 0 && object.compareTo(c.getKey(j - 1)) == 0) {
                    c.getKey(j - 1).increaseFreq();
                    writeNode(c, c.getOffset());
                    return;
                } else {
                    splitChild(start, i, c);
                    if (object.compareTo(start.getKey(i)) > 0) {
                        i++;
                    }
                }

            }
            off = start.getChild(i);
            BTreeNode child = readNode(off);
            BTreeInsertNotFull(child, key);
        }
    }

    /**
     * Splits the child node according to BTree logic.
     * @param start - starting node
     * @param i
     * @param c
     */
    public void splitChild(BTreeNode start, int i, BTreeNode c) {
        BTreeNode d = new BTreeNode();
        d.setLeaf(c.isLeaf());
        d.setParent(c.getParent());
        for (int j = 0; j < degree - 1; j++) {
            d.addKey(c.removeKey(degree));
            d.setNumKeys(d.getNumKeys() + 1);
            c.setNumKeys(c.getNumKeys() - 1);
        }
        if (!c.isLeaf()) {
            for (int j = 0; j < degree; j++) {
                d.addChild(c.removeChild(degree));
            }
        }
        start.addKey2(c.removeKey(degree - 1), i);
        start.setNumKeys(start.getNumKeys() + 1);
        c.setNumKeys(c.getNumKeys() - 1);
        if (start == root && start.getNumKeys() == 1) {
            writeNode(c, insertPoint);
            insertPoint += nodeSize;
            d.setOffset(insertPoint);
            start.addChild2(d.getOffset(), i + 1);
            writeNode(d, insertPoint);
            writeNode(start, currentOffset);
            insertPoint += nodeSize;
        } else {
            writeNode(c, c.getOffset());
            d.setOffset(insertPoint);
            writeNode(d, insertPoint);
            start.addChild2(d.getOffset(), i + 1);
            writeNode(start, start.getOffset());
            insertPoint += nodeSize;
        }
    }

    /**
     * Searches for a specific TreeObject containing a specified key.
     * @param start
     * @param key
     * @return The key of the object if found.
     */
    public TreeObject search(BTreeNode start, long key) {
        int i = 0;
        TreeObject obj = new TreeObject(key);
        while (i < start.getNumKeys() && (obj.compareTo(start.getKey(i)) > 0)) {
            i++;
        }
        if (i < start.getNumKeys() && obj.compareTo(start.getKey(i)) == 0) {
            return start.getKey(i);
        }
        if (start.isLeaf()) {
            return null;
        } else {
            int offset = start.getChild(i);
            BTreeNode y = readNode(offset);
            return search(y, key);
        }
    }

    /**
     * Converts a given long back into a DNA string.
     * @param key
     * @return The string converted from binary back to a DNA strand
     */
    public String convertToDNA(long key) {
        String binKey = Long.toBinaryString(key);
        String str = "";
        String ret = "";
        for (int i = 0; i < (seqLength * 2) - (binKey.length()); i++) {
            str += "0";
        }
        str += binKey;
        for (int i = 0; i <= str.length() - 2; i += 2) {
            if (str.substring(i, i + 2).equals("00")) {
                ret += "a";
            } else if (str.substring(i, i + 2).equals("01")) {
                ret += "c";
            } else if (str.substring(i, i + 2).equals("11")) {
                ret += "t";
            } else if (str.substring(i, i + 2).equals("10")) {
                ret += "g";
            }
        }
        return ret.substring(32-seqLength,32);
    }

    /**
     * Prints the keys using an in order traversal.
     * @param node - node being printed to the console
     */
    public void inOrderPrint(BTreeNode node) {
        if (node.isLeaf() == true) {
            for (int i = 0; i < node.getNumKeys(); i++) {
            	String out = convertToDNA(node.getKey(i).getKey());
                System.out.println(out + ": " + node.getKey(i).getFreq());
            }
            return;
        }
        for (int i = 0; i < node.getNumKeys() + 1; ++i) {
            int offset = node.getChild(i);
            BTreeNode y = readNode(offset);
            inOrderPrint(y);
            if (i < node.getNumKeys())
            	System.out.println(convertToDNA(node.getKey(i).getKey()) + ": " + node.getKey(i).getFreq());

        }

    }

    /**
     * Writes a node to the disk.
     * @param node - node being added to disk
     * @param off - offset of the node
     */
    public void writeNode(BTreeNode nd, int off) {
        	if(cache != null) {
        		BTreeNode cnode = cache.add(nd, off);
        		if(cnode != null) {
        			writeNodeToFile(cnode, cnode.getOffset());
        		}
        	}else {
        		writeNodeToFile(nd, off);
        	}
    }
    /**
     * Writes a node to a file (example: dump)
     * @param nd - node being written to the file
     * @param offset - offset of that node
     */
    private void writeNodeToFile(BTreeNode nd, int offset)
    {
    	int i = 0;
        try {
    	writeNodeData(nd, nd.getOffset());
        disk.writeInt(nd.getParent());
        for (i = 0; i < 2 * degree - 1; i++) {
            if (i < nd.getNumKeys() + 1 && !nd.isLeaf()) {
                disk.writeInt(nd.getChild(i));
            } else if (i >= nd.getNumKeys() + 1 || nd.isLeaf()) {
                disk.writeInt(0);
            }
            if (i < nd.getNumKeys()) {
                long data = nd.getKey(i).getKey();
                disk.writeLong(data);
                int frequency = nd.getKey(i).getFreq();
                disk.writeInt(frequency);
            } else if (i >= nd.getNumKeys() || nd.isLeaf()) {
                disk.writeLong(0);
            }

        }
        if (i == nd.getNumKeys() && !nd.isLeaf()) {
            disk.writeInt(nd.getChild(i));
        }
        } catch (IOException ioe) {

        }
    }
    
    /**
     * Writes tree data to the disk.
     */
    public void writeTreeData() {
        try {
            disk.seek(0);
            disk.writeInt(seqLength);
            disk.writeInt(degree);
            disk.writeInt(32 * degree - 3);
            disk.writeInt(12);
        } catch (IOException ioe) {
            System.err.println("IO Exception occurred!");
            System.exit(-1);
        }
    }

    /**
     * Writes node data to the disk.
     * @param start - starting to base the offset off of
     * @param off - offset of node being written
     */
    public void writeNodeData(BTreeNode start, int off) {
        try {
            disk.seek(off);
            disk.writeBoolean(start.isLeaf());
            disk.writeInt(start.getNumKeys());
        } catch (IOException ioe) {
            System.err.println("IOException!");
            System.exit(-1);
        }
    }

    /**
     * Reads a node for the data it holds.
     * @param off - offset of node being read
     * @return n - node being read
     */
    public BTreeNode readNode(int off) {
        BTreeNode n = null;
        if(cache != null) {
        	n = cache.readNode(off);
        }
        if(n != null) {
        	return n;
        }
        n = new BTreeNode();
        TreeObject object = null;
        n.setOffset(off);
        int k = 0;
        try {
            disk.seek(off);
            boolean isLeaf = disk.readBoolean();
            n.setLeaf(isLeaf);
            int temp = disk.readInt();
            n.setNumKeys(temp);
            int parent = disk.readInt();
            n.setParent(parent);
            for (k = 0; k < (2 * degree) - 1; k++) {
                if (k < n.getNumKeys() + 1 && !n.isLeaf()) {
                    int child = disk.readInt();
                    n.addChild(child);
                } else if (k >= n.getNumKeys() + 1 || n.isLeaf()) {
                    disk.seek(disk.getFilePointer() + 4);
                }
                if (k < n.getNumKeys()) {
                    long value = disk.readLong();
                    int frequency = disk.readInt();
                    object = new TreeObject(value, frequency);
                    n.addKey(object);
                }
            }
            if (k == n.getNumKeys() && !n.isLeaf()) {
                int child = disk.readInt();
                n.addChild(child);
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            System.exit(-1);
        }

        return n;
    }
    
    /**
     * Tests to see if a BTreeNode is full or not
     * @param degree - degree of BTree
     * @param x - Node being tested to see if it is full
     * @return true if the BTree is full, False if not.
     */
    public boolean isFull(int degree, BTreeNode x){
        //keys is equal to 2 times the degree -1
        if(x.getNumKeys() == ((2*degree)-1)){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Gets values to be used in DNA
     * @param goal
     * @param seqlength
     * @return The value from the goal you are trying to obtain
     */
    public static String getValues(String goal, int seqlength) {
    	String item = "";
    	int a = goal.length();
    	for(int i = 0; i<a; i++) {
    		if(i >= a-seqlength) {
    			item += goal.charAt(i);
    		}
    	}
    	return item;
    }
    
    /**
     * Flush cache so that nothing is left.
     */
    public void flushCache()
    {
        if (cache != null)
        {
            for (BTreeNode cnode : cache)
                writeNodeToFile(cnode, cnode.getOffset());
        }
    }
    
    /**
     * Reads the data from the tree file
     */
    public void readTreeData() {
    	 try {
             disk.seek(0);
             degree = disk.readInt();
             nodeSize = disk.readInt();
             currentOffset = disk.readInt();
         } catch (IOException ioe) {
             System.err.println("IO Exception occurred!");
             System.exit(-1);
         }
    }
}
