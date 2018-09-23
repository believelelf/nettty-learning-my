package com.weiquding.netty.learning.netty.protocol.codec;

import com.weiquding.netty.learning.netty.codecs.marshalling.factory.MarshallingCodecFactory;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * @author wubai
 * @date 2018/9/23 21:52
 */
public class MarshallingDecoder {

    private final Unmarshaller unmarshaller;

    public MarshallingDecoder() throws IOException {
        unmarshaller = MarshallingCodecFactory.buildUnmarshaller();
    }

    public Object decode(ByteBuf in) throws IOException, ClassNotFoundException {
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);
        ByteInput input = new ChannelBufferByteInput(buf);
        try {
            unmarshaller.start(input);
            Object object = unmarshaller.readObject();
            unmarshaller.finish();
            in.readerIndex(in.readerIndex() + objectSize);
            return object;
        } finally {
            unmarshaller.close();
        }
    }
}
