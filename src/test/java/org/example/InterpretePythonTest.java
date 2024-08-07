package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpretePythonTest {

    @Test
    void testStringCompiles() {
        InterpretePython interpretePython = new InterpretePython();
        //
        interpretePython.compileCode();
        //
        assertDoesNotThrow(() -> interpretePython.compileCode());
    }


}