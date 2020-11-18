# Calculator
This calculator allows the user to input an expression in the following forms:
* a {operator} b
* (a {operator} b) {operator} (c {operator} d)
Where a,b,c,d are operands (numbers) and the operations consist of addition, subtraction, multiplication and division.
Note that division by 0 is invalid. If the expression is invalid, then the program will say so.
The program then evaluates and outputs the result of the expression as a float.
The user can also save the result, output the saved value, and perform further calculations on it, using the form '{operator} {operand}'.
There is also a feature to display the history of all the valid computed results.
One more expression form that can be evaluated is an expressions of arbitrary length, which uses Java Operator Precedence.
Commands:
* 'evaluate(expression)' - as long as the 'expression' is in one of the forms stated above, this will output the result of that evaluated expression.
* 'm' - stores the last computed result value in memory (0 if the last expression was invalid).
* 'mr' - outputs the value stored in memory.
* 'c' - clears the memory.
* 'h' - displays the history.

# What I Learnt/Applied
* Handling ArrayLists in Java.
* String manipulation.
* Writing recursive functions.
* Iterating through ArrayLists.
* Taking user input using the Scanner class.
