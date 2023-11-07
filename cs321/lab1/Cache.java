import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Cache<T> implements Iterable<T> {
	private T[] list; // array holding objects in list 
	private int count; // number of objects in list / first open position in array 
	private int capacity; // capacity of the array 
	private int modCount; // number of modifications to list
	
	/**
	 * Default constructor 
	 */
	@SuppressWarnings("unchecked")
	public Cache(int Capacity)
	{
		capacity = Capacity;
		list = (T[])(new Object[capacity]); 
		count = 0; 
		modCount = 0; 
	}
	
	public void addToFront(T element) {
		for(int i = count; i > 0; i--) {
			list[i] = list[i- 1];
		}
		list[0] = element;
		count++;
		modCount++;
	}

	
	public void addToRear(T element) {
		list[count] = element;
		count++;
		modCount++;
	}

	
	public void add(T element) {
		list[count] = element;
		count++;
		modCount++;
	}

	
	public void addAfter(T element, T target) {
		if(contains(target)) {	 
			for(int i = count; i > indexOf(target); i--)
			{
				list[i] = list[i - 1]; 
			}
			list[indexOf(target) + 1] = element; 
			count++; 
			modCount++;
		} else {
			throw new NoSuchElementException("AddAfter index off");
		}
	}

	
	public void add(int index, T element) {
		if(index < 0 || index > count)
		{
			throw new IndexOutOfBoundsException("Invalid index of insert method"); 
		} 
		for(int i = count; i > index; i--)
		{
			list[i] = list[i - 1]; 
		}
		list[index] = element; 
		count++; 
		modCount++;
		
	}

	
	public T removeFirst() {
		if (!isEmpty()) {
			T oldItem = list[0];

			for(int i = 0; i < count - 1; i++)
			{
				list[i] = list[i + 1]; 
			}
			list[count - 1] = null;
			count--; 
			modCount++; 
			return oldItem;
		} else {
			throw new NoSuchElementException("List is empty or doesn't have last value somehow.");
		}
	}

	
	public T removeLast() {
		if(!isEmpty()) {
			T oldItem = list[count-1];
			list[count - 1] = null;
			count--; 
			modCount++; 
			return oldItem;
		} else {
			throw new NoSuchElementException("List is empty or doesn't have last value somehow.");
		}
	}

	
	public T remove(T element) {
		if(contains(element)) {
			T oldItem = list[indexOf(element)];
			for(int i = indexOf(element); i < count - 1; i++)
			{
				list[i] = list[i + 1]; 
			}
			list[count - 1] = null;
			count--; 
			modCount++;	
			return oldItem;
		} else {
			throw new NoSuchElementException("No such element. Remove.");
		}
	}

	
	public T remove(int index) {
		if(index < 0 || index >= count)
		{
			throw new IndexOutOfBoundsException("Invalid index for remove method"); 
		}		
		T oldItem = list[index];

		for(int i = index; i < count - 1; i++)
		{
			list[i] = list[i + 1]; 
		}
		list[count - 1] = null;
		count--; 
		modCount++; 
		return oldItem;
	}

	
	public void set(int index, T element) {
		if(index < 0 || index >= count)
		{
			throw new IndexOutOfBoundsException("Invalid index for set method"); 
		}
		list[index] = element;
		modCount++;
	}

	
	public T get(int index) {
		if(index < 0 || index >= count)
		{
			throw new IndexOutOfBoundsException("Invalid index for get method"); 
		}

		return list[index];
	}

	
	public int indexOf(T element) {
		int index = 0;
		boolean found = false; 
		
		while(!found && index < count)
		{
			if(list[index].equals(element))
			{
				found = true; 
			}
			else
			{
				index++;
			}
		}
		if(!found)
		{
			index = -1; 
		}
		
		return index;
	}

	
	public T first() {
		if (isEmpty()) {
			throw new NoSuchElementException("List contains no elements, can't find first element.");
		}
		return list[0];
	}

	
	public T last() {
		if (isEmpty()) {
			throw new NoSuchElementException("List contains no elements, can't find last element.");
		}
		return list[count - 1];
	}

	
	public boolean contains(T target) {
		int index = 0;
		boolean found = false; 
		
		while(!found && index < count)
		{
			if(list[index].equals(target))
			{
				found = true; 
			}
			else
			{
				index++;
			}
		}
		
		return found;
	}

	
	public boolean isEmpty() {
		if (count == 0) {
			return true;
		}
		return false;
	}

	
	public int size() {
		return count;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ArrayIterator();
	}

	
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException("Not a supported iterator for this list.");
	}

	
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException("Not a supported iterator for this list.");
	}
	
	/**
	 * Iterator for the List ADT 
	 * @author Matt T 
	 *
	 */
	private class ArrayIterator implements Iterator<T>
	{
		private int next; // index of next item to be returned 
		private int itrModCount; // number of modifications when initialize iterator 
		private boolean canRemove; // flag for precondition on remove method 
		
		/**
		 * Default constructor, iteration starts at first element
		 */
		public ArrayIterator()
		{
			next = 0;
			itrModCount = modCount; 
			canRemove = false; 
		}
		
		@Override
		public boolean hasNext()
		{
			return (next < count);
		}

		@Override
		public T next()
		{
			checkModifications(); 
			
			if(!hasNext())
			{
				throw new NoSuchElementException("Can't call next, no more items in list"); 
			}
			T item = list[next]; 
			next++; 
			canRemove = true; 
			return item;
		}
		
		@Override 
		public void remove() {
			if(!canRemove) {
				throw new IllegalStateException("Can't remove, haven't called next method");
			}
			
			for (int i = next -1; i < count - 1; i++) {
				list[i] = list[i + 1];
			}
			list[count-1] = null;
			count--;
			next = next -1;
			canRemove=false;
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

