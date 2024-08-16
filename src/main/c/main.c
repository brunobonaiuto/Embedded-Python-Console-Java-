#include <stdio.h>
#include <Python.h>

PyObject* init_module(const char* s);
const char* compile_and_eval(const char* line_of_code, PyObject* global_dict);
const char* get_string_from_pyobject(PyObject* o);

int main()
{
     const char* script =
            "import time, threading                        \n"
            ""
            "def job():                                    \n"
            "    i = 0                                     \n"
            "    while True:                               \n"
            "         print('Python %d' % (i))             \n"
            "         i += 1                               \n"
            "         time.sleep(2)                        \n"
            //"job()                                         \n";
            ""
            "t = threading.Thread(target=job, args = ())   \n"
            "t.daemon = False                               \n"
            "t.start()                                     \n";

     const char* script2 =
            "import time, threading                        \n"
             ""
             "def job2():                                    \n"
             "    i = 99                                     \n"
             "    while True:                               \n"
             "         print('Python %d' % (i))             \n"
             "         i -= 1                               \n"
             "         time.sleep(3)                        \n"
             //"job()                                         \n";
             ""
             "t = threading.Thread(target=job2, args = ())   \n"
             "t.daemon = False                               \n"
             "t.start()                                     \n";

    Py_Initialize();

    PyObject* global_dict = init_module("__main__");

    PyGILState_STATE gstate2;
    gstate2 = PyGILState_Ensure();

    const char* result_from_eval1 = compile_and_eval(script, global_dict);

    const char* result_from_eval2 = compile_and_eval(script2, global_dict);

    PyGILState_Release(gstate2);

    if (Py_FinalizeEx() < 0) {
        printf("Impossible to destroy interpreter");
    }
    return 1;
}


//----------function ----------
PyObject* init_module(const char* s){
    PyObject* main_module = PyImport_AddModule("__main__");
    if(PyErr_Occurred() != NULL){
        printf("\n error Importing module");
    }

    PyObject* global_dict = PyModule_GetDict(main_module);
    if(PyErr_Occurred() != NULL){
        printf("\nAn error getting dict from module");
    }
    return global_dict;
}

//----------function ----------
const char* compile_and_eval(const char* line_of_code, PyObject* global_dict){
    PyObject* code = Py_CompileString(line_of_code, "", Py_file_input);
    if(code == NULL && PyErr_Occurred() != NULL){
        printf("an error occurred in compilation");
    }
    PyObject* resultFromEval = PyEval_EvalCode(code,global_dict, global_dict);
    if(PyErr_Occurred() != NULL){
            printf("an error occurred in eval");
    }
    PyObject* resultInStringPython = PyObject_Str(resultFromEval);
    const char *resultInStringC = PyUnicode_AsUTF8(resultInStringPython);
    return resultInStringC;
}

//----------function ----------
const char* get_string_from_pyobject(PyObject* o){
    PyObject* o_str = PyObject_Str(o);
    const char* o_str_char = PyUnicode_AsUTF8(o_str);
    return o_str_char;
}