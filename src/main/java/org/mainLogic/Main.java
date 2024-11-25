package org.mainLogic;

import org.mainLogic.Service.Server;

public class Main {
    public static void main(String[] args) {

        //create application class for init all program
        //Application has to create EntityArray
        //Application has to start new Tread to start GameLoop

        Server server = new Server();
        server.start();
    }
}