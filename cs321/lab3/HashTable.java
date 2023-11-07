
/**
 * This class manages a hash table and allows for customization
 * @author Steven Meyers
 * @date 8/5/2020
 */
public class HashTable {
	HashObject<Object>[] hashTable;
	int tablesize = 0;
	double loadFactor = 0;
	int items = 0;
	int inputType = 0;
	int hashMode = 0;
	int totalProbes = 0;
	int totalDuplicates = 0;
	int debugType = 0;
	@SuppressWarnings("unchecked")
	HashTable(int size, int inputType, int hashMode, int debugType) {
		this.tablesize = size;
		this.hashTable = new HashObject[size];
		this.inputType = inputType;
		this.hashMode = hashMode;
		this.debugType = debugType;
	}
	
	/**
	 * 
	 * @param key object being passed in to be inserted
	 * @return either index of insertion or -1 if full.
	 */
	public int hashInsert(Object key) {
		HashObject<Object> insertObj = new HashObject<Object>(key);
		if(hashMode == 1) {
			int i = 0;
			while(i < tablesize) {
				int hashOne = PositiveMod(key.hashCode(), tablesize);
				//System.out.println("Starting index " + startIndex + " Key: " + key.toString());
				int j = hashOne + i;
				if(j >= tablesize) {
					j = j - tablesize;
				}
				if(hashTable[j] == null) {
					hashTable[j] = insertObj;
					insertObj.incrementProbes();
					totalProbes++;
					items++;
					updateLoad();
					return j;
				} else if(hashTable[j].equals(key)) {
					hashTable[j].incrementDuplicate();
					totalDuplicates++;
					return j;
				} else {
					insertObj.incrementProbes();
					totalProbes++;
					i++;
				}
			}
			return -1;
		}
		if(hashMode == 2) {
			int i = 0;
			int hashOne = PositiveMod(key.hashCode(), tablesize);
			int hashTwo = PositiveMod(key.hashCode(), tablesize-2);
			int j = hashOne;
			while(i < tablesize) {
				if(hashTable[j] == null) {
					hashTable[j] = insertObj;
					insertObj.incrementProbes();
					totalProbes++;
					items++;
					updateLoad();
					return j;
				} else if(hashTable[j].equals(key)) {
					hashTable[j].incrementDuplicate();
					totalDuplicates++;
					return j;
				} else {
					j += hashTwo + 1;
					j = j % tablesize;
					insertObj.incrementProbes();
					totalProbes++;
					i++;
				}
			}
			return -1;
		}
		return -1;
	}
	
	public int PositiveMod(int dividend, int divisor) {
		int value = dividend % divisor;
		if(value < 0) {
			value += divisor;
		}
		return value;
	}
	public int getSize() {
		return tablesize;
	}
	
	public double getLoad() {
		return loadFactor;
	}
	
	private void updateLoad() {
		loadFactor =((double)items/(double)tablesize); 
	}
	
	public String toString() {
		String string = "";
		if(debugType == 0) {
			if(hashMode == 1) {
				string += "Using Linear Hashing.... \n";
			} else if(hashMode == 2) {
				string += "Using Double Hashing.... \n";
			}
			
			int totalProbesTwo = 0;
			for(int i = 0; i < tablesize; i++) {
				if(hashTable[i] != null) {
					totalProbesTwo += hashTable[i].getProbeCount();
				}
			}
			string += "Input " + (items + totalDuplicates) + " elements, of which " + totalDuplicates + " duplicates \nload factor = " + getLoad() + ", Avg. no. of probes " + ((double)totalProbesTwo/(double)items);
			System.out.println();
			
		} else if(debugType == 1) {
			//int totalProbesTwo = 0;
			for(int i = 0; i < tablesize; i++) {
				if(hashTable[i] != null) {
					if(string.isEmpty()) {
						string += "table[" + i + "]: " + hashTable[i].toString();
					} else {
						string += "\ntable[" + i + "]: " + hashTable[i].toString();
					}
					//totalProbesTwo += hashTable[i].getProbeCount();
				}
			}
			//string += "Table size: " + hashTable.length + " Items in table: " + items + " Load factor: " + getLoad() + "\n" + "Total Probes: " + totalProbes + " Total Duplicates: " + totalDuplicates + 
					//" Average number of probes: " + ((double)totalProbesTwo/(double)items);
		}
		return string;
	}
}
