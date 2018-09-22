package com.weiquding.netty.learning.netty.httpjson.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * @author wubai
 * @date 2018/9/22 18:52
 */
public abstract class AbstractHttpJsonEncoder<T> extends MessageToMessageEncoder<T> {

    protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        byte[] bytes =  mapper.writeValueAsBytes(body);
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        return byteBuf;
    }

}
