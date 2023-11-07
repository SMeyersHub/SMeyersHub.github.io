import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class IUSingleLinkedList<T> implements IndexedUnsortedList<T>, Iterable<T> {
	private int count; // Total number of elements
	private SLLNode<T> head, tail; //The nodes that are the first/last
	private int modCount; // number of the mods to the list.
	
	/**
	 * Default Constructor
	 */
	public IUSingleLinkedList() {
		head = null;
		tail = null;
		count = 0;
		modCount = 0;
	}
	
	@Override
	public void addToFront(T element) {
		
		//Set the new node that is going to have the element in it
		SLLNode<T> newNode = new SLLNode<T>(element);
		
		//Attach it to the head and then move the head back one (to it).
		newNode.setNext(head);
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
		SLLNode<T> newNode = new SLLNode<T>(element);
		
		//test if list is empty and assign head and tail
		if (count == 0) {
			head = newNode;
		} else {
			tail.setNext(newNode); //Only works if the count != 0
		}
		
		//Move the tail forward one (to the newly attached node);
		tail = newNode;
		count++;
		modCount++;
	}

	@Override
	public void add(T element) {
		//Simply add to rear.
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {
		//Check to see if the target element is within the bounds of list or if list is empty
		if(!contains(target)) {
			throw new NoSuchElementException("Target element is not found in the SLL");
		}
		
		//make a node that will be inserted into the list.
		SLLNode<T> newNode = new SLLNode<T>(element);
		SLLNode<T> current = head;
		SLLNode<T> next = current.getNext();
		
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
		SLLNode<T> newNode = new SLLNode<T>(element);
		
		//Check index position to see if at head or tail
		if(index == 0) {
			newNode.setNext(head);
			head = newNode;
		} else {
			
			//Establish a temp node to go through list.
			SLLNode<T> current = head;
			
			//Move all variables back so that the new node can be inserted.
			for (int i = 0; i < index - 1; i++) {
				current = current.getNext();
			}
			
			//Set up a temp node with the next node for the current temp node
			SLLNode<T> next = current.getNext();
			
			//Insert the node between current and next temp nodes
			current.setNext(newNode);
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
		SLLNode<T> current = head;
		SLLNode<T> previous = null; 
		
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
		SLLNode<T> next = current.getNext();
		
		//If the previous node isn't null, link it to current's next node.
		if(previous != null) {
			previous.setNext(next);
		} 
		
		//check to see if the head or tail must be moved if they are being removed.
		if(current.equals(head)) {
			head = next;
		} else if (current.equals(tail)) {
			tail = previous;
			previous.setNext(null);
		}
		
		//Detach current from the current node list by making its link = null
		current.setNext(null);
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
			SLLNode<T> next = head.getNext(); 
			head.setNext(null);
			item = head.getElement(); 
			head = next;
			if(count == 1) {
				tail = null; 
			}
		}
		else
		{
			SLLNode<T> current = head; 
			for(int i = 0; i < index - 1; i++) {
				current = current.getNext(); 
			}
			
			SLLNode<T> next = current.getNext(); 
			current.setNext(next.getNext());
			next.setNext(null);
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
		SLLNode<T> current = head;
		
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
		SLLNode<T> current = head;
		
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
		SLLNode<T> current = head;
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

	@Override
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
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}
	
	
	
	private class SLLIterator implements Iterator<T> {
		private SLLNode<T> next; //node to move over next
		private SLLNode<T> current; //node just moved over
		private SLLNode<T> previous; //node before current
		private int itrModCount; //counts the modifications that have occurred
		private boolean canRemove;
		
		/**
		 * Default constructor
		 */
		public SLLIterator() {
			//Basically [null] [null] [1st element] with next = 1st element
			next = head;
			current = null;
			previous = null;
			itrModCount = modCount;
			//checks to see if a remove has already been called or if the element is removable
			canRemove = false;
		}
		
		public boolean hasNext() {
			checkModifications();
			return (next != null);
		}

		public T next() {
			checkModifications();
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
			return temp;
		}
		
		/**
		 * Removes the current node that has been passed over.
		 */
		public void remove() {
			checkModifications();
			if(current == null || !canRemove) {
				throw new IllegalStateException("Next has not been called");
			}
			
			if(previous == null) {
				head = next;
			} else {
				previous.setNext(next);
				current = previous;
			}
			count--;
			canRemove = false;
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
