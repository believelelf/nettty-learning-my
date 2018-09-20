package com.weiquding.netty.learning.netty.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wubai
 * @date 2018/9/15 17:38
 */
public class NettyTimeServer {

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new NettyTimeServer().bind(port);
    }

    private void bind(int port) throws InterruptedException {
        // 线程组：用于服务端接受客户端连接
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        // 线程组：用于进行SocketChannel的网络读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // SocketBootstrap，启动netty服务端的辅助类
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 创建NioServerSocketChannel
            // 绑定ChildChannelHandler
            bootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            // 绑定监听端口，并等待绑定完成
            ChannelFuture f = bootstrap.bind(port).sync();
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            // 退出
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new NettyTimeServerHandler());
        }
    }
}
