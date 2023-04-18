/**
 *  This class creates and manages one array of pegs from the game MasterMind.
 *
 *  @author Pranav Belligundu
 *  @since September 30, 2021
*/

public class PegArray {

	// array of pegs
	private Peg [] pegs;

	// the number of exact and partial matches for this array
	// as matched against the master.
	// Precondition: these values are valid after getExactMatches() and getPartialMatches()
	//				are called
	private int exactMatches, partialMatches;
		
	/**
	 *	Constructor
	 *	@param numPegs	number of pegs in the array
	 */
	public PegArray(int numPegs) {	
		pegs = new Peg[numPegs];
		for(int i = 0; i < pegs.length; i++) pegs[i] = new Peg();
	}
	
	/**
	 *	Return the peg object
	 *	@param n	The peg index into the array
	 *	@return		the peg object
	 */
	public Peg getPeg(int n) { return pegs[n]; }
	
	/**
	 *  Finds exact matches between master (key) peg array and this peg array
	 *	Postcondition: field exactMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 *	@return			The number of exact matches
	 */
	public int getExactMatches(PegArray master) {
		
		exactMatches = 0;
		for(int i = 0; i < 4; i++){
			if(pegs[i].getLetter() == master.getPeg(i).getLetter()){
				exactMatches++;
				//System.out.println(exactMatches);
			}
		}
		return exactMatches;
	}
	
	/**
	 *  Find partial matches between master (key) peg array and this peg array
	 *	Postcondition: field partialMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 *	@return			The number of partial matches
	 */
	public int getPartialMatches(PegArray master) {
		int[] freq = new int[6];
		int[] masterFreq = new int[6];
		int total = 0;
		for(int i = 0; i < pegs.length; i++){
			freq[pegs[i].getLetter()-'A']++;
			masterFreq[master.getPeg(i).getLetter() - 'A']++;
		}
		for(int i = 0; i < freq.length; i++){
			total += Math.min(freq[i],masterFreq[i]);
		}
		partialMatches = total - getExact();
		//System.out.println(partialMatches);
		return partialMatches;
	}
	
	// Accessor methods
	// Precondition: getExactMatches() and getPartialMatches() must be called first
	public int getExact() { return exactMatches; }
	public int getPartial() { return partialMatches; }

}
