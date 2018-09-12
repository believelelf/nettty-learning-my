package com.weiquding.netty.learning.nonBlockingIO.client;

public class NIOTimeCleint {

    public static void main(String[] args){
        int port = 8080;
        if(args != null && args.length >0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
    }
}
