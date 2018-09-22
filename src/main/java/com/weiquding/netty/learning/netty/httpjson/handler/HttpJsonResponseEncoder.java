package com.weiquding.netty.learning.netty.httpjson.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * @author wubai
 * @date 2018/9/22 20:02
 */
public class HttpJsonResponseEncoder extends AbstractHttpJsonEncoder<HttpJsonResponse> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HttpJsonResponse msg, List<Object> list) throws Exception {
        ByteBuf byteBuf = encode0(channelHandlerContext, msg.getResult());
        FullHttpResponse response = msg.getHttpResponse();
        if(response == null){
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        }else {
            response = new DefaultFullHttpResponse(msg.getHttpResponse().getProtocolVersion(), msg.getHttpResponse().getStatus(), byteBuf);
        }
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, byteBuf.readableBytes());
        list.add(response);
    }
}
