package org.example.interprete.io;

import org.example.Output;

public class OutputConsole implements Output {
    @Override
    public void toConsole(String outputMessage) {
        System.out.print(outputMessage);
    }
}
