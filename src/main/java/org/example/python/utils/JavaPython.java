package org.example.python.utils;

import com.sun.jna.Library;
import org.example.python.objects.PyGILState_STATE;
import org.example.python.objects.PyObject;
import org.example.python.objects.PyThreadState;

/**
 * Interface representing the Java bindings for the Python C API using JNA (Java Native Access).
 * This interface provides methods to interact with the Python interpreter and execute Python code from Java.
 */
public interface JavaPython extends Library {

    /**
     * Initializes the Python interpreter.
     */
    void Py_Initialize();

    /**
     * Adds a module to the Python interpreter.
     * @param moduleName the name of the module to add.
     * @return the module object.
     */
    PyObject PyImport_AddModule(String moduleName);

    /**
     * Gets the dictionary object of a module.
     * @param module the module object.
     * @return the dictionary object.
     */
    PyObject PyModule_GetDict(PyObject module);

    /**
     * Converts a Python object to its string representation.
     * @param object the Python object.
     * @return the string representation of the object.
     */
    PyObject PyObject_Str(PyObject object);

    /**
     * Converts a Python Unicode object to a UTF-8 encoded string.
     * @param stringPython the Python Unicode object.
     * @return the UTF-8 encoded string.
     */
    String PyUnicode_AsUTF8(PyObject stringPython);

    /**
     * Creates a Python Unicode object from a Java string.
     * @param string the Java string.
     * @return the Python Unicode object.
     */
    PyObject PyUnicode_FromString(String string);

    /**
     * Compiles a string of Python code.
     * @param stringCode the Python code as a string.
     * @param fileName the name of the file (for error messages).
     * @param input_type the type of input (e.g., file, eval, single).
     * @return the compiled code object.
     */
    PyObject Py_CompileString(String stringCode, String fileName, int input_type);

    /**
     * Evaluates a compiled code object.
     * @param compileCode the compiled code object.
     * @param globals the global variables dictionary.
     * @param locals the local variables dictionary.
     * @return the result of the evaluation.
     */
    PyObject PyEval_EvalCode(PyObject compileCode, PyObject globals, PyObject locals);

    /**
     * Finalizes the Python interpreter.
     * @return the result of the finalization.
     */
    int Py_FinalizeEx();

    /**
     * Checks if an error occurred in the Python interpreter.
     * @return the error object if an error occurred, null otherwise.
     */
    PyObject PyErr_Occurred();

    /**
     * Clears the error indicator in the Python interpreter.
     */
    void PyErr_Clear();

    /**
     * Gets the current raised exception in the Python interpreter.
     * @return the exception object.
     */
    PyObject PyErr_GetRaisedException();

    /**
     * Imports a module in the Python interpreter.
     * @param moduleName the name of the module to import.
     * @return the module object.
     */
    PyObject PyImport_ImportModule(String moduleName);

    /**
     * Checks if a Python object is callable.
     * @param functionName the Python object.
     * @return 1 if the object is callable, 0 otherwise.
     */
    int PyCallable_Check(PyObject functionName);

    /**
     * Gets an attribute from a Python object by name.
     * @param moduleName the Python object.
     * @param functionName the name of the attribute.
     * @return the attribute object.
     */
    PyObject PyObject_GetAttrString(PyObject moduleName, String functionName);

    /**
     * Calls a Python callable object with arguments.
     * @param pFunc the callable object.
     * @param pArgs the arguments.
     * @return the result of the call.
     */
    PyObject PyObject_CallObject(PyObject pFunc, PyObject pArgs);

    /**
     * Creates a new Python tuple.
     * @param i the size of the tuple.
     * @return the new tuple object.
     */
    PyObject PyTuple_New(int i);

    /**
     * Sets an item in a Python tuple.
     * @param pArgs the tuple object.
     * @param i the index.
     * @param pValue the value to set.
     * @return 0 on success, -1 on failure.
     */
    int PyTuple_SetItem(PyObject pArgs, int i, PyObject pValue);

    /**
     * Gets the Python interpreter version.
     * @return the version string.
     */
    String Py_GetVersion();

    /**
     * Gets the current platform of the Python interpreter.
     * @return the platform string.
     */
    String Py_GetPlatform();

    /**
     * Ensures the Global Interpreter Lock (GIL) is held by the current thread.
     * @return the previous GIL state.
     */
    PyGILState_STATE PyGILState_Ensure();

    /**
     * Releases the Global Interpreter Lock (GIL) held by the current thread.
     * @param state the previous GIL state.
     */
    void PyGILState_Release(PyGILState_STATE state);

    /**
     * Saves the current thread state and releases the GIL.
     * @return the thread state.
     */
    PyThreadState PyEval_SaveThread();

    /**
     * Restores the thread state and acquires the GIL.
     * @param state the thread state.
     */
    void PyEval_RestoreThread(PyThreadState state);

    /**
     * Sets a Python object in the sys module.
     * @param stdout the name of the object.
     * @param object the Python object.
     */
    void PySys_SetObject(String stdout, PyObject object);

    /**
     * Gets a Python object from the sys module.
     * @param object the name of the object.
     * @return the Python object.
     */
    PyObject PySys_GetObject(String object);

    /**
     * Calls a method on a Python object.
     * @param object the Python object.
     * @param methodName the name of the method.
     * @param format the format string for arguments.
     * @return the result of the call.
     */
    PyObject PyObject_CallMethod(PyObject object, String methodName, String format);

    /**
     * Checks if a Python object has a specific attribute.
     * @param moduleName the Python object.
     * @param attribute the name of the attribute.
     * @return 1 if the attribute exists, 0 otherwise.
     */
    int PyObject_HasAttrString(PyObject moduleName, String attribute);
}
