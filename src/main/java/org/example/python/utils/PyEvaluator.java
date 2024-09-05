package org.example.python.utils;

import org.example.python.objects.PyObject;

import static org.example.python.PyHighLevelCaller.*;

public class PyEvaluator {
    private final int PY_EVAL_INPUT = 258;
    private final int PY_FILE_INPUT = 257;
    private final int PY_SINGLE_INPUT = 256;

    private final PyCaller pyCaller;
    private final ErrorManager errorManager;
    private final PyObject main;

    public PyEvaluator(PyCaller pyCaller, PyObject mainModule) {
        this.pyCaller = pyCaller;
        main = mainModule;
        errorManager = new ErrorManager(pyCaller);
    }

    public String evaluateLine(String input) {
        String result;
        int numberOfAssigmentSymbol = input.length() - input.replace(ASSIGMENT_SYMBOL, BLANK_SYMBOL).length();
        if (isStatementLine(input, numberOfAssigmentSymbol)) {
            result = execute(input, PY_FILE_INPUT);
        } else {
            result = execute(input, PY_EVAL_INPUT);
        }
        return result.equals(NONE) ? BLANK_SYMBOL : result;
    }

    private boolean isStatementLine(String input, int numberOfAssigmentSymbol) {
        return numberOfAssigmentSymbol == 1 || input.isBlank() || input.contains(COLON_SYMBOL) || input.contains(IMPORT);
    }

    private String execute(String inputLineFromConsole, int input_type) {
        try {
            PyObject code = pyCaller.compileString(inputLineFromConsole, input_type);
            PyObject evalResult = pyCaller.eval(code, pyCaller.getModuleDict(main), pyCaller.getModuleDict(main));
            return pyCaller.toString(evalResult);
        } catch (IllegalArgumentException e) {
            return errorManager.getStringError();
        }
    }
}