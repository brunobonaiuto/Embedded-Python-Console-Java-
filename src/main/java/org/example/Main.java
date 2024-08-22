package org.example;

import org.example.interprete.EmbeddedPython;

public class Main {
    public static void main(String[] args) {
        EmbeddedPython embeddedPython = new EmbeddedPython();
        embeddedPython.initialize();
    }
}