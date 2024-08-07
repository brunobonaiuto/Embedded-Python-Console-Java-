package org.example.python;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PyRunnerTest {


    @Test
    void testRunLineIsEmpty() {
        String expected = "";
        PyRunner pyRunner = new PyRunner();
        //
        String result = pyRunner.runLine("");
        //
        assertEquals(expected, result);
    }
}