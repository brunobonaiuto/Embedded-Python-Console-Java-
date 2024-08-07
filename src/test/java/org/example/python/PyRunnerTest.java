package org.example.python;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PyRunnerTest {


    @Test
    void testRunLineIsEmpty() {
        String input = "";
        PyRunner pyRunner = new PyRunner();
        //
        String result = pyRunner.runLine(input);
        //
        assertEquals("", result);
    }

    @Test
    void testRunLineSingleCharThrows() {
        String inputLineFromConsole = "b";
        PyRunner pyRunner = new PyRunner();
        //
        //executeCodeModule should throw
        assertThrows(IllegalArgumentException.class, () -> pyRunner.runLine(inputLineFromConsole));
    }

    @Test
    void testRunLineSingleCharThrowsCase2() {
        String inputLineFromConsole = " -";
        PyRunner pyRunner = new PyRunner();
        //
        //executeCodeModule should throw
        assertThrows(IllegalArgumentException.class, () -> pyRunner.runLine(inputLineFromConsole));
    }

    @Test
    void testRunLineContainsAnAssigment() {
        String input = "b = 20";
        String expected = "";
        PyRunner pyRunner = new PyRunner();
        //
        String result = pyRunner.runLine(input);
        //
        assertEquals(expected, result);
    }






}