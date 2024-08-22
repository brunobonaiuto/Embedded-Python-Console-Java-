package org.example.interprete.io;

public class DefaultOutput implements Output {
    @Override
    public void toConsole(String outputMessage) {
        System.out.print(outputMessage);
    }
}
