/**
 *	Utilities for handling HTML
 *
 *	@author	Pranav Belligundu
 *	@since	10/26/21
 */
public class HTMLUtilities {

	// NONE = not nested in a block, COMMENT = inside a comment block 
	// PREFORMAT = inside a pre-format block 
	private enum TokenState { NONE, COMMENT, PREFORMAT }; 
	// the current tokenizer state 
	private TokenState state;      
	
	public HTMLUtilities(){
		state = TokenState.NONE;
	}
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
		for(int i = 0; i < str.length(); i++){
			//System.out.println(str.indexOf("-->",i));
			char current = str.charAt(i);
			
			//the beginning of the comment
			if(str.indexOf("<!--",i) == i){
				state = TokenState.COMMENT;
			}
			
			//the begining of preformat
			else if(str.indexOf("<pre>",i) == i){
				state = TokenState.PREFORMAT;
			}
			
			//ending of preformat
			else if(str.indexOf("</pre>",i) == i){
				state = TokenState.NONE;
			}
			
			//ending of comment
			else if(str.indexOf("-->",i) == i){
				state = TokenState.NONE;
				i+=2; // so that is skips the "-->"
				continue;
			}
			
			if(state == TokenState.COMMENT){
				continue; // moves to the next iteration of the loop
			}
			if(state == TokenState.PREFORMAT){
				result[counter] = str;
				counter++;
				i = str.length();
			}
			else{
				
				// beginning of a token
				if(current == '<'){
					index = str.indexOf('>',i);	// finds the end
												// of the token
					
					result[counter] = str.substring(i,index+1);
					counter++;
					i = index;
				}
				
				// if the char is letter
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
				
				//if the char is a number/digit
				else if(current == '-' || Character.isDigit(current)){
					for(int j = i; j<str.length(); j++){ 
						if(j==str.length()-1)
							index = str.length()-1;
							
						//ignores these punctuation
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
			
		}
		
		// return the correctly sized array
		String [] arr = new String[counter];
		for(int i = 0; i < arr.length; i++){
			arr[i] = result[i];
		}
		return arr;
	}
	/**
	 *	Determines if the char passed in is a punctuation
	 * 
	 *	@param tokens		an array of String tokens
	 *	@return boolean 	return true if input is a punctuation
	 */
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
