package org.netty.client;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.netty.message.RpcRequest;

import java.lang.reflect.Method;

public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("客户端接收:" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        try {
            RpcRequest request= new RpcRequest();
            request.setInterfaceName("org.netty.service.HelloService");
            request.setMethodName("hello");
            request.setParameterTypes(new Class[]{String.class});
            request.setReturnType(String.class);
            request.setParameters(new Object[]{"张三！"});
            ctx.writeAndFlush(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
