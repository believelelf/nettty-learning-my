package com.weiquding.netty.learning.netty.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author wubai
 * @date 2018/9/15 18:11
 */
public class NettyTimeClient {

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new NettyTimeClient().connect("127.0.0.1", port);
    }

    private void connect(String host, int port) throws InterruptedException {
        // 创建客户端处理I/O读写的NioEventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建客户端辅助类Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            // 创建NioSocketChannel
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 添加Channel,实现initChannel方法，
                            socketChannel.pipeline().addLast(new NettyTimeClientHandler());
                        }
                    });

            // 调用connect方法发起异步连接，调用同步方法等待同步成功
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            //等待客户链路关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            //退出
            group.shutdownGracefully();
        }

    }

}
