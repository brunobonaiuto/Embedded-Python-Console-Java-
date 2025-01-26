package org.example.python.utils;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import org.example.python.objects.PyGILState_STATE;
import org.example.python.objects.PyObject;
import org.example.python.objects.PyThreadState;

import java.util.Optional;
import java.util.function.Supplier;

public class PyCaller {
    public static final String FILE_NAME = "<stdin>";
    public static final String IO_MODULE = "io";
    public static final String STRING_IO_ATT = "StringIO";
    public static final String FORMAT_EXCEPTION = "format_exception";

    JavaPython javaPython;

    public void initializePython() {
        javaPython = Native.load(Platform.isWindows() ? "C:/Users/bbbolivar/AppData/Local/Programs/Python/Python312/python312.dll" : "libpython3.10.so", JavaPython.class);
        javaPython.Py_Initialize();
    }

    public void destroy() {
        if (javaPython.Py_FinalizeEx() < 0) {
            throw new IllegalStateException("Impossible to destroy interpreter");
        }
    }

    public PyObject initModule(String moduleName) {
        validateModule(moduleName);
        return safeExecute(() -> javaPython.PyImport_AddModule(moduleName), "Could not create Module");
    }

    private void validateModule(String moduleName) {
        if (moduleName.isEmpty()) {
            throw new IllegalArgumentException("No module name");
        }
    }

    PyObject getModuleDict(PyObject pythonModule) {
        return safeExecute(() -> javaPython.PyModule_GetDict(pythonModule), "Could not get the module");
    }

    public PyObject fromPyObjectGetStr(PyObject object) {
        return safeExecute(() -> javaPython.PyObject_Str(object), "Could not get the module");
    }

    public String convertPyObjStrToJavaString(PyObject pyObject) {
        return safeExecute(() -> javaPython.PyUnicode_AsUTF8(pyObject), "Could not get String from PyObject");
    }

    PyObject compileString(String stringCode, int inputType) {
        return safeExecute(() -> javaPython.Py_CompileString(stringCode, FILE_NAME, inputType), "could not compile");
    }

    PyObject eval(PyObject code, PyObject globalDict, PyObject localDict) {
        return safeExecute(() -> javaPython.PyEval_EvalCode(code, globalDict, localDict), "eval failed");
    }

    public PyObject getExceptionValue() {
        return safeExecute(() -> javaPython.PyErr_GetRaisedException(), "impossible to get exception value");
    }

    public PyObject  importModule(String moduleName) {
        return safeExecute(() -> javaPython.PyImport_ImportModule(moduleName), "impossible to import traceback");
    }

    private PyObject  callObject(PyObject pFunc, PyObject pTuple) {
        return safeExecute(() -> javaPython.PyObject_CallObject(pFunc, pTuple), "impossible to call function");
    }

    PyObject convertJavaStringToPyObjStr(String string) {
        return javaPython.PyUnicode_FromString(string);
    }

    public String toString(PyObject o) {
        PyObject pyObject = fromPyObjectGetStr(o);
        return convertPyObjStrToJavaString(pyObject);
    }

    public String getPythonVersion() {
        return javaPython.Py_GetVersion();
    }

    public String getCurrentPlatform() {
        return javaPython.Py_GetPlatform();
    }

    public PyGILState_STATE unlockGil() {
        return javaPython.PyGILState_Ensure();
    }

    public void releaseGil(PyGILState_STATE state) {
        javaPython.PyGILState_Release(state);
    }

    public PyThreadState EvalSaveThread() {
        return javaPython.PyEval_SaveThread();
    }

    public void EvalRestoreThread(PyThreadState state) {
        javaPython.PyEval_RestoreThread(state);
    }

    public void pyErrClear() {
        javaPython.PyErr_Clear();
    }

    public PyObject fromModuleImportAtt(String module, String att) {
        PyObject ioModule = importModule(module);
        return getAttFromModule(ioModule, att);
    }

    public PyObject getAttFromModule(PyObject module, String attribute) {
        if (javaPython.PyObject_HasAttrString(module, attribute) == 1) {
            return javaPython.PyObject_GetAttrString(module, attribute);
        } else {
            throw new IllegalArgumentException("impossible to import " + attribute + " from module");
        }
    }

    public void redirectStandardOutput() {
        PyObject attribute = fromModuleImportAtt(IO_MODULE, STRING_IO_ATT);
        PyObject tupleArgs = javaPython.PyTuple_New(0);
        PyObject stringIoInstance = javaPython.PyObject_CallObject(attribute, tupleArgs);
        javaPython.PySys_SetObject("stdout", stringIoInstance);
    }

    public PyObject callObjectFromModule(PyObject moduleName, PyObject argument) {
        PyObject pFunc = getFuncRefFromModule(moduleName);
        PyObject pTuple = createTupleOfOneArg(argument);
        return callObject(pFunc, pTuple);
    }

    private PyObject getFuncRefFromModule(PyObject tracebackModule) {
        PyObject pFunc = javaPython.PyObject_GetAttrString(tracebackModule, PyCaller.FORMAT_EXCEPTION);
        if (pFunc != null && javaPython.PyCallable_Check(pFunc) == 1) {
            return pFunc;
        } else {
            throw new IllegalArgumentException("impossible to call format_exception(exc)");
        }
    }

    private PyObject createTupleOfOneArg(PyObject args) {
        PyObject pTuple = javaPython.PyTuple_New(1);
        int state = javaPython.PyTuple_SetItem(pTuple, 0, args);
        if (state == 0) {
            return pTuple;
        } else {
            throw new IllegalArgumentException("impossible to throw tuple");
        }
    }

    public String getRedirectedStandardOutput() {
        PyObject tempStdOut = javaPython.PySys_GetObject("stdout");
        PyObject pValue = javaPython.PyObject_CallMethod(tempStdOut, "getvalue", null);
        PyObject valueStr = fromPyObjectGetStr(pValue);
        return convertPyObjStrToJavaString(valueStr);
    }

    private <T> T safeExecute(Supplier<T> callback, String errorMessage) {
        T result = callback.get();
        if (result != null && javaPython.PyErr_Occurred() == null) {
            return result;
        } else {
            throw new IllegalArgumentException(errorMessage);
        }
    }

}