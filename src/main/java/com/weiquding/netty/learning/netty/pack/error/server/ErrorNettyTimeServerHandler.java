package com.weiquding.netty.learning.netty.pack.error.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @author wubai
 * @date 2018/9/16 9:28
 */
public class ErrorNettyTimeServerHandler extends ChannelHandlerAdapter {

    private  int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8").substring(0, req.length - System.getProperty("line.separator").length());
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
