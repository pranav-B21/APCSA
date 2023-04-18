import java.util.Scanner;

/**
 * Binary Tree for State Objects
 *
 * @author Pranav Belligundu
 * @version 
 */
public class BinaryTree {

	private final String DEFAULT_FILE_NAME = "states2.txt"; // Default input file
	private Scanner keyboard;
	
	private TreeNode root;
	private boolean inTree; // private field that checks if node is in the tree
	private int size;
	private int depth;
	
	public BinaryTree() {
		inTree = false;
		depth = 0;
		size = 0;
	}
	
	/**
	 *	Load data from a text file
	 */
	public void loadData()
	{
		Scanner input = FileUtils.openToRead("states2.txt");
		for(int i = 0 ; i < 50; i++){
			String n = input.next();
			String a = input.next();
			int p = input.nextInt();
			int ar = input.nextInt();
			int r= input.nextInt();
			String c = input.next();
			int m = input.nextInt();
			int d = input.nextInt();
			int y = input.nextInt();
			State s = new State(n,a,p,ar,r,c,m,d,y);
			if(i == 0) root = new TreeNode<State>(s);
			else insert(s);
		}
		
	}
	
	/**
	 * Insert State into tree
	 * @param next  State to insert
	 */
	public void insert(State next) {
		compareWith(next, root);
	}
	/** 
	 * Compares the next value with the current parent node to see
	 * where in the tree it should be placed in
	 * @param next		State to insert
	 * @param compare 	State to compare with/parent
	 */
	public void compareWith(State next, TreeNode<State> compare){
		if(compare == null) {
			compare = new TreeNode<State>(next);
			return;
		}
		if(next.compareTo(compare.getValue()) < 0){
			if(compare.getLeft() == null) compare.setLeft(new TreeNode<State>(next));
			else compareWith(next, compare.getLeft());
		}
		else{
			if(compare.getRight() == null) compare.setRight(new TreeNode<State>(next));
			else compareWith(next, compare.getRight());
		}
	}
	

	/**
	 * Prints the tree as a list in ascending order by state name
	 */
	public void printList() {
		System.out.println("Loading file states2.txt");	
		print(root);
	}
	
	public void print(TreeNode<State> curr){
		if(curr == null) return; 
		print(curr.getLeft());
		System.out.println(curr.getValue());
		print(curr.getRight());
	}
	
	
	/**
	 * Prompts user for State name to find, then starts search
	 */
	public void testFind() {
		String str = "";
		while(!str.equalsIgnoreCase("q")){
			System.out.println();
			inTree = false;
			str = Prompt.getString("Enter state name to search for (Q to quit)");
			testFind(root,str);
			if(!inTree) System.out.println("Name = " + str + " No such state name");
			System.out.println();
		}
		System.out.println();
	}
	
	public void testFind(TreeNode<State> curr, String find){
		if(curr == null){
			return;
		}
		testFind(curr.getLeft(), find);
		if(curr.getValue().getName().equalsIgnoreCase(find)){
			System.out.println(curr.getValue());
			inTree = true;
		}
		testFind(curr.getRight(), find);
	}

	/**
	 * Prompts user for State name to delete
	 * OPTIONAL: Not included in your grade!
	 */
	public void testDelete() {

	}
	
	/**
	 * Finds the number of nodes starting at the root of the tree
	 * @return  the number of nodes in the tree
	 */
	public int size() {
		size(root);
		return size;
	}
	
	public void size(TreeNode<State> curr){
		if(curr!= null){
			size ++;
			size(curr.getLeft());
			size(curr.getRight());
		}
	}
	
	/**
	 * Clears the tree of all nodes
	 */
	public void clear() {
		root = null;
	}
	
	/**
	 * Prompts user for level of tree to print.
	 * The top level (root node) is level 0.
	 */
	public void printLevel() {
		int level;
		do{
			System.out.println();
			level = Prompt.getInt("Enter level value to print (-1 to quit)");
			System.out.println("Level " + level);
			print(root, level, 0);		
		}while(level != -1);
	}
	public void print(TreeNode<State> curr, int level, int counter){
		if (curr != null && counter < level){
			print (curr.getLeft(), level, counter + 1);
			print(curr.getRight(), level, counter + 1);
		}
		else if (counter == level && curr != null){
			System.out.printf ("%-15s", curr.getValue().getName());
			return;
		}
		else
			return;
	}
	
	
	/**
	 * Prints the highest level of the tree (root is level 0),
	 * prints "Tree empty" if empty tree
	 */
	public void testDepth() {
		if(root == null) 
			System.out.println("Tree Empty");
		else{
			depth = maxDepth(root);
			System.out.println("Depth of the tree = " + depth);
		}
	
	}
	
	public int maxDepth(TreeNode<State> node){
		if (node == null)
            return -1;
        else
        {
            /* compute the depth of each subtree */
            int lDepth = maxDepth(node.getLeft());
            int rDepth = maxDepth(node.getRight());
  
            /* use the larger one */
            if (lDepth > rDepth)
                return (lDepth + 1);
             else
                return (rDepth + 1);
        }
		
	}

}
