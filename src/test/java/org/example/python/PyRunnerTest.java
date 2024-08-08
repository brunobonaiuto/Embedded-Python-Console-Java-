package org.example.python;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PyRunnerTest {
    @Test
    void testRunLineIsEmpty() {
        String input = "";
        PyRunner pyRunner = new PyRunner();
        //
        String result = pyRunner.runLine(input);
        pyRunner.quit();
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
        pyRunner.quit();
        //
        assertEquals(expected, result);
    }

    @Test
    void testRunLineAccessingVariable() {
        String inputOne = "b = 20";
        String expectedOne = "";
        String inputTwo = "b";
        String expectedTwo = "20";
        PyRunner pyRunner = new PyRunner();
        //
        String result1 = pyRunner.runLine(inputOne);
        String result2 = pyRunner.runLine(inputTwo);
        pyRunner.quit();
        //
        assertEquals(expectedOne, result1);
        assertEquals(expectedTwo, result2);
    }

    @ParameterizedTest
    @MethodSource("multipleLines")
    void testRunLineAccessingVariableRepeatedTimes(String[] input, String[] expected) {
        PyRunner pyRunner = new PyRunner();
        //
        String actual1 = pyRunner.runLine(input[0]);
        String actual2 = pyRunner.runLine(input[1]);
        String actual3 = pyRunner.runLine(input[2]);
        String actual4 = pyRunner.runLine(input[3]);
        pyRunner.quit();
        //
        assertEquals(expected[0], actual1);
        assertEquals(expected[1], actual2);
        assertEquals(expected[1], actual3);
        assertEquals(expected[2], actual4);
    }

    public static Stream<Arguments> multipleLines() {
        return Stream.of(
                Arguments.of(new String[]{"b = 20", "b", "b", "40" }, new String[]{"", "20", "40"})
        );
    }
}