/**
 * Simulates a priority queue using a heap using an array
 * @author Steven Meyers
 * @date 7/17/20
 */
public class PQueue {
	//Create heap object
	MaxHeap heap = new MaxHeap();
	
	/**
	 * Adds a process to heap
	 * @param p Process to be added into heap
	 */
	public void enPQueue(Process p) {
		heap.add(p);
		heap.heapifyUp();
	}
	
	/**
	 * Returns a process to the top of the heap
	 * @return the Process on the top of the heap
	 */
	public Process dePQueue() {
		return heap.remove();
	}
	
	/**
	 * Returns true if heap is empty
	 * @return true if heap is empty
	 */
	public boolean isEmpty() {
		if(heap.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void print() {
		System.out.print(heap.printHeap()); 
	}

	/**
	 * Updates all the items in the list by incrementing their timeNotProcessed and if necessary 
	 * their priorities.
	 * @param timeToIncrementLevel The time it takes for a process to increment the priority
	 * @param maxLevel The max level a priority can be
	 */
	public void update(int timeToIncrementLevel, int maxLevel) {
		for (int i = 0 ; i < heap.getSize(); i++) {
			Process process = heap.getProcess(i);
			process.incrementTimeNotProcessed();
			if(process.getTimeNotProcessed() >= timeToIncrementLevel) {
				process.resetTimeNotProcessed();
				if(process.getPriority() < maxLevel) {
					process.incrementPriority();
					heap.heapifyUpAt(i);
				}
			}
		}
	}
}