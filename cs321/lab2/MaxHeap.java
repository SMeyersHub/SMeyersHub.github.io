import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Creates and manages a heap using an array
 * @author Steven Meyers
 * @date 7/22/20
 */
public class MaxHeap{
	private int size = 0;
	private int capacity = 1;
	private Process[] arr = new Process[capacity];
	
	/**
	   * Checks to make sure the capcity is ok in array, if not, dobules it
	   */
	private void ensureCapacity() {
		if (size == capacity) {
			arr = Arrays.copyOf(arr, capacity * 2);
			capacity = capacity * 2;
		}
	}
	
	/**
	 * Swaps the two processes at the index
	 * @param index1 Index of first process
	 * @param index2 Index of second process
	 */
	private void swap(int index1, int index2) {
		Process element = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = element;
	}

	/**
	 * Adds the process to the heap
	 * @param p Process to be added to heap
	 */
	public void add(Process p) {
		ensureCapacity();
		arr[size] = p;
		size++;
		heapifyUp();
	}

	/**
	 * Heapifies up the heap
	 */
	public void heapifyUp() {
		int c = size - 1;
		
		while (c >= 0) {
			int p = (c - 1) / 2;
			
			Process child = arr[c];
			Process parent = arr[p];
			
			if(parent.compareTo(child) == -1) {
				swap(c, p);
				
				c = p;
			} else {
				break;
			}
		}
	}
	
	/**
	 * Heapifies up the heap starting at a certain index
	 * @param i Index to heapify up at
	 */
	public void heapifyUpAt(int i) {
		int c = i;
		
		while (c >= 0) {
			int p = (c - 1) / 2;
			
			Process child = arr[c];
			Process parent = arr[p];
			
			if(parent.compareTo(child) == -1) {
				swap(c, p);
				
				c = p;
			} else {
				break;
			}
		}
	}
	
	/**
	 * Heapifies down the heap
	 */
	public void heapifyDown() {
		int k = 0;
		int left = 1;
		
		while(left < size) {
			int max = left;
			int right = left + 1;
			if(right < size) {
				if(arr[right].compareTo(arr[left]) == 1) {
					max = right;
				}
			}
			Process parent = arr[k];
			Process child = arr[max];
			
			if(parent.compareTo(child) == -1) {
				swap(k, max);
				
				k = max;
				left = (2*k) + 1;
			} else {
				break;
			}
		}
	}
	
	/**
	 * Takes off the top of the heap 
	 * @return tempProcess - What used to be at the top of heap
	 * @throws NoSuchElementException - just incase go out of range or no element exists
	 */
	public Process remove() throws NoSuchElementException {
		if(size == 0) {
			throw new NoSuchElementException();
		} else if (size == 1) {
			Process tempProcess = arr[0];
			arr[size - 1] = null;
			size--;
			heapifyDown();
			return tempProcess;
		}
		
		Process tempProcess = arr[0];
		swap(0, size - 1);
		arr[size - 1] = null;
		size--;
		heapifyDown();
		return tempProcess;
	}

	/**
	 * Returns true if heap is empty
	 * @return true if heap size = 0
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Gets the process at node i
	 * @param i - index of node proces is at
	 * @return arr[i] - Process at node i
	 */
	public Process getProcess(int i) {
		if(size == 0) {
			throw new NoSuchElementException();
		} else if (size == 1) {
			return arr[0];
		}
		return arr[i];
	}

	/**
	 * Gets the capacity of the heap
	 * @return capacity - capacity of heap
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Gets the size of the heap
	 * @return size - size of heap (amount of items in heap, not capacity)
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Prints the heap and all the processes in it
	 * @return output - combination of all processes toStrings
	 */
	public String printHeap() {
		String output = " ";
		for(int i = 0; i < size; i++) {
			output += getProcess(i).toString();
		}
		return output;
	}
}