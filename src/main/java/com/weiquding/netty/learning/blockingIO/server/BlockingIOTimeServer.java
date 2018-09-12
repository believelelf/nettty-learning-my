package com.weiquding.netty.learning.blockingIO.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingIOTimeServer {

    public static void main(String[] args ){
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                // use default value
            }
        }

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port:" + port);
            Socket socket = null;
            while (true){
                socket = server.accept();
                // create a new thread for every client socket
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(server != null){
                System.out.println("The time server close");
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                server = null;
            }
        }
    }

    /**
     * Thread Dump
     * ----------------------------------
     * "main@1" prio=5 tid=0x1 nid=NA runnable
     *   java.lang.Thread.State: RUNNABLE
     * 	  at java.net.DualStackPlainSocketImpl.accept0(DualStackPlainSocketImpl.java:-1)
     * 	  at java.net.DualStackPlainSocketImpl.socketAccept(DualStackPlainSocketImpl.java:131)
     * 	  at java.net.AbstractPlainSocketImpl.accept(AbstractPlainSocketImpl.java:459)
     * 	  at java.net.PlainSocketImpl.accept(PlainSocketImpl.java:183)
     * 	  - locked <0x332> (a java.net.SocksSocketImpl) ------------------------------------------> blocking
     * 	  at java.net.ServerSocket.implAccept(ServerSocket.java:551)
     * 	  at java.net.ServerSocket.accept(ServerSocket.java:519)
     * 	  at com.weiquding.netty.learning.blockingIO.server.BlockingIOTimeServer.main(BlockingIOTimeServer.java:29)
     */
}