package org.mainLogic;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {

        //create application class for init all program
        //Application has to create EntityArray
        //Application has to start new Tread to start GameLoop

        Application application = new Application(120000L, (short) 30, (short) 12_000, (short) 6_000, (byte) 20);
        application.run((short) 40);
    }
}