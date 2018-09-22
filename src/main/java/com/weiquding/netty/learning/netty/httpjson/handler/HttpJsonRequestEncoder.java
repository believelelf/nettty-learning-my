package com.weiquding.netty.learning.netty.httpjson.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.HttpHeaders.Names;

import java.net.InetAddress;
import java.util.List;

/**
 * @author wubai
 * @date 2018/9/22 19:00
 */
public class HttpJsonRequestEncoder extends AbstractHttpJsonEncoder<HttpJsonRequest> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HttpJsonRequest msg, List list) throws Exception {

        ByteBuf byteBuf =encode0(channelHandlerContext, msg);
        FullHttpRequest request = msg.getRequest();
        if(request == null){
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do", byteBuf);
            HttpHeaders headers = request.headers();
            headers.set(Names.HOST, InetAddress.getLocalHost().getHostAddress());
            headers.set(Names.CONNECTION, HttpHeaders.Values.CLOSE);
            headers.set(Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP.toString() + ","+ HttpHeaders.Values.DEFLATE.toString());
            headers.set(Names.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(Names.ACCEPT_LANGUAGE, "zh");
            headers.set(Names.USER_AGENT, "netty client");
            headers.set(Names.ACCEPT, "application/json;text/plain;q=0.9,*/*;q=0.8");
        }
        HttpHeaders.setContentLength(request, byteBuf.readableBytes());
        list.add(byteBuf);


    }
}
