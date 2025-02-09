package org.mainLogic.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;

public class Publisher {
    SocketManager socketManager;
    public Publisher(SocketManager socketManager, ObjectMapper objectMapper) {
        this.socketManager = socketManager;
    }

    public void send(String hash, byte[] message) throws IOException {

        Optional<Socket> socket = socketManager.get(hash);

        if(socket.isEmpty()){
            throw new RuntimeException("Socket not found");
        }

        if(socket.get().isClosed()){
            throw new RuntimeException("Socket is closed");
        }

        final OutputStream outputStream = socket.get().getOutputStream();

        outputStream.write(message);
        outputStream.flush();
    }

    public void broadcastAll(byte[] message) throws IOException {

    }
}

