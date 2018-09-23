package com.weiquding.netty.learning.netty.protocol.handler;

import com.weiquding.netty.learning.netty.protocol.domain.Header;
import com.weiquding.netty.learning.netty.protocol.domain.MessageType;
import com.weiquding.netty.learning.netty.protocol.domain.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wubai
 * @date 2018/9/23 23:07
 */
public class HeartBeatRspHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 返回心中应答消息
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()){
            System.out.println("Receive client heart beat message: ---> " + message);
            NettyMessage heartBeat = buildHeartBeat();
            System.out.println("Send heart beat response message to client: ---> " + heartBeat);
        }else{
            ctx.fireChannelRead(msg);
        }

    }

    private NettyMessage buildHeartBeat() {
        NettyMessage message =  new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RSP.value());
        message.setHeader(header);
        return message;
    }
}
