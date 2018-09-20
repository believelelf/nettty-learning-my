package com.weiquding.netty.learning.netty.codecs.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author wubai
 * @date 2018/9/20 22:32
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        // 负责将Object类型的POJO对象编码为byte数组，然后写入到ByteBuf中
        MessagePack msgpack = new MessagePack();
        byte[] raw = msgpack.write(o);
        byteBuf.writeBytes(raw);
    }
}
