import java.util.NoSuchElementException;

/**
 *	SinglyLinkedList - a way to collect data in a simple structore which 
 * 					   is made outof nodes which contain a pointer to the next
 * 					   the last node points to null. They can grow and shrink
 * 					   dynamically. 
 *
 *	@author	 Pranav Belligundu
 *	@since	 April 12 2022
 */
public class SinglyLinkedList<E extends Comparable<E>>
{
	/* Fields */
	private ListNode<E> head, tail;		// head and tail pointers to list
	private int count;
	
	/* No-args Constructors */
	public SinglyLinkedList() {
		head = new ListNode<E>(null);
		tail = new ListNode<E>(null);
		count = 0;
	}
	
	/** Copy constructor */
	public SinglyLinkedList(SinglyLinkedList<E> oldList) {
		ListNode<E> head = oldList.head;
		ListNode<E> node = head;
		while (node.getNext() != null)
		{
			add(node.getValue());
			node = node.getNext();
		}
		add(node.getValue());
	}
	
	/**	Clears the list of elements */
	public void clear() {
		head = null;
		count = 0;
	}
	
	/**	Add the object to the end of the list
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(E obj) {
		ListNode<E> newNode = new ListNode<E>(obj);
		if (count == 0 || head == null) {
			head = newNode;
			tail = newNode;
		} 
		else {
			tail.setNext(newNode);
			tail = newNode;
			newNode.setNext(null);
		}
		count++;
		return true;
	}
	
	/**	Add the object at the specified index
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, E obj) {
		if (index > count) throw new NoSuchElementException("Index out of bounds!");
		ListNode<E> tmp = head;

		if (tmp == null || tmp.getNext() == null) {
			add(obj);
		}
		else if(index == 0){
			ListNode<E> newNode = new ListNode<E>(obj);
			newNode.setNext(head.getNext());
			head.setNext(newNode);
		}
		
		else {
			
			for (int i = 0; i < index - 1; i++) {
				tmp = tmp.getNext();
			}
			
			ListNode<E> newNode = new ListNode<E>(obj);
			newNode.setNext(tmp.getNext());
			tmp.setNext(newNode);
		}
		return true;
	}
	
	/**	@return the number of elements in this list */
	public int size() {
		return count;
	}
	
	/**	Return the ListNode at the specified index
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<E> get(int index) {
		if (index >= count || index < 0) throw new NoSuchElementException("Error: No index = " + index);
		ListNode<E> tmp = head;
		int i = 0;
		while (tmp.getNext() != null) {
			if (i == index) return tmp;
			i++;
			tmp = tmp.getNext();
		}
		
		return null;
	}
	
	/**	Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the object that will replace the original
	 *	@return				the object that was replaced
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E set(int index, E obj) {
		if (index >= count || index < 0)
			throw new NoSuchElementException("Error: No index = " + index);
		ListNode<E> tmp = head;
		int i = 0;
		while (tmp.getNext() != null) {
			if (i == index - 1) {
				ListNode<E> newNode = new ListNode<E>(obj);
				ListNode<E> next = tmp.getNext();
				newNode.setNext(tmp.getNext());
				tmp.setNext(newNode);
				return next.getValue();
			}
			i++;
			tmp = tmp.getNext();
		}
		
		return null;
	}
	
	/**	Remove the element at the specified index
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E remove(int index) {
		if (index >= count || index < 0)
			throw new NoSuchElementException("Error: No index = " + index);
		ListNode<E> tmp = head;
		int i = 0;
		while (tmp.getNext() != null) {
			if (i == index - 1) {
				ListNode<E> remove = tmp.getNext();
				E val = remove.getValue();
				tmp.setNext(remove.getNext());
				remove = null;
				return val;
			}
			i++;
			tmp = tmp.getNext();
		}
		return null;
	}
	
	/**	@return	true if list is empty; false otherwise */
	public boolean isEmpty() {
		return head.getNext() == null;
	}
	
	/**	Tests whether the list contains the given object
	 *	@param object		the object to test
	 *	@return				true if the object is in the list; false otherwise
	 */
	public boolean contains(E object) {
		ListNode<E> tmp = head;
		while (tmp.getNext() != null) {
			if (tmp.getValue().equals(object)) return true;
			tmp = tmp.getNext();
		}
		return false;
	}
	
	/**	Return the first index matching the element
	 *	@param element		the element to match
	 *	@return				if found, the index of the element; otherwise returns -1
	 */
	public int indexOf(E element) {
		int i = 0;
		ListNode<E> tmp = head;
		while (tmp.getNext() != null) {
			if (tmp.getValue().equals(element))
				return i;
			tmp = tmp.getNext();
			i++;
		}
		return -1;
	}
	
	/**	Prints the list of elements */
	public void printList()
	{
		ListNode<E> ptr = head;
		while (ptr != null)
		{
			System.out.print(ptr.getValue() + "; ");
			ptr = ptr.getNext();
		}
	}
}
