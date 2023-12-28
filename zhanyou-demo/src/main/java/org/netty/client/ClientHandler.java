package org.netty.client;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.netty.message.RpcRequest;

public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("客户端接收:" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        RpcRequest request= new RpcRequest();
        request.setContent("请求内容！");
        ctx.writeAndFlush(request);
    }
}
