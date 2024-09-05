package org.example.python.utils;

import org.example.python.objects.PyThreadState;

public class PyThreadSaver {
    private final PyCaller pyCaller;

    public PyThreadSaver(PyCaller pyCaller) {
        this.pyCaller = pyCaller;
    }

    public PyThreadState saveThread(){
        return pyCaller.EvalSaveThread();
    }

    public void restoreThread(PyThreadState state){
        pyCaller.EvalRestoreThread(state);
    }
}
