package com.weiquding.netty.learning.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author wubai
 * @date 2018/9/22 22:42
 */
public class WebSocketServer {

    public static void main(String[] args) throws InterruptedException {
        new WebSocketServer().bind(8080);
    }

    private void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // HttpServerCodec 将请求和应答消息编码或解码为HTTP消息
                            pipeline.addLast(new HttpServerCodec());
                            // 将Http消的多部分组合成一个完整的http消息
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            // 向客户端发送html5文件，用于支持浏览器和服务端进行websocket通信
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new WebSocketServerHandler());
                        }
                    });
            Channel channel = bootstrap.bind(port).sync().channel();
            System.out.println("Web Socket server started at port: " + port);
            System.out.println("Open your browser and navigate http://localhost:" + port + "/");
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
/*
 WebSocket Test
 CONNECTED

 SENT: WebSocket rocks

 RESPONSE: WebSocket rocks, welcome to netty world, now is: Sat Sep 22 23:31:22 CST 2018

 DISCONNECTED
 */