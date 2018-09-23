package com.weiquding.netty.learning.netty.protocol.client;

import com.weiquding.netty.learning.netty.protocol.domain.NettyContant;
import com.weiquding.netty.learning.netty.protocol.handler.HeartBeatReqHandler;
import com.weiquding.netty.learning.netty.protocol.handler.LoginAuthReqHandler;
import com.weiquding.netty.learning.netty.protocol.handler.NettyMessageDecoder;
import com.weiquding.netty.learning.netty.protocol.handler.NettyMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wubai
 * @date 2018/9/24 0:01
 */
public class NettyClient {

    private ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);

    public void connect(int port, String host) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 基于长度的解码器，再进行NettyMessage定制解码
                            pipeline.addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                            pipeline.addLast("nettyMessageEncoder", new NettyMessageEncoder());
                            pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                            pipeline.addLast("loginAuthReqHandler",new LoginAuthReqHandler());
                            pipeline.addLast("heartBeatHandler", new HeartBeatReqHandler());
                        }
                    });
            // 绑定本地端口
            ChannelFuture channelFuture = b.connect(new InetSocketAddress(host, port), new InetSocketAddress(NettyContant.LOCAL_IP, NettyContant.LOCAL_PORT)).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        try{
                            connect(NettyContant.PORT, NettyContant.REMOTE_IP);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyClient().connect(NettyContant.PORT, NettyContant.REMOTE_IP);
    }
}
