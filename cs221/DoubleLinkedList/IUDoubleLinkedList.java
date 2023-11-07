import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
 /**
  * Class that allows for a double linked list to be built and altered using nodes from the DLLNode class. This class
  * includes methods that allow adding at any position, removal of a node,as well as getting and setting nodes.
  * @author Steven Meyers
  *
  * @param <T> - generic type so that the list can be used with any data type.
  */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T>, Iterable<T> {
	private int count; // Total number of elements
	private DLLNode<T> head, tail; //The nodes that are the first/last
	private int modCount; // number of the mods to the list.
	
	@Override
	public void addToFront(T element) {
		
		//Set the new node that is going to have the element in it
		DLLNode<T> newNode = new DLLNode<T>(element);
		
		//Attach it to the head and then move the head back one (to it).
		newNode.setNext(head);
		if(!isEmpty()) {
			head.setPrevious(newNode);
		}
		head = newNode;
		
		//If the list is empty, make the tail also be the new node as its the only node.
		if(count == 0) {
			tail = newNode;
		}
		count++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		DLLNode<T> newNode = new DLLNode<T>(element);
		
		//test if list is empty and assign head and tail
		if (count == 0) {
			head = newNode;
		} else {
			//If list is not empty, assign tail's next node to newNode.
			tail.setNext(newNode); //Only works if the count != 0
			//Assign the newNodes previous node to tail the current tail.
			newNode.setPrevious(tail);
		}
		
		//Move the tail forward one (to the newly attached node);
		tail = newNode;
		count++;
		modCount++;
	}


	@Override
	public void add(T element) {
		addToRear(element);

	}

	@Override
	public void addAfter(T element, T target) {
		//Check to see if the target element is within the bounds of list or if list is empty
		if(!contains(target)) {
			throw new NoSuchElementException("Target element is not found in the SLL");
		}
		
		//make a node that will be inserted into the list.
		DLLNode<T> newNode = new DLLNode<T>(element);
		DLLNode<T> current = head;
		DLLNode<T> next = current.getNext();
		
		//Check to see if the element of current node = target element, if not, keep moving current and next forward.
		while(current.getElement() != target) {
			current = current.getNext();
			next = current.getNext();
		}
		
		//if next is the end of list, simply add to rear. If not, insert the node between current and next.
		if(next == null) {
			addToRear(element);
		} else {
			newNode.setNext(next);
			current.setNext(newNode);
			count++;
		}
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		
		//Check the index to make sure it is in bounds
		if (index < 0 || index > count) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		
		//Set the new node that is going to have the element in it
		DLLNode<T> newNode = new DLLNode<T>(element);
		
		//Check index position to see if at head or tail
		if(index == 0) {
			newNode.setNext(head);
			if(!isEmpty()) {
				head.setPrevious(newNode);
			}
			head = newNode;
		} else {
			
			//Establish a temp node to go through list.
			DLLNode<T> current = head;
			
			//Move all variables back so that the new node can be inserted.
			for (int i = 0; i < index - 1; i++) {
				current = current.getNext();
			}
			
			//Set up a temp node with the next node for the current temp node
			DLLNode<T> next = current.getNext();
			
			//Insert the node between current node and next temp node, and set up its previous call
			current.setNext(newNode);
			newNode.setPrevious(current);
			newNode.setNext(next);
		}
		
		if((count == 0) || (index == count)) {
			tail = newNode;
		}
		count++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		//Check to see if element is empty. If not, remove the first element.
		if(count == 0) {
			throw new NoSuchElementException("Empty list. No element available to remove.");
		}
		T item = remove(0);
		modCount++;
		return item;
	}

	@Override
	public T removeLast() {
		//Check to see if element is empty. If not, remove last element
		if(count == 0) {
			throw new NoSuchElementException("Empty list. No element available to remove.");
		}
		T item = remove(count - 1);
		modCount++;
		return item;
	}

	@Override
	public T remove(T element) {
		//Assign the current marker to head and the previous to the null behind head
		boolean found = false; 
		DLLNode<T> current = head;
		DLLNode<T> previous = null; 
		
		//sort through and find the element if you can
		while(!found && current != null) {
			//Check to see if current == element.
			if(current.getElement().equals(element)) {
				found = true; 
			}
			else {
				//If element != element wanted, move previous and current forward one
				previous = current; 
				current = current.getNext(); 
			}
		}
		
		//If element wasn't found, throws a no such element exception
		if(!found) {
			throw new NoSuchElementException("Can't find item - remove item method"); 
		}
		
		//Assign a next node to the current's next node.
		DLLNode<T> next = current.getNext();
		
		//If the previous node isn't null, link it to current's next node.
		if(previous != null) {
			previous.setNext(next);
		} 
		
		//If the next node isn't null, link it to current's previous node.
		if(next != null) {
			next.setPrevious(previous);
		} 
		//check to see if the head or tail must be moved if they are being removed.
		if(current.equals(head)) {
			head = next;
		} else if (current.equals(tail)) {
			tail = previous;
			previous.setNext(null);
		}
		
		//Detach current from the current node list by making its links = null
		current.setNext(null);
		current.setPrevious(null);
		count--;
		modCount++; 
		return current.getElement();
	}

	@Override
	public T remove(int index) {
		
		//Check to see if the index is in the list's boundaries
		if(index < 0 || index >= count){
			throw new IndexOutOfBoundsException("Invalid index - remove at pos method "); 
		}
		T item = null;
		
		//If the index is the head, move head forward one and remove the head.
		if(index == 0) {
			DLLNode<T> next = head.getNext(); 
			head.setNext(null);
			item = head.getElement(); 
			head = next;
			if(count == 1) {
				tail = null; 
			}
		}
		else
		{
			DLLNode<T> current = head; 
			for(int i = 0; i < index - 1; i++) {
				current = current.getNext(); 
			}
			
			DLLNode<T> next = current.getNext(); 
			current.setNext(next.getNext());
			if(next.getNext() != null) {
				next.getNext().setPrevious(current);
			}
			next.setNext(null);
			next.setPrevious(null);
			item = next.getElement(); 
			if(index == count - 1)
			{
				tail = current; 
			}
		}
		count--;
		modCount++; 
		return item;
	}

	@Override
	public void set(int index, T element) {
		if(index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("Can not set - The index is out of bounds.");
		}
		
		//set the current node to head so that you can move through the SLL
		DLLNode<T> current = head;
		
		//Move through the list to find the index, and replace its element.
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		current.setElement(element);
		modCount++;
	}

	@Override
	public T get(int index) {
		//Check to see if index is in bounds of SLL
		if(index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("Can not get - The index is out of bounds."); 
		}
		
		//Set the current node to head so that you can move through the SLL
		DLLNode<T> current = head;
		
		//Scan through list to get to the node you want to get and return the element
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current.getElement(); 
	}

	@Override
	public int indexOf(T element) {
		//set a boolean to see if the var has been found
		boolean found = false;
		DLLNode<T> current = head;
		int index = 0;
		
		//While loop that keeps looking till index is found or current reaches past last node
		while(!found && current != null) {
			if(current.getElement().equals(element)) {
				found = true;
			} else {
				current = current.getNext();
				index++;
			}
		}
		
		//Return -1 if item has not been found.
		if(!found) {
			index = -1;
		}
		return index;
	}

	@Override
	public T first() {
		//Check if there is a SLL with nodes, if there is, return head
		if(isEmpty()) {
			throw new NoSuchElementException("Empty List - No elements to grab first from.");
		}
		return head.getElement();
	}

	@Override
	public T last() {
		//Check to see if there is a SLL with nodes, if there is, return tail.
		if(isEmpty()) {
			throw new NoSuchElementException("Empty List - No elements to grab first from.");
		}
		return tail.getElement();
	}

	public boolean contains(T target) {
		//Get index of and if that returns -1, return false;
		int valid = indexOf(target);
		if (valid == -1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		//Check count, if count = 0, return false because count tracks nodes.
		if (count == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		//Return count, which tracks size of the list.
		return count;
	}

	@Override
	public Iterator<T> iterator() {
		Iterator<T> iteratorcast = new DLLListIterator();
		return iteratorcast;
	}

	@Override
	public ListIterator<T> listIterator() {
		return new DLLListIterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		return new DLLListIterator(startingIndex);
	}
	
	/**
	 * An iterator class that implements the ListIterator interface. Includes all methods from the interface as well
	 * as optional methods of add, remove, checkIndex, and checkModifications.
	 * @author Steven Meyers
	 */
	private class DLLListIterator implements ListIterator<T> {
		private DLLNode<T> next; //node to move over next
		private DLLNode<T> current; //node just moved over
		private DLLNode<T> previous; //node before current
		private int itrModCount; //counts the modifications that have occurred
		private int index; //An index that tracks where the previous pointer is.
		private boolean canRemove;
		private int lastCall; //An int value that will track if the last call was next (1), prev (0), or neither (-1)
		
		public DLLListIterator() {
			//Basically [null] [null] [1st element] with next = 1st element
			//                ^      ^             ^
			//              prev    current       next
			next = head;
			current = null;
			previous = null;
			itrModCount = modCount;
			//checks to see if a remove has already been called or if the element is removable
			canRemove = false;
			index = 0;
		}
		
		public DLLListIterator(int startingIndex) {
			//Basically [null] [null] [1st element] with current = 1st element. See above constructor.
			next = head;
			current = null;
			previous = null;
			itrModCount = modCount;
			//checks to see if a remove has already been called or if the element is removable
			canRemove = false;
			checkIndex(startingIndex);
			index = startingIndex;
		}
		
		@Override
		public boolean hasNext() {
			checkModifications();
			return (next != null);
		}

		@Override
		public T next() {
			checkModifications();
			//check to see if current is at head and there is no next
			if (next == null) {
				throw new NoSuchElementException("Itr no such next value");
			}
			
			//set a temp element to hold the current element.
			T temp = next.getElement();
			
			//move both the previous and current node forward one
			previous = current;
			current = next;
			//advance current node to the next node.
			next = next.getNext();
			canRemove = true;
			index++;
			lastCall = 1; //set last call int to next
			return temp;
		}

		@Override
		public boolean hasPrevious() {
			checkModifications();
			return (current != null);
		}

		@Override
		public T previous() {
			checkModifications();
			if (current == null) {
				throw new NoSuchElementException("Itr no such previous value");
			}
			
			//set a temp element to hold the previous element.
			T temp = current.getElement();
			
			//move both current and previous node back one
			next = current;
			current = previous;
			
			//Check to make sure the second previous isn't already out of list, then move back one.
			if(previous != null) {
				previous.getPrevious();
			}
			canRemove = true;
			index--;
			lastCall = 0; //set last call int to prev value
			return temp;
		}

		@Override
		public int nextIndex() {
			int nextIndex = index++;
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			if(count == 0) {
				return -1;
			}
			int prevIndex = index - 1;
			return prevIndex;
		}

		@Override
		public void set(T e) {
			checkModifications();
			//check to see what has previously been called or if can remove is false
			if(!canRemove || lastCall == -1) {
				throw new IllegalStateException("Next has not been called");
			}
			
			//Check if it last moved previous or next and change the element in the spot last passed over
			if(lastCall == 0) {
				next.setElement(e);
				canRemove = false;
				lastCall = -1;
			} else if(lastCall == 1) {
				previous.setElement(e);
				canRemove = false;
				lastCall = -1;
			}
		}
		
		@Override
		public void remove() {
			checkModifications();
			//check to see what has previously been called or if can remove is false
			if(lastCall == -1 || canRemove == false) {
				throw new IllegalStateException("Next or prev not last called");
			}
			
			if(lastCall == 0) { //if the last call was a previous
				if(next != null && current != null) { //midlist
					next.setPrevious(null);
					current.setNext(null);
					if(next != tail) {
						next = next.getNext();
					} else { // if tail
						next = null;
						tail = current;
					}
				}
				else if(current == null) { //if last call was a previous on a head
					if(next != tail) {
						next = next.getNext();
						head.setNext(null);
						next.setPrevious(null);
					} else {
						next.setNext(null);
						next.setPrevious(null);
						next = null;
					}
				}
			} else if (lastCall == 1) { //if last call was a next.
				if(previous == null) {
					head = next;
				} else {
					previous.setNext(next);
					current.setPrevious(null); //similar method to the single linked list, next node needs to connect to previous
					if (next != null) {
						next.setPrevious(previous);	
					}
					current = previous;
				}
			}
			
			canRemove = false;
			lastCall = -1;
			count--;
		}
		
		@Override
		public void add(T e) {
			DLLNode<T> newNode = new DLLNode<T>(e);
			if(isEmpty()) { //if empty, assign head tail and current
				head = newNode;
				tail= newNode;
				current = newNode;
				next = null;
				previous = null;
			}
			else if(next == head) { //if add is first method called
				head.setPrevious(newNode);
				newNode.setNext(head);
				head = newNode;
				current = head;
			} 
			else if(next != null && current != null) { //middle of list case
				current.setNext(newNode);
				newNode.setPrevious(current);
				next.setPrevious(newNode);
				newNode.setNext(next);
				previous = current;
				current = newNode;
			}
			else if(next == null) { //tail case
				tail.setNext(newNode);
				newNode.setPrevious(tail);
				previous = tail;
				tail = newNode;
				current = tail;
			}
			canRemove = false;
			lastCall = -1;
			index++;
			count++;
		}
		
		/**
		 * Checks the index to see if valid position.
		 * @param index Index passed through using DLLListIterator constructor
		 */
		public void checkIndex(int index) {
			if(index > count || index < 0) {
				throw new IndexOutOfBoundsException("Index is out of bounds of current list");
			}
		}
		
		/**
		 * Check whether modifications done to list 
		 */
		private void checkModifications() {
			if(modCount != itrModCount) {
				throw new ConcurrentModificationException("Changes made to list"); 
			}
		}
	}
}
