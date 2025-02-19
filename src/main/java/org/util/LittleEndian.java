package org.util;

public class LittleEndian {

    public static byte[] toByteArray(short value) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (value & 0xFF);
        bytes[1] = (byte) ((value >> 8) & 0xFF);

        return bytes;
    }

    public static byte[] toByteArray(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value & 0xFF);
        bytes[1] = (byte) ((value >> 8) & 0xFF);
        bytes[2] = (byte) ((value >> 16) & 0xFF);
        bytes[3] = (byte) ((value >> 24) & 0xFF);

        return bytes;
    }

    public static short toShort(byte[] bytes) {
        return (short) (bytes[1] << 8  | (bytes[0] & 0xff));
    }

    public static int toInt(byte[] bytes) {
        return  (bytes[3] << 24 | bytes[2] << 16 | bytes[1] << 8  | (bytes[0] & 0xff));
    }
}
