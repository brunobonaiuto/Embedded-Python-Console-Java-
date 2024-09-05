package org.example.python.objects;

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;

public class PyThreadState extends PointerType {
    public PyThreadState() {
    }

    public PyThreadState(Pointer p) {
        super(p);
    }
}
