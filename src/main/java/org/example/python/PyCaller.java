package org.example.python;

import com.sun.jna.Native;
import com.sun.jna.Platform;

public class PyCaller {

    public static final String FILE_NAME = "";

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
        PyObject updatedModule = javaPython.PyImport_AddModule(moduleName);
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

    PyObject getStringRepOfPyObject(PyObject object){
        PyObject str = javaPython.PyObject_Str(object);
        if(str != null ) {
            return str;
        }else {
            throw new IllegalArgumentException("Could not get the module");
        }
    }

    String convertPyObjStringToJavaString(PyObject pyObject){
        String stringValue = javaPython.PyUnicode_AsUTF8(pyObject);
        if(stringValue != null && javaPython.PyErr_Occurred() == null){
            return stringValue;
        }else {
            throw new IllegalArgumentException("Could not get String from PyObject");
        }
    }

    PyObject convertJavaStringToPyObjString(String string){
        return javaPython.PyUnicode_FromString(string);
    }

    PyObject compileString(String stringCode, int inputType){
       PyObject code = javaPython.Py_CompileString(stringCode, FILE_NAME, inputType);
       if(code != null && javaPython.PyErr_Occurred() == null ){
           return code;
       }else {
           javaPython.PyErr_Clear();
           throw new IllegalArgumentException("Could not compile the String");
       }
    }

    PyObject executeCodeModule(String moduleName, PyObject code){
       PyObject updated_module = javaPython.PyImport_ExecCodeModule(moduleName,code);
       if(updated_module!= null && javaPython.PyErr_Occurred() == null){
           return updated_module;
       }else {
           javaPython.PyErr_Clear();
           throw new IllegalArgumentException("Could not execute code in module called "+ moduleName);
       }
    }

    String eval(PyObject code, PyObject globalDict, PyObject localDict){
        PyObject result = javaPython.PyEval_EvalCode(code, globalDict, localDict);
        if(javaPython.PyErr_Occurred() == null){
            result = javaPython.PyObject_Str(result);
            return javaPython.PyUnicode_AsUTF8(result);
        }else{
            javaPython.PyErr_Clear();
            throw new IllegalArgumentException("Something else happened");
        }
    }

    PyObject createEmptyDict(){
        return javaPython.PyDict_New();
    }

    String printModule(PyObject moduleName){
        PyObject moduleDict = getModuleDict(moduleName);
        PyObject moduleDictString = getStringRepOfPyObject(moduleDict);
        return convertPyObjStringToJavaString(moduleDictString);
    }
}
