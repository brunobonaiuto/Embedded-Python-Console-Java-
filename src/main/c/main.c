#include <stdio.h>
#include <Python.h>

void py_run_string(const char* s);
void eval(const char* s, int input_type);
void create_main_and_global();
void add_variable_in_global(const char* stringCode);

int main()
{
    Py_Initialize();

    PyObject* local_dict;
    PyObject* main_module = PyImport_AddModule("__main__");
    if(PyErr_Occurred() != NULL){
        printf("\n error ");
    }

    PyObject* global_dict = PyModule_GetDict(main_module);
    if(PyErr_Occurred() != NULL){
        printf("\nAn error happend also");
    }

//------ set up std out ----------
    PyObject* io = NULL;
    PyObject* string_io = NULL;
    PyObject* string_io_instance = NULL;
    io = PyImport_ImportModule("io");
    string_io = PyObject_GetAttrString(io,"StringIO");
    string_io_instance = PyObject_CallFunctionObjArgs(string_io,NULL);
    PySys_SetObject("stdout",string_io_instance);
//-------------------------------------------------------

    PyObject* code = Py_CompileString("print(5+5)", "",Py_file_input);
    if(code == NULL && PyErr_Occurred() != NULL){
        printf("an error occurred");
    }
    PyObject* resultFromEval = PyEval_EvalCode(code,global_dict, global_dict);
    if(PyErr_Occurred() != NULL){
            printf("an error occurred");
    }
    PyObject* resultInStringPython = PyObject_Str(resultFromEval);

    const char *resultInStringC = PyUnicode_AsUTF8(resultInStringPython);
    printf("The result from eval is: %s", resultInStringC);

//-------------- get the stdout -----------
    PyObject* theStd = PySys_GetObject("stdout");
    PyObject* value = PyObject_CallMethod(theStd,"getvalue",NULL);
    PyObject* value2 = PyObject_Str(value);
    const char* value3 = PyUnicode_AsUTF8(value2);
    printf("the std is: %s, which was our print",value3);

    if (Py_FinalizeEx() < 0) {
        printf("Impossible to destroy interpreter");
    }
    return 1;

}