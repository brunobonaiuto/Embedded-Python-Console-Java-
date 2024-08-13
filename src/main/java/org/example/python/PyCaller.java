package org.example.python;

import com.sun.jna.Native;
import com.sun.jna.Platform;

import java.io.Reader;

public class PyCaller {
    public static final String FILE_NAME = "<stdin>";
    public static final String TRACEBACK_MODULE = "traceback";
    public static final String FORMAT_EXCEPTION = "format_exception";
    
    JavaPython javaPython;
    void initializePython() {
        javaPython = Native.load(Platform.isWindows() ? "C:/Users/bbbolivar/AppData/Local/Programs/Python/Python312/python312.dll" : "libpython3.10.so", JavaPython.class);
        javaPython.Py_Initialize();
    }
    void destroy() {
        if (javaPython.Py_FinalizeEx() < 0) {
            throw new IllegalStateException("Impossible to destroy interpreter");
        }
    }
    PyObject initModule(String moduleName) {
        if (moduleName.isEmpty()) {
            throw new IllegalArgumentException("No module name");
        }
        PyObject updatedModule = javaPython.PyImport_AddModule(moduleName);
        if (javaPython.PyErr_Occurred() == null) {
            return updatedModule;
        } else {
            throw new IllegalArgumentException("Could not create Module");
        }
    }
    PyObject getModuleDict(PyObject pythonModule) {
        PyObject moduleDict = javaPython.PyModule_GetDict(pythonModule);
        if (moduleDict != null && javaPython.PyErr_Occurred() == null) {
            return moduleDict;
        } else {
            throw new IllegalArgumentException("Could not get the module");
        }
    }
    PyObject getStringRepOfPyObject(PyObject object) {
        PyObject str = javaPython.PyObject_Str(object);
        if (str != null) {
            return str;
        } else {
            throw new IllegalArgumentException("Could not get the module");
        }
    }
    String convertPyObjStringToJavaString(PyObject pyObject) {
        String stringValue = javaPython.PyUnicode_AsUTF8(pyObject);
        if (stringValue != null && javaPython.PyErr_Occurred() == null) {
            return stringValue;
        } else {
            throw new IllegalArgumentException("Could not get String from PyObject");
        }
    }
    PyObject convertJavaStringToPyObjString(String string) {
        return javaPython.PyUnicode_FromString(string);
    }
    PyObject compileString(String stringCode, int inputType) {
        PyObject code = javaPython.Py_CompileString(stringCode, FILE_NAME, inputType);
        if (code != null && javaPython.PyErr_Occurred() == null) {
            return code;
        } else {
            throw new IllegalArgumentException("could not compile");
        }
    }
    PyObject eval(PyObject code, PyObject globalDict, PyObject localDict) {
        PyObject result = javaPython.PyEval_EvalCode(code, globalDict, localDict);
        if (javaPython.PyErr_Occurred() == null) {
            return result;
        } else {
            throw new IllegalArgumentException("eval failed");
        }
    }
    public String getFullErrMessage() {
        PyObject exceptionValue = getExceptionValue();
        PyObject tracebackModule = importModule(TRACEBACK_MODULE);
        PyObject pResult = callObjectFromModule(tracebackModule, FORMAT_EXCEPTION, exceptionValue);
        PyErr_Clear();
        return convertPyObjStringToJavaString(javaPython.PyObject_Str(pResult));
    }
    private void PyErr_Clear() {
        javaPython.PyErr_Clear();
    }
    private PyObject getExceptionValue() {
        PyObject exceptionValue = javaPython.PyErr_GetRaisedException();
        if (exceptionValue != null) {
            return exceptionValue;
        } else {
            throw new IllegalArgumentException("impossible to get exception value");
        }
    }
    private PyObject importModule(String moduleName) {
        PyObject tracebackModule = javaPython.PyImport_ImportModule(moduleName);
        if (tracebackModule != null && javaPython.PyErr_Occurred() == null) {
            return tracebackModule;
        } else {
            throw new IllegalArgumentException("impossible to import traceback");
        }
    }
    private PyObject callObjectFromModule(PyObject moduleName, String objectToCall, PyObject argument) {
        PyObject pFunc = getFuncRefFromModule(moduleName, objectToCall);
        PyObject pTuple = createTupleOfOneArg(argument);
        PyObject pResult = callObject(pFunc, pTuple);
        return pResult;
    }
    private PyObject getFuncRefFromModule(PyObject tracebackModule, String objectToCall) {
        PyObject pFunc = javaPython.PyObject_GetAttrString(tracebackModule, objectToCall);
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
    private PyObject callObject(PyObject pFunc, PyObject pTuple) {
        PyObject pResult = javaPython.PyObject_CallObject(pFunc, pTuple);
        if (pResult != null) {
            return pResult;
        } else {
            throw new IllegalArgumentException("impossible to call function");
        }
    }
    public String toString(PyObject o) {
        return convertPyObjStringToJavaString(getStringRepOfPyObject(o));
    }

    public String getWelcomeMessage(){
        String version = javaPython.Py_GetVersion();
        String platform = javaPython.Py_GetPlatform();
        String cprt = "Type \"help\", \"copyright\", \"credits\" or \"license\" for more information.";
        return "Python " + version + " on "+ platform+"\n"+ cprt+ "\n";

    }

}