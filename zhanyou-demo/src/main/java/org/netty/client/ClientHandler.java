package org.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.netty.message.Message;

public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("客户端接收:" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Message message= new Message();
        message.setId("1");
        ctx.writeAndFlush(message);
    }
}
