/**
 * This class is an object that can be put into the BTree
 * @author Steven Meyers, Riley Schmid
 * @date 8/15/20
 */
public class TreeObject{
   	 private int freq;
	    private long key;

	    /**
	     * Constructor for the BTree object.
	     *
	     * @param key  - the key of the object.
	     * @param freq - the frequency of the object.
	     */

	    public TreeObject(long key, int freq) {
	        this.key = key;
	        this.freq = freq;
	    }

	    /**
	     * Constructor for the BTree object.
	     *
	     * @param key - the key of the object.
	     */

	    public TreeObject(long key) {
	        this.key = key;
	        this.freq = 1;
	    }

	    /**
	     * Returns the key of the BTree object.
	     *
	     * @return
	     */
	    public Long getKey() {
	        return this.key;
	    }

	    /**
	     * Returns the frequency of the BTree object.
	     *
	     * @return
	     */
	    public int getFreq() {
	        return this.freq;
	    }

	    /**
	     * Increments the frequency of the BTree object.
	     */
	    public void increaseFreq() {
	        freq++;
	    }

	    /**
	     * Compares one key to another.
	     *
	     * @param obj - object being compared to.
	     * @return -1 if less than, 0 if equal, 1 if more
	     */
	    public int compareTo(TreeObject obj) {
	        if (key < obj.key)
	            return -1;
	        if (key > obj.key)
	            return 1;
	        else
	            return 0;
	    }
	    
	    /**
	     * Returns the data stored in the object (key value)
	     * @return key - data from this Object
	     */
	    public Long getData(){
	        return this.key;
	    }

	 
	    /**
	     * Increments the frequency by one
	     */
	    public void incFreq(){
	        freq = freq + 1;
	    }

	   /**
	    * Replaces the existing key data with new data
	    * @param newData - new data that is being put in the node
	    */
	    public void setData(Long newData){
	        key = newData;
	    }

	    /**
	     * Compares the data and returns true/false depending on if they are equal
	     * @param checkData - data being compared to this nodes data
	     * @return true if data is equal, false if not equal
	     */
	    public boolean isEqual(Long checkData){
	        if(key == checkData){
	            return true;
	        }
	        else{
	            return false;
	        }
	    }
}
