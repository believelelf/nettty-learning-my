package com.weiquding.netty.learning.netty.delimiter.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author wubai
 * @date 2018/9/19 23:17
 */
public class EchoClient {

    public void connect (int port, String host) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = b.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
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
        new EchoClient().connect(port, "127.0.0.1");
    }
}
/*
    This is 1times receive server: [HI Welcome to Netty ]
        This is 2times receive server: [HI Welcome to Netty ]
        This is 3times receive server: [HI Welcome to Netty ]
        This is 4times receive server: [HI Welcome to Netty ]
        This is 5times receive server: [HI Welcome to Netty ]
        This is 6times receive server: [HI Welcome to Netty ]
        This is 7times receive server: [HI Welcome to Netty ]
        This is 8times receive server: [HI Welcome to Netty ]
        This is 9times receive server: [HI Welcome to Netty ]
        This is 10times receive server: [HI Welcome to Netty ]
        This is 11times receive server: [HI Welcome to Netty ]
        This is 12times receive server: [HI Welcome to Netty ]
        This is 13times receive server: [HI Welcome to Netty ]
        This is 14times receive server: [HI Welcome to Netty ]
        This is 15times receive server: [HI Welcome to Netty ]
        This is 16times receive server: [HI Welcome to Netty ]
        This is 17times receive server: [HI Welcome to Netty ]
        This is 18times receive server: [HI Welcome to Netty ]
        This is 19times receive server: [HI Welcome to Netty ]
        This is 20times receive server: [HI Welcome to Netty ]
        This is 21times receive server: [HI Welcome to Netty ]
        This is 22times receive server: [HI Welcome to Netty ]
        This is 23times receive server: [HI Welcome to Netty ]
        This is 24times receive server: [HI Welcome to Netty ]
        This is 25times receive server: [HI Welcome to Netty ]
        This is 26times receive server: [HI Welcome to Netty ]
        This is 27times receive server: [HI Welcome to Netty ]
        This is 28times receive server: [HI Welcome to Netty ]
        This is 29times receive server: [HI Welcome to Netty ]
        This is 30times receive server: [HI Welcome to Netty ]
        This is 31times receive server: [HI Welcome to Netty ]
        This is 32times receive server: [HI Welcome to Netty ]
        This is 33times receive server: [HI Welcome to Netty ]
        This is 34times receive server: [HI Welcome to Netty ]
        This is 35times receive server: [HI Welcome to Netty ]
        This is 36times receive server: [HI Welcome to Netty ]
        This is 37times receive server: [HI Welcome to Netty ]
        This is 38times receive server: [HI Welcome to Netty ]
        This is 39times receive server: [HI Welcome to Netty ]
        This is 40times receive server: [HI Welcome to Netty ]
        This is 41times receive server: [HI Welcome to Netty ]
        This is 42times receive server: [HI Welcome to Netty ]
        This is 43times receive server: [HI Welcome to Netty ]
        This is 44times receive server: [HI Welcome to Netty ]
        This is 45times receive server: [HI Welcome to Netty ]
        This is 46times receive server: [HI Welcome to Netty ]
        This is 47times receive server: [HI Welcome to Netty ]
        This is 48times receive server: [HI Welcome to Netty ]
        This is 49times receive server: [HI Welcome to Netty ]
        This is 50times receive server: [HI Welcome to Netty ]
        This is 51times receive server: [HI Welcome to Netty ]
        This is 52times receive server: [HI Welcome to Netty ]
        This is 53times receive server: [HI Welcome to Netty ]
        This is 54times receive server: [HI Welcome to Netty ]
        This is 55times receive server: [HI Welcome to Netty ]
        This is 56times receive server: [HI Welcome to Netty ]
        This is 57times receive server: [HI Welcome to Netty ]
        This is 58times receive server: [HI Welcome to Netty ]
        This is 59times receive server: [HI Welcome to Netty ]
        This is 60times receive server: [HI Welcome to Netty ]
        This is 61times receive server: [HI Welcome to Netty ]
        This is 62times receive server: [HI Welcome to Netty ]
        This is 63times receive server: [HI Welcome to Netty ]
        This is 64times receive server: [HI Welcome to Netty ]
        This is 65times receive server: [HI Welcome to Netty ]
        This is 66times receive server: [HI Welcome to Netty ]
        This is 67times receive server: [HI Welcome to Netty ]
        This is 68times receive server: [HI Welcome to Netty ]
        This is 69times receive server: [HI Welcome to Netty ]
        This is 70times receive server: [HI Welcome to Netty ]
        This is 71times receive server: [HI Welcome to Netty ]
        This is 72times receive server: [HI Welcome to Netty ]
        This is 73times receive server: [HI Welcome to Netty ]
        This is 74times receive server: [HI Welcome to Netty ]
        This is 75times receive server: [HI Welcome to Netty ]
        This is 76times receive server: [HI Welcome to Netty ]
        This is 77times receive server: [HI Welcome to Netty ]
        This is 78times receive server: [HI Welcome to Netty ]
        This is 79times receive server: [HI Welcome to Netty ]
        This is 80times receive server: [HI Welcome to Netty ]
        This is 81times receive server: [HI Welcome to Netty ]
        This is 82times receive server: [HI Welcome to Netty ]
        This is 83times receive server: [HI Welcome to Netty ]
        This is 84times receive server: [HI Welcome to Netty ]
        This is 85times receive server: [HI Welcome to Netty ]
        This is 86times receive server: [HI Welcome to Netty ]
        This is 87times receive server: [HI Welcome to Netty ]
        This is 88times receive server: [HI Welcome to Netty ]
        This is 89times receive server: [HI Welcome to Netty ]
        This is 90times receive server: [HI Welcome to Netty ]
        This is 91times receive server: [HI Welcome to Netty ]
        This is 92times receive server: [HI Welcome to Netty ]
        This is 93times receive server: [HI Welcome to Netty ]
        This is 94times receive server: [HI Welcome to Netty ]
        This is 95times receive server: [HI Welcome to Netty ]
        This is 96times receive server: [HI Welcome to Netty ]
        This is 97times receive server: [HI Welcome to Netty ]
        This is 98times receive server: [HI Welcome to Netty ]
        This is 99times receive server: [HI Welcome to Netty ]
        This is 100times receive server: [HI Welcome to Netty ]*/
