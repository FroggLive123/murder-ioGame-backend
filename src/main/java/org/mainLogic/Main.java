package org.mainLogic;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {

        //create application class for init all program
        //Application has to create EntityArray
        //Application has to start new Tread to start GameLoop

        Application application = new Application();
        application.run(40);
    }
}