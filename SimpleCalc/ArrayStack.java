import java.util.ArrayList; 
import java.util.List;
import java.util.EmptyStackException;

/** Simple stack using ArrayList
 * 
 * @author Pranav Belligundu
 * @since Feb 16th, 2022
*/
public class ArrayStack<E> implements Stack<E>{
	private List<E> theStack;
	
	public ArrayStack(){
		theStack = new ArrayList<E>();
	}
	/** @return 	true if the stack is empty; false otherwise*/
	public boolean isEmpty(){ return theStack.isEmpty();}
	
	/**@return 		the top element on the stack */
	public E peek(){
		if(theStack.isEmpty())
			throw new EmptyStackException();
		return theStack.get(theStack.size() - 1);
	}
	
	/** @param obj		the object to push on the top of teh stack */
	public void push(E obj){ theStack.add(obj); }
	
	public E pop(){
		if(theStack.isEmpty())
			throw new EmptyStackException();
		return theStack.remove(theStack.size() - 1);
	}
}