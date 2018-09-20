package com.weiquding.netty.learning.aio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Async Write
 * @author wubai
 * @date 2018/9/15 14:25
 */
public class WriteCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public WriteCompletionHandler(AsynchronousSocketChannel channel){
        if(this.channel == null){
            this.channel = channel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        // if not end, continue write
        if(attachment.hasRemaining()){
            channel.write(attachment, attachment, this);
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
