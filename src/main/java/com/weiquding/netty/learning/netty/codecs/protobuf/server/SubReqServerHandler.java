package com.weiquding.netty.learning.netty.codecs.protobuf.server;

import com.weiquding.netty.learning.netty.codecs.protobuf.domain.SubscribeReqProto;
import com.weiquding.netty.learning.netty.codecs.protobuf.domain.SubscribeRspProto;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wubai
 * @date 2018/9/22 1:09
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if("wu".equalsIgnoreCase(req.getUserName())){
            System.out.println("Service accept client subscribe req: [" + req.toString() +"]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeRspProto.SubscribeResp resp(int subReqID) {
        SubscribeRspProto.SubscribeResp.Builder builder = SubscribeRspProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setResCode(0);
        builder.setDesc("Netty book order succeed, 3 days laster, sent to the designated address");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
