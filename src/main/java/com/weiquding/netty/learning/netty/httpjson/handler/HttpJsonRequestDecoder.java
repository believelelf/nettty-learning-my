package com.weiquding.netty.learning.netty.httpjson.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author wubai
 * @date 2018/9/22 19:38
 */
public class HttpJsonRequestDecoder extends AbstractHttpJsonDecoder<FullHttpRequest> {

    public HttpJsonRequestDecoder(Class<?> clazz) {
        super(clazz, false);
    }
    public HttpJsonRequestDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest, List<Object> list) throws Exception {
       if(!fullHttpRequest.getDecoderResult().isSuccess()) {
           sendError(channelHandlerContext, fullHttpRequest, HttpResponseStatus.BAD_REQUEST);
           return;
       }
       HttpJsonRequest request = new HttpJsonRequest(fullHttpRequest, decode0(channelHandlerContext, fullHttpRequest.content()));
       list.add(request);
    }

    private void sendError(ChannelHandlerContext channelHandlerContext,FullHttpRequest fullHttpRequest, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().add(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
