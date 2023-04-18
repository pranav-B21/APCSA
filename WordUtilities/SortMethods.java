import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;		// for testing purposes

/**
 *	SortMethods - Sorts an ArrayList of Strings in ascending order.
 *
 *	Requires FileUtils class to compile.
 *	Requires file randomWords.txt to execute a test run.
 *
 *	@author	
 *	@since	
 */
public class SortMethods {
	
	public void mergeSort(ArrayList<String> arr, int n) {
		if (n < 2) {
			return;
		}
		int mid = n / 2;
		ArrayList<String> l = new ArrayList<>();
		ArrayList<String> r = new ArrayList<>();

		for (int i = 0; i < mid; i++) {
			l.add(i,arr.get(i));
		}
		for (int i = mid; i < n; i++) {
			r.add(i-mid,arr.get(i));
		}
		mergeSort(l, mid);
		mergeSort(r, n - mid);

		merge(arr, l, r, mid, n - mid);
	}
	public static void merge(
	  ArrayList<String> arr, ArrayList<String> l,
	  ArrayList<String> r, int left, int right) {
	 
		int i = 0, j = 0, k = 0;
		while (i < left && j < right) {
			if (l.get(i).compareTo(r.get(j)) <= 0){
				arr.set(k++,l.get(i++));
			}
			else {
				arr.set(k++,r.get(j++));
			}
		}
		while (i < left) {
			arr.set(k++,l.get(i++));
		}
		while (j < right) {
			arr.set(k++,r.get(j++));
		}
	}
	
	/**
	 *	Merge Sort algorithm - in ascending order
	 *	@param arr		List of String objects to sort
	 */
	public void mergeSort(ArrayList<String> arr) {
		mergeSort(arr, arr.size());
	}
	
	/**
	 *	Recursive mergeSort method.
	 *	@param arr		List of String objects to sort
	 *	@param first	first index of arr to sort
	 *	@param last		last index of arr to sort
	 */
	public void mergeSortRecurse(List<String> arr, int first, int last) {
		// insert your code here
	}
	
	/**
	 *	Merge two lists that are consecutive elements in array.
	 *	@param arr		List of String objects to merge
	 *	@param first	first index of first list
	 *	@param mid		the last index of the first list;
	 *					mid + 1 is first index of second list
	 *	@param last		last index of second list
	 */
	public void merge(List<String> arr, int first, int mid, int last) {
		// Insert your code here
	}

	
	/**
	 *	Print an List of Strings to the screen
	 *	@param arr		the List of Strings
	 */
	public void printArray(List<String> arr) {
		if (arr.size() == 0) System.out.print("(");
		else System.out.printf("( %-15s", arr.get(0));
		for (int a = 1; a < arr.size(); a++) {
			if (a % 5 == 0) System.out.printf(",\n  %-15s", arr.get(a));
			else System.out.printf(", %-15s", arr.get(a));
		}
		System.out.println(" )");
	}
	
	/*************************************************************/
	/********************** Test program *************************/
	/*************************************************************/
	private final String FILE_NAME = "randomWords.txt";
	
	public static void main(String[] args) {
		SortMethods se = new SortMethods();
		se.run();
	}
	
	public void run() {
		ArrayList<String> arr = new ArrayList<String>();
		// Fill List with random words from file		
		fillArray(arr);
		
		System.out.println("\nMerge Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		mergeSort(arr, arr.size());
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();
	}
	
	// Fill String array with words
	public void fillArray(List<String> arr) {
		Scanner inFile = FileUtils.openToRead(FILE_NAME);
		while (inFile.hasNext())
			arr.add(inFile.next());
		inFile.close();
	}
}
