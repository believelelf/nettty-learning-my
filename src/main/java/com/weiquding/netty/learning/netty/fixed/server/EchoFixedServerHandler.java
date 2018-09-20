package com.weiquding.netty.learning.netty.fixed.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wubai
 * @date 2018/9/20 20:47
 */
public class EchoFixedServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive client :[" + msg + "]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable casue){
        casue.printStackTrace();
        ctx.close();
    }



}
