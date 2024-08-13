package org.example.python;

import com.sun.jna.Native;
import com.sun.jna.Platform;

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

//    public String getExceptionMessage() {
//        //javaPython.PyErr_Print();
//        PyObject exc = javaPython.PyErr_GetRaisedException();
//        PyObject excStr = getStringRepOfPyObject(exc);
//        return convertPyObjStringToJavaString(excStr);
//    }

    PyObject executeCodeModule(String moduleName, PyObject code) {
        PyObject updated_module = javaPython.PyImport_ExecCodeModule(moduleName, code);
        if (updated_module != null && javaPython.PyErr_Occurred() == null) {
            return updated_module;
        } else {
            throw new IllegalArgumentException("Could not execute code in module called " + moduleName + ": NameError: name is not defined");
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

    PyObject createEmptyDict() {
        return javaPython.PyDict_New();
    }

    String printModule(PyObject moduleName) {
        PyObject moduleDict = getModuleDict(moduleName);
        PyObject moduleDictString = getStringRepOfPyObject(moduleDict);
        return convertPyObjStringToJavaString(moduleDictString);
    }

    public String toString(PyObject o) {
        return convertPyObjStringToJavaString(getStringRepOfPyObject(o));
    }


    public String getFullErrMessage() {
        PyObject exceptionValue = getExceptionValue();
        PyObject tracebackModule = importTraceback();
        PyObject pFunc = getFuncRefFromModule(tracebackModule);
        PyObject pTuple = createTupleOfOneArg(exceptionValue);
        PyObject pResult = callFunction(pFunc, pTuple);
        return convertPyObjStringToJavaString(javaPython.PyObject_Str(pResult));
    }

    private PyObject getExceptionValue() {
        PyObject exceptionValue = javaPython.PyErr_GetRaisedException();
        if (exceptionValue != null) {
            return exceptionValue;
        } else {
            throw new IllegalArgumentException("impossible to get exception value");
        }
    }

    private PyObject importTraceback() {
        PyObject tracebackModule = javaPython.PyImport_ImportModule(TRACEBACK_MODULE);
        if (tracebackModule != null && javaPython.PyErr_Occurred() == null) {
            return tracebackModule;
        } else {
            throw new IllegalArgumentException("impossible to import traceback");
        }
    }

    private PyObject getFuncRefFromModule(PyObject tracebackModule) {
        PyObject pFunc = javaPython.PyObject_GetAttrString(tracebackModule, FORMAT_EXCEPTION);
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

    private PyObject callFunction(PyObject pFunc, PyObject pTuple) {
        PyObject pResult = javaPython.PyObject_CallObject(pFunc, pTuple);
        if (pResult != null) {
            return pResult;
        } else {
            throw new IllegalArgumentException("impossible to call function");
        }
    }
}