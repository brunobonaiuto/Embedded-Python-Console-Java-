package org.example.python.objects;

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;

public class PyObject extends PointerType {
    public PyObject() {
    }

    public PyObject(Pointer p) {
        super(p);
    }
}
