package org.example.python;

import com.sun.jna.Library;
import org.example.python.objects.PyGILState_STATE;
import org.example.python.objects.PyObject;
import org.example.python.objects.PyThreadState;

public interface JavaPython extends Library {
    void Py_Initialize();

    PyObject PyImport_AddModule(String moduleName);

    PyObject PyModule_GetDict(PyObject module);

    PyObject PyObject_Str(PyObject object);

    String PyUnicode_AsUTF8(PyObject stringPython);

    PyObject PyUnicode_FromString(String string);

    PyObject Py_CompileString(String stringCode, String fileName, int input_type);

    PyObject PyEval_EvalCode(PyObject compileCode, PyObject globals, PyObject locals);

    int Py_FinalizeEx();

    PyObject PyErr_Occurred();

    void PyErr_Clear();

    PyObject PyErr_GetRaisedException();

    PyObject PyImport_ImportModule(String moduleName);

    int PyCallable_Check(PyObject functionName);

    PyObject PyObject_GetAttrString(PyObject moduleName, String functionName);

    PyObject PyObject_CallObject(PyObject pFunc, PyObject pArgs);

    PyObject PyTuple_New(int i);

    int PyTuple_SetItem(PyObject pArgs, int i, PyObject pValue);

    String Py_GetVersion();

    String Py_GetPlatform();

    PyGILState_STATE PyGILState_Ensure();

    void PyGILState_Release(PyGILState_STATE state);

    PyThreadState PyEval_SaveThread();

    void PyEval_RestoreThread(PyThreadState state);


    void PySys_SetObject(String stdout, PyObject object);

    PyObject PySys_GetObject(String object);

    PyObject PyObject_CallMethod(PyObject object, String methodName, String format);
}
