package com.weiquding.netty.learning.netty.codecs.messagepack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wubai
 * @date 2018/9/20 23:11
 */
public class EchoMessagePackServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server receive the msgpack message: " + msg);
        ctx.writeAndFlush(msg);
    }
}
