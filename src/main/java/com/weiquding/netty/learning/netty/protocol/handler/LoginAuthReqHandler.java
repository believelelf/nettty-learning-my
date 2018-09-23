package com.weiquding.netty.learning.netty.protocol.handler;

import com.weiquding.netty.learning.netty.protocol.domain.Header;
import com.weiquding.netty.learning.netty.protocol.domain.MessageType;
import com.weiquding.netty.learning.netty.protocol.domain.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wubai
 * @date 2018/9/23 22:30
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }

    private NettyMessage buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 如果是握手应答消息，需要判断是否认证成功
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RSP.value()){
            byte loginResult = (byte) message.getBody();
            if(loginResult != 0){
                ctx.close();
            }else{
                System.out.println("Login is ok: " + message);
                ctx.fireChannelRead(msg);
            }
        }else {
            // 不是应答消息，透传给后面的ChannelHandler进行处理
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
