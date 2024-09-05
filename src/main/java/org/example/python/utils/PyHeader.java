package org.example.python.utils;

public class PyHeader {

    private final PyCaller pyCaller;

    public PyHeader(PyCaller pyCaller) {
        this.pyCaller = pyCaller;
    }

    public String getWelcomeMessage() {
        String version = pyCaller.getPythonVersion();
        String platform = pyCaller.getCurrentPlatform();
        String cprt = "Type \"help\", \"copyright\", \"credits\" or \"license\" for more information.";
        return "Python " + version + " on " + platform + "\n" + cprt + "\n";
    }
}
