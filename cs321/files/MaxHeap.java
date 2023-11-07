import java.util.Arrays;
import java.util.NoSuchElementException;

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
	
	  private void swap(int index1, int index2) {
		 	Process element = arr[index1];
		 	arr[index1] = arr[index2];
		 	arr[index2] = element;
	  }


	public void add(Process p) {
		ensureCapacity();
		arr[size] = p;
		size++;
		heapifyUp();
	}

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

	public boolean isEmpty() {
		return size == 0;
	}

	public Process getProcess(int i) {
		if(size == 0) {
			throw new NoSuchElementException();
		} else if (size == 1) {
			return arr[0];
		}
		return arr[i];
	}

	public int getCapacity() {
		return capacity;
	}
	
	public int getSize() {
		return size;
	}
	
	public String printHeap() {
		String output = " ";
		for(int i = 0; i < size; i++) {
			output += getProcess(i).toString();
		}
		return output;
	}
}