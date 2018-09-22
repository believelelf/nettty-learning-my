package com.weiquding.netty.learning.netty.httpjson.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * @author wubai
 * @date 2018/9/22 19:26
 */
public abstract class AbstractHttpJsonDecoder<T> extends MessageToMessageDecoder<T> {

    private Class<?> clazz;
    private boolean isPrint;

    protected AbstractHttpJsonDecoder(Class<?> clazz, boolean isPrint){
        this.clazz = clazz;
        this.isPrint = isPrint;
    }

    protected Object decode0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        if(isPrint){
            System.out.println("The body is: " + new String(bytes));
        }
        Object value = mapper.readValue(bytes, clazz);
        return  value;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
