package com.weiquding.netty.learning.netty.pack.solution.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @author wubai
 * @date 2018/9/16 9:28
 */
public class RightNettyTimeServerHandler extends ChannelHandlerAdapter {

    private  int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String)msg;
        System.out.println("The time server receive order :" + body + " ; the counter is :" + ++counter);
        String currTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currTime = currTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
