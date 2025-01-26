package org.example.python.utils;

public class PyHeader {

    private final PyCaller pyCaller;

    public PyHeader(PyCaller pyCaller) {
        this.pyCaller = pyCaller;
    }

    public String getWelcomeMessage() {
        String cprt = "Type \"help\", \"copyright\", \"credits\" or \"license\" for more information.";
        return "Python " + pyCaller.getPythonVersion() + " on " + pyCaller.getCurrentPlatform() + "\n" + cprt + "\n";
    }
}
