#include <iostream>
#include <limits>
#include <string>
#include <regex>
#include <vector>
using namespace std;

float evalCore(string operandS1, string operator1, string operandS2)
{
    float operand1 = stof(operandS1);float operand2 = stof(operandS2);
    if (operator1.compare("+") == 0) {return (operand1 + operand2);}
    if (operator1.compare("-") == 0) {return (operand1 - operand2);}
    if (operator1.compare("/") == 0) {if (operand2 != 0) {return (operand1 / operand2);} else {cout << "invalid "; return numeric_limits<float>::min();}}
    if (operator1.compare("*") == 0) {return (operand1 * operand2);}

}

void test(string s)
{
    regex e("([+-/*])|([+-]?([[:digit:]])*([\\.])?[[:digit:]]+)");
    smatch m;
    while (regex_search(s,m,e)) {cout << m[0]; s=m.suffix().str();}
}

float evaluate(string s)
{
    try
    {
        vector<string> operation; float result;
        regex e("([+-/*])|([+-]?([[:digit:]])*([\\.])?[[:digit:]]+)");
        smatch m;
        while (regex_search(s,m,e)) {operation.push_back(m[0]); s=m.suffix().str();}
        for (int i=0;i<operation.size()-1;i+=2)
        {
            result = evalCore(operation[i],operation[i+1],operation[i+2]);
            operation[i+2] = to_string(result);
        }
        return stof(operation[operation.size()-1]);
    } catch (...) {cout << "invalid";}
}

int main()
{
    string uInput;
    do
    {
        cout << "Welcome to MJM288's calculator. Please type an expression:" << endl;
        getline(cin,uInput);
        cout << evaluate(uInput) << endl;
    } while (uInput != "q");

    cin.get();
    return 0;
}
