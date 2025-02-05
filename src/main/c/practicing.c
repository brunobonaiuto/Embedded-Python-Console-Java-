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

    add_variable_in_global("b+50");

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
        //PyThreadState_GET();
        printf("\n **** Error **** \n");
        //PyErr_Print();
        PyObject* exception =  PyErr_GetRaisedException();
        PyObject* traceback = PyException_GetTraceback(exception);
        PyObject* exception2 = PyObject_Str(exception);
        PyObject* traceback2 = PyObject_Str(traceback);
        const char* exception3 = PyUnicode_AsUTF8(exception2);
        const char* traceback3 = PyUnicode_AsUTF8(traceback2);
        printf("exception %s", exception3);
        printf("traceback %s", traceback3);

        printf("im not able to compile, and the error occurred");
    }

//------ set up std err ----------
    PyObject* io = NULL;
    PyObject* string_io = NULL;
    PyObject* string_io_instance = NULL;
    io = PyImport_ImportModule("io");
    string_io = PyObject_GetAttrString(io,"StringIO");
    string_io_instance = PyObject_CallFunctionObjArgs(string_io,NULL);
    PySys_SetObject("stderr",string_io_instance);
    PyObject* code2 = Py_CompileString("sdsdsdsd", "",Py_file_input);
    PyObject* resultFromEval99 = PyEval_EvalCode(code2,global_dict, global_dict);

    PyObject *value = NULL, *encoded = NULL;

    char* result = NULL;
    char* temp_result = NULL;
    Py_ssize_t size = 0;

    PyObject* theError = PySys_GetObject("stderr");
    value = PyObject_CallMethod(theError,"getvalue",NULL);
    PyObject* value2 = PyObject_Str(value);
    const char* value3 = PyUnicode_AsUTF8(value2);
    printf("the std is: %s, which was our print",value3);

    encoded = PyUnicode_AsEncodedString(value,"utf-8","strict");

    PyBytes_AsStringAndSize(encoded,&temp_result,&size);
    size += 1;

    result = malloc(sizeof(char)*size);
    for (int i = 0; i<size; ++i) {
        result[i] = temp_result[i];
    }

    printf("\nthe capturing the stderr: %s", result);



    PyObject* new_module_object = PyImport_ExecCodeModule("__main__", code);
        if(new_module_object == NULL && PyErr_Occurred() != NULL){
            //PyThreadState_GET();
            printf("\n **** Error **** \n");


        //------- get the std err -----------






//            PyObject* exception =  PyErr_GetRaisedException();
//            PyObject* exec = PyErr_GetHandledException();
//            PyObject* exception2 = PyObject_Str(exec);
//            const char* exception3 = PyUnicode_AsUTF8(exception2);
//            printf("%s", exception3);

//            PyObject *ptype = NULL, *pvalue = NULL, *ptraceback = NULL;
//            pvalue     = PySys_GetObject("last_value");
//            ptype      = PySys_GetObject("last_type");
//            ptraceback = PySys_GetObject("last_traceback");

            //PyErr_Print();
//            PyObject* exception =  PyErr_GetRaisedException();
//            PyObject* traceback = PyException_GetTraceback(exception);
//            PyObject* exception2 = PyObject_Str(exception);
//            PyObject* traceback2 = PyObject_Str(traceback);
//            const char* exception3 = PyUnicode_AsUTF8(exception2);
//            const char* traceback3 = PyUnicode_AsUTF8(traceback2);
//            printf("exception %s", exception3);
//            printf("traceback %s", traceback3);

            //printf("im not able to compile, and the error occurred");
        }


    //PyObject* eval_code = PyEval_EvalCode(code, global_dict, local_dict);
    PyObject* new_module_in_dict = PyModule_GetDict(new_module_object);

    //global
    PyObject* new_module = PyObject_Str(new_module_in_dict);
    const char* new_module_in_c = PyUnicode_AsUTF8(new_module);

    printf("\nglobal dict: %s", new_module_in_c);

    PyObject* compiledCode = Py_CompileString("a = a + 1","", Py_file_input);
    //optional the execute, at the end is adding to dict
    new_module_object = PyImport_ExecCodeModule("__main__", compiledCode);
    PyObject* resultFromEval = PyEval_EvalCode(compiledCode,global_dict, global_dict);

    PyObject* resultInStringPython = PyObject_Str(resultFromEval);

    const char *resultInStringC = PyUnicode_AsUTF8(resultInStringPython);
    printf("\nthe result from PyEval2 is : %s", resultInStringC);

    //-------------
    PyObject* compiledCode2 = Py_CompileString("a","", Py_eval_input);
    //optional the execute, at the end is adding to dict
    PyImport_ExecCodeModule("__main__", compiledCode2);
    PyObject* resultFromEval2 = PyEval_EvalCode(compiledCode2,global_dict, global_dict);

    PyObject* resultInStringPython2 = PyObject_Str(resultFromEval2);

    const char *resultInStringC2 = PyUnicode_AsUTF8(resultInStringPython2);
    printf("\nthe result from PyEval is : %s", resultInStringC2);

}