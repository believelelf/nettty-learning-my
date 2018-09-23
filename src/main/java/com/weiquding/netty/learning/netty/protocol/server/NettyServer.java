package com.weiquding.netty.learning.netty.protocol.server;

import com.weiquding.netty.learning.netty.protocol.domain.NettyContant;
import com.weiquding.netty.learning.netty.protocol.handler.HeartBeatRspHandler;
import com.weiquding.netty.learning.netty.protocol.handler.LoginAuthRspHandler;
import com.weiquding.netty.learning.netty.protocol.handler.NettyMessageDecoder;
import com.weiquding.netty.learning.netty.protocol.handler.NettyMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author wubai
 * @date 2018/9/24 0:18
 */
public class NettyServer {

    public void bind() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new NettyMessageDecoder(1024*1024, 4, 4));
                            pipeline.addLast(new NettyMessageEncoder());
                            pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                            pipeline.addLast("loginAuthRspHandler", new LoginAuthRspHandler());
                            pipeline.addLast("heartBeatHandler", new HeartBeatRspHandler());
                        }
                    });
            bootstrap.bind(NettyContant.REMOTE_IP, NettyContant.PORT).sync();
            System.out.println("Netty server start ok：" + NettyContant.REMOTE_IP + " ： " + NettyContant.PORT);
        }finally {
           // bossGroup.shutdownGracefully();
            // workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer().bind();
    }
}
