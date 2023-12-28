package org.netty.server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.netty.message.RpcRequest;
import org.netty.message.RpcResponse;

import java.util.Optional;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("CLIENT: " + getRemoteAddress(ctx) + " 接入连接");
    }

    private static String getRemoteAddress(ChannelHandlerContext ctx) {
        return Optional.ofNullable(ctx.channel().remoteAddress().toString()).orElse("").replace("/", "");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("服务端接收:" + msg);
        RpcResponse response = new RpcResponse();
        response.setSuccess(true);
        response.setValue("收到到了您的消息！");
        ctx.writeAndFlush(response);
    }
}
