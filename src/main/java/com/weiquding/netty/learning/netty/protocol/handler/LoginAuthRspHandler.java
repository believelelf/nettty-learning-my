package com.weiquding.netty.learning.netty.protocol.handler;

import com.weiquding.netty.learning.netty.protocol.domain.Header;
import com.weiquding.netty.learning.netty.protocol.domain.MessageType;
import com.weiquding.netty.learning.netty.protocol.domain.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wubai
 * @date 2018/9/23 22:20
 */
public class LoginAuthRspHandler extends ChannelHandlerAdapter {

    // 节点检查
    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();

    private String[] whiteList = {"127.0.0.1", "192.168.1.3", "192.168.174.1", "192.168.137.1"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = new NettyMessage();

        //如果是握手消息，进行处理，其它消息透传
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.value()){
            // ip
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginRsp = null;
            if(nodeCheck.containsKey(nodeIndex)){
                loginRsp = buildResponse((byte)-1);
            }else{
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOK = false;
                for(String wip : whiteList){
                    if(wip.equals(ip)){
                        isOK = true;
                        break;
                    }
                }
                loginRsp = isOK ? buildResponse((byte)0) : buildResponse((byte)-1);
                if(isOK){
                    nodeCheck.put(nodeIndex, true);
                }
                System.out.println("The login response is : " + loginRsp + " body [" + loginRsp.getBody() + "]");
                ctx.writeAndFlush(loginRsp);
            }
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildResponse(byte result) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RSP.value());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }
}
