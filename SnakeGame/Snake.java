/**
 *	Snake class that contains all the properties of a snake. This includes
 * 	its location and for it to return all the values in the snake.
 * 
 *	@author	 Pranav Belligundu
 *	@since	May 12th, 2022
 */
public class Snake extends SinglyLinkedList<Coordinate> {
	
	// Constructors
	public Snake(int row, int col) {
		this(new Coordinate(row, col));
	}
	
	public Snake(Coordinate loc) {
		super();
		for (int i = 0; i < 5; i++) {
			add(new Coordinate(loc.getX(), loc.getY() + i));
		}
	 }

	public String toString() {
		String str = new String();
		ListNode<Coordinate> temp = super.get(0);
		while (temp.getNext() != null) {
			str += temp.getNext().getValue().toString() + " ";
			temp = temp.getNext();
;		}
		return str;
	}
	
}
