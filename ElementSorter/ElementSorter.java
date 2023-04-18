import java.util.ArrayList;
import java.util.Scanner;
/**
 *	ElementSorter - (Description goes here)
 *
 *	Requires the Prompt and FileUtils classes
 *	@author	Pranav Belligundu
 *	@since	Dec 2nd, 2021
 */

public class ElementSorter {

	// fields	
	private ArrayList<ElementEntry> elements;		// Array of elements

	public static void main(String[] args){
		ElementSorter es = new ElementSorter();
		es.printIntroduction();
		es.run();
	}
	public ElementSorter(){
		elements = new ArrayList<>();
	}
	public void run(){
		int num = displayPrompt();
		getElements();
		if(num == 1) bubbleSort();
		else if(num == 2) selectionSort();
		else if(num == 3) insertionSort();
		else if(num == 4) mergeSort(elements, elements.size());
		else if(num == 5) System.exit(1);
		printElements();
		
	}
	/**
	 *	Swaps two Integer objects in array arr
	 *	@param arr		array of Integer objects
	 *	@param x		index of first object to swap
	 *	@param y		index of second object to swap
	 */
	private void swap(ArrayList<ElementEntry> elements, int x, int y) {
		ElementEntry temp = elements.get(x);
		elements.set(x, elements.get(y));
		elements.set(y, temp);
	}
	
	public void bubbleSort(){ //name
		for(int outer = elements.size() -1; outer > 0; outer --){
			for(int inner = 0; inner < outer; inner ++){
				if(elements.get(inner).compareToName(elements.get(inner+1)) 
					> 0){
					swap(elements, inner,inner+1);
				}
			}
		}	
	}
	public void selectionSort(){ //number
		for(int outer = elements.size(); outer > 1; outer --){
			int max = 0;
			for(int inner = 1; inner < outer; inner++){
				if(elements.get(inner).num > elements.get(max).num){
					max = inner;
				}
				swap(elements, max, outer -1);
			}
		}
	}
	public void insertionSort(){ //atomic mass
		for(int i = 1; i < elements.size(); i++){
			for(int j = i; j > 0 && elements.get(i).compareToAM(elements.get(i-1))<0;j--){
				swap(elements, j, j-1);
			}
		}
	}
	public void mergeSort(ArrayList<ElementEntry> elements, int n) {
		if (n < 2) {
			return;
		}
		int mid = n / 2;
		ArrayList<ElementEntry> l = new ArrayList<>();
		ArrayList<ElementEntry> r = new ArrayList<>();

		for (int i = 0; i < mid; i++) {
			l.add(i,elements.get(i));
		}
		for (int i = mid; i < n; i++) {
			r.add(i-mid,elements.get(i));
		}
		mergeSort(l, mid);
		mergeSort(r, n - mid);

		merge(elements, l, r, mid, n - mid);
	}
	public static void merge(
	  ArrayList<ElementEntry> elements, ArrayList<ElementEntry> l,
	  ArrayList<ElementEntry> r, int left, int right) {
	 
		int i = 0, j = 0, k = 0;
		while (i < left && j < right) {
			if (l.get(i).compareToSymbol(r.get(j)) <= 0){
				elements.set(k++,l.get(i++));
				//arr[k++] = l[i++];
			}
			else {
				elements.set(k++,r.get(j++));
				//arr[k++] = r[j++];
			}
		}
		while (i < left) {
			elements.set(k++,l.get(i++));
			//arr[k++] = l[i++];
		}
		while (j < right) {
			elements.set(k++,r.get(j++));
			//arr[k++] = r[j++];
		}
	}
	public void getElements() {
		Scanner input = null;
		// Open the HTML file
		input = FileUtils.openToRead("elements.txt");
		while (input.hasNext()) {
			int num = input.nextInt();
			String symbol = input.next();
			String element = input.next();
			double atomicMass = input.nextDouble();

			elements.add(new ElementEntry(num, symbol, element, atomicMass));
		}
		input.close();
	}
	/**
	 *	Display the user prompt
	 */
	public int displayPrompt() {
		System.out.println("\n1: Bubble Sort by name");
		System.out.println("2: Selection Sort by number");
		System.out.println("3: Insertion Sort by atomic mass");
		System.out.println("4: Merge Sort by symbol");
		System.out.println("5: Exit\n");
		return Prompt.getInt("Please Enter 1 through 5, indicating your "
									+ "choice from the menu above: ", 1, 5);
	}
	
	/**
	 *	Print the element array
	 */
	public void printElements() {
		System.out.println();
		System.out.println("+------------------+");
		System.out.println("| List of Elements |");
		System.out.println("+------------------+------------------------+");
		System.out.println("|                                           |");
		System.out.println("|   #   Element        Symbol  Atomic Mass  |");
		System.out.println("|                                           |");
		int count = 1;
		for (ElementEntry e : elements) {
			System.out.println("| " + e + "   |");
			if (count % 5 == 0)
				System.out.println("|                                           |");
			count++;
		}
		System.out.println("+-------------------------------------------+");
	}
	
	/**
	 *	Introduction
	 */
	public void printIntroduction() {
		System.out.println();
		System.out.println("    ______ __                               __  _____               __             ");
		System.out.println("   / ____// /___   ____ ___   ___   ____   / /_/ ___/ _" +
							"___   _____ / /_ ___   _____");
		System.out.println("  / __/  / // _ \\ / __ `__ \\ / _ \\ / __ \\ / __/\\__ " +
							"\\ / __ \\ / ___// __// _ \\ / ___/");
		System.out.println(" / /___ / //  __// / / / / //  __// / / // /_ ___/ // /_/" +
							" // /   / /_ /  __// /    ");
		System.out.println("/_____//_/ \\___//_/ /_/ /_/ \\___//_/ /_/ \\__//____/ " +
							"\\____//_/    \\__/ \\___//_/     ");
		System.out.print("\nWelcome to ElementSorter, a sorting routine");
		System.out.println(" for the periodic table of elements.");
		System.out.println("\nLet's begin.\n");
	}
	
	
}
