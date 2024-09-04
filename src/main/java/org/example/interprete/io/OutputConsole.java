package org.example.interprete.io;

import org.example.interprete.Output;

public class OutputConsole implements Output {
    @Override
    public void toConsole(String outputMessage) {
        System.out.print(outputMessage);
    }
}
