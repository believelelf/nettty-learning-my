package com.weiquding.netty.learning.netty.codecs.messagepack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wubai
 * @date 2018/9/20 22:55
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public void run() throws Exception{
        // Configure the server
        EventLoopGroup acceptGroup = new NioEventLoopGroup();
        EventLoopGroup ioGroup = new NioEventLoopGroup();
        try {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(acceptGroup, ioGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                            socketChannel.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
                            socketChannel.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                            socketChannel.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
                            socketChannel.pipeline().addLast(new EchoMessagePackServerHandler());
                        }
                    });

            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            acceptGroup.shutdownGracefully();
            ioGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception {
        new EchoServer(8080).run();
    }
}
