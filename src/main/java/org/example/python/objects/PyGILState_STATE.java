package org.example.python.objects;

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;

public class PyGILState_STATE extends PointerType {
    public PyGILState_STATE() {
    }

    public PyGILState_STATE(Pointer p) {
        super(p);
    }
}
