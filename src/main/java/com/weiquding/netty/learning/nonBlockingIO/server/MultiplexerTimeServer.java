package com.weiquding.netty.learning.nonBlockingIO.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class MultiplexerTimeServer implements Runnable{

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    /**
     * init selector, binding port
     */
    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            // non blocking
            serverSocketChannel.configureBlocking(false);
            // blacklog:requested maximum length of the queue of incoming connections.
            serverSocketChannel.socket().bind(new InetSocketAddress(port) ,1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port:" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    //TODO handle the I/O worker
    @Override
    public void run() {

    }
}
