package com.weiquding.netty.learning.netty.pack.solution.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author wubai
 * @date 2018/9/15 18:11
 */
public class RightNettyTimeClient {

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new RightNettyTimeClient().connect("127.0.0.1", port);
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
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new RightNettyTimeClientHandler());
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

/*
    Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :1
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :2
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :3
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :4
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :5
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :6
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :7
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :8
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :9
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :10
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :11
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :12
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :13
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :14
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :15
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :16
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :17
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :18
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :19
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :20
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :21
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :22
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :23
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :24
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :25
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :26
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :27
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :28
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :29
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :30
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :31
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :32
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :33
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :34
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :35
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :36
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :37
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :38
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :39
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :40
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :41
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :42
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :43
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :44
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :45
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :46
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :47
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :48
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :49
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :50
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :51
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :52
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :53
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :54
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :55
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :56
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :57
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :58
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :59
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :60
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :61
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :62
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :63
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :64
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :65
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :66
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :67
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :68
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :69
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :70
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :71
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :72
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :73
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :74
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :75
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :76
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :77
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :78
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :79
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :80
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :81
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :82
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :83
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :84
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :85
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :86
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :87
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :88
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :89
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :90
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :91
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :92
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :93
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :94
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :95
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :96
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :97
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :98
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :99
        Now is : Wed Sep 19 23:38:44 CST 2018 ; the counter is :100
*/

