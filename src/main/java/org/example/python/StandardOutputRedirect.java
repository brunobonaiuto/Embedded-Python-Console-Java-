package org.example.python;

import org.example.interprete.io.Output;

import java.util.List;

public class StandardOutputRedirect implements Runnable {

    private final PyCaller pyCaller;
    private final Output output;
    private int oldSize = 0;
    private String stdout = "";

    public StandardOutputRedirect(PyCaller pyCaller2, Output output) {
        pyCaller = pyCaller2;
        this.output = output;
        pyCaller.redirectStandardOutput();
    }


    @Override
    public void run() {
        while(true) {
            PyGILState_STATE state = pyCaller.unlockGil();
            stdout = pyCaller.getRedirectedStandardOutput();
            pyCaller.releaseGil(state);
            if(stdout.length() > oldSize){
                output.toConsole(stdout.substring(oldSize));
                oldSize = stdout.length();
            }
        }
    }
}