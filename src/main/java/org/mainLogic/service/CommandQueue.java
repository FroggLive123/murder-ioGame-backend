package org.mainLogic.service;

public class CommandQueue {
    byte[][] commands;
    int first;
    int last;

    public CommandQueue(final int length) {
        commands = new byte[length][];
    }

    public byte[] next() {
        byte[] command = commands[first];
        commands[first] = null;

        first++;

        if (first == commands.length - 1) {
            first = 0;
        }

        return command;
    }

    public void put(final byte[] command) {
        if(commands[last] != null) {
            throw new IllegalStateException("CommandQueue is full");
        }

        commands[last] = command;

        last++;

        if (last == commands.length - 1) {
            last = 0;
        }
    }

    public boolean hasNext() {
        return commands[first] != null;
    }
}