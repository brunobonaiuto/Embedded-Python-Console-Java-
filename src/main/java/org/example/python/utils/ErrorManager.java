package org.example.python.utils;

import org.example.python.objects.PyObject;

import java.util.Optional;

public class ErrorManager {

    private static final String TRACEBACK_MODULE = "traceback";

    private final PyCaller pyCaller;

    public ErrorManager(PyCaller pycaller) {
        this.pyCaller = pycaller;
    }

    public String getStringError() {
        String message = getFullErrMessage();
        return message.replaceAll("[\\[\\]'\"]", "")
                .replace(", ", "")
                .replaceAll("\\\\n", "\n")
                .trim();
    }

    public String getFullErrMessage() {
        PyObject exceptionValue = pyCaller.getExceptionValue();
        PyObject tracebackModule = pyCaller.importModule(TRACEBACK_MODULE);
        PyObject pResult = pyCaller.callObjectFromModule(tracebackModule, exceptionValue);
        pyCaller.pyErrClear();
        return String.valueOf(pyCaller.convertPyObjStrToJavaString(pyCaller.fromPyObjectGetStr(pResult)));
    }
}
