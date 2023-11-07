/**
 * Simple class that provides the creation and alteration of a node in a double linked list.
 * @author Steven Meyers
 *
 * @param <T> generic type that the class will use to allow for data storage of any type
 */
public class DLLNode<T> {
	private DLLNode<T> previous; //Reference to the node before
	private DLLNode<T> next;		// reference to next node
	private T element;			// reference to object stored in node 
	
	/**
	 * Constructor - with given element 
	 * @param element - object of type T
	 */
	public DLLNode(T element)
	{
		setElement(element);
		setPrevious(null);
		setNext(null);
	}

	/**
	 * Returns reference to next node
	 * @return - ref to DLLNode<T> object 
	 */
	public DLLNode<T> getNext()
	{
		return next;
	}
	
	/**
	 * Returns reference to next node
	 * @return - ref to DLLNode<T> object 
	 */
	public DLLNode<T> getPrevious()
	{
		return previous;
	}

	/**
	 * Assign reference to next node 
	 * @param next - ref to DLLNode<T> object 
	 */
	public void setNext(DLLNode<T> next)
	{
		this.next = next;
	}
	
	/**
	 * Assign reference to previous node 
	 * @param previous - ref to DLLNode<T> object 
	 */
	public void setPrevious(DLLNode<T> previous) {
		this.previous = previous;
	}

	/**
	 * Returns reference to element stored in node 
	 * @return - ref to object of type T 
	 */
	public T getElement()
	{
		return element;
	}

	/**
	 * Sets reference to element stored at node
	 * @param element - ref to object of type T
	 */
	public void setElement(T element)
	{
		this.element = element;
	}
	
	
}
