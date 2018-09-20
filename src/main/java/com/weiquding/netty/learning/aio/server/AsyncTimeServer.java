package com.weiquding.netty.learning.aio.server;

/**
 * @author wubai
 * @date 2018-09-15
 *
 * AsyncTimeServer--> AsyncTimeServerHandler-->AcceptCompletionHandler-->ReadCompletionHandler-->WriteCompletionHandler
 *
 */
public class AsyncTimeServer {

    public static void main(String[] args){
        int port = 8080;
        if(args != null && args.length >0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        AsyncTimeServerHandler timeServerHandler = new AsyncTimeServerHandler(port);
        new Thread(timeServerHandler, "AIO-AsyncTimeServerHandler-001").start();
    }

}
