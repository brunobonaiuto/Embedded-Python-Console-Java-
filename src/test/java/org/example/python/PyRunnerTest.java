package org.example.python;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PyRunnerTest {
    private PyRunner pyRunner;
    @AfterEach
    void tearDown() {
        pyRunner.quit();
    }

    @Test
    void testRunLineIsEmpty() {
        pyRunner = new PyRunner();

        String result = pyRunner.runLine("");

        assertEquals("", result);
    }

    @Test
    void testRunLineContainsAnAssigment() {
        pyRunner = new PyRunner();
        //
        String result = pyRunner.runLine("b = 20");
        pyRunner.quit();
        //
        assertEquals("", result);
    }

    @Test
    void testRunLineAccessingVariable() {
        pyRunner = new PyRunner();

        String result1 = pyRunner.runLine("b = 20");
        String result2 = pyRunner.runLine("b");

        assertEquals("", result1);
        assertEquals("20", result2);
    }

    @Test
    void testRunLineAccessingVariableRepeatedTimes() {
        pyRunner = new PyRunner();

        assertEquals("", pyRunner.runLine("b = 20"));
        assertEquals("20", pyRunner.runLine("b"));
        assertEquals("20", pyRunner.runLine("b"));
        assertEquals("40", pyRunner.runLine("40"));
        assertEquals("10", pyRunner.runLine("5+5"));
        assertEquals("True", pyRunner.runLine("b==20"));
        assertEquals("", pyRunner.runLine("b = b+1"));
        assertEquals("21", pyRunner.runLine("b"));
    }

    @Test
    void testRunLineSummingOne() {
        String expectedOne = "";
        String expectedTwo = "";
        String expectedThree = "56";
        pyRunner = new PyRunner();

        assertEquals(expectedOne, pyRunner.runLine("b = 55"));
        assertEquals(expectedTwo, pyRunner.runLine("b = b + 1"));
        assertEquals(expectedThree, pyRunner.runLine("b"));
    }

    @Test
    void testRunLinePrint() {
        pyRunner = new PyRunner();

        pyRunner.runLine("print(25)");
    }

    @Test
    void testRunLineFunctions() {
        pyRunner = new PyRunner();

        String result1 = pyRunner.runLine("abs(-7.25)");
        String result2 = pyRunner.runLine("globals()");

        assertEquals("7.25", result1);
        assertEquals("{'__name__': '__main__', '__doc__': None, '__package__': None, " +
                "'__loader__': <class '_frozen_importlib.BuiltinImporter'>, '__spec__': None, " +
                "'__annotations__': {}, '__builtins__': <module 'builtins' (built-in)>}", result2);
    }

    @Test
    void testRunLineDefFunction() {
        pyRunner = new PyRunner();

        assertEquals("", pyRunner.runLine("def function(name):\n  return \"hello \" + name"));
        assertEquals("hello person", pyRunner.runLine("function(\"person\")"));
    }

    @Test
    void testRunLineImportModule() {
        pyRunner = new PyRunner();

        assertEquals("", pyRunner.runLine("import os"));
    }

    @Test
    void testWhenInvalidSyntax() {
        pyRunner = new PyRunner();

        String result1 = pyRunner.runLine("-");

        assertEquals("invalid syntax (<stdin>, line 1)", result1);
    }

    @Test
    void testWhenNameIsNotDefined() {
        String input = "jj";
        pyRunner = new PyRunner();

        String result1 = pyRunner.runLine(input);

        assertEquals("name '"+input+"' is not defined", result1);
    }
}