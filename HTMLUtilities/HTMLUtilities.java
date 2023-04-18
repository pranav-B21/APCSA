/**
 *	Utilities for handling HTML
 *
 *	@author	Pranav Belligundu
 *	@since	10/26/21
 */
public class HTMLUtilities {

	/**
	 *	Break the HTML string into tokens. The array returned is
	 *	exactly the size of the number of tokens in the HTML string.
	 *	Example:	HTML string = "Goodnight moon goodnight stars"
	 *				returns { "Goodnight", "moon", "goodnight", "stars" }
	 *	@param str			the HTML string
	 *	@return				the String array of tokens
	 */
	public String[] tokenizeHTMLString(String str) {
		// make the size of the array large to start
		String[] result = new String[100000];
		int index = -1;
		int counter = 0;
		boolean isToken = true;
		boolean isToken1 = true;
		
		for(int i = 0; i < str.length(); i++){
			char current = str.charAt(i);
			if(current == '<'){
				index = str.indexOf('>',i);	
				
				result[counter] = str.substring(i,index+1);
				counter++;
				i = index;
			}
			else if(Character.isLetter(current)){
				for(int j = i; j<str.length(); j++){ 
					if(str.charAt(j) != 45 && !Character.isLetter(str.charAt(j))){
						index = j-1;
						j = str.length()+1;
					}
					else if(j==str.length()-1)
						index = str.length()-1;
				}
				result[counter] = str.substring(i,index+1);
				counter++;
				i = index;
			}
			else if(current == '-' || Character.isDigit(current)){
				for(int j = i; j<str.length(); j++){ 
					if(j==str.length()-1)
						index = str.length()-1;
					else if(!Character.isDigit(str.charAt(j)) && str.charAt(j) != '.' && str.charAt(j) != 'e' && str.charAt(j) != '-'){
						index = j-1;
						j = str.length()+1;
					}
				}
				result[counter] = str.substring(i,index+1);
				counter++;
				i = index;
			}
			else if(isPunctuation(current)){
				result[counter] = str.charAt(i)+"";
				counter++;
			}
		}
		// return the correctly sized array
		String [] arr = new String[counter];
		for(int i = 0; i < arr.length; i++){
			arr[i] = result[i];
		}
		return arr;
	}
	public boolean isPunctuation(char c){
		char [] arr = {'.', ',', ';', ':', '(', ')', '?', '!', '=', '&', '~', '+', '-'};
		for(int i = 0; i < arr.length; i++){
			if(arr[i] == c) return true;
		}
		return false;
	}
	/**
	 *	Print the tokens in the array to the screen
	 *	Precondition: All elements in the array are valid String objects.
	 *				(no nulls)
	 *	@param tokens		an array of String tokens
	 */
	public void printTokens(String[] tokens) {
		if (tokens == null) return;
		for (int a = 0; a < tokens.length; a++) {
			if (a % 5 == 0) System.out.print("\n  ");
			System.out.print("[token " + a + "]: " + tokens[a] + " ");
		}
		System.out.println();
	}

}
