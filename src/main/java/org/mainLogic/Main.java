package org.mainLogic;

import org.mainLogic.service.Server;

import java.time.Instant;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //create application class for init all program
        //Application has to create EntityArray
        //Application has to start new Tread to start GameLoop

//        Application application = new Application();
//        application.run(40);

        System.out.println((Instant.now()).toEpochMilli());
    }
}