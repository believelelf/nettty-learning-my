package com.weiquding.netty.learning.netty.pack.solution.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author wubai
 * @date 2018/9/15 17:38
 */
public class RightNettyTimeServer {

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new RightNettyTimeServer().bind(port);
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
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 依次偏历ByteBuf中可读的字节，判断看是否有“\n”或“\r\n”;如果有，就以此位置为结束位置。
                            // 从可读索引结束位置区间的字节，就组成了一行。1024为最大读取长度。
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new RightNettyTimeServerHandler());
                        }
                    });
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
}

/*
    The time server receive order :QUERY TIME ORDER ; the counter is :1
        The time server receive order :QUERY TIME ORDER ; the counter is :2
        The time server receive order :QUERY TIME ORDER ; the counter is :3
        The time server receive order :QUERY TIME ORDER ; the counter is :4
        The time server receive order :QUERY TIME ORDER ; the counter is :5
        The time server receive order :QUERY TIME ORDER ; the counter is :6
        The time server receive order :QUERY TIME ORDER ; the counter is :7
        The time server receive order :QUERY TIME ORDER ; the counter is :8
        The time server receive order :QUERY TIME ORDER ; the counter is :9
        The time server receive order :QUERY TIME ORDER ; the counter is :10
        The time server receive order :QUERY TIME ORDER ; the counter is :11
        The time server receive order :QUERY TIME ORDER ; the counter is :12
        The time server receive order :QUERY TIME ORDER ; the counter is :13
        The time server receive order :QUERY TIME ORDER ; the counter is :14
        The time server receive order :QUERY TIME ORDER ; the counter is :15
        The time server receive order :QUERY TIME ORDER ; the counter is :16
        The time server receive order :QUERY TIME ORDER ; the counter is :17
        The time server receive order :QUERY TIME ORDER ; the counter is :18
        The time server receive order :QUERY TIME ORDER ; the counter is :19
        The time server receive order :QUERY TIME ORDER ; the counter is :20
        The time server receive order :QUERY TIME ORDER ; the counter is :21
        The time server receive order :QUERY TIME ORDER ; the counter is :22
        The time server receive order :QUERY TIME ORDER ; the counter is :23
        The time server receive order :QUERY TIME ORDER ; the counter is :24
        The time server receive order :QUERY TIME ORDER ; the counter is :25
        The time server receive order :QUERY TIME ORDER ; the counter is :26
        The time server receive order :QUERY TIME ORDER ; the counter is :27
        The time server receive order :QUERY TIME ORDER ; the counter is :28
        The time server receive order :QUERY TIME ORDER ; the counter is :29
        The time server receive order :QUERY TIME ORDER ; the counter is :30
        The time server receive order :QUERY TIME ORDER ; the counter is :31
        The time server receive order :QUERY TIME ORDER ; the counter is :32
        The time server receive order :QUERY TIME ORDER ; the counter is :33
        The time server receive order :QUERY TIME ORDER ; the counter is :34
        The time server receive order :QUERY TIME ORDER ; the counter is :35
        The time server receive order :QUERY TIME ORDER ; the counter is :36
        The time server receive order :QUERY TIME ORDER ; the counter is :37
        The time server receive order :QUERY TIME ORDER ; the counter is :38
        The time server receive order :QUERY TIME ORDER ; the counter is :39
        The time server receive order :QUERY TIME ORDER ; the counter is :40
        The time server receive order :QUERY TIME ORDER ; the counter is :41
        The time server receive order :QUERY TIME ORDER ; the counter is :42
        The time server receive order :QUERY TIME ORDER ; the counter is :43
        The time server receive order :QUERY TIME ORDER ; the counter is :44
        The time server receive order :QUERY TIME ORDER ; the counter is :45
        The time server receive order :QUERY TIME ORDER ; the counter is :46
        The time server receive order :QUERY TIME ORDER ; the counter is :47
        The time server receive order :QUERY TIME ORDER ; the counter is :48
        The time server receive order :QUERY TIME ORDER ; the counter is :49
        The time server receive order :QUERY TIME ORDER ; the counter is :50
        The time server receive order :QUERY TIME ORDER ; the counter is :51
        The time server receive order :QUERY TIME ORDER ; the counter is :52
        The time server receive order :QUERY TIME ORDER ; the counter is :53
        The time server receive order :QUERY TIME ORDER ; the counter is :54
        The time server receive order :QUERY TIME ORDER ; the counter is :55
        The time server receive order :QUERY TIME ORDER ; the counter is :56
        The time server receive order :QUERY TIME ORDER ; the counter is :57
        The time server receive order :QUERY TIME ORDER ; the counter is :58
        The time server receive order :QUERY TIME ORDER ; the counter is :59
        The time server receive order :QUERY TIME ORDER ; the counter is :60
        The time server receive order :QUERY TIME ORDER ; the counter is :61
        The time server receive order :QUERY TIME ORDER ; the counter is :62
        The time server receive order :QUERY TIME ORDER ; the counter is :63
        The time server receive order :QUERY TIME ORDER ; the counter is :64
        The time server receive order :QUERY TIME ORDER ; the counter is :65
        The time server receive order :QUERY TIME ORDER ; the counter is :66
        The time server receive order :QUERY TIME ORDER ; the counter is :67
        The time server receive order :QUERY TIME ORDER ; the counter is :68
        The time server receive order :QUERY TIME ORDER ; the counter is :69
        The time server receive order :QUERY TIME ORDER ; the counter is :70
        The time server receive order :QUERY TIME ORDER ; the counter is :71
        The time server receive order :QUERY TIME ORDER ; the counter is :72
        The time server receive order :QUERY TIME ORDER ; the counter is :73
        The time server receive order :QUERY TIME ORDER ; the counter is :74
        The time server receive order :QUERY TIME ORDER ; the counter is :75
        The time server receive order :QUERY TIME ORDER ; the counter is :76
        The time server receive order :QUERY TIME ORDER ; the counter is :77
        The time server receive order :QUERY TIME ORDER ; the counter is :78
        The time server receive order :QUERY TIME ORDER ; the counter is :79
        The time server receive order :QUERY TIME ORDER ; the counter is :80
        The time server receive order :QUERY TIME ORDER ; the counter is :81
        The time server receive order :QUERY TIME ORDER ; the counter is :82
        The time server receive order :QUERY TIME ORDER ; the counter is :83
        The time server receive order :QUERY TIME ORDER ; the counter is :84
        The time server receive order :QUERY TIME ORDER ; the counter is :85
        The time server receive order :QUERY TIME ORDER ; the counter is :86
        The time server receive order :QUERY TIME ORDER ; the counter is :87
        The time server receive order :QUERY TIME ORDER ; the counter is :88
        The time server receive order :QUERY TIME ORDER ; the counter is :89
        The time server receive order :QUERY TIME ORDER ; the counter is :90
        The time server receive order :QUERY TIME ORDER ; the counter is :91
        The time server receive order :QUERY TIME ORDER ; the counter is :92
        The time server receive order :QUERY TIME ORDER ; the counter is :93
        The time server receive order :QUERY TIME ORDER ; the counter is :94
        The time server receive order :QUERY TIME ORDER ; the counter is :95
        The time server receive order :QUERY TIME ORDER ; the counter is :96
        The time server receive order :QUERY TIME ORDER ; the counter is :97
        The time server receive order :QUERY TIME ORDER ; the counter is :98
        The time server receive order :QUERY TIME ORDER ; the counter is :99
        The time server receive order :QUERY TIME ORDER ; the counter is :100*/
