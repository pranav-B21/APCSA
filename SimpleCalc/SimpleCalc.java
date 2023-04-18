import java.util.List;		// used by expression evaluator

/**
 *	This class is a simple calculator that uses stacks to perform its 
 * 	calculations. Each operiton and operand are seperated into 2 different
 * 	stacks and depending on precedence a calculation is performed, and 
 * 	the result is printed out in the end.
 * 
 *
 *	@author	Pranav Belligundu
 *	@since	Feb 21st, 2022
 */
public class SimpleCalc {
	
	private ExprUtils utils;	// expression utilities
	
	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack

	// constructor	
	public SimpleCalc() {
		utils = new ExprUtils();
		valueStack = new ArrayStack<Double>();
		operatorStack = new ArrayStack<String>();
	}
	
	public static void main(String[] args) {
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}
	
	public void run() {
		System.out.println("\nWelcome to SimpleCalc!!!\n");
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}
	
	/**
	 *	Prompt the user for expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc() {
		String userInput = "";
		while (!userInput.trim().equals("q")) {
			userInput = Prompt.getString("");
			if (userInput.trim().equals("h")) 
				printHelp();
			else if (!userInput.trim().equals("q")) {
				double answer = evaluateExpression(utils.tokenizeExpression(userInput));
				System.out.println(answer);
			}	
		}
	}
	
	/**	Print help */
	public void printHelp() {
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'\n");
	}
	
	/**
	 *	Evaluate expression and return the value
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens) {	
		for(int i = 0; i < tokens.size(); i++){
			String str = tokens.get(i);
			if (Character.isDigit(str.charAt(0)))
				valueStack.push(Double.parseDouble(str));
			else if (str.equals("("))
				operatorStack.push(str);
			else if (str.equals(")")) {
				while (!operatorStack.peek().equals("(")) {
					valueStack.push(evalTopTwo());
				}
				operatorStack.pop();
			}
			else if (operatorStack.isEmpty() || !operatorStack.isEmpty() && hasPrecedence(operatorStack.peek(), str))
				operatorStack.push(str);
			else  {
				valueStack.push(evalTopTwo());
				operatorStack.push(str);
			}
		}
		while (!operatorStack.isEmpty()) {
			valueStack.push(evalTopTwo());
		}
		
		return valueStack.pop();
	}
	
	public double evalTopTwo(){
		String operation = operatorStack.pop();
		double val1 = valueStack.pop();
		switch(operation){
			case "+":
				return valueStack.pop() + val1;
			case "-":
				return valueStack.pop() - val1;
			case "*":
				return valueStack.pop() * val1;
			case "/":
				return valueStack.pop() / val1;
			case "%":
				return valueStack.pop() % val1;			
			case "^":
				return Math.pow(valueStack.pop(), val1);		
		}
		return 0.0;
	}
	
	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher precedence; false otherwise
	 *	Algorithm:
	 * 		if op1 is either left or right parenthesis, then true
	 *		if op2 is exponent, and op1 is not exponent then true
	 *		if op1 is addition or subtraction and 
	 *				op2 is multiplication or division or modulus, then true
	 *		otherwise false
	 */
	private boolean hasPrecedence(String op1, String op2) {
		if (op1.equals("(") || op1.equals(")")) return true;
		if (op2.equals("^") && !op1.equals("^")) return true;	
		if ((op1.equals("+") || op1.equals("-"))
				&& (op2.equals("*") || op2.equals("/") || op2.equals("%")))
			return true;	
		return false;
	}
	 
}
