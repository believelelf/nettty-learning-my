package com.weiquding.netty.learning.netty.httpjson.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

import java.util.List;

/**
 * @author wubai
 * @date 2018/9/22 20:11
 */
public class HttpJsonResponseDecoder extends AbstractHttpJsonDecoder<DefaultFullHttpResponse> {

    public HttpJsonResponseDecoder(Class<?> clazz) {
        super(clazz, false);
    }

    public HttpJsonResponseDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DefaultFullHttpResponse msg, List<Object> list) throws Exception {
        HttpJsonResponse response = new HttpJsonResponse(msg, decode0(channelHandlerContext, msg.content()));
        list.add(response);

    }
}
