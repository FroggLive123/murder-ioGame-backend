package org.mainLogic.service;

public class CommandQueue {
    byte[][] commands;
    int first;
    int last;

/* Commands are stored in class CommandQueue in bytes.
    0 ll be used to separate different parts of the command.
    There is no space 0 between first type byte and second data bytes for optimizing space usage.

    commands:
        1 - BotMove
        2 - UserMove
        3 - Kill
        4 - Reborn

    BotMove syntax:
        1 - type of the command ( in BotMove this byte equals to 1 )

    UserMove syntax:
        1 - type of the command ( in UserMove this byte equals to 2 )
        2 - id of the user
        3 - direction ( from 1 to 8 )

    Kill syntax:
        1 - type of the command ( in Kill this byte equals to 3 )
        2 - id of the user
        3 - direction ( from 1 to 8 )

    Reborn syntax:
        1 - type of the command ( in Reborn this byte equals to 4 )


*/

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