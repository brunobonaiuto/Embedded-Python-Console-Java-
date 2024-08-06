#include <stdio.h>
#include <Python.h>

int main()
{
    Py_Initialize();

    char input[] = "print(\"Hello, im able to run a string\")";

    PyObject* global_dict;
    global_dict = PyDict_New();

    PyRun_String(input, Py_eval_input, global_dict, global_dict);

    if (Py_FinalizeEx() < 0) {
        printf("Impossible to destroy interpreter");
    }
    return 1;
}