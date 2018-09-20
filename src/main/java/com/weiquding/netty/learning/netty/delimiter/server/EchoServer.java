package com.weiquding.netty.learning.netty.delimiter.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wubai
 * @date 2018/9/19 23:05
 */
public class EchoServer {

    public void bind(int port) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new EchoServerHandler());

                        }
                    });

            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new EchoServer().bind(port);
    }

}
/*
    This is 1times receive client:[HI Welcome to Netty ]
        This is 2times receive client:[HI Welcome to Netty ]
        This is 3times receive client:[HI Welcome to Netty ]
        This is 4times receive client:[HI Welcome to Netty ]
        This is 5times receive client:[HI Welcome to Netty ]
        This is 6times receive client:[HI Welcome to Netty ]
        This is 7times receive client:[HI Welcome to Netty ]
        This is 8times receive client:[HI Welcome to Netty ]
        This is 9times receive client:[HI Welcome to Netty ]
        This is 10times receive client:[HI Welcome to Netty ]
        This is 11times receive client:[HI Welcome to Netty ]
        This is 12times receive client:[HI Welcome to Netty ]
        This is 13times receive client:[HI Welcome to Netty ]
        This is 14times receive client:[HI Welcome to Netty ]
        This is 15times receive client:[HI Welcome to Netty ]
        This is 16times receive client:[HI Welcome to Netty ]
        This is 17times receive client:[HI Welcome to Netty ]
        This is 18times receive client:[HI Welcome to Netty ]
        This is 19times receive client:[HI Welcome to Netty ]
        This is 20times receive client:[HI Welcome to Netty ]
        This is 21times receive client:[HI Welcome to Netty ]
        This is 22times receive client:[HI Welcome to Netty ]
        This is 23times receive client:[HI Welcome to Netty ]
        This is 24times receive client:[HI Welcome to Netty ]
        This is 25times receive client:[HI Welcome to Netty ]
        This is 26times receive client:[HI Welcome to Netty ]
        This is 27times receive client:[HI Welcome to Netty ]
        This is 28times receive client:[HI Welcome to Netty ]
        This is 29times receive client:[HI Welcome to Netty ]
        This is 30times receive client:[HI Welcome to Netty ]
        This is 31times receive client:[HI Welcome to Netty ]
        This is 32times receive client:[HI Welcome to Netty ]
        This is 33times receive client:[HI Welcome to Netty ]
        This is 34times receive client:[HI Welcome to Netty ]
        This is 35times receive client:[HI Welcome to Netty ]
        This is 36times receive client:[HI Welcome to Netty ]
        This is 37times receive client:[HI Welcome to Netty ]
        This is 38times receive client:[HI Welcome to Netty ]
        This is 39times receive client:[HI Welcome to Netty ]
        This is 40times receive client:[HI Welcome to Netty ]
        This is 41times receive client:[HI Welcome to Netty ]
        This is 42times receive client:[HI Welcome to Netty ]
        This is 43times receive client:[HI Welcome to Netty ]
        This is 44times receive client:[HI Welcome to Netty ]
        This is 45times receive client:[HI Welcome to Netty ]
        This is 46times receive client:[HI Welcome to Netty ]
        This is 47times receive client:[HI Welcome to Netty ]
        This is 48times receive client:[HI Welcome to Netty ]
        This is 49times receive client:[HI Welcome to Netty ]
        This is 50times receive client:[HI Welcome to Netty ]
        This is 51times receive client:[HI Welcome to Netty ]
        This is 52times receive client:[HI Welcome to Netty ]
        This is 53times receive client:[HI Welcome to Netty ]
        This is 54times receive client:[HI Welcome to Netty ]
        This is 55times receive client:[HI Welcome to Netty ]
        This is 56times receive client:[HI Welcome to Netty ]
        This is 57times receive client:[HI Welcome to Netty ]
        This is 58times receive client:[HI Welcome to Netty ]
        This is 59times receive client:[HI Welcome to Netty ]
        This is 60times receive client:[HI Welcome to Netty ]
        This is 61times receive client:[HI Welcome to Netty ]
        This is 62times receive client:[HI Welcome to Netty ]
        This is 63times receive client:[HI Welcome to Netty ]
        This is 64times receive client:[HI Welcome to Netty ]
        This is 65times receive client:[HI Welcome to Netty ]
        This is 66times receive client:[HI Welcome to Netty ]
        This is 67times receive client:[HI Welcome to Netty ]
        This is 68times receive client:[HI Welcome to Netty ]
        This is 69times receive client:[HI Welcome to Netty ]
        This is 70times receive client:[HI Welcome to Netty ]
        This is 71times receive client:[HI Welcome to Netty ]
        This is 72times receive client:[HI Welcome to Netty ]
        This is 73times receive client:[HI Welcome to Netty ]
        This is 74times receive client:[HI Welcome to Netty ]
        This is 75times receive client:[HI Welcome to Netty ]
        This is 76times receive client:[HI Welcome to Netty ]
        This is 77times receive client:[HI Welcome to Netty ]
        This is 78times receive client:[HI Welcome to Netty ]
        This is 79times receive client:[HI Welcome to Netty ]
        This is 80times receive client:[HI Welcome to Netty ]
        This is 81times receive client:[HI Welcome to Netty ]
        This is 82times receive client:[HI Welcome to Netty ]
        This is 83times receive client:[HI Welcome to Netty ]
        This is 84times receive client:[HI Welcome to Netty ]
        This is 85times receive client:[HI Welcome to Netty ]
        This is 86times receive client:[HI Welcome to Netty ]
        This is 87times receive client:[HI Welcome to Netty ]
        This is 88times receive client:[HI Welcome to Netty ]
        This is 89times receive client:[HI Welcome to Netty ]
        This is 90times receive client:[HI Welcome to Netty ]
        This is 91times receive client:[HI Welcome to Netty ]
        This is 92times receive client:[HI Welcome to Netty ]
        This is 93times receive client:[HI Welcome to Netty ]
        This is 94times receive client:[HI Welcome to Netty ]
        This is 95times receive client:[HI Welcome to Netty ]
        This is 96times receive client:[HI Welcome to Netty ]
        This is 97times receive client:[HI Welcome to Netty ]
        This is 98times receive client:[HI Welcome to Netty ]
        This is 99times receive client:[HI Welcome to Netty ]
        This is 100times receive client:[HI Welcome to Netty ]*/
