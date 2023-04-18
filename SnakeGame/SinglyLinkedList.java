import java.util.NoSuchElementException;

/**
 *	SinglyLinkedList - a way to collect data in a simple structore which 
 * 					   is made outof nodes which contain a pointer to the next
 * 					   the last node points to null. They can grow and shrink
 * 					   dynamically. 
 *
 *	@author	 Pranav Belligundu
 *	@since	 May 12 2022
 */
public class SinglyLinkedList<E extends Comparable<E>>
{
	/* Fields */
	private ListNode<E> head, tail;		// head and tail pointers to list
	
	/* No-args Constructors */
	public SinglyLinkedList() {
		head = new ListNode<E>(null);
		tail = new ListNode<E>(null);
	}
	
	/** Copy constructor */
	public SinglyLinkedList(SinglyLinkedList<E> oldList) {e
		ListNode<E> old = oldList.head;
		head = new ListNode<E>(old.getValue());
		
		ListNode<E> newNode = new ListNode<E>(old.getNext().getValue());
		head.setNext(newNode);
		
		old = old.getNext();
		while (old != null) {
			newNode.setNext(new ListNode<E>(old.getValue()));
			old = old.getNext();
			newNode = newNode.getNext();
		}
	}
	
	/**	Clears the list of elements */
	public void clear() {
		head = null;
	}
	
	/**	Add the object to the end of the list
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(E obj) {
		
		ListNode<E> newNode = new ListNode<E>(obj);
		if (size() == 0 || head == null) {
			head = newNode;
			tail = newNode;
		} 
		else {
			tail.setNext(newNode);
			tail = newNode;
			newNode.setNext(null);
		}
		return true;
	}
	
	/**	Add the object at the specified index
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, E obj) {
		if (index > size()) 
			throw new NoSuchElementException("Out of bounds!");
		ListNode<E> temp = head;

		if (temp == null || temp.getNext() == null) {
			add(obj);
		}
		else if(index == 0){
			ListNode<E> newNode = new ListNode<E>(obj);
			newNode.setNext(head.getNext());
			head.setNext(newNode);
		}
		
		else {
			
			for (int i = 0; i < index - 1; i++) {
				temp = temp.getNext();
			}
			
			ListNode<E> newNode = new ListNode<E>(obj, temp.getNext());
			temp.setNext(newNode);
		}
		return true;
	}
	
	/**	@return the number of elements in this list */
	public int size() {
		int counter = 0;
		ListNode<E> temp = head;
		if (head.getValue() != null) counter++;
		while (temp.getNext() != null) {
			counter++;
			temp = temp.getNext();
		}
		return counter;
	}
	
	/**	Return the ListNode at the specified index
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<E> get(int index) {
		if (index >= size() || index < 0) 
			throw new NoSuchElementException("Error: No index = " + index);
		ListNode<E> temp = head;
		if (index == size() - 1) return tail;
		int i = 0;
		while (temp.getNext() != null) {
			if (i == index) return temp;
			i++;
			temp = temp.getNext();
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
		if (index >= size() || index < 0)
			throw new NoSuchElementException("Error: No index = " + index);
		ListNode<E> temp = head;
		for (int i = 0; i < index; i++) {
			temp = temp.getNext();
		}

		E val = temp.getValue();
		temp.setValue(obj);

		return val;
	}
	
	/**	Remove the element at the specified index
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E remove(int index) {
		if (index >= size() || index < 0)
			throw new NoSuchElementException("Error: No index = " + index);
		ListNode<E> temp = head;
		int i = 0;
		
		if (head == null) return null;
		
		if (size() == 1){
			E tempVal = head.getValue();
			clear();
			return tempVal;
		}
		
		while (temp.getNext() != null){

			if (index == 0 && i == 0){
				head = head.getNext();
				return head.getValue();
			}
			
			if (i == index - 1){
				ListNode<E> remove = temp.getNext();
				E tempVal = remove.getValue();
				temp.setNext(remove.getNext());
				remove = null;
				return tempVal;
			}
			i++;
			temp = temp.getNext();
		}
		return null;
	}
	
	/**	@return	true if list is empty; false otherwise */
	public boolean isEmpty(){
		boolean size = false;
		if(size() == 0) return !size;
		else return size;
	}
	
	/**	Tests whether the list contains the given object
	 *	@param object		the object to test
	 *	@return				true if the object is in the list; false otherwise
	 */
	public boolean contains(E object){
		ListNode<E> temp = head;
		
		while (temp.getNext() != null) {
			if (temp.getValue().equals(object)) return true;
			temp = temp.getNext();
		}
		return false;
	}
	
	/**	Return the first index matching the element
	 *	@param element		the element to match
	 *	@return				if found, the index of the element; otherwise returns -1
	 */
	public int indexOf(E element){
		int i = 0;
		ListNode<E> temp = head;
		
		while (temp.getNext() != null) {
			if (temp.getValue().equals(element))
				return i;
			i++;
			temp = temp.getNext();
		}
		return -1;
	}
	
	/**	Prints the list of elements */
	public void printList()
	{
		ListNode<E> list = head;
		while (list != null)
		{
			System.out.print(list.getValue() + "; "); 
			list = list.getNext();
		}
	}
}
