package com.weiquding.netty.learning.netty.codecs.marshalling.server;

import com.weiquding.netty.learning.netty.codecs.marshalling.domain.SubscribeReq;
import com.weiquding.netty.learning.netty.codecs.marshalling.domain.SubscribeRsp;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wubai
 * @date 2018/9/22 1:09
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        if("wu".equalsIgnoreCase(req.getUserName())){
            System.out.println("Service accept client subscribe req: [" + req.toString() +"]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeRsp resp(int subReqID) {
        SubscribeRsp builder = new SubscribeRsp();
        builder.setSubReqID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("Netty book order succeed, 3 days laster, sent to the designated address");
        return builder;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
