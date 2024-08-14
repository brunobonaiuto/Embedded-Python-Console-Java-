#include <stdio.h>
#include <Python.h>

PyObject* init_module(const char* s);
const char* compile_and_eval(const char* line_of_code, PyObject* global_dict);
const char* get_string_from_pyobject(PyObject* o);

int main()
{
    Py_Initialize();

    PyObject* global_dict = init_module("__main__");

    PyObject* version_sys = PySys_GetObject("version");
    const char* version_sys_str = get_string_from_pyobject(version_sys);
    printf("\nversion from PySys is: \n%s", version_sys_str);

    PyObject* platform_sys = PySys_GetObject("platform");
    const char* platform_sys_str = get_string_from_pyobject(platform_sys);
    printf("\nplatfrom from PySys is: \n%s", platform_sys_str);

    const char* version_str2 = Py_GetVersion();
    printf("\nversion from PyGetVersion() is: \n%s", version_str2);

    const char* platform_str2 = Py_GetPlatform();
    printf("\nplatform from PyGetPlatform() is: \n%s", platform_str2);

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
const char* get_string_from_pyobject(PyObject* o){
    PyObject* o_str = PyObject_Str(o);
    const char* o_str_char = PyUnicode_AsUTF8(o_str);
    return o_str_char;
}