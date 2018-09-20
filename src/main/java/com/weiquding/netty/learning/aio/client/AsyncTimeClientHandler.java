package com.weiquding.netty.learning.aio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author wubai
 * @date 2018/9/15 16:15
 */
public class AsyncTimeClientHandler implements Runnable {

    private String host;
    private int port;
    private CountDownLatch countDownLatch;
    private AsynchronousSocketChannel channel;

    public AsyncTimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            this.channel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        // connect event
        channel.connect(new InetSocketAddress(host, port), this, new CompletionHandler<Void, AsyncTimeClientHandler>() {

            @Override
            public void completed(Void result, AsyncTimeClientHandler attachment) {
                byte[] req = "QUERY TIME ORDER".getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
                writeBuffer.put(req);
                writeBuffer.flip();
                // write event
                channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        //see doc/thread-dump/aio_compeltionHandler_threads_report.txt
                        //  completionHander#completed, use ThreadPoolExecutor to finish callback

                        //"Thread-3@885" daemon prio=5 tid=0x12 nid=NA runnable
                        //java.lang.Thread.State: RUNNABLE
                        //	  at com.weiquding.netty.learning.aio.client.AsyncTimeClientHandler$1$1.completed(AsyncTimeClientHandler.java:48)
                        // 	  at com.weiquding.netty.learning.aio.client.AsyncTimeClientHandler$1$1.completed(AsyncTimeClientHandler.java:45)
                        // 	  at sun.nio.ch.Invoker.invokeUnchecked(Invoker.java:127)
                        // 	  at sun.nio.ch.Invoker.invokeUnchecked(Invoker.java:282)
                        // 	  at sun.nio.ch.WindowsAsynchronousSocketChannelImpl$WriteTask.completed(WindowsAsynchronousSocketChannelImpl.java:829)
                        // 	  at sun.nio.ch.Iocp$EventHandlerTask.run(Iocp.java:387)
                        // 	  at sun.nio.ch.AsynchronousChannelGroupImpl$1.run(AsynchronousChannelGroupImpl.java:112)
                        // 	  at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1135)
                        // 	  at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
                        // 	  at java.lang.Thread.run(Thread.java:844)
                        if(attachment.hasRemaining()){
                            channel.write(attachment, attachment, this);
                        }else {
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            // read event
                            channel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                                @Override
                                public void completed(Integer result, ByteBuffer attachment) {
                                    attachment.flip();
                                    byte[] bytes = new byte[attachment.remaining()];
                                    attachment.get(bytes);
                                    try {
                                        String body = new String(bytes, "UTF-8");
                                        System.out.println("Now is :" + body);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    countDownLatch.countDown();
                                }

                                @Override
                                public void failed(Throwable exc, ByteBuffer attachment) {
                                    try {
                                        channel.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    countDownLatch.countDown();
                                }
                            });
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        countDownLatch.countDown();
                    }
                });
            }

            @Override
            public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        });
        // avoid current thread end
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
