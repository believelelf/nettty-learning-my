package com.weiquding.netty.learning.netty.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;

import java.io.IOException;

public class ChannelBufferByteInput implements ByteInput {
    private final ByteBuf buffer;

    public ChannelBufferByteInput(ByteBuf buffer) {
        this.buffer = buffer;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public int available() throws IOException {
        return this.buffer.readableBytes();
    }

    @Override
    public int read() throws IOException {
        return this.buffer.isReadable() ? this.buffer.readByte() & 255 : -1;
    }

    @Override
    public int read(byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }

    @Override
    public int read(byte[] dst, int dstIndex, int length) throws IOException {
        int available = this.available();
        if (available == 0) {
            return -1;
        } else {
            length = Math.min(available, length);
            this.buffer.readBytes(dst, dstIndex, length);
            return length;
        }
    }

    @Override
    public long skip(long bytes) throws IOException {
        int readable = this.buffer.readableBytes();
        if ((long)readable < bytes) {
            bytes = (long)readable;
        }

        this.buffer.readerIndex((int)((long)this.buffer.readerIndex() + bytes));
        return bytes;
    }
}
