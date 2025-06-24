package org.mainLogic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mainLogic.entity.ClientSession;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Optional;

public class Publisher {
    SocketManager socketManager;
    public Publisher(SocketManager socketManager, ObjectMapper objectMapper) {
        this.socketManager = socketManager;
    }

    public void send(short id, byte[] message) throws IOException {

        Optional<ClientSession> clientSession = socketManager.get(id);
        Optional<Socket> socket1 = Optional.ofNullable(clientSession.get().getSocket());

        if (socket1.isEmpty()) {
            throw new RuntimeException("Socket not found");
        }

        if (socket1.get().isClosed()) {
            throw new RuntimeException("Socket is closed");
        }
        final OutputStream outputStream = socket1.get().getOutputStream();

        outputStream.write(encode(message));
        outputStream.flush();

    }

    public void broadcast(byte[] message) throws IOException {
        for (Iterator<Short> it = socketManager.getIdIterator(); it.hasNext(); ) {
            short id = it.next();
            send(id, message);
        }
    }

    public byte[] encode(String mess) throws IOException {
        return encode(mess.getBytes());
    }

    public byte[] encode(byte[] rawData) throws IOException {
        int frameCount = 0;
        byte[] frame = new byte[10];

        frame[0] = (byte) 129;

        if (rawData.length <= 125) {
            frame[1] = (byte) rawData.length;
            frameCount = 2;
        } else if (rawData.length >= 126 && rawData.length <= 65535) {
            frame[1] = (byte) 126;
            int len = rawData.length;
            frame[2] = (byte) ((len >> 8) & (byte) 255);
            frame[3] = (byte) (len & (byte) 255);
            frameCount = 4;
        } else {
            frame[1] = (byte) 127;
            int len = rawData.length;
            frame[2] = (byte) ((len >> 56) & (byte) 255);
            frame[3] = (byte) ((len >> 48) & (byte) 255);
            frame[4] = (byte) ((len >> 40) & (byte) 255);
            frame[5] = (byte) ((len >> 32) & (byte) 255);
            frame[6] = (byte) ((len >> 24) & (byte) 255);
            frame[7] = (byte) ((len >> 16) & (byte) 255);
            frame[8] = (byte) ((len >> 8) & (byte) 255);
            frame[9] = (byte) (len & (byte) 255);
            frameCount = 10;
        }

        int bLength = frameCount + rawData.length;

        byte[] reply = new byte[bLength];

        int bLim = 0;
        for (int i = 0; i < frameCount; i++) {
            reply[bLim] = frame[i];
            bLim++;
        }
        for (int i = 0; i < rawData.length; i++) {
            reply[bLim] = rawData[i];
            bLim++;
        }

        return reply;
    }
}

