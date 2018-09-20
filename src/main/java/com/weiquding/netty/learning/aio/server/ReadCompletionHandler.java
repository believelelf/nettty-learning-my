package com.weiquding.netty.learning.aio.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

/**
 * IO Thread
 * @author wubai
 * @date 2018/9/15 14:15
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private  AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel result) {
        if(this.channel == null){
            this.channel = result;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body, "UTF-8");
            System.out.println("The time server receive order:" + req);
            String currTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
            doWrite(currTime);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    private void doWrite(String currTime) {
        if(currTime != null && currTime.length() > 0){
            byte[] bytes = currTime.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer, writeBuffer, new WriteCompletionHandler(channel));

        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
