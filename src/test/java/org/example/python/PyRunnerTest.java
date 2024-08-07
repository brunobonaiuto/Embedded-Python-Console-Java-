package org.example.python;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

    @Test
    void testRunLineSingleChar() {
        String inputLineFromConsole = "b";
        PyRunner pyRunner = new PyRunner();
        //
        assertThrows(IllegalArgumentException.class, () -> pyRunner.runLine(inputLineFromConsole));
    }

}