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
        "         print('Python %d' % (i))                      \n"
        "         i += 1                               \n"
        "         time.sleep(1)                        \n"
        ""
        "t = threading.Thread(target=job, args = ())   \n"
        "t.daemon = True                               \n"
        "t.start()                                     \n";
    PyEval_InitThreads();
    //------------------

    Py_Initialize();

    PyObject* global_dict = init_module("__main__");

    const char* result_from_eval = compile_and_eval(script, global_dict);
//    printf("\nresult from eval %s end]", result_from_eval);


    //------------
    //Py_BEGIN_ALLOW_THREADS
    PyThreadState *_save;

    _save = PyEval_SaveThread();
    while (1){
        printf("hello from c\n");
        usleep(1000 * 1000);
//        const char* result_from_eval = compile_and_eval("print(\"hello\"", global_dict);
//        printf("\nresult from eval %s end]", result_from_eval);
    }
    PyEval_RestoreThread(_save);
    //Py_END_ALLOW_THREADS

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