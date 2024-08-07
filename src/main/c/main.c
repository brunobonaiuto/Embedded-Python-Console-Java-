#include <stdio.h>
#include <Python.h>

void py_run_string(const char* s);
void eval(const char* s, int input_type);
void create_main_and_global();
void add_variable_in_global(const char* stringCode);

int main()
{
    Py_Initialize();
//    //calling methods
//    char code1[] = "print(\"hello\")";
//    //it compiles and evaluates the code, if a print is contained it will print the result without any printf from C
//    py_run_string(code1);
//    char code2[] = "5+5";
//    //char code3[] = "def myFunction(number):\n   return number\nprint('able to print out of function')";
//
//    eval(code2,Py_eval_input); //the result from PyEval is : 10
//    eval(code2,Py_file_input); //the result from PyEval is : None
//    eval(code2,Py_single_input); //10the result from PyEval is : None, but also evaluates and expression and prints value 10

    //Creation of module __main__ and the global dict
    //create_main_and_global();

    add_variable_in_global("");

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

void create_main_and_global(){
    //PyObject* global_dict;
    PyObject* local_dict;

    //global_dict = PyDict_New();
    local_dict = PyDict_New();

    //create the main module
    PyObject* main_module = PyImport_AddModule("__main__"); //returns --> {'__name__': '__main__', '__doc__': None, '__package__': None, '__loader__': <class '_frozen_importlib.BuiltinImporter'>, '__spec__': None, '__annotations__': {}, '__builtins__': <module 'builtins' (built-in)>}
    //PyObject* main_module = PyModule_New("__main__");  //returns --> {'__name__': '__main__', '__doc__': None, '__package__': None, '__loader__': None, '__spec__': None}

    PyObject* global_dict = PyModule_GetDict(main_module);

    //global
    PyObject* global_dict_str = PyObject_Str(global_dict);
    const char *global_dict_str_to_c = PyUnicode_AsUTF8(global_dict_str);

    //local
    PyObject* local_dict_str = PyObject_Str(local_dict);
    const char *local_dict_str_to_c = PyUnicode_AsUTF8(local_dict_str);

    //prints
    printf("\nglobal dict: %s", global_dict_str_to_c);
    printf("\n-------------------------------\n");
    printf("local dict: %s", local_dict_str_to_c);
}

void add_variable_in_global(const char* stringCode){
    //PyObject* global_dict;
    PyObject* local_dict;

    //global_dict = PyDict_New();
    local_dict = PyDict_New();

    //create the main module
    PyObject* main_module = PyImport_AddModule("__main__"); //returns --> {'__name__': '__main__', '__doc__': None, '__package__': None, '__loader__': <class '_frozen_importlib.BuiltinImporter'>, '__spec__': None, '__annotations__': {}, '__builtins__': <module 'builtins' (built-in)>}
    //PyObject* main_module = PyModule_New("__main__");  //returns --> {'__name__': '__main__', '__doc__': None, '__package__': None, '__loader__': None, '__spec__': None}

    if(PyErr_Occurred() == NULL){
        printf("\nNo error before");
    }
    //PyObject* global_dict = PyModule_GetDict(local_dict); //will fail if i dont pass a module
    PyObject* global_dict = PyModule_GetDict(main_module);
    if(PyErr_Occurred() != NULL){
        printf("\nAn error happend also");
    }
    local_dict = global_dict;

    //global
    PyObject* global_dict_str = PyObject_Str(global_dict);
    const char *global_dict_str_to_c = PyUnicode_AsUTF8(global_dict_str);

    //local
    PyObject* local_dict_str = PyObject_Str(local_dict);
    const char *local_dict_str_to_c = PyUnicode_AsUTF8(local_dict_str);

    //prints
    printf("\nbefore eval:");
    printf("\nglobal dict: %s", global_dict_str_to_c);
    printf("\n-------------------------------\n");
    printf("local dict: %s", local_dict_str_to_c);
    //----------------------------------------------------------------------
    printf("\nafter eval:");
    //compile string first
    PyObject* code = Py_CompileString(stringCode, "",Py_file_input);
    if(code == NULL && PyErr_Occurred() != NULL){
        printf("im not able to compile, and the error occurred");
    }

    PyObject* new_module_object = PyImport_ExecCodeModule("__main__", code);

    //PyObject* eval_code = PyEval_EvalCode(code, global_dict, local_dict);
    PyObject* new_module_in_dict = PyModule_GetDict(new_module_object);

    //global
    PyObject* new_module = PyObject_Str(new_module_in_dict);
    const char* new_module_in_c = PyUnicode_AsUTF8(new_module);

    printf("\nglobal dict: %s", new_module_in_c);

    PyObject* compiledCode = Py_CompileString("","", Py_eval_input);
    //optional the execute, at the end is adding to dict
    new_module_object = PyImport_ExecCodeModule("__main__", compiledCode);
    PyObject* resultFromEval = PyEval_EvalCode(compiledCode,global_dict, global_dict);

    PyObject* resultInStringPython = PyObject_Str(resultFromEval);

    const char *resultInStringC = PyUnicode_AsUTF8(resultInStringPython);
    printf("\nthe result from PyEval is : %s", resultInStringC);

}