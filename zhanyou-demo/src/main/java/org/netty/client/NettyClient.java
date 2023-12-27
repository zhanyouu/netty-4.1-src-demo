package org.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.netty.protocal.codec.MessageCodec;

import java.net.InetSocketAddress;

public class NettyClient {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 9999;


    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = NettyClient.bootstrap();
        /*连接到远程节点，阻塞直到连接完成*/
        ChannelFuture f = bootstrap.connect(new InetSocketAddress(HOST,PORT)).sync();
        /*阻塞程序，直到Channel发生了关闭*/
        f.channel().closeFuture().sync();
    }

    public static Bootstrap bootstrap() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,12,4,0,0));
                        ch.pipeline().addLast(new MessageCodec());
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        return bootstrap;
    }
}
