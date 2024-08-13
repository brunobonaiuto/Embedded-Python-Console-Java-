#include <stdio.h>
#include <Python.h>

PyObject* init_module(const char* s);
const char* compile_and_eval(const char* line_of_code, PyObject* global_dict);
PyObject* get_class_error_with_pyfetch();
PyObject* get_class_error_with_sysgetobject();
const char* get_string_from_pyobject(PyObject* o);
const char* get_format_exception_from_traceback(PyObject* exception_value);

int main()
{
    Py_Initialize();

    PyObject* global_dict = init_module("__main__");

    const char* result_from_eval = compile_and_eval("aa", global_dict);
    printf("\nresult from eval %s end]", result_from_eval);

    PyObject* value = get_class_error_with_pyfetch();
    const char* value_str = get_string_from_pyobject(value);
    printf("\nthe value from fetch is is: %s end] ", value_str);

    const char* full_err_message = get_format_exception_from_traceback(value);
    printf("\nresult from calling traceback.format_exception(exc_value) is:  %s ", full_err_message);

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
PyObject* get_class_error_with_pyfetch(){
    //PyObject *pExcType , *pExcValue , *pExcTraceback ;
    //PyErr_Fetch( pExcType , pExcValue , pExcTraceback ) ;
    //or an easier way
    PyObject* pExcValue = PyErr_GetRaisedException();
    return pExcValue;
}

//----------function ----------
PyObject* get_class_error_with_sysgetobject(){
    PyErr_PrintEx(1);
    PyObject* ptype = PySys_GetObject("last_type");
    return ptype;
}
//----------function ----------
const char* get_format_exception_from_traceback(PyObject* exception_value){
    PyObject* traceback_obj = PyImport_ImportModule("traceback");
    PyObject* method_name = PyObject_GetAttrString(traceback_obj,"format_exception");
    //PyObject* result = PyObject_CallMethodObjArgs(method_name, exception_value);
    PyObject* result = PyObject_CallFunctionObjArgs(method_name, exception_value);
    const char* result_str = get_string_from_pyobject(result);
    return result_str;
}
//----------function ----------
const char* get_string_from_pyobject(PyObject* o){
    PyObject* o_str = PyObject_Str(o);
    const char* o_str_char = PyUnicode_AsUTF8(o_str);
    return o_str_char;
}

// ---------------------- OLD CODE ----------------------

//void old_code(){
////------ set up std out/err ----------
//    PyObject* io = NULL;
//    PyObject* string_io = NULL;
//    PyObject* string_io_instance = NULL;
//    io = PyImport_ImportModule("io");
//    string_io = PyObject_GetAttrString(io,"StringIO");
//    string_io_instance = PyObject_CallFunctionObjArgs(string_io,NULL);
//    PySys_SetObject("stdout",string_io_instance);
////-------------------------------------------------------
//
//    PyObject* code = Py_CompileString("print(5+5+5+5+5)", "",Py_file_input);
//    if(code == NULL && PyErr_Occurred() != NULL){
//        printf("an error occurred");
//    }
//    PyObject* resultFromEval = PyEval_EvalCode(code,global_dict, global_dict);
//    if(PyErr_Occurred() != NULL){
//            printf("an error occurred");
//    }
//    PyObject* resultInStringPython = PyObject_Str(resultFromEval);
//
//    const char *resultInStringC = PyUnicode_AsUTF8(resultInStringPython);
//    printf("The result from eval is: %s", resultInStringC);
//
////-------------- get the stdout/err -----------
//    PyObject* theStd = PySys_GetObject("stdout");
//    PyObject* value = PyObject_CallMethod(theStd,"getvalue",NULL);
//    PyObject* value2 = PyObject_Str(value);
//    const char* value3 = PyUnicode_AsUTF8(value2);
//    printf("the std is: %s, which was our print",value3);
//    fprintf(stdout, "error is: %s", value3);
//}

