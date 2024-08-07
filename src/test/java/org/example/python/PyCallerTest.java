package org.example.python;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PyCallerTest {
    private PyCaller pyCaller;
    public static final String MODULE_NAME = "__main__";
    public static final String A_STRING= "sample string";

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
        assertDoesNotThrow(()-> pyCaller.getStringFromObject(moduleMain));
        assertDoesNotThrow(()-> pyCaller.getStringFromObject(moduleDict));

    }

    @Test
    void testFromPyObjectToString() {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        PyObject moduleMain = pyCaller.initModule(MODULE_NAME);
        PyObject moduleDict = pyCaller.getModuleDict(moduleMain);
        PyObject str = pyCaller.getStringFromObject(moduleDict);
        //
        String stringValue = pyCaller.fromPyObjectToString(str);
        //
        assertEquals("{'__name__': '__main__', '__doc__': None, '__package__': None, '__loader__': None, '__spec__': None}", stringValue);
    }

    @Test
    void testStringToPyObject() {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        //
        PyObject pyObject = pyCaller.fromStringToPyObject(A_STRING);
        String actual = pyCaller.fromPyObjectToString(pyObject);
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
        assertDoesNotThrow(()-> pyCaller.compileString(rightCode));
        assertThrows(IllegalArgumentException.class, ()->pyCaller.compileString(wrongCode));
    }

    @Test
    void testExecuteCode() {
        String code = "a = 20";
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        pyCaller.initModule(MODULE_NAME);
        PyObject compiledCode = pyCaller.compileString(code);
        //
        PyObject updatedModule =  pyCaller.executeCodeModule(MODULE_NAME, compiledCode);
        //
        assertDoesNotThrow(()->  pyCaller.executeCodeModule(MODULE_NAME, compiledCode));
        assertEquals("{'__name__': '__main__', '__doc__': None, '__package__': None, '__loader__': <class '_frozen_importlib.BuiltinImporter'>, " +
                "'__spec__': ModuleSpec(name='__main__', loader=<class '_frozen_importlib.BuiltinImporter'>, origin='C:\\\\Users\\\\bbbolivar\\\\Documents\\\\MEGA\\\\MEGAsync\\\\IdeaProjects\\\\PythonConsole2'), " +
                "'__annotations__': {}, '__builtins__': <module 'builtins' (built-in)>, '__file__': '', '__cached__': None, 'a': 20}", pyCaller.fromPyObjectToString(pyCaller.getStringFromObject(pyCaller.getModuleDict(updatedModule))));
    }

    @AfterEach
    void tearDown() {
        pyCaller.destroy();
    }
}