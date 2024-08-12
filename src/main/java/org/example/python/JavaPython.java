package org.example.python;

import com.sun.jna.Library;

public interface JavaPython extends Library {
    void Py_Initialize();

    PyObject PyDict_New();

    PyObject PyImport_AddModule(String moduleName);

    PyObject PyModule_GetDict(PyObject module);

    PyObject PyObject_Str(PyObject object);

    String PyUnicode_AsUTF8(PyObject stringPython);

    PyObject PyUnicode_FromString(String string);

    PyObject Py_CompileString(String stringCode, String fileName, int input_type);

    PyObject PyImport_ExecCodeModule(String moduleName, PyObject compiledCode);

    PyObject PyEval_EvalCode(PyObject compileCode, PyObject globals, PyObject locals);

    int Py_FinalizeEx();

    PyObject PyErr_Occurred();
    void PyErr_Clear();
    void PyErr_Print();
    PyObject PyErr_GetRaisedException();
    void PyErr_SetRaisedException(PyObject exc);
    int PyType_Check(PyObject o);
}
