package com.weiquding.netty.learning.netty.codecs.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author wubai
 * @date 2018/9/20 22:32
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 首先从数据包byteBuf中获取需要的解码的byte数组，然后调用MessagePack的read方法，将其序列化为Object对象，将解码后的对象加入到解码列表list中。
        final byte[] array;
        final int length = byteBuf.readableBytes();
        array = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, length);
        MessagePack msgpack = new MessagePack();
        list.add(msgpack.read(array));
    }

}
