import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author M.J.M 
 */

public class Calculator {
	ArrayList<Float> history = new ArrayList<>(); //stores all previously calculated valid results
	private float result; //computed result
	private float memoryValue = 0; //result the user chooses to temporarily store
	private boolean recursion; //if the function is being called recursively
	public static void main(String[] args) {
				
		Calculator myCalc = new Calculator();
		Scanner sc = new Scanner(System.in);
		String uInput = "";
		
		while (uInput != "x") {
			uInput = sc.nextLine();
			switch (uInput) {
			case "m": //stores result value in memory
				myCalc.setMemoryValue(myCalc.getCurrentValue());
				break;
			case "mr": //outputs value stored in memory
				System.out.println(myCalc.getMemoryValue());
				break;
			case "c": //clears memory
				myCalc.clearMemory();
				break;
			case "h": //displays history
				myCalc.displayHistory();
				break;
			case "x":
				break;
			default:
				if (uInput.length() < 10) {
					System.out.println("Invalid input.");
					continue;
				}
				if (uInput.substring(0,8).equals("evaluate")) {
					try { //evaluate expression
						System.out.println(myCalc.evaluate(uInput.substring(9, uInput.length() - 1)));
					} catch (Exception e) {		
					}
				}
			}
		}
		sc.close();
		
	}
	

	public Calculator() { //constructor
		history = new ArrayList<>();
		result = 0;
		memoryValue = 0;
		recursion = false;
	}
	
	public float evaluate(String expression) {
		int currentIndex, spaceCount = 0;
		float operand1, operand2;
		char operator;		
		
		if (expression.isBlank()) { //blank expression is invalid
			System.out.println("Invalid input.");
			result = 0;
			return Float.MIN_VALUE;
		}
		
		String currentExpress = expression.replace(' ', '#'); 
		
		if (((expression.substring(0,1).equals("+")) || (expression.substring(0,1).equals("-")) //format {operator}{space}{operand}
				|| (expression.substring(0,1).equals("*")) || (expression.substring(0,1).equals("/"))) && (currentExpress.indexOf('#') == 1)) {
			try {
				operand1 = Float.parseFloat(expression.substring(2)); //takes operand
			} catch (Exception e) {
				System.out.println("Invalid input.");
				result = 0;
				return Float.MIN_VALUE;
			}
			switch (expression.substring(0,1)) { //selects operator then computes result
			case "+":
				result = memoryValue + operand1;
				if (recursion == false) {history.add(result);}
				return result;
			case "-":
				result = memoryValue - operand1;
				if (recursion == false) {history.add(result);}
				return result;
			case "*":
				result = memoryValue * operand1;
				if (recursion == false) {history.add(result);}
				return result;
			case "/":
				if (operand1 == 0) {
					System.out.println("Invalid input.");
					return Float.MIN_VALUE;
				}
				result = memoryValue / operand1;
				if (recursion == false) {history.add(result);}
				return result;
			default:
				System.out.println("Invalid input.");
				result = 0;
				return Float.MIN_VALUE;
			}
		}

		String testExpress = currentExpress;
		int testLength = testExpress.length(), parIndex = -1, testIndex = -2;
		boolean parMode = false;
		
		if (expression.substring(0,1).equals("(")) {parMode = true;} //expression with parentheses
		
		for (int i=0; i < testLength; i++) { //counts number of spaces/hashes in expression
			if (testExpress.charAt(0) == '#') {
				spaceCount++;
				if ((spaceCount == 3) && parMode) {parIndex = i;}
				if ((i - 1) == testIndex) {
					System.out.println("Invalid input.");
					result = 0;
					return Float.MIN_VALUE;
				} else {testIndex = i;}
			}
			testExpress = testExpress.substring(1);
		}
		//note: standard 'expression' format is {operand}{space}{operator}{space}{operand}
		if (parMode) { //format {expression}{space}{operator}{space}{expression}
			
			if (spaceCount != 6) { //this format has exactly 6 spaces so it checks for that
				System.out.println("Invalid input.");
				result = 0;
				return Float.MIN_VALUE;
			}
			try {
				recursion = true; //recursively calls evaluate() for each expression in the parentheses
				operand1 = evaluate(expression.substring(1, parIndex - 1)); //each result forms an operand
				operator = expression.charAt(parIndex + 1);
				operand2 = evaluate(expression.substring(parIndex + 4, expression.length() - 1));
				if ((operand1 == Float.MIN_VALUE) || (operand2 == Float.MIN_VALUE)) { //if one of the inner expressions was invalid then the entire expression is invalid
					recursion = false;
					result = 0;
					return Float.MIN_VALUE;
				}
				String opFinal = (Float.toString(operand1) + " " + operator + " " + Float.toString(operand2)); 
				recursion = false;
				return evaluate(opFinal);
			} catch (Exception e) {
				System.out.println("Invalid input.");
				result = 0;
				return Float.MIN_VALUE;
			}
		}
				
		if (spaceCount == 2) { //standard expression - format {operand}{space}{operator}{space}{operand}
			//manipulates expression string to collect each operator
			currentIndex = currentExpress.indexOf('#');
			try {
				operand1 = Float.parseFloat(expression.substring(0, currentIndex));
			} catch (Exception e) {
				System.out.println("Invalid input.");
				result = 0;
				return Float.MIN_VALUE;
			}
			
			currentExpress = currentExpress.substring(currentIndex + 1);
			operator = currentExpress.charAt(0);
			
			try {
				operand2 = Float.parseFloat(currentExpress.substring(2));

			} catch (Exception e) {
				System.out.println("Invalid input.");
				result = 0;
				return Float.MIN_VALUE;
			}
			
			
			switch(operator) //selects operator
			{
			case '+':
				result = (operand1 + operand2);
				if (recursion == false) {history.add(result);}
				return result;
			case '-':
				result = (operand1 - operand2);
				if (recursion == false) {history.add(result);}
				return result;
			case '*':
				result = (operand1 * operand2);
				if (recursion == false) {history.add(result);}
				return result;
			case '/':
				if (operand2 == 0) {System.out.println("Invalid input."); result = 0; return Float.MIN_VALUE;}
				result = (operand1 / operand2);
				if (recursion == false) {history.add(result);}
				return result;
			default:
				System.out.println("Invalid input.");
				result = 0;
				return Float.MIN_VALUE;
			}
		}
		//expression of arbitrary length
		ArrayList<String> operation = new ArrayList<>(); //array which splits up each operand and operator in the expression
		String sendExpress = currentExpress;
		while (sendExpress.indexOf('#') > -1) { //while a space exists it collects elements in the operation array
			operation.add(sendExpress.substring(0, sendExpress.indexOf('#')));
			sendExpress = sendExpress.substring(sendExpress.indexOf('#') + 1);
		}
		operation.add(sendExpress);
		
		if (operation.size() == 1) { //validates input
			System.out.println("Invalid input.");
			result = 0;
			return Float.MIN_VALUE;
		}
		
		String operationStr = "";
		int opIndex;
		float newOperand, testFloat;
		
		for (int i=0; i < operation.size(); i++) { //validates each element
			switch(operation.get(i)) { //checks if it's an operator
			case "+":
				break;
			case "-":
				break;
			case "*":
				break;
			case "/":
				break;
			default:
				try { //checks if it's an operand
					testFloat = Float.parseFloat(operation.get(i));
				} catch (Exception e) { //if it's not numeric then it can't be converted to float - not valid
					System.out.println("Invalid input.");
					result = 0;
					return Float.MIN_VALUE;
				}
			}
		}
		
		//order of operations: * or / -> + or -
		while (operation.contains("*") || operation.contains("/")) { //forms expressions with * or / first (left -> right order)
			if (operation.contains("*") && operation.contains("/")) {
				if (operation.indexOf("*") < operation.indexOf("/")) {
					opIndex = operation.indexOf("*");
				} else {
					opIndex = operation.indexOf("/");
				}
			} else {
				if (operation.indexOf("*") != -1) {
					opIndex = operation.indexOf("*");
				} else {
					opIndex = operation.indexOf("/");
				}
				
			}
			
			try { //validation
				testFloat = Float.parseFloat(operation.get(opIndex - 1));
				testFloat = Float.parseFloat(operation.get(opIndex + 1));
			} catch (Exception e) {
				System.out.println("Invalid input.");
				result = 0;
				return Float.MIN_VALUE;
			}
			
			operationStr = operation.get(opIndex - 1) + " " + operation.get(opIndex) + " " + operation.get(opIndex + 1); 
			recursion = true;
			newOperand = evaluate(operationStr); //evaluates small expression
			recursion = false;
			if (newOperand == Float.MIN_VALUE) {
				recursion = false;
				result = 0;
				return Float.MIN_VALUE;
			}
			operation.set(opIndex, Float.toString(newOperand)); //replaces elements with the result of the computed expression
			operation.remove(opIndex - 1);
			operation.remove(opIndex);	
		}
		while (operation.size() > 1) { //now forms expressions with + or -
			if (operation.contains("+") && operation.contains("-")) {
				if (operation.indexOf("+") < operation.indexOf("-")) {
					opIndex = operation.indexOf("+");
				} else {
					opIndex = operation.indexOf("-");
				}
			} else {
				if (operation.indexOf("+") != -1) {
					opIndex = operation.indexOf("+");
				} else {
					opIndex = operation.indexOf("-");
				}
			}
			
			try {
				testFloat = Float.parseFloat(operation.get(opIndex - 1));
				testFloat = Float.parseFloat(operation.get(opIndex + 1));
			} catch (Exception e) {
				System.out.println("Invalid input.");
				result = 0;
				return Float.MIN_VALUE;
			}
			
			operationStr = operation.get(opIndex - 1) + " " + operation.get(opIndex) + " " + operation.get(opIndex + 1);
			recursion = true;
			newOperand = evaluate(operationStr); //evaluates small expression
			recursion = false;
			operation.set(opIndex, Float.toString(newOperand)); //replaces elements with the result of the computed expression
			operation.remove(opIndex - 1);
			operation.remove(opIndex);	
		}
		result = Float.parseFloat(operation.get(0));
		if (recursion == false) {history.add(result);}
		return result;
				
	}
	
	public float getCurrentValue() {
		return result;
	}
	
	public float getMemoryValue() {
		return memoryValue;
	}
	
	public void setMemoryValue(float memval) {
		memoryValue = memval;
	}
	
	public void clearMemory() {
		setMemoryValue(0);
	}
	
	public float getHistoryValue(int index) {
		try {
			return history.get(index);
		} catch (Exception e) {
			System.out.println("Index out of range.");
			return 0;
		}
	}
	
	public void displayHistory() { //displays history of results using for loop
		for (int i=0; i < history.size(); i++) {
			System.out.print(getHistoryValue(i));
			System.out.print(" ");
		}
		System.out.println();

	}
}
