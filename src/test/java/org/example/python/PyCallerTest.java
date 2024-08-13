package org.example.python;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PyCallerTest {
    private PyCaller pyCaller;
    public static final String MODULE_NAME = "__main__";
    public static final String A_STRING= "sample string";

    private final int PY_EVAL_INPUT = 258;
    private final int PY_FILE_INPUT = 257;
    private final int PY_SINGLE_INPUT = 256;

    @AfterEach
    void tearDown() {
        pyCaller.destroy();
    }

    @Test
    void testInitModule() {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        //
        assertDoesNotThrow(()-> pyCaller.initModule(MODULE_NAME));
        assertThrows(IllegalArgumentException.class, () -> pyCaller.initModule(""));
    }

    @Test
    void testGetModuleDict() {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        PyObject moduleMain = pyCaller.initModule(MODULE_NAME);
        //
        assertDoesNotThrow(()-> pyCaller.getModuleDict(moduleMain));
    }

    @Test
    void testGetStringFromObject() {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        PyObject moduleMain = pyCaller.initModule(MODULE_NAME);
        PyObject moduleDict = pyCaller.getModuleDict(moduleMain);
        //
        assertDoesNotThrow(()-> pyCaller.getStringRepOfPyObject(moduleMain));
        assertDoesNotThrow(()-> pyCaller.getStringRepOfPyObject(moduleDict));

    }

    @Test
    void testFromPyObjectToString() {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        PyObject moduleMain = pyCaller.initModule(MODULE_NAME);
        PyObject moduleDict = pyCaller.getModuleDict(moduleMain);
        PyObject str = pyCaller.getStringRepOfPyObject(moduleDict);
        //
        String stringValue = pyCaller.convertPyObjStringToJavaString(str);
        //
        assertEquals("{'__name__': '__main__', '__doc__': None, '__package__': None, '__loader__': <class '_frozen_importlib.BuiltinImporter'>, '__spec__': None, '__annotations__': {}, '__builtins__': <module 'builtins' (built-in)>}", stringValue);
    }

    @Test
    void testStringToPyObject() {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        //
        PyObject pyObject = pyCaller.convertJavaStringToPyObjString(A_STRING);
        String actual = pyCaller.convertPyObjStringToJavaString(pyObject);
        //
        assertEquals(A_STRING, actual);
    }

    @Test
    void testCompileString() {
        String rightCode = "a = 20";
        String wrongCode = " -";
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        //
        assertDoesNotThrow(()-> pyCaller.compileString(rightCode, PY_FILE_INPUT));
        assertThrows(IllegalArgumentException.class, ()->pyCaller.compileString(wrongCode, PY_FILE_INPUT));
    }

//    @Test
//    void testExecuteCode() {
//        String code = "a = 20";
//        pyCaller = new PyCaller();
//        pyCaller.initializePython();
//        pyCaller.initModule(MODULE_NAME);
//        PyObject compiledCode = pyCaller.compileString(code, PY_FILE_INPUT);
//        //
//        PyObject updatedModule =  pyCaller.executeCodeModule(MODULE_NAME, compiledCode);
//        //
//        assertDoesNotThrow(()->  pyCaller.executeCodeModule(MODULE_NAME, compiledCode));
//        assertEquals("{'__name__': '__main__', '__doc__': None, '__package__': None, '__loader__': <class '_frozen_importlib.BuiltinImporter'>, '__spec__': ModuleSpec(name='__main__', loader=<class '_frozen_importlib.BuiltinImporter'>, origin='C:\\\\Users\\\\bbbolivar\\\\Documents\\\\MEGA\\\\MEGAsync\\\\IdeaProjects\\\\PythonConsole2\\\\<stdin>'), '__annotations__': {}, '__builtins__': <module 'builtins' (built-in)>, '__file__': '<stdin>', '__cached__': None, 'a': 20}",
//                pyCaller.convertPyObjStringToJavaString(pyCaller.getStringRepOfPyObject(pyCaller.getModuleDict(updatedModule))));
//    }

//    @Test
//    void testEvalCode() {
//        String code = "b = 20";
//        pyCaller = new PyCaller();
//        pyCaller.initializePython();
//        pyCaller.initModule(MODULE_NAME);
//        PyObject compiledCode = pyCaller.compileString(code, PY_FILE_INPUT);
//        pyCaller.executeCodeModule(MODULE_NAME, compiledCode);
//        String lineTwo = "b";
//        PyObject compiledCode2 = pyCaller.compileString(lineTwo, PY_EVAL_INPUT);
//        PyObject main = pyCaller.executeCodeModule(MODULE_NAME, compiledCode2);
//        //
//        PyObject result = pyCaller.eval(compiledCode2,pyCaller.getModuleDict(main) ,pyCaller.getModuleDict(main));
//        //
//        assertEquals("20", pyCaller.toString(result));
//    }
}