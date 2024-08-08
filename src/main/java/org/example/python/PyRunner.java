package org.example.python;

public class PyRunner {

    private PyCaller pyCaller;

    private PyObject main;

    public PyRunner() {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        main = pyCaller.initModule(MODULE_NAME);
    }

    public static final String MODULE_NAME = "__main__";
    private final int PY_EVAL_INPUT = 258;
    private final int PY_FILE_INPUT = 257;
    private final int PY_SINGLE_INPUT = 256;

    public static final String ASSIGMENT_SYMBOL = "=";

    public String runLine(String inputLineFromConsole) {
        int numberOfEquals = inputLineFromConsole.length() - inputLineFromConsole.replace("=", "").length();
        if(numberOfEquals == 1 || inputLineFromConsole.isBlank() || inputLineFromConsole.contains(":")){
            execute(inputLineFromConsole, PY_FILE_INPUT);
            return "";
        }else{
           String result = execute(inputLineFromConsole, PY_EVAL_INPUT);
           return result.equals("None") ? "" : result;
        }
    }

    private String execute(String inputLineFromConsole, int input_type) {
        PyObject code = pyCaller.compileString(inputLineFromConsole, input_type);
        PyObject evalResult = pyCaller.eval(code, pyCaller.getModuleDict(main), pyCaller.getModuleDict(main));
        return pyCaller.toString(evalResult);
    }

    public void quit() {
        pyCaller.destroy();
    }
}
