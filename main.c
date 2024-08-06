#include <stdio.h>
#include <Python.h>

void py_run_string(const char* s);
void eval(const char* s, int input_type);

int main()
{

    Py_Initialize();
    //calling methods
    char code1[] = "print(\"hello\")";
    //it compiles and evaluates the code, if a print is contained it will print the result without any printf from C
    py_run_string(code1);
    char code2[] = "5+5";
    //char code3[] = "def myFunction(number):\n   return number\nprint('able to print out of function')";

    eval(code2,Py_eval_input); //the result from PyEval is : 10
    eval(code2,Py_file_input); //the result from PyEval is : None
    eval(code2,Py_single_input); //10the result from PyEval is : None, but also evaluates and expression and prints value 10

    if (Py_FinalizeEx() < 0) {
        printf("Impossible to destroy interpreter");
    }
    return 1;

}

void py_run_string(const char* s){
    PyObject* global_dict;
    global_dict = PyDict_New();
    PyRun_String(s, Py_eval_input, global_dict, global_dict);
}

void eval(const char* s, int input_type){
    //Py_eval_input is equivalent to the built-in {eval} -- it evaluates an expression.
    //Py_file_input is equivalent to {exec} -- It executes Python code, but does not return anything.
    //Py_single_input evaluates an expression and prints its value -- used in the interpreter.

    PyObject* global_dict = PyDict_New();
    PyObject* local_dict = PyDict_New();

    PyObject* compiledCode = Py_CompileString(s,"no file", input_type);
    PyObject* resultFromEval = PyEval_EvalCode(compiledCode,global_dict, local_dict);

    PyObject* resultInStringPython = PyObject_Str(resultFromEval);

    const char *resultInStringC = PyUnicode_AsUTF8(resultInStringPython);
    printf("\nthe result from PyEval is : %s", resultInStringC);
}