package org.example.python;

public class PyRunner {

    private PyCaller pyCaller;

    public PyRunner() {
        pyCaller = new PyCaller();
    }

    public static final String MODULE_NAME = "__main__";
    private final int PY_EVAL_INPUT = 258;
    private final int PY_FILE_INPUT = 257;
    private final int PY_SINGLE_INPUT = 256;

    //public static final String ASSIGMENT_SYMBOL = "=";

    public String runLine(String inputLineFromConsole) {
        pyCaller.initializePython();
        PyObject main = pyCaller.initModule(MODULE_NAME);
        PyObject code = pyCaller.compileString(inputLineFromConsole, PY_FILE_INPUT);
        pyCaller.executeCodeModule(MODULE_NAME,code);
        PyObject evalResult =  pyCaller.eval(code, pyCaller.getModuleDict(main),pyCaller.getModuleDict(main));
        String result = pyCaller.toString(evalResult);
        return result.equals("None") ? "" : result;
    }
}
