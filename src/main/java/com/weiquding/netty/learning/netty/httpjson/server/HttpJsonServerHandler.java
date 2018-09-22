package com.weiquding.netty.learning.netty.httpjson.server;

import com.weiquding.netty.learning.netty.httpjson.domain.Order;
import com.weiquding.netty.learning.netty.httpjson.handler.HttpJsonRequest;
import com.weiquding.netty.learning.netty.httpjson.handler.HttpJsonResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;

/**
 * @author wubai
 * @date 2018/9/22 20:51
 */
public class HttpJsonServerHandler extends SimpleChannelInboundHandler<HttpJsonRequest> {


    @Override
    protected void messageReceived(final ChannelHandlerContext channelHandlerContext, HttpJsonRequest msg) throws Exception {
        HttpRequest request = msg.getRequest();
        Order order = (Order) msg.getBody();
        System.out.println("Http sever receive request:" + order);
        dobusiness(order);
        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(new HttpJsonResponse(null, order));
        if(!isKeepAlive(request)){
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    channelHandlerContext.close();
                }
            });
        }
    }

    private void dobusiness(Order order) {
        order.getCustomer().setFirstName("di");
        order.getCustomer().setLastName("yun");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(ctx.channel().isActive()){
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }
}
