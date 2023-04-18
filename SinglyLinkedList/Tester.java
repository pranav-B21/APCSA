import java.security.Principal;
import java.util.ArrayList;
public class Tester{
	
	private SinglyLinkedList<String> list = new SinglyLinkedList<String>();
	
	public static void main(String [] args){
		int[][] a = new int[3][4];
		ArrayList<Integer> g = new ArrayList<Integer>(2);
		g.add(9);
		g.add(2);
		g.add(4);

		System.out.println(g.size());
		System.out.println(a.length);
		System.out.println(a[0].length);
			Tester Aditya = new Tester();
			Aditya.addToIndex();
	}
	
	public void addToIndex(){
		list.add("Yash"); // 0 
		list.add("Aditya"); // 1
		list.add( "Shreyas"); // 2
		list.add("Anurag");  // 3 
		System.out.println("Here is the OG List");
		list.printList();
		System.out.println();
		SinglyLinkedList<String> listCopy = new SinglyLinkedList<String>(list);
		System.out.println("HERE IS A COPY: 	");
		listCopy.printList();
		boolean f = listCopy.isEmpty();
		System.out.println("Is the list empty: "+ f);

		System.out.println("REMOVE");
		listCopy.remove(0);	
		System.out.println("HERE IS A COPY: 	");
		listCopy.printList();

		System.out.println("COMPARE ME");
		list.printList();
	}
	
}
