package com.weiquding.netty.learning.netty.codecs.messagepack;

import com.weiquding.netty.learning.netty.codecs.serializable.UserInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wubai
 * @date 2018/9/20 22:42
 */
public class EchoMessagePackClientHandler extends ChannelHandlerAdapter {

    private int sendNumber;

    public EchoMessagePackClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        UserInfo[] infos = userInfo();
        for (UserInfo userInfo : infos){
            ctx.write(userInfo);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client receive the msgpack message:" + msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private UserInfo[] userInfo() {
        UserInfo[] userInfos = new UserInfo[sendNumber];
        UserInfo userInfo = null;
        for(int i = 0; i < sendNumber; i++){
            userInfo = new UserInfo();
            userInfo.setAge(i);
            userInfo.setName("ABCDEFG--->" + i);
            userInfos[i] = userInfo;
        }
        return userInfos;
    }



}
