package com.weiquding.netty.learning.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingIOWithThreadPoolTimeServer {

    public static void main(String[] args){
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("The time sever is start in port:" + port);
            Socket socket = null;
            // create a thread pool to handle client socket
            // the thread pool and work queue both are bounded, avoid OutOfMemoryError
            // else but this is also a blocking IO
            TimeServerHandlerExecutePool timeServerHandlerExecutePool = new TimeServerHandlerExecutePool(50, 1000);
            while (true) {
                socket = serverSocket.accept();
                timeServerHandlerExecutePool.execute(new TimeServerHandler(socket));
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        /***
         *  Reasons:
         * 1.InputStream.read()--->blocking: both at server side and client side,current thread must wait for util three situation below when reading
         *   ==>This method blocks until input data is available, the end of the stream is detected, or an exception is thrown.
         * 2.OutputStream.write()--->blocking: if client receive data slowly, the tcp buffer will be full,the window size will be reduce until zero.
         *  buffer full-->window size 0 --> both on keep-Alive-->blocking
         *    always blocking until An IOException occurs.
         *   ==>@see {@link java.io.ObjectOutputStream} The java.io.ObjectOutputStream.write(byte[] buf) method writes an array of bytes. This method will block until the bytes are actually written.
         */

    }
}
