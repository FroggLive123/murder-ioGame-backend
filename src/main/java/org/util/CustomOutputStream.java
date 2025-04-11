package org.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class CustomOutputStream extends OutputStream {


    private byte[] data;
    private int position;

    public CustomOutputStream(int capacity) {
        this.data = new byte[capacity];
        this.position = 0;
    }

    public CustomOutputStream(byte[] initialData){
        this.data = initialData;
        this.position = 0;
    }

    public void reset() {
        this.position = 0;
        Arrays.fill(data , (byte) 0);
    }

    public void writeByte(byte value) {
        ensureCapacity(1);
        data[position++] = value;
    }

    public void writeShort(short value) {
        ensureCapacity(2);
        ByteBuffer.wrap(data, position, 2).order(ByteOrder.BIG_ENDIAN).putShort(value);
        position += 2;
    }

    public void writeInt(int value) {
        ensureCapacity(4);
        ByteBuffer.wrap(data, position, 4).order(ByteOrder.BIG_ENDIAN).putInt(value);
        position += 4;
    }

    public void writeLong(long value) {
        ensureCapacity(8);
        ByteBuffer.wrap(data, position, 8).order(ByteOrder.BIG_ENDIAN).putLong(value);
        position += 8;
    }

    public void writeFloat(float value) {
        ensureCapacity(4);
        ByteBuffer.wrap(data, position, 4).order(ByteOrder.BIG_ENDIAN).putFloat(value);
        position += 4;
    }

    public void writeDouble(double value) {
        ensureCapacity(8);
        ByteBuffer.wrap(data, position, 8).order(ByteOrder.BIG_ENDIAN).putDouble(value);
        position += 8;
    }

    public void writeBytes(byte[] bytes) {
        ensureCapacity(bytes.length);
        System.arraycopy(bytes, 0, data, position, bytes.length);
        position += bytes.length;
    }

    public byte[] toByteArray() {
        byte[] result = new byte[position];
        System.arraycopy(data, 0, result, 0, position);
        return result;
    }

    public int getPosition() {
        return position;
    }

    private void ensureCapacity(int requiredCapacity) {
    }

    @Override
    public void write(int b) throws IOException {
        writeByte((byte) b);
    }

    public void write(String b) throws IOException {
        write(b.getBytes());
    }
}
