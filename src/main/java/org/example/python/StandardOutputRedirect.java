package org.example.python;

import org.example.interprete.io.Output;

import java.util.List;

public class StandardOutputRedirect implements Runnable {

    private final PyCaller pyCaller;
    private final Output output;
    private int oldSize = 0;

    public StandardOutputRedirect(PyCaller pyCaller2, Output output) {
        pyCaller = pyCaller2;
        this.output = output;
        pyCaller.redirectStandardOutput();
    }

    @Override
    public void run() {
        while(true) {
            PyGILState_STATE state = pyCaller.unlockGil();
            List stdout = pyCaller.getRedirectedStandardOutput();
            pyCaller.releaseGil(state);
            if(stdout.size() > oldSize){
                output.toConsole(stdout.getLast().toString()+"\n");
                oldSize = stdout.size();
            }
        }
    }
}