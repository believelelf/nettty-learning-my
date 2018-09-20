package com.weiquding.netty.learning.aio.client;

/**
 * @author wubai
 * @date 2018/9/15 16:12
 */
public class AsyncTimeClient {

    public static void main(String[] args){
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
    }
}
