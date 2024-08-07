package org.example.python;

import com.sun.jna.Native;
import com.sun.jna.Platform;

public class PyCaller {

    public static final String FILE_NAME = "";
    private final int PY_EVAL_INPUT = 258;
    private final int PY_FILE_INPUT = 257;
    private final int PY_SINGLE_INPUT = 256;
    JavaPython javaPython;

    void initializePython() {
        javaPython = Native.load(Platform.isWindows() ? "C:/Users/bbbolivar/AppData/Local/Programs/Python/Python312/python312.dll" : "libpython3.10.so", JavaPython.class);
        javaPython.Py_Initialize();
    }

    void destroy(){
        if (javaPython.Py_FinalizeEx() < 0) {
            throw new IllegalStateException("Impossible to destroy interpreter");
        }
    }

    PyObject initModule(String moduleName){
        if(moduleName.isEmpty()){
            throw new IllegalArgumentException("No module name");
        }
        PyObject updatedModule = javaPython.PyModule_New(moduleName);
        if(javaPython.PyErr_Occurred() == null){
            return updatedModule;
        }else {
            javaPython.PyErr_Clear();
            throw new IllegalArgumentException("Could not create Module");
        }
    }

    PyObject getModuleDict(PyObject pythonModule){
        PyObject moduleDict = javaPython.PyModule_GetDict(pythonModule);
        if(moduleDict != null && javaPython.PyErr_Occurred() == null) {
            return moduleDict;
        }else {
            javaPython.PyErr_Clear();
            throw new IllegalArgumentException("Could not get the module");
        }
    }

    PyObject getStringFromObject(PyObject object){
        PyObject str = javaPython.PyObject_Str(object);
        if(str != null ) {
            return str;
        }else {
            throw new IllegalArgumentException("Could not get the module");
        }
    }

    String fromPyObjectToString(PyObject pyObject){
        String stringValue = javaPython.PyUnicode_AsUTF8(pyObject);
        if(stringValue != null && javaPython.PyErr_Occurred() == null){
            return stringValue;
        }else {
            throw new IllegalArgumentException("Could not get String from PyObject");
        }
    }

    PyObject fromStringToPyObject(String string){
        return javaPython.PyUnicode_FromString(string);
    }

    PyObject compileString(String stringCode){
       PyObject code = javaPython.Py_CompileString(stringCode, FILE_NAME, PY_FILE_INPUT);
       if(code != null && javaPython.PyErr_Occurred() == null ){
           return code;
       }else {
           javaPython.PyErr_Clear();
           throw new IllegalArgumentException("Could not compile the String");
       }
    }
}
