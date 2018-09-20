package com.weiquding.netty.learning.aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Acceptor
 * @author wubai
 * @date 2018/9/15 14:00
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        // 再次调用synchronousServerSocketChannel#accept,接收原的客户端请求
        // Reason: 调用AsynchronousServerSocketChannel的accept方法后，如果有客户端连接接入后，系统将回调我们传入的CompletionHandler实例的completed方法， 表示接入成功。
        // 同时一个AsynchronousServerSocketChannel可以接收成千上万的请求，所以需要继续调用它的accept方法，接收其他客户端的连接，形成一个循环。
        // 每当接收一个客户端连接成功后，再异步接收新的客户端连接。
        attachment.getAsynchronousServerSocketChannel().accept(attachment,this);

        ByteBuffer buffer= ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
            exc.printStackTrace();
            attachment.getCountDownLatch().countDown();
    }
}
