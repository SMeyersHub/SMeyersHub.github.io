import java.util.Comparator;

/**
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator.
 * As written uses Mergesort algorithm.
 *
 * @author CS221
 * @author Steven Meyers - editted the mergesort methods
 */
public class Sort
{	
	/**
	 * Returns a new list that implements the IndexedUnsortedList interface. 
	 * As configured, uses WrappedDLL. Must be changed if using 
	 * your own IUDoubleLinkedList class. 
	 * 
	 * @return a new list that implements the IndexedUnsortedList interface
	 */
	private static <T> IndexedUnsortedList<T> newList() 
	{
		return new WrappedDLL<T>(); //TODO: replace with your IUDoubleLinkedList for extra-credit
	}
	
	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @see IndexedUnsortedList 
	 */
	public static <T extends Comparable<T>> void sort(IndexedUnsortedList<T> list) 
	{
		mergesort(list);
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using given Comparator.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 * @see IndexedUnsortedList 
	 */
	public static <T> void sort(IndexedUnsortedList <T> list, Comparator<T> c) 
	{
		mergesort(list, c);
	}
	
	/**
	 * Mergesort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface, 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 */
	private static <T extends Comparable<T>> void mergesort(IndexedUnsortedList<T> list)
	{
		//write method here and then swap out compareTo with c.compare in the lower method.
		//if list is not empty or a size of 1, going to use recursion.
		if(list.size() != 0 && list.size() != 1) {
			//Make two sublists for each side of the middle of list.
			IndexedUnsortedList<T> sublistOne = newList();
			IndexedUnsortedList<T> sublistTwo = newList();
			//Find the middle of the list, make an int value to use for for loops to fill the sublists.
			int origlistSize = list.size();
			int sublistSize = list.size()/2;
			
			//Start the counter at the beginning of the list and add it to the first sublist.
			for(int i = 0; i < sublistSize; i++) {
				sublistOne.add(list.removeFirst());
			}
			
			//Start the counter at the middle of list and count up, removing values from list and adding them to sublist.
			for(int i = 0; i < origlistSize - sublistSize; i++) {
				sublistTwo.add(list.removeFirst());
			}
			//Recursively call this on both lists till they ideally would be sorted in some form.
			mergesort(sublistOne);
			mergesort(sublistTwo);
			
			
			//MERGE SECTION
			int cursor = 0;
			int cursorTwo = 0;
			IndexedUnsortedList<T> tempList = newList();
			while(cursor < sublistOne.size() && cursorTwo < sublistTwo.size()) {
				if(sublistOne.get(cursor).compareTo(sublistTwo.get(cursorTwo)) < 0) {
					tempList.add(sublistOne.get(cursor));
					cursor++;
				} else {
					tempList.add(sublistTwo.get(cursorTwo));
					cursorTwo++;
				}
			}
			
			//copy rest of first sublist
			while(cursor < sublistOne.size()) {
				tempList.add(sublistOne.get(cursor));
				cursor++;
			}
			
			//copy rest of second sublist
			while(cursorTwo < sublistTwo.size()) {
				tempList.add(sublistTwo.get(cursorTwo));
				cursorTwo++;
			}
			
			
			for(int i = 0; i < origlistSize; i++) {
				list.add(tempList.get(i));
			}
		}
	}
		
	/**
	 * Mergesort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface,
	 * using the given Comparator.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 */
	private static <T> void mergesort(IndexedUnsortedList<T> list, Comparator<T> c)
	{
		//write method here and then swap out compareTo with c.compare in the lower method.
				//if list is not empty or a size of 1, going to use recursion.
				if(list.size() != 0 && list.size() != 1) {
					//Make two sublists for each side of the middle of list.
					IndexedUnsortedList<T> sublistOne = newList();
					IndexedUnsortedList<T> sublistTwo = newList();
					//Find the middle of the list, make an int value to use for for loops to fill the sublists.
					int origlistSize = list.size();
					int sublistSize = list.size()/2;
					
					//Start the counter at the beginning of the list and add it to the first sublist.
					for(int i = 0; i < sublistSize; i++) {
						sublistOne.add(list.remove(0));
					}
					
					//Start the counter at the middle of list and count up, removing values from list and adding them to sublist.
					for(int i = 0; i < origlistSize - sublistSize; i++) {
						sublistTwo.add(list.remove(0));
					}
					//Recursively call this on both lists till they ideally would be sorted in some form.
					mergesort(sublistOne, c);
					mergesort(sublistTwo, c);
					
					
					//MERGE SECTION
					//set two cursors, one to track left side and one to track right side
					int cursor = 0;
					int cursorTwo = 0;
					//set up a temp list to move over to the final list once done.
					IndexedUnsortedList<T> tempList = newList();
					
					//Set a while loop to look through all of the sublists and check to see if they are greater/less than
					while(cursor < sublistOne.size() && cursorTwo < sublistTwo.size()) {
						if(c.compare(sublistOne.get(cursor), sublistTwo.get(cursorTwo)) < 0) {
							tempList.add(sublistOne.get(cursor));
							cursor++;
						} else {
							tempList.add(sublistTwo.get(cursorTwo));
							cursorTwo++;
						}
					}
					
					//copy rest of first sublist
					while(cursor < sublistOne.size()) {
						tempList.add(sublistOne.get(cursor));
						cursor++;
					}
					
					//copy rest of second sublist
					while(cursorTwo < sublistTwo.size()) {
						tempList.add(sublistTwo.get(cursorTwo));
						cursorTwo++;
					}
					
					
					for(int i = 0; i < origlistSize; i++) {
						list.add(tempList.get(i));
					}
				}

	}
	
}
