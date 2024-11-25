package org.mainLogic.Service;

import jakarta.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static MessageDigest SHA1;
    private static final List<Socket> clients = new ArrayList<>();

    // change to hashMap, key for hashMap is user hash

    public static void server() {

    }

    public void start() {

        try {
            SHA1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        int portNumber = 8000;

        ServerSocket server;
        try {
            server = new ServerSocket(portNumber);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not create web server", exception);
        }

        listenServerSocket(server);

        try {
            System.out.println("print input stream");
            printInputStream();
        } catch (IOException printException) {
            throw new IllegalStateException("Could not connect to client input stream", printException);
        }

    }


    private static void listenServerSocket(final ServerSocket server) {
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket;
                    try {
                        socket = server.accept(); //waits until a client connects
                    } catch (IOException waitException) {
                        throw new IllegalStateException("Could not wait for client connection", waitException);
                    }

                    InputStream inputStream;
                    try {
                        inputStream = socket.getInputStream();
                    } catch (IOException inputStreamException) {
                        throw new IllegalStateException(
                                "Could not connect to client input stream",
                                inputStreamException
                        );
                    }

                    OutputStream outputStream;
                    try {
                        outputStream = socket.getOutputStream();
                    } catch (IOException inputStreamException) {
                        throw new IllegalStateException(
                                "Could not connect to client input stream",
                                inputStreamException
                        );
                    }

                    try {
                        doHandShakeToInitializeWebSocketConnection(inputStream, outputStream);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        outputStream.write(encode("Hello from Server!"));
                        outputStream.flush();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //send hash for clients

                    System.out.println("Add socket to pool");
                    clients.add(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //Source for encoding and decoding:
    //https://stackoverflow.com/questions/8125507/how-can-i-send-and-receive-websocket-messages-on-the-server-side


    private static void printInputStream() throws IOException {
        int len = 0;
        byte[] b = new byte[1024]; //1kB
        //rawIn is a Socket.getInputStream();
        while (true) {
            if (System.currentTimeMillis() % 5000 == 0) {
                System.out.println("CLIENTS: " + clients.size());
            }

            for (int ci = 0; ci < clients.size(); ci++) {
                System.out.println("READ CLIENT: " + ci);
                var client = clients.get(ci);
                if (client.isClosed()) {
                    clients.remove(client);
                }
                var input = client.getInputStream();
                System.out.printf("CLIENT %d READ\n", ci);
                synchronized (input) {
                    len = input.read(b);
                }
                if (len == -1) {
                    continue;
                }

                byte rLength = 0;
                int rMaskIndex = 2;
                int rDataStart = 0;
                //b[0] is always text in my case so no need to check;
                byte data = b[1];
                byte op = (byte) 127;
                rLength = (byte) (data & op);

                if (rLength == (byte) 126)
                    rMaskIndex = 4;
                if (rLength == (byte) 127)
                    rMaskIndex = 10;

                byte[] masks = new byte[4];

                int j = 0;
                int i = 0;
                for (i = rMaskIndex; i < (rMaskIndex + 4); i++) {
                    masks[j] = b[i];
                    j++;
                }

                rDataStart = rMaskIndex + 4;

                int messLen = len - rDataStart;

                byte[] message = new byte[messLen];

                for (i = rDataStart, j = 0; i < len; i++, j++) {
                    message[j] = (byte) (b[i] ^ masks[j % 4]);
                }

                System.out.println(new String(message));

                b = new byte[1024];

                var out = client.getOutputStream();
                var encoded = encode("Pong");
                System.out.printf("OUT %s\n", new String(encodeHex(encoded)));
                out.write(encoded);
                out.flush();
            }
        }
    }

    //81 04 50 6F 6E 67

    public static byte[] encodeHex(final byte[] payload) {
        byte[] hexChars = new byte[payload.length * 2];
        for (int j = 0; j < payload.length; j++) {
            int v = payload[j] & 0xFF;
            hexChars[j * 2] = (byte) HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = (byte) HEX_ARRAY[v & 0x0F];
        }
        return hexChars;
    }

    public static byte[] encode(String mess) throws IOException {
        byte[] rawData = mess.getBytes();

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

    private static void doHandShakeToInitializeWebSocketConnection(InputStream inputStream, OutputStream outputStream)
            throws UnsupportedEncodingException {
        String data;
        synchronized (inputStream) {
            data = new Scanner(inputStream, "UTF-8").useDelimiter("\\r\\n\\r\\n").next();
        }

        System.out.println(">>>>RECEIVED message: " + data);
        System.out.println();

        Matcher get = Pattern.compile("^GET").matcher(data);

        if (get.find()) {
            Matcher match = Pattern.compile("(Sec-WebSocket-Key|Sec-Websocket-Key): (.*)").matcher(data);
            if (!match.find()) {
                System.err.println("(Sec-WebSocket-Key|Sec-Websocket-Key): (.*) <<< pattern do not match");
            }

            String response;
            var accept = match.group(2) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
            System.out.println("ACCEPT: " + accept);
            System.out.println();

            var acceptDigest = SHA1.digest(accept.getBytes(StandardCharsets.UTF_8));
            var acceptFinal = DatatypeConverter.printBase64Binary(acceptDigest);
            response = (
                    "HTTP/1.1 101 Switching Protocols\r\n"
                            + "Connection: Upgrade\r\n"
                            + "Upgrade: websocket\r\n"
                            + "Sec-WebSocket-Accept: " + acceptFinal
                            + "\r\n\r\n"
            );

            System.out.println("<<<<RESPONSE message: " + response);
            System.out.println();

            try {
                var bts = response.getBytes(StandardCharsets.UTF_8);
                outputStream.write(bts, 0, bts.length);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {

        }
    }


}


