package com.weiquding.netty.learning.netty.pack.error.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wubai
 * @date 2018/9/16 9:44
 */
public class ErrorNettyTimeClientHandler extends ChannelHandlerAdapter {

    private int counter;
    private byte[] req;

    public ErrorNettyTimeClientHandler(){
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // send 100 lines
        ByteBuf buf = null;
        for(int i = 0; i < 100; i++){
            buf = Unpooled.buffer(req.length);
            buf.writeBytes(req);
            ctx.writeAndFlush(buf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       ByteBuf buf = (ByteBuf) msg;
       byte[] resp = new byte[buf.readableBytes()];
       buf.readBytes(resp);
       String body = new String(resp, "UTF-8");
       System.out.println("Now is : " + body + " ; the counter is :" + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
