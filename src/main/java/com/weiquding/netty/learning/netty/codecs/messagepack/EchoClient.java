package com.weiquding.netty.learning.netty.codecs.messagepack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author wubai
 * @date 2018/9/20 22:20
 */
public class EchoClient {

    private final String host;
    private final int port;
    private final int sendNumber;

    public EchoClient(String host, int port, int sendNumber){
        this.host = host;
        this.port = port;
        this.sendNumber = sendNumber;
    }

    public void run() throws Exception{
        // Configure the client
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                            socketChannel.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
                            socketChannel.pipeline().addLast(new LengthFieldPrepender(2));
                            socketChannel.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
                            socketChannel.pipeline().addLast(new EchoMessagePackClientHandler(sendNumber));
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1", 8080, 1000).run();
    }
}
