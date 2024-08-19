package org.example.python;

import com.sun.jna.Pointer;

public class PyGILState_STATE extends Pointer {
    public PyGILState_STATE(long peer) {
        super(peer);
    }
}
