package com.weiquding.netty.learning.netty.httpjson.client;

import com.weiquding.netty.learning.netty.httpjson.domain.Order;
import com.weiquding.netty.learning.netty.httpjson.handler.HttpJsonRequestEncoder;
import com.weiquding.netty.learning.netty.httpjson.handler.HttpJsonResponseDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.net.InetSocketAddress;

/**
 * @author wubai
 * @date 2018/9/22 20:15
 */
public class HttpJsonClient {



    public static void main(String[] args) throws InterruptedException {
        new HttpJsonClient().connect(8080);
    }

    private void connect(int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("http-decoder", new HttpResponseDecoder());
                            socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            socketChannel.pipeline().addLast("json-decoder", new HttpJsonResponseDecoder(Order.class, true));

                            socketChannel.pipeline().addLast("http-encoder", new HttpRequestEncoder());
                            socketChannel.pipeline().addLast("json-encoder", new HttpJsonRequestEncoder());
                            socketChannel.pipeline().addLast("jsonClientHandler", new HttpJsonClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(port)).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
