package com.weiquding.netty.learning.netty.httpjson.client;

import com.weiquding.netty.learning.netty.httpjson.domain.OrderFactory;
import com.weiquding.netty.learning.netty.httpjson.handler.HttpJsonRequest;
import com.weiquding.netty.learning.netty.httpjson.handler.HttpJsonResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wubai
 * @date 2018/9/22 20:24
 */
public class HttpJsonClientHandler extends SimpleChannelInboundHandler<HttpJsonResponse> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        HttpJsonRequest request = new HttpJsonRequest(null, OrderFactory.create(11110));
        ctx.writeAndFlush(request);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, HttpJsonResponse msg) throws Exception {
        System.out.println("The client receive response of http header is : " + msg.getHttpResponse().headers().names());
        System.out.println("The client receive response of http body is :" + msg.getResult());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
