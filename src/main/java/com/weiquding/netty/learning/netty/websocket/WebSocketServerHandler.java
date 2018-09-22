package com.weiquding.netty.learning.netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * @author wubai
 * @date 2018/9/22 22:55
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if(o instanceof FullHttpRequest){
            // 传统的http接入
            handleHttpRequest(channelHandlerContext, (FullHttpRequest)o);
        }else if(o instanceof WebSocketFrame){
            handleWebSocketFrame(channelHandlerContext, (WebSocketFrame)o);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleWebSocketFrame(ChannelHandlerContext channelHandlerContext, WebSocketFrame frame) {
        // 判断是否是关闭链路的指令
        if(frame instanceof CloseWebSocketFrame){
           handshaker.close(channelHandlerContext.channel(), ((CloseWebSocketFrame) frame).retain());
            return;
        }
        // 判断是否ping指令
        if(frame instanceof PingWebSocketFrame){
            channelHandlerContext.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        //仅支持文本消息
        if(!(frame instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException(String.format("%s frame types not support", frame.getClass().getName()));
        }
        String request = ((TextWebSocketFrame)frame).text();
        System.out.println(String.format("%s received %s", channelHandlerContext.channel(), request));

        channelHandlerContext.channel().write(new TextWebSocketFrame(request + ", welcome to netty world, now is: " + new Date(System.currentTimeMillis())));


    }

    private void handleHttpRequest(ChannelHandlerContext channelHandlerContext, FullHttpRequest request) {
        // 如果http解码失败，返回异常
        if(!request.getDecoderResult().isSuccess()
                || !("websocket".equalsIgnoreCase(request.headers().get("Upgrade")))){
            sendHttpResponse(channelHandlerContext, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // 构建握手响应返回，本机测试
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
       handshaker = factory.newHandshaker(request);
        if(handshaker == null){
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(channelHandlerContext.channel());
        }else{
            handshaker.handshake(channelHandlerContext.channel(), request);
        }

    }

    private void sendHttpResponse(ChannelHandlerContext channelHandlerContext, FullHttpRequest request, DefaultFullHttpResponse response) {
        // 返回应答给客户端
        if(response.getStatus().code() != 200){
            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            HttpHeaders.setContentLength(response, response.content().readableBytes());
        }
        // 如果是非keep-alive,关闭链接
        ChannelFuture channelFuture = channelHandlerContext.channel().writeAndFlush(response);
        if(!HttpHeaders.isKeepAlive(request) || response.getStatus().code() != 200){
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
