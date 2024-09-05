package org.example.python.utils;

public class ErrorManager {

    private final PyCaller pyCaller;

    public ErrorManager(PyCaller pycaller) {
        this.pyCaller = pycaller;
    }

    public String getStringError() {
        String message = pyCaller.getFullErrMessage();
        return message.replaceAll("[\\[\\]'\"]", "")
                .replace(", ", "")
                .replaceAll("\\\\n", "\n")
                .trim();
    }
}
